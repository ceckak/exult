#include <Configuration.h>
#include <SDL_system.h>
#include <TouchUI_Android.h>

#include <iostream>

extern "C" JNIEXPORT void JNICALL
		Java_info_exult_ExultActivity_setVirtualJoystick(
				JNIEnv* env, jobject /* this */, jfloat x, jfloat y) {
	Sint32 axisX   = x * SDL_JOYSTICK_AXIS_MAX;
	Sint32 axisY   = y * SDL_JOYSTICK_AXIS_MAX;
	auto   touchUI = TouchUI_Android::getInstance();
	touchUI->setVirtualJoystick(
			std::clamp(axisX, SDL_JOYSTICK_AXIS_MIN, SDL_JOYSTICK_AXIS_MAX),
			std::clamp(axisY, SDL_JOYSTICK_AXIS_MIN, SDL_JOYSTICK_AXIS_MAX));
}

extern "C" JNIEXPORT void JNICALL
		Java_info_exult_ExultActivity_setName(
				JNIEnv* env, jobject /* this */, jstring javaName) {
	const char* name = env->GetStringUTFChars(javaName, nullptr);
	TouchUI::onTextInput(name);
	env->ReleaseStringUTFChars(javaName, name);
}

TouchUI_Android* TouchUI_Android::m_instance = nullptr;

TouchUI_Android* TouchUI_Android::getInstance() {
	return TouchUI_Android::m_instance;
}

void TouchUI_Android::setVirtualJoystick(Sint16 x, Sint16 y) {
	auto joystick = SDL_GameControllerGetJoystick(m_gameController);
	SDL_JoystickSetVirtualAxis(joystick, SDL_CONTROLLER_AXIS_LEFTX, x);
	SDL_JoystickSetVirtualAxis(joystick, SDL_CONTROLLER_AXIS_LEFTY, y);
}

TouchUI_Android::TouchUI_Android() {
	m_instance   = this;
	m_jniEnv     = static_cast<JNIEnv*>(SDL_AndroidGetJNIEnv());
	auto jclass  = m_jniEnv->FindClass("info/exult/ExultActivity");
	auto jmethod = m_jniEnv->GetStaticMethodID(
			jclass, "instance", "()Linfo/exult/ExultActivity;");
	m_exultActivityObject = m_jniEnv->CallStaticObjectMethod(jclass, jmethod);
	m_showGameControlsMethod = m_jniEnv->GetMethodID(
			jclass, "showGameControls", "(Ljava/lang/String;)V");
	m_hideGameControlsMethod
			= m_jniEnv->GetMethodID(jclass, "hideGameControls", "()V");
	m_promptForNameMethod = m_jniEnv->GetMethodID(
			jclass, "promptForName", "(Ljava/lang/String;)V");

	int joystickDeviceIndex = SDL_JoystickAttachVirtual(
			SDL_JOYSTICK_TYPE_GAMECONTROLLER, SDL_CONTROLLER_AXIS_MAX,
			SDL_CONTROLLER_BUTTON_MAX, 0);
	if (joystickDeviceIndex < 0) {
		std::cerr << "SDL_JoystickAttachVirtual failed: " << SDL_GetError()
				  << std::endl;
	} else {
		m_gameController = SDL_GameControllerOpen(joystickDeviceIndex);
		if (!m_gameController) {
			std::cerr << "SDL_GameControllerOpen failed for virtual joystick: "
					  << SDL_GetError() << std::endl;
			SDL_JoystickDetachVirtual(joystickDeviceIndex);
		}
	}
}

TouchUI_Android::~TouchUI_Android() {
	if (m_gameController) {
		if (m_gameController) {
			const SDL_JoystickID joystickId = SDL_JoystickInstanceID(
					SDL_GameControllerGetJoystick(m_gameController));
			SDL_GameControllerClose(m_gameController);
			for (int i = 0, n = SDL_NumJoysticks(); i < n; ++i) {
				if (SDL_JoystickGetDeviceInstanceID(i) == joystickId) {
					SDL_JoystickDetachVirtual(i);
					break;
				}
			}
		}
	}
}

void TouchUI_Android::promptForName(const char* name) {
	auto javaName = m_jniEnv->NewStringUTF(name);
	m_jniEnv->CallVoidMethod(
			m_exultActivityObject, m_promptForNameMethod, javaName);
}

void TouchUI_Android::showGameControls() {
	std::string dpadLocation;
	config->value("config/touch/dpad_location", dpadLocation, "right");
	auto javaDpadLocation = m_jniEnv->NewStringUTF(dpadLocation.c_str());
	m_jniEnv->CallVoidMethod(
			m_exultActivityObject, m_showGameControlsMethod, javaDpadLocation);
}

void TouchUI_Android::hideGameControls() {
	m_jniEnv->CallVoidMethod(m_exultActivityObject, m_hideGameControlsMethod);
}

void TouchUI_Android::showButtonControls() {
}

void TouchUI_Android::hideButtonControls() {
}

void TouchUI_Android::onDpadLocationChanged() {
}

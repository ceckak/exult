#ifndef TOUCHUI_ANDROID_H
#define TOUCHUI_ANDROID_H

#include <jni.h>
#include <touchui.h>

class TouchUI_Android: public TouchUI {
public:
	TouchUI_Android();
	~TouchUI_Android() final;
	void promptForName(const char* name) final;
	void showGameControls() final;
	void hideGameControls() final;
	void showButtonControls() final;
	void hideButtonControls() final;
	void onDpadLocationChanged() final;

	static TouchUI_Android* getInstance();
	void                    setVirtualJoystick(Sint16 x, Sint16 y);

private:
	static TouchUI_Android* m_instance;
	JNIEnv*                 m_jniEnv;
	jobject                 m_exultActivityObject;
	jmethodID               m_showGameControlsMethod;
	jmethodID               m_hideGameControlsMethod;
	SDL_GameController*     m_gameController;
	jmethodID               m_promptForNameMethod;
};

#endif

#include "android_log_streambuf.h"

#include <android/log.h>

#include <SDL2/SDL_system.h>

#include <iostream>

AndroidLog_streambuf::AndroidLog_streambuf(int priority, const char* tag)
		: priority{priority}, tag{tag} {
	m_jniEnv     = static_cast<JNIEnv*>(SDL_AndroidGetJNIEnv());
	auto jclass  = m_jniEnv->FindClass("info/exult/ExultActivity");
	auto jmethod = m_jniEnv->GetStaticMethodID(
			jclass, "instance", "()Linfo/exult/ExultActivity;");
	m_exultActivityObject = m_jniEnv->CallStaticObjectMethod(jclass, jmethod);
	m_writeToConsoleMethod = m_jniEnv->GetMethodID(
            jclass, "writeToConsole", "(Ljava/lang/String;)V");
}

std::streambuf::int_type AndroidLog_streambuf::overflow(int_type ch) {
	if ('\n' == ch || traits_type::eof() == ch) {
		__android_log_write(priority, tag.c_str(), line_buf.c_str());

                jstring jline_buf = m_jniEnv->NewStringUTF(line_buf.c_str());
                m_jniEnv->CallVoidMethod(m_exultActivityObject, m_writeToConsoleMethod, jline_buf);

		line_buf = "";
	} else {
		line_buf += ch;
	}
	return ch;
}

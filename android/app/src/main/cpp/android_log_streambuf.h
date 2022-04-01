#include <jni.h>

#include <streambuf>
#include <string>

class AndroidLog_streambuf : public std::streambuf {
public:
	AndroidLog_streambuf(int priority, const char* tag);

protected:
	int_type overflow(int_type ch) override;

private:
	const int         priority;
	const std::string tag;
	std::string       line_buf;

	JNIEnv*                 m_jniEnv;
	jobject                 m_exultActivityObject;
	jmethodID               m_writeToConsoleMethod;
};

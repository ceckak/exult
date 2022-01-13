#include "android_log_streambuf.h"

#include <android/log.h>

#include <iostream>

AndroidLog_streambuf::AndroidLog_streambuf(int priority, const char* tag)
		: priority{priority}, tag{tag} {}

std::streambuf::int_type AndroidLog_streambuf::overflow(int_type ch) {
	if ('\n' == ch || traits_type::eof() == ch) {
		__android_log_write(priority, tag.c_str(), line_buf.c_str());
		line_buf = "";
	} else {
		line_buf += ch;
	}
	return ch;
}

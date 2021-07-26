#include "android_log_streambuf.h"

#include <SDL2/SDL_main.h>
#include <android/log.h>
#include <jni.h>

#include <iostream>

extern "C" JNIEXPORT int JNICALL ExultAndroid_main(int argc, char* argv[]) {
	AndroidLog_streambuf exult_cout(ANDROID_LOG_DEBUG, "exult-android-wrapper");
	AndroidLog_streambuf exult_cerr(ANDROID_LOG_ERROR, "exult-android-wrapper");
	auto                 ndk_cout = std::cout.rdbuf(&exult_cout);
	auto                 ndk_cerr = std::cerr.rdbuf(&exult_cerr);

        auto result = SDL_main(argc, argv);

	std::cout.rdbuf(ndk_cout);
	std::cerr.rdbuf(ndk_cerr);

	return 0;
}

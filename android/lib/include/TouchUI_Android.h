#ifndef TOUCHUI_ANDROID_H
#define TOUCHUI_ANDROID_H

#include <touchui.h>

class TouchUI_Android: public TouchUI {
public:
	TouchUI_Android();
	void promptForName(const char *name) final;
	void showGameControls() final;
	void hideGameControls() final;
	void showButtonControls() final;
	void hideButtonControls() final;
	void onDpadLocationChanged() final;
};

#endif

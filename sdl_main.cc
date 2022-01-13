#include <SDL_main.h>

#undef main

int main(
    int argc,
    char *argv[]
) {
	return SDL_main(argc, argv);
}

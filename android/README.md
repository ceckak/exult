# Exult instructions for Android

## To Play
First you need to get Ultima 7 or Serpent Isle. Either you own it already, or you buy it somewhere.

Second, you need the Android Exult app itself.  It is not yet available in any app stores, but you can download snapshot builds from [here](https://github.com/ceckak/exult/releases) (recommended) or build from source as described below.

The Android app starts up with a launcher screen that assists with installing the content (game files, music pack, and mods).  You will need to get the content archives copied/downloaded to your Android device.  Then you can browse to them from the launcher, which will extract the archives and install the content in the correct locations so Exult finds them automatically.

The launcher has been tested and can automatically install content from the following sources:
- [Ultima 7 Complete at Good Old Games](https://www.gog.com/game/ultima_7_complete)
  - From your android device, log into your GOG account through the browser.
  - Select Mac as your platform
  - Look, under "DOWNLOAD OFFLINE BACKUP GAME INSTALLERS"
  - Download the `.pkg` file(s)
- [Zipped all-in-one audio pack for manual installation](http://prdownloads.sourceforge.net/exult/exult_audio.zip)
- [Marzo's Black Gate Keyring (requires FoV)](http://exult.info/snapshots/Keyring.zip)
- [Marzo's Serpent Isle Fixes (requires SS)](http://exult.info/snapshots/Sifixes.zip)
- [SourceForge Island (requires FoV)](http://exult.info/snapshots/SFisland.zip)

Now download Exult, and have fun!

## More Information

More information can be found in the accompanying files README and FAQ.  In addition, you might want to check out our homepage at http://exult.info

## How to compile for Android

The following guide is only for people that want to compile Exult for Android on their own. This is not needed if there is a current snapshot for Android available on our download page.

The Android APK is built as an optional component of a regular native Exult build.  Before attempting to build the Android APK, follow [the instructions](..//INSTALL) to install any dependencies and build Exult for your desktop.

In addition to the regular desktop Exult dependencies, you will need the android SDK and NDK.  The easiest way to get these is to install Android Studio.  You will also need to edit your path so that the build can find the `gradle` and `sdkmanager` commands.  Android Studio can be installed for free from [Google](https://developer.android.com/studio).

With those dependencies in place, you can build Exult as normal, but pass the `--enable-android-apk` flag to the `configure` script.  The following is a quick summary of the commands to build:

```
$ ./autogen.sh
$ ./configure --enable-android-apk=debug
$ make
```

The final APK will be located in `./android/app/build/outputs/apk/debug/app-debug.apk`.

## Known issues and planned improvements

In no particular order, the following is a cumulative list of known issues and potentially useful improvements for the Android port of Exult.

- Building
  - Enable building apk without building complete native exult
  - Add support for out-of-tree mod builds
    - And once working, enable bundling mods in the apk
  - Clean up compile warnings
  - Sort out warnings about gnu make extensions
  - enable mt32 emulation
- Portability
  - Remove dependency on glob so we can support older devices
  - Figure out how to get std::filesystem to work
  - Migrate archive installer magic into C++ and limit android specifics to thin UI
- Android UI
  - Figure out how to initially hide dpad
  - Change to new dpad style
  - Implement the escape button to close all dialogs
    - Lack of an escape button also prevents you from getting out of the telescope view in BG on moonglow
      - Workaround is to connect a USB keyboard and hit escape
  - Is there anything special needed to enable screentime management for kids
    - on FireOS
    - on Android
- Testing
  - Test with physical mouse on android
- Consistency
  - Use a common icon image across platforms
  - Get rid of symbolic links to dpad images (stage them in build dir instead)
    - This may be required to support android builds from a Windows host
- Launcher
  - Detect/report errors when importing content
  - Progress bar when importing content
  - Enable adding arbitrary mods
  - Figure out how to enable multiple orientations in launcher
- Save Games
  - Enable saving games to Documents directory
  - Explore adding an autosave option so you don't lose progress on mobile devices when the battery dies
  - Look into options for cloud storage of savegames
    - https://rclone.org/ looks like it might be a useful abstraction
  - Come up with a shortcut to quicksave/load
- Performance
  - Check into enabling game mode to see if that improves performance
- Stability
  - Fix crash when launching after exiting
- Configuration
  - Default to digital music when it is installed
  - Default to interpolation

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
$ ./configure --enable-android-apk
$ make
```

The final APK will be located in `./android/app/build/outputs/apk/debug/app-debug.apk`.

## Known issues and planned improvements

In no particular order, the following is a cumulative list of known issues and potentially useful improvements for the Android port of Exult.

- Enable building apk without building complete native exult
- Figure out if/why ogg/vorbis patch is making mac exult hang
- Test with physical mouse on android
- Figure out how to initially hide dpad
- Get native mac build working
- Add support for out-of-tree mod builds
  - And once working, enable bundling mods in the apk
- Remove dependency on glob so we can support older devices
- Figure out how to get std::filesystem to work
- Get rid of symbolic links to dpad images (stage them in build dir instead)
- Use a common icon image across platforms
- Enable relase builds with optimization
- Detect/report errors when importing content
- Progress bar when importing content
- Add links to download sites to launcher
- Add UI tabs
- Enable adding arbitrary mods
- Add option for displaying console output in a UI tab
- Enable saving games to Documents directory
- Look into leveraging google's cloud savegame feature
  - can this be enabled as a runtime option in an APK that runs on FireOS?
  - does Amazon have something similar?
- Is there anything special needed to enable screentime management for kids
  - on FireOS
  - on Android
- Fix crash when launching after exiting
- Add shortcuts to dpad for combat and target
  - Might not be needed with the shortcut icons
- Would be nice to have a shortcut for inventory too
- Figure out why intro video is so slow
- Consider having the arrow move on the screen in sync with the dpad
  - Or hide it like iOS does
- Default to digital music when it is installed
- Default to interpolation
- Default to enabling dpad
- Revisit whether dpad is the best way to control movement
  - Could use multi-touch to simulate RMB
- Inject exult version number into AndroidManifest.xml
- Clean up cruft in android wrapper project
- Clean up compile warnings
- Sort out warnings about gnu make extensions
- Figure out how to enable multiple orietations in launcher
- Migrate archive installer magic into C++ and limit android specifics to thin UI
- Still seems unreasonably sluggish; look into performance
- iOS port has another UI element (escape button?); find out if it is necessary
- Find out how to open journal without a 'j' key
- Come up with a shortcut to quicksave/load

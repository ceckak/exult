package info.exult;

import org.libsdl.app.SDLActivity;

/**
 * This class implements the Activity that will launch the Exult game engine on Android.
 */
public class ExultActivity extends SDLActivity {

  /**
   * This method returns the name of the application entry point It can be overridden by derived
   * classes.
   */
  protected String getMainFunction() {
    return "ExultAndroid_main";
  }

  /**
   * This method is called by SDL before loading the native shared libraries. It can be overridden
   * to provide names of shared libraries to be loaded. The default implementation returns the
   * defaults. It never returns null. An array returned by a new implementation must at least
   * contain "SDL2". Also keep in mind that the order the libraries are loaded may matter.
   *
   * @return names of shared libraries to be loaded (e.g. "SDL2", "main").
   */
  protected String[] getLibraries() {
    return new String[] {"SDL2", "exult-android-wrapper"};
  }
}

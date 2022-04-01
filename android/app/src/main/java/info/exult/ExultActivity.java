package info.exult;

import android.app.Activity;
import android.os.Bundle;

/**
 * This class implements the Activity that will launch the Exult game engine on Android.
 */
public class ExultActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}

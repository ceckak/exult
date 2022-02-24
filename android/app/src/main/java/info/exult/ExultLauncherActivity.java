package info.exult;

import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;

import android.content.Context;
import android.content.SharedPreferences;

public class ExultLauncherActivity extends AppCompatActivity {
  private SharedPreferences m_sharedPreferences;

  // This feels pretty hacky, but haven't figured out a better way to detect when the Activity
  // is returning from a launch versus being opened and eligble to auto-launch.
  private boolean m_launched = false;

  @Override
  public void onResume() {
      super.onResume();
      if (getAutoLaunch()) {
          if (!m_launched) {
              launchExult();
          } else {
              m_launched = false;
          }
      }
  }

  private static final String AUTO_LAUNCH_KEY = "autoLaunch";

  public void setAutoLaunch(boolean autoLaunch) {
      m_sharedPreferences
          .edit()
          .putBoolean(AUTO_LAUNCH_KEY, autoLaunch)
          .commit();
  }

  public boolean getAutoLaunch() {
      return m_sharedPreferences.getBoolean(AUTO_LAUNCH_KEY, false);
  }

  public void launchExult() {
      m_launched = true;
      Intent launchExultIntent = new Intent(this, ExultActivity.class);
      ExultActivity.consoleLog = "";
      startActivity(launchExultIntent);
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_sharedPreferences = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager,
                              new TabLayoutMediator.TabConfigurationStrategy() {
                                  @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                      tab.setText(ViewPagerAdapter.getItemText(position));
                                  }
                              }).attach();
    }
}

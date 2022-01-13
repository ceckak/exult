package info.exult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Context;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.Intent;

import android.util.Log;

public class LauncherFragment extends Fragment implements View.OnClickListener {
  private static final String AUTO_LAUNCH_KEY = "autoLaunch";
  private SharedPreferences m_sharedPreferences;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.launcher_card, container, false);

      // Route auto-launch toggles to this class
      CheckBox autoLaunchCheckBox = (CheckBox) view.findViewById(R.id.autoLaunchCheckBox);
      autoLaunchCheckBox.setOnClickListener(this);

      // Set the initial auto-launch state
      Context context = view.getContext();
      ContentResolver contentResolver = context.getContentResolver();
      m_sharedPreferences = context.getSharedPreferences("launcherFragment", Context.MODE_PRIVATE);
      boolean autoLaunch = m_sharedPreferences.getBoolean(AUTO_LAUNCH_KEY, false);
      autoLaunchCheckBox.setChecked(autoLaunch);

      // Route launch clicks to this class
      Button launchExultButton = (Button) view.findViewById(R.id.launchExultButton);
      launchExultButton.setOnClickListener(this);

      // Start the game if auto-launch is enabled
      if (autoLaunch) {
          launchExult();
      }

      return view;
  }

  @Override
  public void onClick(View view) {
      switch (view.getId()) {
      case R.id.autoLaunchCheckBox:
          CheckBox checkBox  = (CheckBox) view;
          boolean checked = checkBox.isChecked();
          SharedPreferences.Editor sharedPreferencesEditor = m_sharedPreferences.edit();
          sharedPreferencesEditor.putBoolean(AUTO_LAUNCH_KEY, checked).commit();
          break;
      case R.id.launchExultButton:
          launchExult();
          break;
       default:
          break;
      }
  }

  private void launchExult() {
      Intent launchExultIntent = new Intent(getActivity(), ExultActivity.class);
      ExultActivity.consoleLog = "";
      startActivity(launchExultIntent);
  }
}

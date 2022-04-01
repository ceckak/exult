package info.exult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.fragment.app.Fragment;

public class LauncherFragment extends Fragment implements View.OnClickListener {
  // Probably not ideal to be assuming/referencing the parent activity from a fragment,
  // but I haven't come up with a better way to handle auto-launch yet.
  private ExultLauncherActivity getExultLauncherActivity() {
    return (ExultLauncherActivity) getActivity();
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.launcher_card, container, false);

    // Route auto-launch toggles to this class
    CheckBox autoLaunchCheckBox = (CheckBox) view.findViewById(R.id.autoLaunchCheckBox);
    autoLaunchCheckBox.setOnClickListener(this);

    // Set the initial auto-launch state
    autoLaunchCheckBox.setChecked(getExultLauncherActivity().getAutoLaunch());

    // Route launch clicks to this class
    Button launchExultButton = (Button) view.findViewById(R.id.launchExultButton);
    launchExultButton.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.autoLaunchCheckBox:
        CheckBox checkBox = (CheckBox) view;
        boolean checked = checkBox.isChecked();
        getExultLauncherActivity().setAutoLaunch(checked);
        break;
      case R.id.launchExultButton:
        getExultLauncherActivity().launchExult();
        break;
      default:
        break;
    }
  }
}

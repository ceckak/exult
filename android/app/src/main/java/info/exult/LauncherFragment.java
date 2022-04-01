package info.exult;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class LauncherFragment extends Fragment implements View.OnClickListener {
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.launcher_card, container, false);

    // Route launch clicks to this class
    Button launchExultButton = (Button) view.findViewById(R.id.launchExultButton);
    launchExultButton.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.launchExultButton:
        launchExult();
        break;
      default:
        break;
    }
  }

  private void launchExult() {
    Intent launchExultIntent = new Intent(getActivity(), ExultActivity.class);
    startActivity(launchExultIntent);
  }
}

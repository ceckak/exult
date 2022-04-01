package info.exult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ConsoleLogFragment extends Fragment {
  private View view;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.console_log_card, container, false);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();

    TextView consoleLogTextView = (TextView) view.findViewById(R.id.consoleLogTextView);
    if (consoleLogTextView == null) {
      return;
    }

    consoleLogTextView.setText(ExultActivity.getConsole());
  }
}

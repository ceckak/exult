package info.exult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.widget.CheckBox;

import android.util.Log;

public class ModsFragment extends ContentInstallerFragment {

  ModsFragment() {
      super(R.layout.mods_card, R.string.mods_card_text);
  }

  @Override
  protected ExultContent buildContentFromView(String name, View view) {
      String game = (String) view.getTag(R.id.game);
      if (null == game) {
          return null;
      }
      Log.d("ModsFragment", "Found " + game + "." + name);
      return new ExultModContent(game, name, view.getContext());
  }
}

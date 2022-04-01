package info.exult;

import android.util.Log;
import android.view.View;

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

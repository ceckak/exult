package info.exult;

import android.view.View;

public class GamesFragment extends ContentInstallerFragment {

  GamesFragment() {
    super(R.layout.games_card, R.string.games_card_text);
  }

  @Override
  protected ExultContent buildContentFromView(String name, View view) {
    String crc32String = (String) view.getTag(R.id.crc32);
    Long crc32 = null;
    if (crc32String != null) {
      crc32 = Long.decode(crc32String);
    }
    return new ExultGameContent(name, view.getContext(), crc32);
  }
}

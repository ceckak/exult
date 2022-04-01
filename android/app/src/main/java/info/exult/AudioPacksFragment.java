package info.exult;

import android.view.View;

public class AudioPacksFragment extends ContentInstallerFragment {

  AudioPacksFragment() {
    super(R.layout.audio_packs_card, R.string.audio_packs_card_text);
  }

  @Override
  protected ExultContent buildContentFromView(String name, View view) {
    return new ExultAudioDataPackContent(name, view.getContext());
  }
}

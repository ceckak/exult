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

public class AudioPacksFragment extends ContentInstallerFragment {

  AudioPacksFragment() {
      super(R.layout.audio_packs_card, R.string.audio_packs_card_text);
  }

  @Override
  protected ExultContent buildContentFromView(String name, View view) {
      return new ExultAudioDataPackContent(name, view.getContext());
  }
}

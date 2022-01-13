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
import android.content.Intent;
import android.app.AlertDialog;

import android.util.Log;

import java.util.HashMap;

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

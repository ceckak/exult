package info.exult;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentStateAdapter {
  private static class TabData {
      public TabData(String name, Class fragment) {
          this.name = name;
          this.fragment = fragment;
      }
      public String name;
      public Class fragment;
  }

  private static final TabData[] TABS = {
      new TabData("Launcher", LauncherFragment.class),
      new TabData("Games", GamesFragment.class),
      new TabData("Audio Packs", AudioPacksFragment.class),
      new TabData("Console Log", ConsoleLogFragment.class)
  };

  public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
  }
  @NonNull @Override public Fragment createFragment(int position) {
      try {
          return (Fragment) TABS[position].fragment.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
          Log.d("ViewPagerAdapter",
                "exception instantiating tab position " + position + ": " + e.toString());
          return null;
      }
  }
  @Override public int getItemCount() {
      return TABS.length;
  }
  public static String getItemText(int position) {
      return TABS[position].name;
  }
}

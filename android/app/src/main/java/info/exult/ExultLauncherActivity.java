package info.exult;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ExultLauncherActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ViewPager2 viewPager = findViewById(R.id.view_pager);
    TabLayout tabLayout = findViewById(R.id.tabs);

    viewPager.setAdapter(new ViewPagerAdapter(this));
    new TabLayoutMediator(
            tabLayout,
            viewPager,
            new TabLayoutMediator.TabConfigurationStrategy() {
              @Override
              public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(ViewPagerAdapter.getItemText(position));
              }
            })
        .attach();
  }
}

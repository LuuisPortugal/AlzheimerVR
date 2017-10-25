package tk.geta.alzheimervr.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import tk.geta.alzheimervr.Adapter.VideosPagerFragmentAdapter;
import tk.geta.alzheimervr.Interface.OnBackPressed;
import tk.geta.alzheimervr.Interface.OnCreateOptionsMenu;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.View.Fragment.Videos.Novos;
import tk.geta.alzheimervr.View.Fragment.Videos.Salvos;

public class Videos extends AppCompatActivity implements TabLayout.OnTabSelectedListener, OnBackPressed {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        mViewPager = (ViewPager) findViewById(R.id.activity_videos_viewpager);
        mViewPager.setAdapter(
                new VideosPagerFragmentAdapter(getSupportFragmentManager())
                        .addFragment(Novos.newInstance())
                        .addFragment(Salvos.newInstance())
        );

        tabLayout = (TabLayout) findViewById(R.id.activity_videos_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.activity_videos_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressedListener() {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount());
        if (currentFragment instanceof OnBackPressed)
            ((OnBackPressed) currentFragment).onBackPressedListener();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount());
        if (currentFragment instanceof OnCreateOptionsMenu)
            return ((OnCreateOptionsMenu) currentFragment).onCreateOptionsMenuListener(menu);
        else
            return super.onCreateOptionsMenu(menu);
    }
}
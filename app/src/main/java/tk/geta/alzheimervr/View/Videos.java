package tk.geta.alzheimervr.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import tk.geta.alzheimervr.Adapter.PagerFragmentAdapter;
import tk.geta.alzheimervr.Interface.OnBackPressedListener;
import tk.geta.alzheimervr.Interface.OnCreateOptionsMenuListener;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.View.Fragment.Videos.Novos;
import tk.geta.alzheimervr.View.Fragment.Videos.Salvos;

public class Videos extends AppCompatActivity implements TabLayout.OnTabSelectedListener, OnBackPressedListener {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        mViewPager = (ViewPager) findViewById(R.id.activity_videos_viewpager);
        mViewPager.setAdapter(
                new PagerFragmentAdapter(getSupportFragmentManager())
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
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount());
        if (currentFragment instanceof OnBackPressedListener)
            ((OnBackPressedListener) currentFragment).onBackPressed();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount());
        if (currentFragment instanceof OnCreateOptionsMenuListener)
            return ((OnCreateOptionsMenuListener) currentFragment).onCreateOptionsMenu(menu);
        else
            return super.onCreateOptionsMenu(menu);
    }
}
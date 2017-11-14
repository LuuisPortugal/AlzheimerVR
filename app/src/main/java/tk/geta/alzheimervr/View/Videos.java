package tk.geta.alzheimervr.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import tk.geta.alzheimervr.Adapter.PagerFragmentAdapter;
import tk.geta.alzheimervr.Interface.OnBackPressedListenerInterface;
import tk.geta.alzheimervr.Interface.OnCreateOptionsMenuListenerInterface;
import tk.geta.alzheimervr.R;
import tk.geta.alzheimervr.View.Fragment.Videos.NovosVideosFragment;
import tk.geta.alzheimervr.View.Fragment.Videos.SalvosVideosFragment;

public class Videos extends AppCompatActivity implements TabLayout.OnTabSelectedListener, OnBackPressedListenerInterface {

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
                        .addFragment(NovosVideosFragment.newInstance())
                        .addFragment(SalvosVideosFragment.newInstance())
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
        if (currentFragment instanceof OnBackPressedListenerInterface)
            ((OnBackPressedListenerInterface) currentFragment).onBackPressedListener();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount());
        if (currentFragment instanceof OnCreateOptionsMenuListenerInterface)
            return ((OnCreateOptionsMenuListenerInterface) currentFragment).onCreateOptionsMenuListener(menu);
        else
            return super.onCreateOptionsMenu(menu);
    }
}
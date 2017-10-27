package tk.geta.alzheimervr.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import tk.geta.alzheimervr.Interface.OnGetPageTitleListener;

public class PagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    public PagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment currentFragment = mFragmentList.get(position);
        if (currentFragment instanceof OnGetPageTitleListener)
            return ((OnGetPageTitleListener) currentFragment).onGetPageTitle();
        return "Sem Titulo";
    }

    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }

    public PagerFragmentAdapter setFragmentList(List<Fragment> mFragmentListParam){
        mFragmentList = mFragmentListParam;
        return this;
    }

    public PagerFragmentAdapter addFragment(Fragment fragmentParam){
        mFragmentList.add(fragmentParam);
        return this;
    }
}

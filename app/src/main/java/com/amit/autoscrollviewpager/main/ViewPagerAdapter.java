package com.amit.autoscrollviewpager.main;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit Barjatya on 10/20/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFramentTitles;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragments, ArrayList<String> titles) {
        super(manager);
        mFragmentManager = manager;
        mFragmentList = fragments;
        this.mFramentTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragmentManager.findFragmentByTag(getPageTitle(position).toString());
        if (fragment != null)
            return fragment;
        else
            return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFramentTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
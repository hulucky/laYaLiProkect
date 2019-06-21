package com.example.hu.layaliprokect.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TestAdapter extends FragmentPagerAdapter {
    private List<String> titleList;
    private Context context;
    private List<Fragment> fragmentList;

    public TestAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList, Context context) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

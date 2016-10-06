package com.geojit.libin.sip_v3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.geojit.libin.sip_v3.fragments.AggressiveFundsFragment;
import com.geojit.libin.sip_v3.fragments.ConservativeFundsFragment;
import com.geojit.libin.sip_v3.fragments.ModerateFundsFragment;
import com.geojit.libin.sip_v3.fragments.VeryAggressiveFundsFragment;

import java.util.ArrayList;

/**
 * Created by 10945 on 8/6/2016.
 */
public class FundsTabAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> tabs;

    public FundsTabAdapter(FragmentManager fm, ArrayList<Fragment> tabs) {
        super(fm);

        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}

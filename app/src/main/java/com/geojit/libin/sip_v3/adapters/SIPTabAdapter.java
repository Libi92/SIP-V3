package com.geojit.libin.sip_v3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.geojit.libin.sip_v3.fragments.AdvancedSipFragment;
import com.geojit.libin.sip_v3.fragments.SIPCalculatorFragment;

import java.util.ArrayList;

/**
 * Created by libin on 15/07/16.
 */
public class SIPTabAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> tabs;

    public SIPTabAdapter(FragmentManager fm) {
        super(fm);

        tabs = new ArrayList<>();
        tabs.add(new SIPCalculatorFragment());
        tabs.add(new AdvancedSipFragment());
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

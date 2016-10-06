package com.geojit.libin.sip_v3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.geojit.libin.sip_v3.fragments.FundPerformanceFragment;
import com.geojit.libin.sip_v3.fragments.SchemeDetailsFragment;
import com.geojit.libin.sip_v3.fragments.TopHoldingsFragment;

import java.util.ArrayList;

/**
 * Created by 10946 on 8/8/2016.
 */
public class FundDetailsTabAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> tabs;

    public FundDetailsTabAdapter(FragmentManager fm) {
        super(fm);

        tabs = new ArrayList<>();
        tabs.add(new TopHoldingsFragment());
        tabs.add(new FundPerformanceFragment());
        tabs.add(new SchemeDetailsFragment());

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

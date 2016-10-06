package com.geojit.libin.sip_v3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.geojit.libin.sip_v3.fragments.MyPorfolioFragment;
import com.geojit.libin.sip_v3.fragments.PortfolioFragment;
import com.geojit.libin.sip_v3.slides.Slide1;
import com.geojit.libin.sip_v3.slides.Slide2;
import com.geojit.libin.sip_v3.slides.Slide3;
import com.geojit.libin.sip_v3.slides.Slide4;

import java.util.ArrayList;

/**
 * Created by 10946 on 8/23/2016.
 */
public class PortfolioPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> slides;

    public PortfolioPagerAdapter(FragmentManager fm) {
        super(fm);

        slides = new ArrayList<>();
        slides.add(new MyPorfolioFragment());
        slides.add(new PortfolioFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return slides.get(position);
    }

    @Override
    public int getCount() {
        return slides.size();
    }
}

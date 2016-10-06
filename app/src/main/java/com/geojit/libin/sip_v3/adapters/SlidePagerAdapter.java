package com.geojit.libin.sip_v3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.geojit.libin.sip_v3.slides.Slide1;
import com.geojit.libin.sip_v3.slides.Slide2;
import com.geojit.libin.sip_v3.slides.Slide3;
import com.geojit.libin.sip_v3.slides.Slide4;

import java.util.ArrayList;

/**
 * Created by libin on 29/06/16.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> slides;

    public SlidePagerAdapter(FragmentManager fm) {
        super(fm);

        slides = new ArrayList<>();
        slides.add(new Slide1());
        slides.add(new Slide2());
        slides.add(new Slide3());
        slides.add(new Slide4());
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

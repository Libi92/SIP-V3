package com.geojit.libin.sip_v3.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.adapters.FundsTabAdapter;
import com.geojit.libin.sip_v3.callbacks.OnFundItemClickListner;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.excelreader.FundModal;
import com.geojit.libin.sip_v3.modals.FundDataModal;

import java.util.ArrayList;

public class TopFundFragment extends Fragment implements TabLayout.OnTabSelectedListener, OnFundItemClickListner {

    private static final int rippleDelay = 300;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_fund, container, false);

        setToolbar();
        initComponents();

        return view;
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imageViewDrawerBack = (ImageView) toolbar.findViewById(R.id.imageViewDrawerBack);
        imageViewDrawerBack.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu_icon);
        SIPTextView toolbarTitle = (SIPTextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.top_funds_label));
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initComponents() {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        TabLayout.Tab tab1 = createTab(getString(R.string.conservative));
        setTabSelected(tab1);

        TabLayout.Tab tab2 = createTab(getString(R.string.moderate));
        setTabUnSelected(tab2);

        TabLayout.Tab tab3 = createTab(getString(R.string.aggressive));
        setTabUnSelected(tab3);

        TabLayout.Tab tab4 = createTab(getString(R.string.very_aggressive));
        setTabUnSelected(tab4);

        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.addTab(tab4);

        tabLayout.setOnTabSelectedListener(this);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        ArrayList<Fragment> tabs = new ArrayList<>();
        ConservativeFundsFragment e = new ConservativeFundsFragment();
        e.setOnFundItemClickListner(this);
        tabs.add(e);

        ModerateFundsFragment moderate = new ModerateFundsFragment();
        moderate.setOnFundItemClickListner(this);
        tabs.add(moderate);

        AggressiveFundsFragment aggressive = new AggressiveFundsFragment();
        aggressive.setOnFundItemClickListner(this);
        tabs.add(aggressive);

        VeryAggressiveFundsFragment veryAggressive = new VeryAggressiveFundsFragment();
        veryAggressive.setOnFundItemClickListner(this);
        tabs.add(veryAggressive);

        FundsTabAdapter adapter = new FundsTabAdapter(getChildFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
                setTabSelected(tab);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelected(tab);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabUnSelected(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        setTabSelected(tab);
    }

    private TabLayout.Tab createTab(String text) {
        TabLayout.Tab tab = tabLayout.newTab().setCustomView(R.layout.tab_layout);
        SIPTextView textView = (SIPTextView) tab.getCustomView().findViewById(R.id.tabTextView);
        textView.setText(text);

        return tab;
    }

    private void setTabSelected(TabLayout.Tab tab) {
        SIPTextView textView = (SIPTextView) tab.getCustomView().findViewById(R.id.tabTextView);
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
    }

    private void setTabUnSelected(TabLayout.Tab tab) {
        SIPTextView textView = (SIPTextView) tab.getCustomView().findViewById(R.id.tabTextView);
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.fade_white));
        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
    }

    @Override
    public void onClick(FundDataModal model, FundModal performanceModal) {
        final FundDetailsFragment fragment = new FundDetailsFragment();

        Intent intent = getActivity().getIntent();
        intent.putExtra(FundDetailsFragment.FUND_PERFORMANCE, performanceModal);
        intent.putExtra(FundDetailsFragment.FUND, model);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }, rippleDelay);
    }


}

package com.geojit.libin.sip_v3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geojit.libin.sip_v3.InfoActivity;
import com.geojit.libin.sip_v3.PortfolioActivity;
import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.SIPTextView;

/**
 * Created by 10945 on 8/10/2016.
 */
public class HomeFragment extends Fragment {
    private static final int rippleDelay = 300;
    private View view;
    private View itemSipCalculator, itemTopFunds, itemPortfolio, itemEmiCalculator, itemInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        setToolbar();
        initComponents();
        setListners();
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
        toolbarTitle.setText(getResources().getString(R.string.app_name));
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_fade_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initComponents() {
        itemSipCalculator = view.findViewById(R.id.itemSipCalculator);
        itemTopFunds = view.findViewById(R.id.itemTopFunds);
        itemPortfolio = view.findViewById(R.id.itemPortfolio);
        itemEmiCalculator = view.findViewById(R.id.itemEmiCalculator);
        itemInfo = view.findViewById(R.id.itemInfo);

    }

    private void setListners() {

        itemSipCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SipFragment fragment = new SipFragment();
                showView(fragment);
            }
        });

        itemPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PortfolioActivity.class);
                startActivity(intent);
            }
        });

        itemTopFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopFundFragment fragment = new TopFundFragment();
                showView(fragment);
            }
        });

        itemEmiCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMICalculatorFragment fragment = new EMICalculatorFragment();
                showView(fragment);
            }
        });

        itemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(InfoActivity.class);
            }
        });
    }

    private void startActivity(final Class infoActivityClass) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), infoActivityClass);
                startActivity(intent);
            }
        }, rippleDelay);
    }

    private void showView(final Fragment fragment) {

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

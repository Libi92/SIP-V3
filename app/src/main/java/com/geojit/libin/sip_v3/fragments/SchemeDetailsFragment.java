package com.geojit.libin.sip_v3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.SIPTextView;

/**
 * Created by 10946 on 8/8/2016.
 */
public class SchemeDetailsFragment extends Fragment {

    SIPTextView fundType,fundTypeLabel,investmentPlan,investmentPlanLabel,launchDate,launchDateLabel,benchmark,benchmarklabel,assetSize,assetSizeLabel,minimumInvestment, minimumInvestmentLabel, lastDivident, lastDividentLabel, bonus, bonusLabel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scheme_details,container,false);

        linkingComponents(view);

        return view;
    }

    public void linkingComponents(View view){

        fundType = (SIPTextView) view.findViewById(R.id.fundType);
        fundTypeLabel = (SIPTextView) view.findViewById(R.id.fundTypeLabel);
        investmentPlan = (SIPTextView) view.findViewById(R.id.investmentPlan);
        investmentPlanLabel = (SIPTextView) view.findViewById(R.id.investmentPlanLabel);
        launchDate = (SIPTextView) view.findViewById(R.id.launchDate);
        launchDateLabel = (SIPTextView) view.findViewById(R.id.launchDateLabel);
        benchmark = (SIPTextView) view.findViewById(R.id.benchmark);
        benchmarklabel = (SIPTextView) view.findViewById(R.id.benchmarkLabel);
        assetSize = (SIPTextView) view.findViewById(R.id.assetSize);
        assetSizeLabel = (SIPTextView) view.findViewById(R.id.assetSizeLabel);
        minimumInvestment = (SIPTextView) view.findViewById(R.id.minimumInvestment);
        minimumInvestmentLabel = (SIPTextView) view.findViewById(R.id.minimumInvestmentLabel);
        lastDivident = (SIPTextView) view.findViewById(R.id.lastDivident);
        lastDividentLabel = (SIPTextView) view.findViewById(R.id.lastDividentLabel);
        bonus = (SIPTextView) view.findViewById(R.id.bonus);
        bonusLabel = (SIPTextView) view.findViewById(R.id.bonusLabel);
    }

}

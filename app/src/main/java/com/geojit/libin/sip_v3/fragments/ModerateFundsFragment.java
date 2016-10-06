package com.geojit.libin.sip_v3.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.adapters.FundAdapter;
import com.geojit.libin.sip_v3.callbacks.OnFundItemClickListner;
import com.geojit.libin.sip_v3.excelreader.CSVHelper;
import com.geojit.libin.sip_v3.excelreader.FundModal;
import com.geojit.libin.sip_v3.excelreader.PortfolioReturns;
import com.geojit.libin.sip_v3.modals.FundDataModal;
import com.geojit.libin.sip_v3.utils.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by 10945 on 8/6/2016.
 */
public class ModerateFundsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerViewTopFunds;
    private Activity mActivity;
    private ArrayList<FundDataModal> data;
    private ArrayList<FundModal> funds;
    private FundModal selectedFund;

    private OnFundItemClickListner onFundItemClickListner;

    public void setOnFundItemClickListner(OnFundItemClickListner onFundItemClickListner) {
        this.onFundItemClickListner = onFundItemClickListner;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fund_moderate, container, false);

        initComponents();
        createDataSet();
        setAdapter();
        setListners();

        return view;
    }

    private void initComponents() {
        mActivity = getActivity();

        recyclerViewTopFunds = (RecyclerView) view.findViewById(R.id.recyclerViewTopFunds);
    }

    private void setListners() {
        recyclerViewTopFunds.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recyclerViewTopFunds, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectedFund = funds.get(position);

                if (onFundItemClickListner != null) {
                    onFundItemClickListner.onClick(data.get(position), selectedFund);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setAdapter() {
        FundAdapter adapter = new FundAdapter(data, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerViewTopFunds.setLayoutManager(layoutManager);
        recyclerViewTopFunds.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTopFunds.setAdapter(adapter);
    }

    private void createDataSet() {
        data = new ArrayList<>();

        PortfolioReturns portfolioReturns = CSVHelper.read(mActivity);
        funds = portfolioReturns.get(PortfolioReturns.MODERATE);

        for (FundModal fund : funds) {
            FundDataModal modal = new FundDataModal();
            modal.setSchemeCode(fund.getSchemeCode());
            modal.setSchemeName(fund.getSchemeName());
            modal.setFundType(fund.getCategory());
            modal.setNAV(fund.getNav());
            modal.setGrowth(fund.getChange());
            modal.setGrowthPercentage(fund.getPercentage());
            modal.setIcon(fund.getImage());

            data.add(modal);
        }
    }

}

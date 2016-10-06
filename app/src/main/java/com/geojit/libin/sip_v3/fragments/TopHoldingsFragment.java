package com.geojit.libin.sip_v3.fragments;

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
import com.geojit.libin.sip_v3.modals.FundDataModal;

import java.util.ArrayList;

/**
 * Created by 10946 on 8/8/2016.
 */
public class TopHoldingsFragment extends Fragment {

    RecyclerView recyclerTopHolding;

    private ArrayList<FundDataModal> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_holdings, container, false);

        recyclerTopHolding = (RecyclerView) view.findViewById(R.id.recyclerTopHolding);

        setDataSet();

        setAdapter();

        return view;
    }

    private void setDataSet() {

        data = new ArrayList<>();

        FundDataModal modal = new FundDataModal();
        modal.setSchemeName("HDFC Bank");
        modal.setFundType("Banking/Finance");
        modal.setNAV("634.90");
        modal.setGrowth("");
        modal.setGrowthPercentage("6.78");
        data.add(0,modal);

        modal = new FundDataModal();
        modal.setSchemeName("Infosys");
        modal.setFundType("Technology");
        modal.setNAV("501.93");
        modal.setGrowth("");
        modal.setGrowthPercentage("5.36");
        data.add(1,modal);

        modal = new FundDataModal();
        modal.setSchemeName("Reliance");
        modal.setFundType("Oil & Gas");
        modal.setNAV("389.56");
        modal.setGrowth("");
        modal.setGrowthPercentage("4.16");
        data.add(2,modal);

        modal = new FundDataModal();
        modal.setSchemeName("ITC");
        modal.setFundType("Tobacco");
        modal.setNAV("348.35");
        modal.setGrowth("");
        modal.setGrowthPercentage("3.72");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Larsen");
        modal.setFundType("Engineering");
        modal.setNAV("336.18");
        modal.setGrowth("");
        modal.setGrowthPercentage("3.59");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("ICICI Bank");
        modal.setFundType("Banking/Finance");
        modal.setNAV("279.06");
        modal.setGrowth("");
        modal.setGrowthPercentage("2.98");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Tata Motors");
        modal.setFundType("Automotive");
        modal.setNAV("253.77");
        modal.setGrowth("");
        modal.setGrowthPercentage("2.71");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("IndusInd Bank");
        modal.setFundType("Banking/Finance");
        modal.setNAV("236.92");
        modal.setGrowth("");
        modal.setGrowthPercentage("2.53");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("HCL Tech");
        modal.setFundType("Technology");
        modal.setNAV("227.55");
        modal.setGrowth("");
        modal.setGrowthPercentage("2.43");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Maruti Suzuki");
        modal.setFundType("Automotive");
        modal.setNAV("219.13");
        modal.setGrowth("");
        modal.setGrowthPercentage("2.34");
        data.add(modal);


    }

    private void setAdapter() {

        FundAdapter adapter = new FundAdapter(data, true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerTopHolding.setLayoutManager(layoutManager);
        recyclerTopHolding.setItemAnimator(new DefaultItemAnimator());
        recyclerTopHolding.setAdapter(adapter);
    }
}

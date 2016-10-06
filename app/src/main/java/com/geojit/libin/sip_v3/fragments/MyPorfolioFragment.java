package com.geojit.libin.sip_v3.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.geojit.libin.sip_v3.HomeActivity;
import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.adapters.FundAdapter;
import com.geojit.libin.sip_v3.adapters.PortfolioAdapter;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.modals.PortfolioDataModel;
import com.geojit.libin.sip_v3.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class MyPorfolioFragment extends Fragment {

    public static String BUNDLE_SCHEME_NAME = "schemeName";
    private RecyclerView portfolioRecycler;
    private SIPTextView currentHoldingCost, currentHoldingCostLabel, currentHoldingValue, getCurrentHoldingValueLabel;
    private View view;
    private Context mActivity;
    private ArrayList<PortfolioDataModel> data = new ArrayList<>();
    private Typeface bnpp_sans;
    private boolean selectionFlag = false;
    private MenuItem item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_porfolio, container, false);

        setHasOptionsMenu(true);

        initComponents();
        createDataSet();
        setAdapter();
        setListners();

        return view;
    }

    public void initComponents() {

        mActivity = getActivity();
        bnpp_sans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bnpp/bnpp-sans.ttf");
        portfolioRecycler = (RecyclerView) view.findViewById(R.id.recyclerPortfolio);
        currentHoldingCost = (SIPTextView) view.findViewById(R.id.currentHoldingCost);
        currentHoldingValue = (SIPTextView) view.findViewById(R.id.currentHoldingValue);
        currentHoldingCostLabel = (SIPTextView) view.findViewById(R.id.currentHoldingCostLabel);
        getCurrentHoldingValueLabel = (SIPTextView) view.findViewById(R.id.currentHoldingValueLabel);
        currentHoldingCostLabel.setTypeface(bnpp_sans);
        getCurrentHoldingValueLabel.setTypeface(bnpp_sans);
    }

    public void createDataSet() {

        /*for (int i = 0; i < 20; i++) {

            PortfolioDataModel portfolioDataModel = new PortfolioDataModel();
            portfolioDataModel.setFundType("Finance");
            portfolioDataModel.setHoldingUnit("10");
            portfolioDataModel.setHoldingValue("2000");
            portfolioDataModel.setNAV("200");
            portfolioDataModel.setIcon("birla");
            portfolioDataModel.setSchemeCode("code" + i);
            portfolioDataModel.setSchemeName("Birla Sun Life Mutual Fund asd asdas dasda sd asd asd");
            data.add(portfolioDataModel);

        }*/

        PortfolioDataModel portfolioDataModel = new PortfolioDataModel();
        portfolioDataModel.setFundType("Finance");
        portfolioDataModel.setHoldingUnit("10");
        portfolioDataModel.setHoldingValue("4000");
        portfolioDataModel.setNAV("182.980");
        portfolioDataModel.setIcon("birla");
        portfolioDataModel.setSchemeCode("code");
        portfolioDataModel.setSchemeName("Birla Sun Life Frontline Equity Fund (G)");
        data.add(portfolioDataModel);

        PortfolioDataModel portfolioDataModel1 = new PortfolioDataModel();
        portfolioDataModel1.setFundType("Finance");
        portfolioDataModel1.setHoldingUnit("10");
        portfolioDataModel1.setHoldingValue("3000");
        portfolioDataModel1.setNAV("26.614");
        portfolioDataModel1.setIcon("kotak");
        portfolioDataModel1.setSchemeCode("code");
        portfolioDataModel1.setSchemeName("Kotak Select Focus Fund - Regular Plan (G)");
        data.add(portfolioDataModel1);

        PortfolioDataModel portfolioDataModel2 = new PortfolioDataModel();
        portfolioDataModel2.setFundType("Finance");
        portfolioDataModel2.setHoldingUnit("10");
        portfolioDataModel2.setHoldingValue("5000");
        portfolioDataModel2.setNAV("32.418");
        portfolioDataModel2.setIcon("sbi");
        portfolioDataModel2.setSchemeCode("code");
        portfolioDataModel2.setSchemeName("SBI Blue Chip Fund (G)");
        data.add(portfolioDataModel2);

        PortfolioDataModel portfolioDataModel3 = new PortfolioDataModel();
        portfolioDataModel3.setFundType("Finance");
        portfolioDataModel3.setHoldingUnit("10");
        portfolioDataModel3.setHoldingValue("8000");
        portfolioDataModel3.setNAV("37.643");
        portfolioDataModel3.setIcon("franklin");
        portfolioDataModel3.setSchemeCode("code");
        portfolioDataModel3.setSchemeName("Franklin (I) Smaller Cos (G)");
        data.add(portfolioDataModel3);

        PortfolioDataModel portfolioDataModel4 = new PortfolioDataModel();
        portfolioDataModel4.setFundType("Finance");
        portfolioDataModel4.setHoldingUnit("10");
        portfolioDataModel4.setHoldingValue("2000");
        portfolioDataModel4.setNAV("28.705");
        portfolioDataModel4.setIcon("l&t");
        portfolioDataModel4.setSchemeCode("code");
        portfolioDataModel4.setSchemeName("L&T India Value Fund (G)");
        data.add(portfolioDataModel4);
    }

    public void setAdapter() {
        PortfolioAdapter adapter = new PortfolioAdapter(data, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        portfolioRecycler.setLayoutManager(layoutManager);
        portfolioRecycler.setItemAnimator(new DefaultItemAnimator());
        portfolioRecycler.setAdapter(adapter);
    }

    private void setListners() {
        portfolioRecycler.addOnItemTouchListener(new RecyclerTouchListener(mActivity, portfolioRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Fragment newFragment = new PortfolioFragment();
                Bundle arguments = new Bundle();
                arguments.putString(BUNDLE_SCHEME_NAME, data.get(position).getSchemeName());
                newFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
                if(selectionFlag){
                    portfolioRecycler.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.graph_start));
                    //Toast.makeText(getActivity(),view.getId()+"",Toast.LENGTH_SHORT).show();
                }else {
                    selectionFlag = false;
                    getActivity().invalidateOptionsMenu();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

                    portfolioRecycler.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.graph_start));
                    //Toast.makeText(getActivity(),view.getId()+"",Toast.LENGTH_SHORT).show();
                    selectionFlag = true;
                    getActivity().invalidateOptionsMenu();

            }
        }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.portfolio_menu,menu);

        item = menu.findItem(R.id.menuCancel);

        item.setVisible(false);

        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuCancel){

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            selectionFlag = false;
            getActivity().invalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        if(selectionFlag){
            item.setVisible(true);
        }else{
            item.setVisible(false);
        }

        super.onPrepareOptionsMenu(menu);
    }
}

package com.geojit.libin.sip_v3.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PortfolioFragment extends Fragment {

    private View view;
    private SIPTextView holdingCostTextView, currentValueTextView, realizedPLTextView, unRealizedPLTextView, dividentTextView, graphSelected;
    private String holdingCost, currentValue, realizedPL, unrealizedPL, divident;
    private PieChart pieChart;
    private ArrayList<PieEntry> entries;
    private PieDataSet dataset;
    private PieData data;
    //private String schemeName;
    private ArrayList<String> lengendLabels = new ArrayList<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private HashMap<Float, String> pieEntries = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragement_portfolio, container, false);

        initComponents();
        setToolbar();
        createDataSet();
        settingChart();
        setValues();

        return view;
    }

    public void initComponents() {

//        schemeName = getArguments().getString(MyPorfolioFragment.BUNDLE_SCHEME_NAME);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        holdingCostTextView = (SIPTextView) view.findViewById(R.id.holdingCost);
        currentValueTextView = (SIPTextView) view.findViewById(R.id.currentValue);
        realizedPLTextView = (SIPTextView) view.findViewById(R.id.realizedPL);
        unRealizedPLTextView = (SIPTextView) view.findViewById(R.id.unRealizedPL);
        dividentTextView = (SIPTextView) view.findViewById(R.id.divident);
        graphSelected = (SIPTextView) view.findViewById(R.id.textViewSelected);
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    public void createDataSet() {

        holdingCost = "2,15,073";
        currentValue = "2,15,073.83";
        realizedPL = "20,000";
        unrealizedPL = "1,20,000";
        divident = "14,000";
    }

    public void settingChart() {

        createChartData();
        chartProperties();
        setChartListeners();
    }

    public void setValues() {

        holdingCostTextView.setText(holdingCost);
        currentValueTextView.setText(currentValue);
        realizedPLTextView.setText(realizedPL);
        unRealizedPLTextView.setText(unrealizedPL);
        dividentTextView.setText(divident);
    }

    public void createChartData() {

        setDataSet();
        setChartColors();
        setLegend();
    }

    public void setChartListeners() {

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                graphSelected.setText(pieEntries.get(e.getY()));
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    public void chartProperties() {

        pieChart.setDrawEntryLabels(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateY(1000);
        dataset.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);      //Setting Chart slice label outside
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);      //Setting Chart slice value outside
        pieChart.setDrawEntryLabels(true);
        pieChart.saveToGallery("/sd/mychart.jpg", 85);

        /* Piechart center circle properties */

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setEntryLabelTextSize(10f);
        pieChart.setDescription("");

    }

    public void setChartColors() {

        final int[] MY_COLORS = {Color.rgb(26, 171, 118), Color.rgb(109, 201, 167), Color.rgb(146, 215, 189),
                Color.rgb(166, 222, 201), Color.rgb(2, 125, 80), Color.rgb(0, 161, 102), Color.rgb(23, 169, 116),
                Color.rgb(26, 171, 118), Color.rgb(109, 201, 167), Color.rgb(146, 215, 189)};
        colors = new ArrayList<Integer>();

        for (int c : MY_COLORS) colors.add(c);

        dataset.setColors(colors);

    }

    public void setDataSet() {

        entries = new ArrayList<>();
        PieEntry entry = new PieEntry(18, formatText("Birla Sun Life Frontline Equity Fund (G)"));
        entries.add(entry);
        entry = new PieEntry(14, formatText("Kotak Select Focus Fund - Regular Plan (G)"));
        entries.add(entry);
        entry = new PieEntry(23, formatText("SBI Blue Chip Fund (G)"));
        entries.add(entry);
        entry = new PieEntry(36, formatText("Mirae Asset Emerging Bluechip Fund (G)"));
        entries.add(entry);
        entry = new PieEntry(9, formatText("L&T India Value Fund (G)"));
        entries.add(entry);

        pieEntries.put(18f, "Birla Sun Life Frontline Equity Fund (G)");
        pieEntries.put(14f, "Kotak Select Focus Fund - Regular Plan (G)");
        pieEntries.put(23f, "SBI Blue Chip Fund (G)");
        pieEntries.put(36f, "Mirae Asset Emerging Bluechip Fund (G)");
        pieEntries.put(9f, "L&T India Value Fund (G)");

        dataset = new PieDataSet(entries, "# of Funds");
        data = new PieData(dataset);
        pieChart.setData(data);
    }

    public void setLegend() {

        lengendLabels = new ArrayList<String>();
        lengendLabels.add("Legend1");
        lengendLabels.add("Legend2");
        lengendLabels.add("Legend3");
        lengendLabels.add("Legend4");
        lengendLabels.add("Legend5");

        /* Legend Properties */

        pieChart.getLegendRenderer().computeLegend(data);
        pieChart.getLegend().setComputedColors(colors);
        pieChart.getLegend().setComputedLabels(lengendLabels);
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

    }

    public String formatText(String text) {

        if (text.length() > 7) {
            text = text.substring(0, 10);
            text += "...";
        } else if (!text.equals("Others")) {
            text += "...";
        }

        return text;
    }
}

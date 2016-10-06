package com.geojit.libin.sip_v3.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.ChartMarker;
import com.geojit.libin.sip_v3.excelreader.FundModal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by 10946 on 8/8/2016.
 */
public class FundPerformanceFragment extends Fragment {


    private LineChart lineChartFund;
    private FundModal modal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fund_performance,container,false);

        initComponents(view);

        createChart();

        ArrayList<Entry> curentValues = createCurrentDataSet();
        ArrayList<Entry> investedValues = createInvestedDataSet();
        setChartProperties(curentValues, investedValues);

        return view;
    }

    public void initComponents(View view){

        lineChartFund = (LineChart) view.findViewById(R.id.lineChartFund);

        modal = FundDetailsFragment.performanceModal;

    }


    private void createChart() {
        lineChartFund.setDrawGridBackground(false);
        lineChartFund.setDescription("");
        lineChartFund.setNoDataTextDescription("year wise data not available");
        lineChartFund.setTouchEnabled(true);
        lineChartFund.setDragEnabled(true);
        lineChartFund.setScaleEnabled(false);
        lineChartFund.setPinchZoom(false);
        lineChartFund.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ChartMarker marker = new ChartMarker(getActivity(), R.layout.custom_marker_view);
        lineChartFund.setMarkerView(marker);
        XAxis xAxis = lineChartFund.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + " year";
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        lineChartFund.getAxisRight().setEnabled(false);


        lineChartFund.animateX(500);
        Legend legend = lineChartFund.getLegend();
        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f);
        legend.setYEntrySpace(5f);

    }


    private ArrayList<Entry> createCurrentDataSet() {


        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, modal.getMonthlyAmount()));
        values.add(new Entry(3, modal.getYear3().getCurrentValue()));
        values.add(new Entry(5, modal.getYear5().getCurrentValue()));
        values.add(new Entry(10, modal.getYear10().getCurrentValue()));

        return values;
    }

    private ArrayList<Entry> createInvestedDataSet() {


        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, modal.getMonthlyAmount()));
        values.add(new Entry(3, modal.getYear3().getTotalAmountInvested()));
        values.add(new Entry(5, modal.getYear5().getTotalAmountInvested()));
        values.add(new Entry(10, modal.getYear10().getTotalAmountInvested()));

        return values;
    }

    private void setChartProperties(ArrayList<Entry> currentValues, ArrayList<Entry> investedValues) {
        LineDataSet set1, set2;

        if (lineChartFund.getData() != null &&
                lineChartFund.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChartFund.getData().getDataSetByIndex(0);
            set1.setValues(currentValues);

            set2 = (LineDataSet) lineChartFund.getData().getDataSetByIndex(1);
            set2.setValues(investedValues);

            lineChartFund.getData().notifyDataChanged();
            lineChartFund.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(currentValues, "Current Value");

            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(3f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);

            set2 = new LineDataSet(investedValues, "Invested Amount");

            set2.setColor(Color.BLACK);
            set2.setCircleColor(Color.BLACK);
            set2.setLineWidth(3f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            LineData data = new LineData(dataSets);
            lineChartFund.setData(data);
            lineChartFund.fitScreen();

        }
    }
}

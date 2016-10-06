package com.geojit.libin.sip_v3.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.ChartMarker;
import com.geojit.libin.sip_v3.components.RupeesTextView;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.utils.CurrencyValueFormatter;
import com.geojit.libin.sip_v3.utils.SIPCalculator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by 10945 on 7/28/2016.
 */
public class AdvancedSipFragment extends Fragment implements
        OnChartGestureListener, OnChartValueSelectedListener {

    //public static NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
    public static DecimalFormat formatter = new DecimalFormat("##,##,##,###");
    private static double monthlyInvestAmount = 5000;
    private static int year = 10;
    private static int yearMax = 15;
    private static int rateOfReturn = 17;
    private static int rateOfReturnAgressive = 19;
    private static int rateOfReturnVeryAggressive = 20;
    private static int rateOfReturnModerate = 16;
    private static int rateOfReturnConservative = 14;
    private static int selectedRate = rateOfReturn;
    SIPCalculator sipCalculator = new SIPCalculator();
    private float baseX, baseY;
    private View view;
    private LineChart mChart;
    private TextView yearValue, variationValue, yearHeading, investmentHeading, percentageValue, riskLabel;
    private RupeesTextView returnValue, amountValue;
    private ImageView scrollImage;
    private double depositAmount = 0;
    private double actualAmount = 0;
    private double currentInvestment = 0;
    private float graphDividerWidth = 2f;
    private int investmentMargin = 1000;
    private int graphAnimationTime = 1000;
    private LineDataSet investedSet, actualSet, actualAggressiveSet, actualVeryAggressiveSet, actualModerateSet, actualConservativeSet;
    private LineData data;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_advanced_sip, container, false);

        initComponents();
        setToolbar();
        setListeners();
        setChartProperties();
        settingValues();
        setData();

        mChart.animateX(1000);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        return view;
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imageViewDrawerBack = (ImageView) toolbar.findViewById(R.id.imageViewDrawerBack);
        imageViewDrawerBack.setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu_icon);
        SIPTextView toolbarTitle = (SIPTextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.app_name));
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    public void settingValues() {

        yearValue.setText(Integer.toString(year));
        String temp = formatter.format(monthlyInvestAmount);
        amountValue.setText(temp.toString());
        double value = sipCalculation(year, 1, rateOfReturn);
        temp = formatter.format(value);
        returnValue.setText(temp);
        double deposit = (monthlyInvestAmount * year * 12);
        double difference = (value - deposit);
        difference = (double) Math.round(difference * 100) / 100;
        double variation = (difference * 100) / deposit;
        temp = formatter.format(difference);
        variationValue.setText("+" + temp);
        percentageValue.setText("(" + (int) variation + "%)");
        riskLabel.setText("Moderate" + " (" + rateOfReturnModerate + "%)");
    }

    public void setListeners() {

        scrollImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //TOUCH STARTED
                        baseX = event.getX();
                        baseY = event.getY();

//                        Log.e("Swipe", "touched: X-" + baseX + ", Y-" + baseY);

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //FINGER IS MOVING
                        //Do your calculations here, using the x and y positions relative to the starting values you get in ACTION_DOWN

//                        Log.e("Swipe move", "touched: X-" + event.getX() + ", Y-" + event.getY());

                        if ((event.getY() - baseY) > 5) {

                            baseX = event.getX();
                            baseY = event.getY();

//                            Log.e("swipe", "decrement");

                            onSwipeBottom();

                        } else if ((baseY - event.getY()) > 5) {

                            baseX = event.getX();
                            baseY = event.getY();

//                            Log.e("swipe", "increment");

                            onSwipeTop();

                        }

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        //TOUCH COMPLETED
                        return true;
                }

                return false;
            }

            public void onSwipeTop() {

                increment();

                updateChartValues();

                mChart.invalidate();

                data.clearValues();

                mChart.clear();

                setData();

//                mChart.animateX(graphAnimationTime);

                mChart.getLegend().setEnabled(false);

                mChart.notifyDataSetChanged();
            }

            public void onSwipeRight() {

            }

            public void onSwipeLeft() {

            }

            public void onSwipeBottom() {

                decrement();

                updateChartValues();

                data.clearValues();

                mChart.clear();

                setData();

//                mChart.animateX(graphAnimationTime);

                mChart.getLegend().setEnabled(false);

                mChart.notifyDataSetChanged();
            }

        });
    }

    public void initComponents() {

        mActivity = getActivity();

        yearValue = (TextView) view.findViewById(R.id.yearValue);
        returnValue = (RupeesTextView) view.findViewById(R.id.returnValue);
        variationValue = (TextView) view.findViewById(R.id.variationValue);
        amountValue = (RupeesTextView) view.findViewById(R.id.amountValue);
        mChart = (LineChart) view.findViewById(R.id.chart1);
        yearHeading = (TextView) view.findViewById(R.id.yearHeading);
        percentageValue = (TextView) view.findViewById(R.id.percentageValue);
        investmentHeading = (TextView) view.findViewById(R.id.investmentHeading);
        riskLabel = (TextView) view.findViewById(R.id.riskLabel);
        scrollImage = (ImageView) view.findViewById(R.id.scrollImage);

    }

    public void setChartProperties() {

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getXAxis().setGridColor(Color.WHITE);

        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);

        ChartMarker mv = new ChartMarker(mActivity, R.layout.custom_marker_view);
        mChart.setMarkerView(mv);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(android.R.color.white));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setTextColor(getResources().getColor(android.R.color.white));

        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setValueFormatter(new MyValueFormatter());

        mChart.getAxisRight().setEnabled(false);

    }


    private ArrayList<Entry> createCurrentDataSet(int rate) {


        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 1; i <= yearMax; i++) {

            values.add(new Entry(i, (float) sipCalculation(i, 1, rate)));

        }

        return values;
    }

    private ArrayList<Entry> createInvestedDataSet(int rate) {


        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 1; i <= yearMax; i++) {

            values.add(new Entry(i, (float) sipCalculation(i, 0, rate)));

        }

        return values;

    }

    private void setData() {

        StringTokenizer st = new StringTokenizer(amountValue.getText().toString(), ",");
        String temp = "";
        while (st.hasMoreTokens()) {
            temp += st.nextToken();
        }

        monthlyInvestAmount = Double.parseDouble(temp);

        functionInvested();
        functionActual();
        functionAggressive();
        functionVeryAggressive();
        functionModerate();
        functionConservative();

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(actualVeryAggressiveSet);
        dataSets.add(actualAggressiveSet);
        dataSets.add(actualModerateSet);
        dataSets.add(actualConservativeSet);
        dataSets.add(actualSet);
        dataSets.add(investedSet);

        data = new LineData(dataSets);

        mChart.setData(data);


    }


    public void functionInvested() {

        ArrayList<Entry> invested = createInvestedDataSet(rateOfReturn);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            investedSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            investedSet.setValues(invested);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            investedSet = new LineDataSet(invested, "Invested");
            investedSet.setColor(getResources().getColor(R.color.fade_black));
            investedSet.setCircleColor(Color.BLACK);
            investedSet.setLineWidth(graphDividerWidth);
            investedSet.setCircleRadius(3f);
            investedSet.setDrawCircleHole(false);
            investedSet.setValueTextSize(0f);
            investedSet.setDrawFilled(true);
            investedSet.setDrawCircles(false);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.color.sip_graph_invested_background);
                investedSet.setFillDrawable(drawable);
            } else {
                investedSet.setFillColor(Color.BLACK);
            }
        }

    }


    public void functionActual() {

        ArrayList<Entry> actual = createCurrentDataSet(rateOfReturn);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            actualSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            actualSet.setValues(actual);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            actualSet = new LineDataSet(actual, "Actual");
            actualSet.setColor(Color.WHITE);
            actualSet.setCircleColor(Color.BLACK);
            actualSet.setLineWidth(graphDividerWidth);
            actualSet.setCircleRadius(3f);
            actualSet.setDrawCircleHole(false);
            actualSet.setValueTextSize(0f);
            actualSet.setDrawCircles(false);
            actualSet.setDrawFilled(false);

            if (Utils.getSDKInt() >= 18) {

            } else {
                actualSet.setFillColor(Color.BLACK);
            }
        }
    }

    public void functionAggressive() {

        ArrayList<Entry> actualAggressive = createCurrentDataSet(rateOfReturnAgressive);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            actualAggressiveSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            actualAggressiveSet.setValues(actualAggressive);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            actualAggressiveSet = new LineDataSet(actualAggressive, "Aggressive");
            actualAggressiveSet.setColor(getResources().getColor(R.color.fade_black));
            actualAggressiveSet.setCircleColor(Color.BLACK);
            actualAggressiveSet.setLineWidth(graphDividerWidth);
            actualAggressiveSet.setCircleRadius(3f);
            actualAggressiveSet.setDrawCircleHole(false);
            actualAggressiveSet.setValueTextSize(0f);
            actualAggressiveSet.setDrawCircles(false);
            actualAggressiveSet.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.color.sip_graph_aggressive_background);
                actualAggressiveSet.setFillDrawable(drawable);
            } else {
                actualAggressiveSet.setFillColor(Color.BLACK);
            }
        }
    }

    public void functionVeryAggressive() {

        ArrayList<Entry> actualVeryAggressive = createCurrentDataSet(rateOfReturnVeryAggressive);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            actualVeryAggressiveSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            actualVeryAggressiveSet.setValues(actualVeryAggressive);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            actualVeryAggressiveSet = new LineDataSet(actualVeryAggressive, "Very Aggressive");
            actualVeryAggressiveSet.setColor(getResources().getColor(R.color.fade_black));
            actualVeryAggressiveSet.setCircleColor(Color.BLACK);
            actualVeryAggressiveSet.setLineWidth(graphDividerWidth);
            actualVeryAggressiveSet.setCircleRadius(3f);
            actualVeryAggressiveSet.setDrawCircleHole(false);
            actualVeryAggressiveSet.setValueTextSize(0f);
            actualVeryAggressiveSet.setDrawCircles(false);
            actualVeryAggressiveSet.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.color.sip_graph_very_aggressive_background);
                actualVeryAggressiveSet.setFillDrawable(drawable);
            } else {
                actualVeryAggressiveSet.setFillColor(Color.BLACK);
            }

        }

    }

    public void functionModerate() {
        ArrayList<Entry> actualModerate = createCurrentDataSet(rateOfReturnModerate);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            actualModerateSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            actualModerateSet.setValues(actualModerate);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            actualModerateSet = new LineDataSet(actualModerate, "Moderate");
            actualModerateSet.setColor(getResources().getColor(R.color.fade_black));
            actualModerateSet.setCircleColor(Color.BLACK);
            actualModerateSet.setLineWidth(graphDividerWidth);
            actualModerateSet.setCircleRadius(3f);
            actualModerateSet.setDrawCircleHole(false);
            actualModerateSet.setValueTextSize(0f);
            actualModerateSet.setDrawCircles(false);
            actualModerateSet.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.color.sip_graph_moderate_background);
                actualModerateSet.setFillDrawable(drawable);
            } else {
                actualModerateSet.setFillColor(Color.BLACK);
            }

        }
    }

    public void functionConservative() {
        ArrayList<Entry> actualConservative = createCurrentDataSet(rateOfReturnConservative);

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            actualConservativeSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            actualConservativeSet.setValues(actualConservative);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            actualConservativeSet = new LineDataSet(actualConservative, "Conservative");
            actualConservativeSet.setColor(getResources().getColor(R.color.fade_black));
            actualConservativeSet.setCircleColor(Color.BLACK);
            actualConservativeSet.setLineWidth(graphDividerWidth);
            actualConservativeSet.setCircleRadius(3f);
            actualConservativeSet.setDrawCircleHole(false);
            actualConservativeSet.setValueTextSize(0f);
            actualConservativeSet.setDrawCircles(false);
            actualConservativeSet.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mActivity, R.color.sip_graph_conservative_background);
                actualConservativeSet.setFillDrawable(drawable);
            } else {
                actualConservativeSet.setFillColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
//        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
//        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
//        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        Log.i("Entry selected", e.toString());
//        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
//        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

        } else {

            int i = h.getDataSetIndex();

            String selectedRisk = "";

            switch (i) {

                case 5:
                    selectedRate = rateOfReturn;
                    selectedRisk = "Invested";
                    break;
                case 3:
                    selectedRate = rateOfReturnConservative;
                    selectedRisk = "Conservative";
                    break;
                case 2:
                    selectedRate = rateOfReturnModerate;
                    selectedRisk = "Moderate";
                    break;
                case 4:
                    selectedRate = rateOfReturn;
                    selectedRisk = "Actual";
                    break;
                case 0:
                    selectedRate = rateOfReturnVeryAggressive;
                    selectedRisk = "Very Aggressive";
                    break;
                case 1:
                    selectedRate = rateOfReturnAgressive;
                    selectedRisk = "Aggressive";
                    break;

                default:
                    selectedRate = rateOfReturn;
                    break;
            }

            riskLabel.setText(selectedRisk + " (" + selectedRate + "%)");

            getSelectedValue(e);

        }
    }

    @Override
    public void onNothingSelected() {
//        Log.i("Nothing selected", "Nothing selected.");
    }


    public double sipCalculation(int y, int status, int rate) {
        sipCalculator.setYear(y);
        sipCalculator.setRate(rate);
        sipCalculator.setInvestment(monthlyInvestAmount);

        depositAmount = (monthlyInvestAmount * y * 12);

        actualAmount = sipCalculator.calculateSIP();


        if (status == 0) {
            return depositAmount;
        } else {
            return actualAmount;
        }
    }

    public void getSelectedValue(Entry e) {

        String s = Utils.formatNumber(e.getY(), 0, false);
        year = Integer.parseInt(Utils.formatNumber(e.getX(), 0, false));
        String temp = s;

        s = formatter.format(Double.parseDouble(s));
        returnValue.setText(s);
        String ss = Utils.formatNumber(e.getX(), 0, false);
        yearValue.setText(ss);
        currentInvestment = sipCalculation(Integer.parseInt(ss), 0, selectedRate);
        double currentAmount = Double.parseDouble(temp);
        double difference = currentAmount - currentInvestment;
        double percent = (difference * 100) / currentInvestment;

        s = Double.toString(difference);
        s = CurrencyValueFormatter.generalFormatter(s);
        s = formatter.format(Double.parseDouble(s));
        variationValue.setText("+" + s);
        percentageValue.setText("(" + (int) percent + "%)");
    }

    public void decrement() {

        String currentInvestmentTemp = amountValue.getText().toString();
        String currentInvestment = "";
        StringTokenizer st = new StringTokenizer(currentInvestmentTemp, ",");
        while (st.hasMoreTokens()) {

            currentInvestment += st.nextToken();
        }

        float currentInvestmentFloat = Float.parseFloat(currentInvestment);

        if (currentInvestmentFloat >= 2000) {

            currentInvestmentFloat -= investmentMargin;
        }

        String currentInvestment1 = formatter.format(currentInvestmentFloat);

        amountValue.setText(currentInvestment1);

    }

    public void increment() {

        String currentInvestmentTemp = amountValue.getText().toString();
        String currentInvestment = "";
        StringTokenizer st = new StringTokenizer(currentInvestmentTemp, ",");
        while (st.hasMoreTokens()) {

            currentInvestment += st.nextToken();
        }

        float currentInvestmentFloat = Float.parseFloat(currentInvestment);

        if (currentInvestmentFloat <= 49000) {

            currentInvestmentFloat += investmentMargin;
        }

        String currentInvestment1 = formatter.format(currentInvestmentFloat);

        amountValue.setText(currentInvestment1);
    }

    public void updateChartValues() {

        StringTokenizer stt = new StringTokenizer(amountValue.getText().toString(), ",");
        String customTemp = "";
        while (stt.hasMoreTokens()) {
            customTemp += stt.nextToken();
        }

        monthlyInvestAmount = Double.parseDouble(customTemp);
        yearValue.setText(Integer.toString(year));
        String temp = formatter.format(monthlyInvestAmount);
        amountValue.setText(temp);
        double value = sipCalculation(year, 1, selectedRate);
        temp = formatter.format(value);
        returnValue.setText(temp);
        double deposit = (monthlyInvestAmount * year * 12);
        double difference = (value - deposit);
        difference = (double) Math.round(difference * 100) / 100;
        double variation = (difference * 100) / deposit;
        temp = formatter.format(difference);
        variationValue.setText("+" + temp);
        percentageValue.setText("(" + (int) variation + "%)");

    }

    public class MyValueFormatter implements AxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String temp = Float.toString(value);

            temp = CurrencyValueFormatter.generalFormatter(temp);

            temp = CurrencyValueFormatter.currencyFormatter(temp);

            return temp;
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }

    }
}

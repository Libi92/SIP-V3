package com.geojit.libin.sip_v3.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.adapters.FundAdapter;
import com.geojit.libin.sip_v3.adapters.FundDetailsTabAdapter;
import com.geojit.libin.sip_v3.components.ChartMarkerFund;
import com.geojit.libin.sip_v3.components.RupeesTextView;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.excelreader.FundModal;
import com.geojit.libin.sip_v3.graphdatareader.GraphDailyData;
import com.geojit.libin.sip_v3.graphdatareader.GraphDataReader;
import com.geojit.libin.sip_v3.modals.FundDataModal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class FundDetailsFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    public static final String FUND = "fund";
    public static final String FUND_PERFORMANCE = "fund_performance";
    public static FundModal performanceModal;
    public FundDataModal modal;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView recyclerTopHolding;
    private LinearLayout fundDetailsLayout, mainLayout;
    private ArrayList<FundDataModal> data;
    private LineChart lineChartFund;
    private RupeesTextView textViewNAV;
    private SIPTextView textViewGrowth, textViewSchemeName;
    private float baseX, baseY;
    private int firsLayoutHeight;
    private int firstLayoutMinHeight;
    Handler m_handler;
    Runnable m_handlerTask;

    private final int PERMISSIONS_REQUEST_READ_STORAGE = 1001;
    private View view;

    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fund_details, container, false);


        mActivity = getActivity();
        initComponents();
        setToolbar();
        getInitialValues();
        setListeners();

        new CreateChartAsync().execute();

        return view;
    }

    private void initComponents() {
        modal = (FundDataModal) mActivity.getIntent().getSerializableExtra(FUND);
        performanceModal = (FundModal) mActivity.getIntent().getSerializableExtra(FUND_PERFORMANCE);

        if (performanceModal == null) {
            Log.e("performance Modal: ", "null");
        }

        textViewNAV = (RupeesTextView) view.findViewById(R.id.textViewNAV);
        textViewGrowth = (SIPTextView) view.findViewById(R.id.textViewGrowth);
        textViewSchemeName = (SIPTextView) view.findViewById(R.id.textViewSchemeName);
        fundDetailsLayout = (LinearLayout) view.findViewById(R.id.fundDetailsLayout);
        mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);
        textViewSchemeName.setText(modal.getSchemeName());
        textViewNAV.setText(modal.getNAV());
        textViewGrowth.setText(String.format("+%s (%s%%)", modal.getGrowth(), modal.getGrowthPercentage()));
        lineChartFund = (LineChart) view.findViewById(R.id.lineChartFund);
        recyclerTopHolding = (RecyclerView) view.findViewById(R.id.recyclerTopHolding);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        TabLayout.Tab tab1 = createTab("Top Holdings");
        setTabSelected(tab1);
        TabLayout.Tab tab2 = createTab("Performance");
        setTabUnSelected(tab2);
        TabLayout.Tab tab3 = createTab("Scheme Details");
        setTabUnSelected(tab3);
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.setOnTabSelectedListener(this);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        FundDetailsTabAdapter adapter = new FundDetailsTabAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

    }

    public void setListeners() {

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

        /*fundDetailsLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        baseX = event.getX();
                        baseY = event.getY();
                        Log.e("Clicked: Layout", "#######");

                        return true;
                    case MotionEvent.ACTION_MOVE:

                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if ((event.getY() - baseY) > 100) {

                            baseX = event.getX();
                            baseY = event.getY();
                            Log.e("Moved: Layout", "#######");

                            onShrink();
                        } else if ((baseY - event.getY()) > 100) {

                            baseX = event.getX();
                            baseY = event.getY();
                            Log.e("Moved: Layout", "#######");

                            onExpand();
                        }
                        return true;
                }
                return false;
            }
        });

        tabLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        baseX = event.getX();
                        baseY = event.getY();
                        Log.e("Clicked: Tab Layout", "#######");

                        return true;

                    case MotionEvent.ACTION_MOVE:

                        return true;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if ((event.getY() - baseY) > 100) {

                            baseX = event.getX();
                            baseY = event.getY();
                            Log.e("Moved: Tab Layout", "#######");

                            onShrink();

                        } else if ((baseY - event.getY()) > 100) {

                            baseX = event.getX();
                            baseY = event.getY();
                            Log.e("Moved: Tab Layout", "#######");

                            onExpand();
                        }
                        return true;
                }
                return false;
            }
        });*/

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
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
    }

    private void setTabUnSelected(TabLayout.Tab tab) {
        SIPTextView textView = (SIPTextView) tab.getCustomView().findViewById(R.id.tabTextView);
        textView.setTextColor(getResources().getColor(R.color.fade_white));
        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imageViewDrawerBack = (ImageView) toolbar.findViewById(R.id.imageViewDrawerBack);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        imageViewDrawerBack.setVisibility(View.VISIBLE);
        imageViewDrawerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        SIPTextView toolbarTitle = (SIPTextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
        toolbarTitle.setText(modal.getSchemeName());
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    private void setDataSet() {
        data = new ArrayList<>();

        FundDataModal modal = new FundDataModal();
        modal.setSchemeName("Large Company Stock");
        modal.setFundType("Featured");
        modal.setNAV("541.85");
        modal.setGrowth("5.95");
        modal.setGrowthPercentage("1.11");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Real Estate");
        modal.setFundType("Featured");
        modal.setNAV("541.85");
        modal.setGrowth("5.95");
        modal.setGrowthPercentage("1.11");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Corporate");
        modal.setFundType("Featured");
        modal.setNAV("541.85");
        modal.setGrowth("5.95");
        modal.setGrowthPercentage("1.11");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Emerging");
        modal.setFundType("Featured");
        modal.setNAV("541.85");
        modal.setGrowth("5.95");
        modal.setGrowthPercentage("1.11");
        data.add(modal);

        modal = new FundDataModal();
        modal.setSchemeName("Large Company Stock");
        modal.setFundType("Featured");
        modal.setNAV("541.85");
        modal.setGrowth("5.95");
        modal.setGrowthPercentage("1.11");
        data.add(modal);
    }

    private void setAdapter() {
        FundAdapter adapter = new FundAdapter(data, true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerTopHolding.setLayoutManager(layoutManager);
        recyclerTopHolding.setItemAnimator(new DefaultItemAnimator());
        recyclerTopHolding.setAdapter(adapter);
    }

    private void createChart() {
        int height = (int) getResources().getDimension(R.dimen.graph_height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        params.setMargins(-60, 0, -60, -60);
        lineChartFund.setLayoutParams(params);
        lineChartFund.setDrawGridBackground(false);
        lineChartFund.setDescription("");
        lineChartFund.setNoDataTextDescription("year wise data not available");
        lineChartFund.setTouchEnabled(true);
        lineChartFund.setDragEnabled(true);
        lineChartFund.setScaleEnabled(true);
        lineChartFund.setPinchZoom(true);
        lineChartFund.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ChartMarkerFund marker = new ChartMarkerFund(mActivity, R.layout.custom_marker_view);
        lineChartFund.setMarkerView(marker);
        XAxis xAxis = lineChartFund.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = lineChartFund.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        lineChartFund.getAxisRight().setEnabled(false);


        lineChartFund.animateX(500);
        lineChartFund.getLegend().setEnabled(false);
    }

    private ArrayList<Entry> createDataSet() {

        if (modal != null) {
            ArrayList<GraphDailyData> data = GraphDataReader.getData(mActivity, modal.getSchemeCode());
            ArrayList<Entry> values = new ArrayList<>();

            for (GraphDailyData item : data) {
                values.add(new Entry(item.getDate().getTime(), item.getNAV()));
            }

            return values;
        }


        return null;
    }

    private void setChartProperties(ArrayList<Entry> currentValues) {
        LineDataSet set1;

        if (lineChartFund.getData() != null &&
                lineChartFund.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChartFund.getData().getDataSetByIndex(0);
            set1.setValues(currentValues);

            lineChartFund.getData().notifyDataChanged();
            lineChartFund.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(currentValues, "NAV");

            set1.setColor(Color.WHITE);
            set1.setDrawCircles(false);
            set1.setLineWidth(2f);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFillDrawable(getResources().getDrawable(R.drawable.graph_fill));

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            lineChartFund.setData(data);

        }
    }

    public void getInitialValues() {

        lineChartFund.post(new Runnable() {
            @Override
            public void run() {

                firsLayoutHeight = lineChartFund.getHeight();
                firstLayoutMinHeight = (int) (0.30 * firsLayoutHeight);
            }
        });
    }

    public void onExpand() {

        Log.e("Expand: ", "expanded");
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = lineChartFund.getLayoutParams();
                if (lineChartFund.getHeight() > firstLayoutMinHeight) {
                    if ((lineChartFund.getHeight() - firstLayoutMinHeight) > 80) {
                        params.height = lineChartFund.getHeight() - 80;
                    } else {
                        params.height = firstLayoutMinHeight;
                    }
                    lineChartFund.setLayoutParams(params);
                } else {
                    m_handler.removeCallbacks(m_handlerTask);
                }
                m_handler.post(m_handlerTask);
            }
        };
        m_handlerTask.run();
    }

    public void onShrink() {

        Log.e("Shrink: ", "shrinked");
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (lineChartFund.getHeight() < firsLayoutHeight) {
                    ViewGroup.LayoutParams params = lineChartFund.getLayoutParams();
                    if ((firsLayoutHeight - lineChartFund.getHeight()) > 80) {
                        params.height = lineChartFund.getHeight() + 80;
                    } else {
                        params.height = firsLayoutHeight;
                    }
                    lineChartFund.setLayoutParams(params);
                } else {
                    m_handler.removeCallbacks(m_handlerTask);
                }
                m_handler.post(m_handlerTask);
            }
        };
        m_handlerTask.run();
    }

    private class CreateChartAsync extends AsyncTask<Void, Void, ArrayList<Entry>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Fetching Data");
            progressDialog.setMessage("Please wait..!");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Entry> doInBackground(Void... params) {
            ArrayList<Entry> result = createDataSet();
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Entry> data) {
            createChart();
            setChartProperties(data);
            getInitialValues();
            progressDialog.dismiss();
            super.onPostExecute(data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

}

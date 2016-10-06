package com.geojit.libin.sip_v3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.geojit.libin.sip_v3.components.SIPTextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FundsActivity extends AppCompatActivity {

    public static final String FUND_KEY = "fund";
    private final int PERMISSIONS_REQUEST_READ_STORAGE = 1001;

    private SIPTextView textViewTitle;
    private SIPTextView textViewCategory;
    private SIPTextView textViewMonthlyAmount;
    private SIPTextView textViewAllocation;
    private SIPTextView textViewInvestedAmount;
    private SIPTextView textViewCurrentValue;
    private SIPTextView textViewXIRR;
    private SIPTextView textViewCARG;
    private FundModal modal;
    private RadioGroup yearRadioGroup;
    private LineChart lineChartFund;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);

        mContext = FundsActivity.this;

        Toast.makeText(getApplicationContext(),"Fund Activity",Toast.LENGTH_SHORT).show();

        setToolbar();
        initComponents();
        setListners();

        createChart();

        ArrayList<Entry> curentValues = createCurrentDataSet();
        ArrayList<Entry> investedValues = createInvestedDataSet();
        setChartProperties(curentValues, investedValues);

    }

    private void setToolbar() {
//        ImageView imageViewDrawer = (ImageView) findViewById(R.id.imageViewDrawer);
//        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
    }

    private void initComponents() {
        textViewMonthlyAmount = (SIPTextView) findViewById(R.id.textViewMonthlyAmount);
        textViewAllocation = (SIPTextView) findViewById(R.id.textViewAllocation);
        textViewTitle = (SIPTextView) findViewById(R.id.textViewTitle);
        textViewCategory = (SIPTextView) findViewById(R.id.textViewCategory);
        textViewInvestedAmount = (SIPTextView) findViewById(R.id.textViewInvestedAmount);
        textViewCurrentValue = (SIPTextView) findViewById(R.id.textViewCurrentValue);
        textViewXIRR = (SIPTextView) findViewById(R.id.textViewXIRR);
        textViewCARG = (SIPTextView) findViewById(R.id.textViewCIGR);
        yearRadioGroup = (RadioGroup) findViewById(R.id.yearGroupLayout);

        lineChartFund = (LineChart) findViewById(R.id.lineChartFund);

        modal = (FundModal) getIntent().getSerializableExtra(FUND_KEY);
        if (modal != null) {
            textViewTitle.setText(modal.getSchemeName());
            textViewCategory.setText(modal.getCategory());
            textViewMonthlyAmount.setText(modal.getMonthlyAmount() + "");
            textViewAllocation.setText(modal.getAllocation());
            textViewInvestedAmount.setText(modal.getYear3().getTotalAmountInvested() + "");
            textViewCurrentValue.setText(modal.getYear3().getCurrentValue() + "");
            textViewXIRR.setText(modal.getYear3().getXIRR() + "");
            textViewCARG.setText(modal.getYear3().getCAGR() + "");
        }

    }

    private void setListners() {

        yearRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton3) {
                    textViewInvestedAmount.setText(modal.getYear3().getTotalAmountInvested() + "");
                    textViewCurrentValue.setText(modal.getYear3().getCurrentValue() + "");
                    textViewXIRR.setText(modal.getYear3().getXIRR() + "");
                    textViewCARG.setText(modal.getYear3().getCAGR() + "");
                } else if (checkedId == R.id.radioButton5) {
                    textViewInvestedAmount.setText(modal.getYear5().getTotalAmountInvested() + "");
                    textViewCurrentValue.setText(modal.getYear5().getCurrentValue() + "");
                    textViewXIRR.setText(modal.getYear5().getXIRR() + "");
                    textViewCARG.setText(modal.getYear5().getCAGR() + "");
                } else {
                    textViewInvestedAmount.setText(modal.getYear10().getTotalAmountInvested() + "");
                    textViewCurrentValue.setText(modal.getYear10().getCurrentValue() + "");
                    textViewXIRR.setText(modal.getYear10().getXIRR() + "");
                    textViewCARG.setText(modal.getYear10().getCAGR() + "");
                    textViewCARG.setText(modal.getYear10().getCAGR() + "");
                }
            }
        });
    }

    private void createChart() {
        lineChartFund.setDrawGridBackground(false);
        lineChartFund.setDescription("");
        lineChartFund.setNoDataTextDescription("year wise data not available");
        lineChartFund.setTouchEnabled(true);
        lineChartFund.setDragEnabled(true);
        lineChartFund.setScaleEnabled(true);
        lineChartFund.setPinchZoom(true);
        lineChartFund.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//        ChartMarker marker = new ChartMarker(this, R.layout.custom_marker_view);
//        lineChartFund.setMarkerView(marker);
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

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.menu_item_share) {

            if (Build.VERSION.SDK_INT > 22) {

                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_STORAGE);

                } else {
                    shareScreen();
                }

            } else {
                shareScreen();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareScreen();

                } else {

                    Toast.makeText(getApplicationContext(), "Please grand permission", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    private void shareScreen() {
        File[] files = getCacheDir().listFiles();
        for (File file : files) {
            file.delete();
        }
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap icon = Bitmap.createBitmap(v1.getDrawingCache());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File fDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "SIP");
        if(!fDirectory.exists()){
            fDirectory.mkdir();
        }
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "SIP" + File.separator + "geojit_sip.jpg");
        try {
            if(f.exists()){
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(f);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}

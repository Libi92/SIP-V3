package com.geojit.libin.sip_v3.components;

import android.content.Context;
import android.widget.TextView;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.fragments.AdvancedSipFragment;
import com.geojit.libin.sip_v3.utils.CurrencyValueFormatter;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by 10946 on 8/9/2016.
 */
public class ChartMarkerFund extends MarkerView {
    private TextView tvContent;

    public ChartMarkerFund(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

        } else {

            String s = Utils.formatNumber(e.getY(), 0, true);
            String temp = "";
            StringTokenizer st = new StringTokenizer(s, ".");
            while (st.hasMoreTokens()) {

                temp = temp + st.nextToken();
            }
            s = CurrencyValueFormatter.generalFormatter(temp);
            s = AdvancedSipFragment.formatter.format(Double.parseDouble(s));

            long time = (long) e.getX();
            Date date = new Date(time);
            SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
            String dateText = df2.format(date);
            tvContent.setText(String.format("NAV %s on %s", s, dateText));

//            tvContent.setText("" + s + " X: " + e.getData());
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}

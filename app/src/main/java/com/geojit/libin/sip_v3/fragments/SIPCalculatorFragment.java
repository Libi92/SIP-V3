package com.geojit.libin.sip_v3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.RupeesTextView;
import com.geojit.libin.sip_v3.components.SIPEditText;
import com.geojit.libin.sip_v3.components.SIPTextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by 10945 on 7/28/2016.
 */
public class SIPCalculatorFragment extends Fragment {
    private static int amountSeekStep = 500;
    private View view;
    private SeekBar seekBarAmount;
    private SeekBar seekBarReturnRate;
    private SIPEditText editTextAmount;
    private SIPEditText editTextReturnRate;
    private RupeesTextView textView3YearAmount;
    private RupeesTextView textView5YearAmount;
    private RupeesTextView textView10YearAmount;
    private ProgressBar progressBar3Year;
    private ProgressBar progressBar5Year;
    private ProgressBar progressBar10Year;
    private DecimalFormat formatter = new DecimalFormat("##,##,##,###");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sip_calculator, container, false);

        initComponents();
        setToolbar();
        setListners();
        calculateEvent();

        return view;
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imageViewDrawerBack = (ImageView) toolbar.findViewById(R.id.imageViewDrawerBack);
        imageViewDrawerBack.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu_icon);
        SIPTextView toolbarTitle = (SIPTextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.sip_calculator_label));
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initComponents() {
        seekBarAmount = (SeekBar) view.findViewById(R.id.seekBarAmount);
        seekBarReturnRate = (SeekBar) view.findViewById(R.id.seekBarReturnRate);

        editTextAmount = (SIPEditText) view.findViewById(R.id.editTextAmount);
        editTextReturnRate = (SIPEditText) view.findViewById(R.id.editTextReturnRate);

        textView3YearAmount = (RupeesTextView) view.findViewById(R.id.textView1YearAmount);
        textView5YearAmount = (RupeesTextView) view.findViewById(R.id.textView3YearAmount);
        textView10YearAmount = (RupeesTextView) view.findViewById(R.id.textView5YearAmount);

        progressBar3Year = (ProgressBar) view.findViewById(R.id.progressBar3Year);
        progressBar5Year = (ProgressBar) view.findViewById(R.id.progressBar5Year);
        progressBar10Year = (ProgressBar) view.findViewById(R.id.progressBar10Year);
    }

    private void setListners() {
        seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String icon = getResources().getString(R.string.rs);

                editTextAmount.setText(String.format(Locale.UK, "%s %s", icon, formatter.format(progress * amountSeekStep)));

                calculateEvent();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarReturnRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                editTextReturnRate.setText(progress + "%");

                calculateEvent();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editTextAmount.setKeyboardEventListener(new SIPEditText.KeyboardEventListener() {
            @Override
            public void onKeyboardClosed() {
                Log.e("editTextAmount", "keyBoardClose");
                handleAmountChange();
            }
        });

        editTextAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("editTextAmount", "onFocusChange");
                handleAmountChange();
            }
        });

        editTextReturnRate.setKeyboardEventListener(new SIPEditText.KeyboardEventListener() {
            @Override
            public void onKeyboardClosed() {
                Log.e("editTextReturnRate", "keyBoardClose");
                handleReturnRate();
            }
        });

        editTextReturnRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("editTextReturnRate", "onFocusChange");
                handleReturnRate();
            }
        });

        editTextReturnRate.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleReturnRate();
                    return true;
                }
                return false;
            }
        });
    }

    public void handleAmountChange() {
        String value = editTextAmount.getText().toString();

        String icon = getResources().getString(R.string.rs);

        try {
            char start = value.charAt(0);

            if (start != icon.charAt(0)) {
                if (start != ' ') {
                    editTextAmount.setText(icon + " " + value);
                } else {
                    editTextAmount.setText(icon + value);
                }
            } else if (value.charAt(1) != ' ') {
                value = icon + " " + value.substring(1);
                editTextAmount.setText(value);
            }

            value = editTextAmount.getText().toString();
            try {
                String amt = value.split(" ")[1];
                int progress = formatter.parse(amt).intValue();
                Log.e("Progress Amount", progress + "");
                progress = Math.round(progress / amountSeekStep);
                seekBarAmount.setProgress(progress);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            value = String.format("%s %s", icon, "0");
            editTextAmount.setText(value);
        }
    }

    public void handleReturnRate() {
        String value = editTextReturnRate.getText().toString();
        int length = value.length();
        try {
            if (value.charAt(length - 1) != '%') {
                value += '%';
            }

            length = value.length();
            try {
                String val = value.substring(0, length - 1);
                Integer.parseInt(val);
            } catch (Exception e) {
                value = "0%";
            }

            length = value.length();
            String val = value.substring(0, length - 1);
            int progress = Integer.parseInt(val);
            Log.e("Progress Rate", progress + "");
            seekBarReturnRate.setProgress(progress);
        } catch (Exception ex) {
            value = "0%";
        }

        editTextReturnRate.setText(value);
    }

    private double[] calculateSIP(double investAmount, double rateOfReturn, double year) {
        double i = (rateOfReturn / 100d) / 12d;
        double n = year * 12d;

        double iPlus_1 = (1 + i);
        double iPlus_1Rais_n = Math.pow(iPlus_1, n);
        double pow_by_i = (iPlus_1Rais_n - 1d) / i;
        double returnAmount = investAmount * pow_by_i;
        returnAmount *= iPlus_1;

        double totalInvest = investAmount * year * 12;
        return new double[]{totalInvest, returnAmount};
    }

    private void calculateEvent() {

        String amountString = editTextAmount.getText().toString();
        amountString = amountString.split(" ")[1];
        String returnString = editTextReturnRate.getText().toString();
        int length = returnString.length();
        returnString = returnString.substring(0, length - 1);

        double amount;
        try {
            amount = formatter.parse(amountString).doubleValue();

            double returnRate = Double.parseDouble(returnString);
            double year = 3d;

            double[] result = calculateSIP(amount, returnRate, year);
            long returnAmount = Math.round(result[1]);

            textView3YearAmount.setText(formatter.format(returnAmount));

            int progress = (int) returnAmount;
            progress = Math.round(progress);
            progressBar3Year.setProgress(progress);
            progress = (int) Math.round(result[0]);
            progressBar3Year.setSecondaryProgress(progress);

            year = 5d;
            result = calculateSIP(amount, returnRate, year);
            returnAmount = Math.round(result[1]);
            textView5YearAmount.setText(formatter.format(returnAmount));

            progress = (int) returnAmount;
            progress = Math.round(progress);
            progressBar5Year.setProgress(progress);
            progress = (int) Math.round(result[0]);
            progressBar5Year.setSecondaryProgress(progress);

            year = 10d;
            result = calculateSIP(amount, returnRate, year);
            returnAmount = Math.round(result[1]);
            textView10YearAmount.setText(formatter.format(returnAmount));

            progress = (int) returnAmount;
            progress = Math.round(progress);
            progressBar10Year.setProgress(progress);
            progress = (int) Math.round(result[0]);
            progressBar10Year.setSecondaryProgress(progress);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

package com.geojit.libin.sip_v3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.RupeesTextView;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.utils.SIPCalculator;

import java.text.DecimalFormat;

/**
 * Created by 10945 on 8/8/2016.
 */
public class EMICalculatorFragment extends Fragment {

    public static DecimalFormat formatter = new DecimalFormat("##,##,##,###");
    private RupeesTextView monthlyInvestmentLabel, goalValueLabel;
    private TextView monthlyHeading, goalLabel, yearLabel, goalYearLabel, growthLabel, growthValueLabel;
    private SeekBar seekBarGrowth, seekBarYear, seekBarAmount;
    private SIPCalculator emiCalculator = new SIPCalculator();
    private int goalSeekProgress = 0, yearSeekProgress = 0, growthSeekProgress = 0;
    private int minimumGoalValue = 50000, minimumGrowthValue = 5, minimumYearValue = 1, goalThreshold = 50000;
    private double monthlyInvestmentAmount = 0;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_emi_calculator, container, false);

        setToolbar();
        initComponents();
        setListeners();
        initValues();

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
        toolbarTitle.setText(getResources().getString(R.string.emi_calculator_label));
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    public void initComponents() {

        monthlyHeading = (TextView) view.findViewById(R.id.monthlyHeading);
        monthlyInvestmentLabel = (RupeesTextView) view.findViewById(R.id.monthlyInvestmentLabel);
        goalLabel = (TextView) view.findViewById(R.id.goalLabel);
        goalValueLabel = (RupeesTextView) view.findViewById(R.id.goalValueLabel);
        yearLabel = (TextView) view.findViewById(R.id.yearLabel);
        goalYearLabel = (TextView) view.findViewById(R.id.goalYearLabel);
        growthLabel = (TextView) view.findViewById(R.id.growthLabel);
        growthValueLabel = (TextView) view.findViewById(R.id.growthValueLabel);
        seekBarAmount = (SeekBar) view.findViewById(R.id.seekBarAmount);
        seekBarGrowth = (SeekBar) view.findViewById(R.id.seekBarGrowth);
        seekBarYear = (SeekBar) view.findViewById(R.id.seekBarYear);

    }


    public void initValues() {

        goalSeekProgress = minimumGoalValue+seekBarAmount.getProgress() * goalThreshold;
        yearSeekProgress = minimumYearValue+seekBarYear.getProgress();
        growthSeekProgress = minimumGrowthValue+seekBarGrowth.getProgress();

        String currentGoalValue = formatter.format(goalSeekProgress);

        goalValueLabel.setText(currentGoalValue);
        goalYearLabel.setText(Integer.toString(yearSeekProgress));
        growthValueLabel.setText(Integer.toString(growthSeekProgress) + "%");
    }

    public void setListeners() {

        seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {

                    goalSeekProgress = minimumGoalValue;


                } else {

                    goalSeekProgress = minimumGoalValue + (progress * goalThreshold);
                }

                String result = formatter.format(goalSeekProgress);

                goalValueLabel.setText(result);

                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGrowth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                growthSeekProgress = progress + minimumGrowthValue;

                growthValueLabel.setText(Integer.toString(growthSeekProgress) + "%");

                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarYear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                yearSeekProgress = progress + minimumYearValue;

                goalYearLabel.setText(Integer.toString(yearSeekProgress));

                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calculate() {

        emiCalculator.setYear(yearSeekProgress);
        emiCalculator.setAmount(goalSeekProgress);
        emiCalculator.setRate(growthSeekProgress);

        monthlyInvestmentAmount = emiCalculator.calculateEMI();

        String result = formatter.format(monthlyInvestmentAmount);

        monthlyInvestmentLabel.setText(result);
    }

}

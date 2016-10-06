package com.geojit.libin.sip_v3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.geojit.libin.sip_v3.adapters.SlidePagerAdapter;
import com.geojit.libin.sip_v3.utils.TransitionHelper;

public class TutorialActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ImageView[] buttons;
    private Button buttonSkip;
    private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tutorial);

        initComponents();
        setListners();
        setViewPager();
    }

    private void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.viewPagerSlides);

        ImageView button1 = (ImageView) findViewById(R.id.btn1);
        ImageView button2 = (ImageView) findViewById(R.id.btn2);
        ImageView button3 = (ImageView) findViewById(R.id.btn3);
        ImageView button4 = (ImageView) findViewById(R.id.btn4);

        buttons = new ImageView[]{button1, button2, button3, button4};

        buttonSkip = (Button) findViewById(R.id.buttonSkip);
        buttonDone = (Button) findViewById(R.id.buttonDone);
    }

    private void setListners() {
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
    }

    private void setViewPager() {
        SlidePagerAdapter adapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (ImageView btn : buttons) {
                    if (TransitionHelper.isBelow_21()) {
                        btn.setImageDrawable(getResources().getDrawable(R.mipmap.holo_circle));
                    } else {
                        btn.setImageDrawable(getResources().getDrawable(R.mipmap.holo_circle, getTheme()));
                    }
                }

                if (TransitionHelper.isBelow_21()) {
                    buttons[position].setImageDrawable(getResources().getDrawable(R.mipmap.fill_circle));
                } else {
                    buttons[position].setImageDrawable(getResources().getDrawable(R.mipmap.fill_circle, getTheme()));
                }

                if (position == buttons.length - 1) {
                    buttonSkip.setEnabled(false);
                    buttonDone.setEnabled(true);
                    buttonSkip.setTextColor(getResources().getColor(R.color.gray2X));
                    buttonDone.setTextColor(getResources().getColor(android.R.color.white));
                } else {
                    buttonSkip.setEnabled(true);
                    buttonDone.setEnabled(false);
                    buttonSkip.setTextColor(getResources().getColor(android.R.color.white));
                    buttonDone.setTextColor(getResources().getColor(R.color.gray2X));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void goToHome() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TutorialActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 300);
    }
}

package com.geojit.libin.sip_v3;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.geojit.libin.sip_v3.adapters.PortfolioPagerAdapter;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.utils.TransitionHelper;

public class PortfolioActivity extends AppCompatActivity {

    private ViewPager portfolioPager;
    private ImageView[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        initComponents();
        setToolbar();
        setViewPager();
    }

    public void initComponents(){

        portfolioPager = (ViewPager) findViewById(R.id.portfolioPager);
        ImageView button1 = (ImageView) findViewById(R.id.load1);
        ImageView button2 = (ImageView) findViewById(R.id.load2);

        buttons = new ImageView[]{button1, button2};


    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imageViewDrawerBack = (ImageView) toolbar.findViewById(R.id.imageViewDrawerBack);
        imageViewDrawerBack.setVisibility(View.VISIBLE);
        imageViewDrawerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SIPTextView toolbarTitle = (SIPTextView) toolbar.findViewById(R.id.toolbarTitle);
        toolbar.setTitle("");
        //toolbarTitle.setText(schemeName);
        toolbarTitle.setText(R.string.portfolio_label);
        toolbar.setBackgroundColor(getResources().getColor(R.color.geojit_green));
        setSupportActionBar(toolbar);
    }

    public void setViewPager(){

        PortfolioPagerAdapter adapter = new PortfolioPagerAdapter(getSupportFragmentManager());
        portfolioPager.setAdapter(adapter);

        portfolioPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        new MenuInflater(getApplicationContext()).inflate(R.menu.portfolio_menu,menu);

        MenuItem item = menu.findItem(R.id.menuCancel);

        item.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }*/
}

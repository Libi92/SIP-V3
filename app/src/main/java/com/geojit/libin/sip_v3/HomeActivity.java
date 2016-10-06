package com.geojit.libin.sip_v3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.geojit.libin.sip_v3.adapters.MenuAdapter;
import com.geojit.libin.sip_v3.components.CircularImageView;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.fragments.EMICalculatorFragment;
import com.geojit.libin.sip_v3.fragments.HomeFragment;
import com.geojit.libin.sip_v3.fragments.ProfileFragment;
import com.geojit.libin.sip_v3.fragments.SipFragment;
import com.geojit.libin.sip_v3.fragments.TopFundFragment;
import com.geojit.libin.sip_v3.utils.DividerItemDecoration;
import com.geojit.libin.sip_v3.utils.PreferenceUtils;
import com.geojit.libin.sip_v3.utils.RecyclerTouchListener;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private static final int rippleDelay = 300;
    private static final String API_KEY = "AIzaSyAyLQBEDA0egw28Wh1T7JsKTSdLAcwMwMA";
    private static final String VIDEO_INDEX = "_ovxpXA8gVg";
    private final int PERMISSIONS_REQUEST_READ_STORAGE = 1001;
    private int backCount = 0;
    private Button buttonSignIn;
    private RecyclerView recyclerViewMenu;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private CircularImageView imageViewProfile;
    private View userView;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setToolBar();
        setDrawer();
        setListners();

        HomeFragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
    }

    private void setDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        buttonSignIn = (Button) navigationView.findViewById(R.id.buttonSignIn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerViewMenu = (RecyclerView) navigationView.findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(layoutManager);

        String[] menu = getResources().getStringArray(R.array.menu);
        MenuAdapter menuAdapter = new MenuAdapter(menu);

        recyclerViewMenu.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayout.VERTICAL, R.drawable.menu_divider));
        recyclerViewMenu.setAdapter(menuAdapter);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.menu_icon);

        ImageView closeButton = (ImageView) navigationView.findViewById(R.id.drawer_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        userView = navigationView.findViewById(R.id.userOpt);
        View loginView = navigationView.findViewById(R.id.signInOpt);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            loginView.setVisibility(View.GONE);
            userView.setVisibility(View.VISIBLE);

            SIPTextView textViewUser = (SIPTextView) findViewById(R.id.textViewUser);

            PreferenceUtils preferenceUtils = PreferenceUtils.getInstance(HomeActivity.this);
            String name = preferenceUtils.getProfileName();
            textViewUser.setText(name);

            imageViewProfile = (CircularImageView) navigationView.findViewById(R.id.imageViewProfile);
            if (imageViewProfile != null) {

                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    getImageFromFile();

                } else {
                    String url = preferenceUtils.getProfileImage();

                    if (url != PreferenceUtils.DEFAULT_STRING) {
                        Log.e("URL", url);
                        Picasso.with(HomeActivity.this).load(url).resize(40, 40).placeholder(R.mipmap.profile_placeholder).into(imageViewProfile);

                    }
                }

            }
        }
    }

    private void getImageFromFile() {
        String imagePath = getFilesDir().getAbsolutePath() + File.separator + PreferenceUtils.fileName;

        File imgFile = new File(imagePath);

        if (imgFile.exists()) {

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageViewProfile.setImageBitmap(bitmap);
        }
    }

    private void setListners() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SplashActivity.class);
                finish();
            }
        });

        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.profileContainer, new ProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                isEditMode = true;
            }
        });

        recyclerViewMenu.addOnItemTouchListener(new RecyclerTouchListener(HomeActivity.this, recyclerViewMenu, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = new EMICalculatorFragment();
                        showView(fragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        fragment = new SipFragment();
                        showView(fragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        Intent intent = new Intent(HomeActivity.this, PortfolioActivity.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        fragment = new TopFundFragment();
                        showView(fragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case 6:
                        addInfoView();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (isEditMode) {
                    onBackPressed();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void startActivity(final Class destination) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HomeActivity.this, destination);
                startActivity(intent);
            }
        }, rippleDelay);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (isEditMode) {
                super.onBackPressed();
                isEditMode = false;

                getImageFromFile();

                return;
            } else {
                drawer.closeDrawer(GravityCompat.START);
                return;
            }

        } else {
            if (isEditMode) {
                super.onBackPressed();
                isEditMode = false;
                return;
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            if (backCount < 1) {
                View view = getWindow().getDecorView();
                Snackbar.make(view, "Press back button again to exit", Snackbar.LENGTH_SHORT).show();
                backCount++;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backCount = 0;
                    }
                }, 3000);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
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

        } else if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        View screenView = getWindow().getDecorView().getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap icon = Bitmap.createBitmap(screenView.getDrawingCache());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        screenView.setDrawingCacheEnabled(false);
        File fDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "SIP");
        if (!fDirectory.exists()) {
            fDirectory.mkdir();
        }
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "SIP" + File.separator + "geojit_sip1.jpg");
        try {
            if (f.exists()) {
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

    private void addInfoView() {
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(VIDEO_INDEX);
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

    }
}

package com.geojit.libin.sip_v3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.utils.PreferenceUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final int RC_SIGN_IN = 1003;
    private final int RC_WRITE_EXTERNAL_STORAGE = 1004;

    private SIPTextView buttonSkip;
    private Button buttonLogin;
    private ImageView buttonGoogle;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initComponents();
        setListners();

        initGoogle();

    }

    private void initComponents() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSkip = (SIPTextView) findViewById(R.id.buttonSkip);
        buttonGoogle = (ImageView) findViewById(R.id.buttonGoogle);


    }

    private void initGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount acct = result.getSignInAccount();
            String token = acct.getIdToken();
            if (token != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SplashActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    String name = acct.getDisplayName();
                                    imageUri = acct.getPhotoUrl();

                                    PreferenceUtils preferenceUtils = PreferenceUtils.getInstance(SplashActivity.this);
                                    if (name != null) {
                                        preferenceUtils.saveProfileName(name);
                                        Toast.makeText(getApplicationContext(), String.format("Signed in as %s", name), Toast.LENGTH_SHORT).show();
                                    }

                                    if (imageUri != null) {

                                        preferenceUtils.saveProfileImage(imageUri.toString());

                                        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                != PackageManager.PERMISSION_GRANTED) {

                                            ActivityCompat.requestPermissions(SplashActivity.this,
                                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    RC_WRITE_EXTERNAL_STORAGE);
                                        } else {
                                            new LoadProfileImage().execute();
                                            goToTutorial();
                                        }
                                    } else {

                                        goToTutorial();
                                    }


                                }
                            }
                        });
            }

        } else {
            Toast.makeText(getApplicationContext(), "Error in connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTutorial();
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTutorial();
            }
        });
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }


    private void goToTutorial() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, TutorialActivity.class);
                startActivity(intent);
                finish();
            }
        }, 300);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == RC_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new LoadProfileImage().execute();
            } else {
                Toast.makeText(getApplicationContext(), "Please grand the permission", Toast.LENGTH_SHORT).show();
            }

            goToTutorial();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... urls) {
            String urldisplay = imageUri.toString();
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

                FileOutputStream outputStream = new FileOutputStream(getFilesDir().getPath() + File.separator + PreferenceUtils.fileName);
                mIcon11.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

    }
}

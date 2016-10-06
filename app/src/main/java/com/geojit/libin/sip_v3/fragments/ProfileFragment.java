package com.geojit.libin.sip_v3.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.CircularImageView;
import com.geojit.libin.sip_v3.components.SIPButton;
import com.geojit.libin.sip_v3.components.SIPEditText;
import com.geojit.libin.sip_v3.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by 10945 on 8/12/2016.
 */
public class ProfileFragment extends Fragment {

    private static final int START_YEAR = 1980, START_MONTH = 1, START_DATE = 1;
    private static final int CAMERA_PIC_REQUEST = 1003;
    private View view;
    private CircularImageView imageViewProfile;
    private Activity mActivity;
    private SIPEditText editTextName, editTextDate;
    private SIPButton buttonSaveProfile;
    private Bitmap mBitMap = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initComponents();
        setListners();
        return view;
    }

    private void initComponents() {
        mActivity = getActivity();

        imageViewProfile = (CircularImageView) view.findViewById(R.id.imageViewProfile);
        editTextName = (SIPEditText) view.findViewById(R.id.editTextName);
        editTextDate = (SIPEditText) view.findViewById(R.id.editTextDate);
        buttonSaveProfile = (SIPButton) view.findViewById(R.id.buttonSaveProfile);

        PreferenceUtils preferenceUtils = PreferenceUtils.getInstance(mActivity);
        String name = preferenceUtils.getProfileName();
        if (name != null) {
            editTextName.setText(name);
        }

        String dob = preferenceUtils.getDOB();
        if (name != null) {
            editTextDate.setText(dob);
        }

        if (imageViewProfile != null) {

            String imagePath = mActivity.getFilesDir().getAbsolutePath() + File.separator + PreferenceUtils.fileName;

            File imgFile = new File(imagePath);
            if (ContextCompat.checkSelfPermission(mActivity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && imgFile.exists()) {

                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageViewProfile.setImageBitmap(bitmap);

            } else {
                preferenceUtils = PreferenceUtils.getInstance(mActivity);
                String url = preferenceUtils.getProfileImage();

                if (url != PreferenceUtils.DEFAULT_STRING) {
                    Log.e("URL", url);
                    Picasso.with(mActivity).load(url).resize(40, 40).placeholder(R.mipmap.profile_placeholder).into(imageViewProfile);
                }
            }

        }
    }

    private void setListners() {
        editTextDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String date = String.format("%02d/%02d/%d", day, month + 1, year);
                            editTextDate.setText(date);
                        }
                    }, START_YEAR, START_MONTH, START_DATE).show();
                }

                return true;
            }
        });

        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String dob = editTextDate.getText().toString();

                PreferenceUtils preferenceUtils = PreferenceUtils.getInstance(mActivity);
                preferenceUtils.saveProfileName(name);
                preferenceUtils.saveDOB(dob);

                FileOutputStream outputStream = null;
                try {

                    String filePath = mActivity.getFilesDir().getAbsolutePath() + File.separator + PreferenceUtils.fileName;

                    if (mBitMap != null) {
                        outputStream = new FileOutputStream(filePath);
                        mBitMap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Toast.makeText(mActivity, "Profile Updated", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            mBitMap = (Bitmap) data.getExtras().get("data");
            imageViewProfile.setImageBitmap(mBitMap);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(mActivity, "Event Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}

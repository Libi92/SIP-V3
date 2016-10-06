package com.geojit.libin.sip_v3.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by libin on 22/07/16.
 */
public class PreferenceUtils {
    public static final String DEFAULT_STRING = "null";
    public static final String fileName = "profile.jpg";
    private static final String IMAGE_DIR = "resipe";
    private static final String PREF_NAME = "GEOJIT_SIP";
    private static final String IMAGE_KEY = "PROFILE_IMAGE";
    private static final String NAME_KEY = "PROFILE_NAME";
    private static final String DOB_KEY = "PROFILE_DOB";
    private static PreferenceUtils preferenceUtils;
    private SharedPreferences preferences;

    private PreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null) {
            preferenceUtils = new PreferenceUtils(context);
        }

        return preferenceUtils;
    }

//    public static String getImageDir() {
//        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + IMAGE_DIR;
//    }

    public void saveProfileImage(String uri) {
        preferences.edit().putString(IMAGE_KEY, uri).apply();
    }

    public String getProfileImage() {
        return preferences.getString(IMAGE_KEY, DEFAULT_STRING);
    }

    public void saveProfileName(String name) {
        preferences.edit().putString(NAME_KEY, name).apply();
    }

    public String getProfileName() {
        return preferences.getString(NAME_KEY, DEFAULT_STRING);
    }

    public void saveDOB(String dob) {
        preferences.edit().putString(DOB_KEY, dob).apply();
    }

    public String getDOB() {
        return preferences.getString(DOB_KEY, DEFAULT_STRING);
    }
}

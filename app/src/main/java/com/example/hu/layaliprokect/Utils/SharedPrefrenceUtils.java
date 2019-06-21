package com.example.hu.layaliprokect.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceUtils {
    private String TAG = SharedPrefrenceUtils.class.getSimpleName();
    public static final String SHARED_PREFS_FILE = "SAVE";

    public static void setUnitName(Context context, String unitName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("unitName", unitName);
        editor.commit();
    }

    public static String getUnitName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("unitName", "");
    }

    public static void removeUnitName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("unitName");
        editor.commit();
    }

    public static void setPeopleName(Context context, String peopleName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("peopleName", peopleName);
        editor.commit();
    }

    public static String getPeopleName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("peopleName", "");
    }

    public static void removePeopleName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("peopleName");
        editor.commit();
    }
}

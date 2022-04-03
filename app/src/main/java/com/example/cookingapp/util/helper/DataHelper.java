package com.example.cookingapp.util.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.util.setting.AppSetting;

public class DataHelper {
    public static <T extends AppCompatActivity> SharedPreferences getPref(T activity) {
        return activity.getSharedPreferences(AppSetting.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public static <T extends AppCompatActivity> void putPrefString(String key, String value, T activity) {
        getPref(activity).edit()
            .putString(key, value)
            .apply();
    }

    public static <T extends AppCompatActivity> String getPrefString(String key, T activity, String defaultValue) {
        return getPref(activity).getString(key, defaultValue);
    }

    public static <T extends AppCompatActivity> String getPrefString(String key, T activity) {
        return getPrefString(key, activity, "");
    }
}

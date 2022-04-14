package com.example.cookingapp.util.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.cookingapp.util.setting.AppSetting;

public class DataHelper {
    public static <T extends ContextWrapper> SharedPreferences getPref(@NonNull T context) {
        return context.getSharedPreferences(AppSetting.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public static <T extends ContextWrapper> void putPrefString(String key, String value, T context) {
        getPref(context).edit()
            .putString(key, value)
            .apply();
    }

    public static <T extends ContextWrapper> String getPrefString(String key, T context, String defaultValue) {
        return getPref(context).getString(key, defaultValue);
    }

    public static <T extends ContextWrapper> String getPrefString(String key, T context) {
        return getPrefString(key, context, "");
    }

    public static <T extends ContextWrapper> void deleteSharedPreferences(T context) {
        context.deleteSharedPreferences(AppSetting.SHARED_PREFERENCE_FILE);
    }
}

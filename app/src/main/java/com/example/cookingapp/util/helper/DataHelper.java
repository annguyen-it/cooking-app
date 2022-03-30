package com.example.cookingapp.util.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.util.setting.AppSetting;

public class DataHelper {
    public static <T extends AppCompatActivity> SharedPreferences getPreferences(T activity) {
        return activity.getSharedPreferences(AppSetting.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
    }
}

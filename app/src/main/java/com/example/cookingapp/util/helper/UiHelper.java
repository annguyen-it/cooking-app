package com.example.cookingapp.util.helper;

import android.content.Context;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UiHelper {
    public static <T extends AppCompatActivity> void hideActionBar(T activity) {
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public static <T extends AppCompatActivity> void displayActionBarNavigateBackButton(T activity) {
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static <T extends AppCompatActivity> void hideKeyboard(T activity) {
        try {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception ignored) { }
    }

    public static Uri getUri(@IdRes int id) {
        return Uri.parse("android..resource://com.example.cookingapp" + id);
    }
}

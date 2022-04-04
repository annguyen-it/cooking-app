package com.example.cookingapp.util.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UiHelper {
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
        catch (Exception ignored) {
        }
    }

    public static Uri getUri(Context context, int id) {
        final Resources resources = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                         + "://" + resources.getResourcePackageName(id)
                         + "/" + resources.getResourceTypeName(id)
                         + "/" + resources.getResourceEntryName(id));
    }
}

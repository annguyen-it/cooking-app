package com.example.cookingapp.util.helper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.service.http.FileService;
import com.example.cookingapp.service.http.HttpService;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static <T extends Activity> void hideKeyboard(T activity) {
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

    public static void setImageBitmap(ImageView imageView, InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView.setImageBitmap(bitmap);
    }

    public static <A extends ContextWrapper> void setImageBitmapAsync(ImageView imageView, String imageName, A context) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                final InputStream inputStream = response.body().byteStream();
                setImageBitmap(imageView, inputStream);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) { }
        };
        new HttpService<>(context).instance(FileService.class)
            .getByName(imageName)
            .enqueue(callback);
    }

    public static void fade(View view, int duration) {
        fade(view, duration, true);
    }

    public static void fade(View view, int duration, boolean fadeOut) {
        AlphaAnimation animate = fadeOut
                                 ? new AlphaAnimation(1, 0)
                                 : new AlphaAnimation(0, 1);
        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
}

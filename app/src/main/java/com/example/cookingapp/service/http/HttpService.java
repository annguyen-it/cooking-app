package com.example.cookingapp.service.http;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.setting.AppSetting;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService<A extends FragmentActivity> {
    private final A activity;

    public HttpService(A activity) {
        this.activity = activity;
    }

    public <T> T http(Class<T> type) {
        OkHttpClient client = new OkHttpClient
            .Builder()
            .addInterceptor(chain -> {
                SharedPreferences pref = activity.getPreferences(Context.MODE_PRIVATE);
                String accessToken = pref.getString(PreferencesConstant.ACCESS_TOKEN, "");

                if (accessToken.isEmpty()) {
                    return chain.proceed(chain.request());
                }

                return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build());
            }).build();

        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(AppSetting.BASE_API_URL)
            .build()
            .create(type);
    }
}

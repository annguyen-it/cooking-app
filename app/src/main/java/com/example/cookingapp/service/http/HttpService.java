package com.example.cookingapp.service.http;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.helper.DataHelper;
import com.example.cookingapp.util.setting.AppSetting;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService<A extends AppCompatActivity> {
    private final A activity;

    public HttpService(A activity) {
        this.activity = activity;
    }

    public <T> T instance(Class<T> type) {
        final OkHttpClient client = buildClient();

        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(AppSetting.BASE_API_URL)
            .build()
            .create(type);
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient
            .Builder()
            .addInterceptor(new OkHttpProfilerInterceptor())
            .addInterceptor(chain -> {
                final SharedPreferences pref = DataHelper.getPreferences(activity);
                final String accessToken = pref.getString(PreferencesConstant.ACCESS_TOKEN, "");
                final Request original = chain.request();

                if (accessToken.isEmpty()) {
                    return chain.proceed(original);
                }

                final Request newRequest = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .method(original.method(), original.body())
                    .build();

                return chain.proceed(newRequest);
            })
            .build();
    }
}

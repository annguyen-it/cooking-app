package com.example.cookingapp.service.http;

import android.content.ContextWrapper;

import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.helper.DataHelper;
import com.example.cookingapp.util.setting.AppSetting;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService<A extends ContextWrapper> {
    private final A context;

    public HttpService(A context) {
        this.context = context;
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
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient
            .Builder()
            .addInterceptor(new OkHttpProfilerInterceptor())
            .addInterceptor(logging)
            .addInterceptor(chain -> {
                final String accessToken =
                    DataHelper.getPrefString(PreferencesConstant.ACCESS_TOKEN, context);
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

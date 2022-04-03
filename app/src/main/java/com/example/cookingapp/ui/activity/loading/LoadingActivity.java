package com.example.cookingapp.ui.activity.loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.ui.activity.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.data.model.UserModel;
import com.example.cookingapp.databinding.ActivityLoadingBinding;
import com.example.cookingapp.service.http.AccountService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.shared.SharedFunction;
import com.example.cookingapp.ui.activity.login.LoginActivity;
import com.example.cookingapp.util.constant.BundleConstant;
import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.helper.DataHelper;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivityLoadingBinding binding = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding components
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // After login screen
        final Bundle extras = savedInstanceState != null
                              ? savedInstanceState
                              : getIntent().getExtras();
        if (extras != null && !extras.getString(BundleConstant.TOKEN).equals("")) {
            getUserInfoAndRedirectToMain();
            return;
        }

        // Auto login
        final String savedUsername = DataHelper.getPrefString(PreferencesConstant.USERNAME, this);
        final String savedPassword = DataHelper.getPrefString(PreferencesConstant.PASSWORD, this);
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            autoLogin(savedUsername, savedPassword);
        }
        else {
            redirectToLogin();
        }
    }

    private void autoLogin(String username, String password) {
        final Callback<LoginModel> loginCallback = new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (!response.isSuccessful()) {
                    redirectToLogin();
                }

                saveAccessToken(response);
                getUserInfoAndRedirectToMain();
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                redirectToLogin();
            }
        };

        SharedFunction.login(this, username, password, loginCallback);
    }

    private void getUserInfoAndRedirectToMain() {
        final LoadingActivity thisActivity = this;

        final Callback<UserModel> callback = new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, Response<UserModel> response) {
                final UserModel userModel = response.body();

                final Bundle bundle = new Bundle();
                bundle.putString(BundleConstant.ACCOUNT, new Gson().toJson(userModel));

                final Intent intent = new Intent(thisActivity, MainActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {

            }
        };
        new HttpService<>(this).instance(AccountService.class)
            .getInfo()
            .enqueue(callback);
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void saveAccessToken(Response<LoginModel> response) {
        final LoginModel loginModel = response.body();
        DataHelper.putPrefString(PreferencesConstant.ACCESS_TOKEN,
            loginModel != null ? loginModel.accessToken : "", this);
    }
}

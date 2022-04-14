package com.example.cookingapp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.databinding.ActivityLoginBinding;
import com.example.cookingapp.shared.SharedFunction;
import com.example.cookingapp.ui.activity.loading.LoadingActivity;
import com.example.cookingapp.ui.activity.signUp.SignUpActivity;
import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.helper.DataHelper;
import com.example.cookingapp.util.helper.UiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView txtUsername;
    private TextView txtPassword;
    private Button btnLogin;
    private TextView txtSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding components
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);

        // Add events
        addEventToLoginButton();
        addEventToSignUpText();
    }

    private void addEventToLoginButton() {
        btnLogin.setOnClickListener(view -> {
            // Hide keyboard
            UiHelper.hideKeyboard(this);

            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (username.isEmpty()) {
                showToast("Hãy nhập tên đăng nhập");
                return;
            }
            if (password.isEmpty()) {
                showToast("Hãy nhập mật khẩu");
                return;
            }

            btnLogin.setEnabled(false);
            final LoginActivity thisActivity = this;
            final Callback<LoginModel> loginCallback = new Callback<LoginModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        final LoginModel loginModel = response.body();

                        DataHelper.putPrefString(PreferencesConstant.USERNAME, username, thisActivity);
                        DataHelper.putPrefString(PreferencesConstant.PASSWORD, password, thisActivity);
                        DataHelper.putPrefString(PreferencesConstant.ACCESS_TOKEN,
                            loginModel != null ? loginModel.accessToken : "", thisActivity);

                        redirectToLoading();
                        return;
                    }

                    btnLogin.setEnabled(true);
                    if (response.code() == 401) {
                        showToast("Mật khẩu không chính xác");
                    }
                    else {
                        showToast("Lỗi hệ thống, vui lòng thử lại sau 1");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                    btnLogin.setEnabled(true);
                    showToast("Lỗi hệ thống, vui lòng thử lại sau");
                }
            };

            SharedFunction.login(this, username, password, loginCallback);
        });
    }

    private void addEventToSignUpText() {
        txtSignUp.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void showToast(String text) {
        UiHelper.showToast(this, text);
    }

    private void redirectToLoading() {
        startActivity(new Intent(this, LoadingActivity.class));
        finish();
    }
}

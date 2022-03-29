package com.example.cookingapp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.MainActivity;
import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.LoginDto;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.databinding.ActivityLoginBinding;
import com.example.cookingapp.service.http.AccountService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.ui.signUp.SignUpActivity;
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

        // Hide action bar
        UiHelper.hideActionBar(this);

        // Auto login
        final SharedPreferences pref = DataHelper.getPreferences(this);

        final String savedUsername = pref.getString(PreferencesConstant.USERNAME, "");
        final String savedPassword = pref.getString(PreferencesConstant.PASSWORD, "");
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            autoLogin(savedUsername, savedPassword);
            return;
        }

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

    private void login(String username, String password, Callback<LoginModel> callback) {
        new HttpService<>(this).http(AccountService.class)
            .login(new LoginDto(username, password))
            .enqueue(callback);
    }

    private void autoLogin(String username, String password) {
        startActivity(new Intent(this, MainActivity.class));
        final LoginActivity thisActivity = this;

        login(username, password, new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (!response.isSuccessful()) { return; }

                final LoginModel loginModel = response.body();
                DataHelper.getPreferences(thisActivity).edit()
                    .putString(PreferencesConstant.ACCESS_TOKEN, loginModel != null ? loginModel.accessToken : "")
                    .apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) { }
        });
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

            login(username, password, new Callback<LoginModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        final LoginModel loginModel = response.body();
                        DataHelper.getPreferences(thisActivity).edit()
                            .putString(PreferencesConstant.USERNAME, username)
                            .putString(PreferencesConstant.PASSWORD, password)
                            .putString(PreferencesConstant.ACCESS_TOKEN,
                                loginModel != null ? loginModel.accessToken : "")
                            .apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        return;
                    }

                    btnLogin.setEnabled(true);
                    if (response.code() == 401) {
                        showToast("Mật khẩu không chính xác");
                    }
                    else {
                        showToast("Lỗi hệ thống, vui lòng thử lại sau");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                    btnLogin.setEnabled(true);
                    showToast("Lỗi hệ thống, vui lòng thử lại sau");
                }
            });
        });
    }

    private void addEventToSignUpText() {
        txtSignUp.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    private void showToast(String text) {
        UiHelper.showToast(LoginActivity.this, text);
    }
}

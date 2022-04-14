package com.example.cookingapp.ui.activity.signUp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;
import com.example.cookingapp.data.dto.SignUpDto;
import com.example.cookingapp.data.model.SignUpModel;
import com.example.cookingapp.databinding.ActivitySignUpBinding;
import com.example.cookingapp.service.http.AccountService;
import com.example.cookingapp.service.http.HttpService;
import com.example.cookingapp.util.constant.PreferencesConstant;
import com.example.cookingapp.util.helper.DataHelper;
import com.example.cookingapp.util.helper.StringHelper;
import com.example.cookingapp.util.helper.UiHelper;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private TextView txtFullName;
    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtPasswordConfirm;
    private Button btnSignUp;
    private TextView txtLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view
        final ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding components
        txtFullName = findViewById(R.id.txtFullName);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm);
        btnSignUp = findViewById(R.id.btnLogin);
        txtLogin = findViewById(R.id.txtSignUp);

        // Add events
        addEventToSignUpButton();
        addEventToLoginText();
    }

    private void addEventToSignUpButton() {
        btnSignUp.setOnClickListener(view -> {
            // Hide keyboard
            UiHelper.hideKeyboard(this);

            final String fullName = txtFullName.getText().toString();
            final String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            final String passwordConfirm = txtPasswordConfirm.getText().toString();

            if (fullName.isEmpty()) {
                showToast("Hãy nhập tên của bạn");
                return;
            }
            if (username.isEmpty()) {
                showToast("Hãy nhập tên đăng nhập");
                return;
            }
            if (password.isEmpty()) {
                showToast("Hãy nhập mật khẩu");
                return;
            }
            if (passwordConfirm.isEmpty()) {
                showToast("Hãy nhập xác nhận mật khẩu");
                return;
            }
            if (!password.equals(passwordConfirm)) {
                showToast("Mật khẩu xác nhận không trùng với mật khẩu!");
                return;
            }

            btnSignUp.setEnabled(false);

//            try {
//                password = StringHelper.GetMd5Hash(password);
//            }
//            catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//                return;
//            }

            final String finalPassword = password;
            final SignUpDto signUpDto = new SignUpDto(fullName, username, finalPassword);
            final SignUpActivity thisActivity = this;

            new HttpService<>(this).instance(AccountService.class)
                .signUp(signUpDto)
                .enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SignUpModel> call, @NonNull Response<SignUpModel> response) {
                        if (response.isSuccessful()) {
                            DataHelper.putPrefString(PreferencesConstant.USERNAME, username, thisActivity);
                            DataHelper.putPrefString(PreferencesConstant.PASSWORD, finalPassword, thisActivity);

                            showToast("Tạo tài khoản thành công");
                            finish();
                            return;
                        }

                        btnSignUp.setEnabled(true);
                        if (response.code() == 409) {
                            showToast("Tên đăng nhập đã được sử dụng, vui lòng chọn tên khác");
                            return;
                        }

                        showToast("Lỗi hệ thống, vui lòng thử lại sau");
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpModel> call, @NonNull Throwable t) {
                        btnSignUp.setEnabled(true);
                        showToast("Lỗi hệ thống, vui lòng thử lại sau");
                    }
                });
        });
    }

    private void addEventToLoginText() {
        txtLogin.setOnClickListener(view -> finish());
    }

    private void showToast(String text) {
        UiHelper.showToast(this, text);
    }
}

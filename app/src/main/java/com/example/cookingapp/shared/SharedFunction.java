package com.example.cookingapp.shared;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.data.dto.LoginDto;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.service.http.AccountService;
import com.example.cookingapp.service.http.HttpService;

import retrofit2.Callback;

public class SharedFunction {
    public static <A extends AppCompatActivity> void login(A activity, String username, String password, Callback<LoginModel> callback) {
        new HttpService<>(activity).instance(AccountService.class)
            .login(new LoginDto(username, password))
            .enqueue(callback);
    }
}

package com.example.cookingapp.service.http;

import com.example.cookingapp.data.dto.LoginDto;
import com.example.cookingapp.data.dto.SignUpDto;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.data.model.SignUpModel;
import com.example.cookingapp.data.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountService {
    @POST("auth")
    Call<LoginModel> login(@Body LoginDto loginDto);

    @POST("account")
    Call<SignUpModel> signUp(@Body SignUpDto signUpDto);

    @GET("account")
    Call<UserModel> getInfo();
}

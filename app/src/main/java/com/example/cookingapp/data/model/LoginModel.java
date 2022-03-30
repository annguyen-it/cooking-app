package com.example.cookingapp.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("access_token")
    public final String accessToken;

    public LoginModel(String accessToken) {
        this.accessToken = accessToken;
    }
}

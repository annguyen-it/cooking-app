package com.example.cookingapp.data.model;

import com.google.gson.annotations.SerializedName;

public class SignUpModel {
    @SerializedName("id")
    public final int id;

    @SerializedName("username")
    public final String username;

    @SerializedName("password")
    public final String password;

    @SerializedName("fullName")
    public final String fullName;

    public SignUpModel(int id, String username, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
}

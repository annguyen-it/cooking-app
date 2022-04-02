package com.example.cookingapp.data.model;

import android.net.Uri;

public class UserModel {
    private String username;
    private String fullName;
    private int idImage;

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIdImage() {
        return idImage;
    }

    public UserModel(String username, String fullname, int idImage) {
        this.username = username;
        this.fullName = fullname;
        this.idImage = idImage;
    }



}

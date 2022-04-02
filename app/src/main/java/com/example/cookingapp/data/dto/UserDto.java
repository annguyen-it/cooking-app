package com.example.cookingapp.data.dto;

import android.net.Uri;

public class UserDto {

    private int id;
    private String userName;
    private String fullName;
    private Uri imgAvatar;

    public UserDto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Uri getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(Uri imgAvatar) {
        this.imgAvatar = imgAvatar;
    }



}

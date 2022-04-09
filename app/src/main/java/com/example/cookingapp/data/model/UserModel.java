package com.example.cookingapp.data.model;

public class UserModel {
    public final int id;
    public final String username;
    public final String fullName;
    public final ImageModel image;

    public UserModel(int id, String username, String fullName, ImageModel image) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.image = image;
    }
}

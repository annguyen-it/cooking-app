package com.example.cookingapp.data.model;

public class UserModel {
    public final int id;
    public final String username;
    public final String fullName;
    public final int idImage;

    public UserModel(int id, String username, String fullName, int idImage) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.idImage = idImage;
    }
}

package com.example.cookingapp.data.model;

public class UserModel {
    private final int id;
    private final String username;
    private final String fullName;
    private final int idImage;

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIdImage() {
        return idImage;
    }

    public UserModel(int id, String username, String fullName, int idImage) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.idImage = idImage;
    }
}

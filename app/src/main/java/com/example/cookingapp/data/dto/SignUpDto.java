package com.example.cookingapp.data.dto;

public class SignUpDto {
    public final String fullName;
    public final String username;
    public final String password;

    public SignUpDto(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }
}

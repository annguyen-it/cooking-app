package com.example.cookingapp.data.dto;

public class LoginDto {
    public final String username;
    public final String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

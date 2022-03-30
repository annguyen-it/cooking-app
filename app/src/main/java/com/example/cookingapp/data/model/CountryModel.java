package com.example.cookingapp.data.model;

import androidx.annotation.NonNull;

public class CountryModel {
    public final String code;
    public final String name;

    public CountryModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

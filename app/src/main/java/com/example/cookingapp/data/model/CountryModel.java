package com.example.cookingapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel {
    @SerializedName("code")
    @Expose
    public final String code;


    @SerializedName("name")
    @Expose
    public final String name;

    public CountryModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

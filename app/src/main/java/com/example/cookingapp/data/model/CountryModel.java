package com.example.cookingapp.data.model;

import androidx.annotation.NonNull;

import com.example.cookingapp.data.model.core.HasDefaultValue;

public class CountryModel extends HasDefaultValue<CountryModel> {
    public final String code;
    public final String name;

    public CountryModel() {
        this("", "");
    }

    public CountryModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public CountryModel defaultValue() {
        return new CountryModel("", "Hãy chọn quốc gia");
    }
}

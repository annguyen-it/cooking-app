package com.example.cookingapp.util.helper;

import com.google.gson.Gson;

public class ObjectHelper {
    public static <T> T fromJson(String json, Class<T> classOfTg) {
        return new Gson().fromJson(json, classOfTg);
    }

    public static String toJson(Object json) {
        return new Gson().toJson(json);
    }
}

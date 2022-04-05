package com.example.cookingapp.data.model;

import java.util.List;

public class SearchResponseModel {
    public final List<FoodModel> foods;
    public final List<UserModel> users;

    public SearchResponseModel(List<FoodModel> foods, List<UserModel> users) {
        this.foods = foods;
        this.users = users;
    }
}

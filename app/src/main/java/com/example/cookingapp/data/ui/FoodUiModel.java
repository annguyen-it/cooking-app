package com.example.cookingapp.data.ui;

import com.example.cookingapp.data.model.FoodModel;

public class FoodUiModel {
    public final int id;
    public final String name;

    public FoodUiModel(FoodModel foodModel) {
        this.id = foodModel.id;
        this.name = foodModel.name;
    }
}

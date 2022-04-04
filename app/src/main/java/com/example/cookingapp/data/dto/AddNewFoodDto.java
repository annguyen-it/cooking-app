package com.example.cookingapp.data.dto;

import java.util.ArrayList;
import java.util.List;

public class AddNewFoodDto {
    public final String name;
    public final String country;
    public final int difficultLevel;
    public final boolean isVegetarian;
    public final String description;
    public final int timeToCook;
    public final String ingredient;
    public final String tips;
    public List<AddNewFoodStepDto> steps;

    public AddNewFoodDto(String name, String country, int difficultLevel, boolean isVegetarian, String description, int timeToCook, String ingredient, String tips) {
        this.name = name;
        this.country = country;
        this.difficultLevel = difficultLevel;
        this.isVegetarian = isVegetarian;
        this.description = description;
        this.timeToCook = timeToCook;
        this.ingredient = ingredient;
        this.tips = tips != null ? tips : "";
        this.steps = new ArrayList<>();
    }

    public static class AddNewFoodStepDto {
        public final String name;
        public final String description;

        public AddNewFoodStepDto(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}

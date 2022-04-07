package com.example.cookingapp.data.model;

import java.util.Date;
import java.util.List;

public class FoodModel {
    public final int id;
    public final String name;
    public final String description;
    public final boolean isVegetarian;
    public final int difficultLevel;
    public final int timeToCook;
    public final Date timePost;
    public final String ingredient;
    public final String tips;
    public final float voteAvg;
    public final int voteCount;
    public final UserModel owner;
    public final CountryModel country;
    public final List<StepModel> steps;
    public final List<RateModel> rates;
    public final ImageModel image;

    public FoodModel(int id, String name, String description, boolean isVegetarian, int difficultLevel, int timeToCook,
                     Date timePost, String ingredient, String tips, float voteAvg, int voteCount, UserModel owner,
                     CountryModel country, List<StepModel> steps, List<RateModel> rates, ImageModel image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isVegetarian = isVegetarian;
        this.difficultLevel = difficultLevel;
        this.timeToCook = timeToCook;
        this.timePost = timePost;
        this.ingredient = ingredient;
        this.tips = tips;
        this.voteAvg = voteAvg;
        this.voteCount = voteCount;
        this.owner = owner;
        this.country = country;
        this.steps = steps;
        this.rates = rates;
        this.image = image;
    }
}

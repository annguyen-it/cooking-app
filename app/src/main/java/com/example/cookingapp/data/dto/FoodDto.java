package com.example.cookingapp.data.dto;

import android.net.Uri;

import com.example.cookingapp.data.model.CountryModel;
import com.example.cookingapp.data.ui.StepUiModel;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class FoodDto {
    private int id;
    private UserDto owner;
    private String name;
    private ImageDto image;
    private String description;
    private boolean isVegetarian;
    private int difficultLevel;
    private int timeToCook;
    private CountryModel country;
    private Date timePost;
    private String ingredient;
    private String tips;
    private float voteAvg;
    private int voteCount;
    private List<StepUiModel> steps;


    public FoodDto(@Nullable float voteAvg, int id, String name, boolean isVegetarian, int difficultLevel, int timeToCook, ImageDto image, CountryModel country) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isVegetarian = isVegetarian;
        this.difficultLevel = difficultLevel;
        this.timeToCook = timeToCook;
        this.country = country;
        this.voteAvg = voteAvg;
    }

    public UserDto getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public ImageDto getImage() {
        return image;
    }

    public float getVoteAvg() {
        return voteAvg;
    }
}

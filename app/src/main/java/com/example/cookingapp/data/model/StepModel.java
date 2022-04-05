package com.example.cookingapp.data.model;

public class StepModel {
    public final String name;
    public final int stepNumber;
    public final String description;
    public final ImageModel images;

    public StepModel(String name, int stepNumber, String description, ImageModel images) {
        this.name = name;
        this.stepNumber = stepNumber;
        this.description = description;
        this.images = images;
    }
}

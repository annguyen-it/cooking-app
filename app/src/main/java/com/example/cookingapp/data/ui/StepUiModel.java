package com.example.cookingapp.data.ui;

import android.net.Uri;

public class StepUiModel {
    private String name;
    private String description;
    private Uri imageUri;

    public StepUiModel() {
        this("", "", null);
    }

    private StepUiModel(String name, String description, Uri imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

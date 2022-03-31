package com.example.cookingapp.data.dto;

import android.net.Uri;

public class Step {
    private String name;
    private String description;
    private Uri imageUri;

    public Step() {
        this("", "", null);
    }

    private Step(String name, String description, Uri imageUri) {
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

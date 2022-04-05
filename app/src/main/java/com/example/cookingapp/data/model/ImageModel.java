package com.example.cookingapp.data.model;

import java.util.Date;

public class ImageModel {
    public final String name;
    public final Date uploadedAt;
    public final int type;
    public final String mimeType;

    public ImageModel(String name, Date uploadedAt, int type, String mimeType) {
        this.name = name;
        this.uploadedAt = uploadedAt;
        this.type = type;
        this.mimeType = mimeType;
    }
}

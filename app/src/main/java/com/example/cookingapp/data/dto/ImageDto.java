package com.example.cookingapp.data.dto;

public class ImageDto {
    private String name;
    private String uploadedAt;
    private int type;
    private String mimeType;


    public ImageDto(String name, String uploadedAt, int type, String mimeType) {
        this.name = name;
        this.uploadedAt = uploadedAt;
        this.type = type;
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }
}

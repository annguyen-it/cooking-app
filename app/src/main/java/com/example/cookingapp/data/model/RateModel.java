package com.example.cookingapp.data.model;

import java.util.Date;

public class RateModel {
    public final int id;
    public final int idFood;
    public final int idOwner;
    public final int rate;
    public final String comment;
    public final Date timeCreated;

    public RateModel(int id, int idFood, int idOwner, int rate, String comment, Date timeCreated) {
        this.id = id;
        this.idFood = idFood;
        this.idOwner = idOwner;
        this.rate = rate;
        this.comment = comment;
        this.timeCreated = timeCreated;
    }
}

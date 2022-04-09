package com.example.cookingapp.data.model;

import java.util.Date;

public class RateModel {
    public final int id;
    public final int idFood;
    public final int rate;
    public final String comment;
    public final Date timeCreated;
    public final UserModel owner;

    public RateModel(int id, int idFood, int rate, String comment, Date timeCreated, UserModel owner) {
        this.id = id;
        this.idFood = idFood;
        this.rate = rate;
        this.comment = comment;
        this.timeCreated = timeCreated;
        this.owner = owner;
    }
}

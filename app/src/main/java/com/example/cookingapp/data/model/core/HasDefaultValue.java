package com.example.cookingapp.data.model.core;

import android.os.Parcelable;

public abstract class HasDefaultValue<T> implements Parcelable {
    public abstract T defaultValue();
}

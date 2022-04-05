package com.example.cookingapp.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.cookingapp.data.model.core.HasDefaultValue;

public class CountryModel extends HasDefaultValue<CountryModel> {
    public final String code;
    public final String name;

    public CountryModel() {
        this("", "");
    }

    public CountryModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CountryModel(Parcel parcel) {
        code = parcel.readString();
        name = parcel.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public CountryModel defaultValue() {
        return new CountryModel("", "Hãy chọn quốc gia");
    }

    @Override
    public boolean isDefaultValue() {
        return code.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
    }

    public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {
        @Override
        public CountryModel createFromParcel(Parcel parcel) {
            return new CountryModel(parcel);
        }

        @Override
        public CountryModel[] newArray(int i) {
            return new CountryModel[i];
        }
    };
}

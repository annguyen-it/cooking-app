package com.example.cookingapp.service.http;

import com.example.cookingapp.data.model.CountryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService {
    @GET("country")
    Call<ArrayList<CountryModel>> getCountries();
}

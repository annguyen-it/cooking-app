package com.example.cookingapp.service.http;

import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.data.model.LoginModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface FoodService {
    @POST("food")
    @Multipart
    Call<LoginModel> addNewFood(@Part MultipartBody.Part mainImage,
                                @Part MultipartBody.Part[] stepImages,
                                @Part("data") RequestBody data);

    @GET("food/{id}")
    Call<FoodModel> getFoodById(@Path("id") int id);

    @GET("search")
    Call<List<FoodModel>> searchFood(@Query("q") String q,
                                   @Query("isVegetarian") Boolean isVegetarina,
                                   @Query("country") String country);
}

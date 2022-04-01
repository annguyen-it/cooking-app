package com.example.cookingapp.service.http;

import com.example.cookingapp.data.model.LoginModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FoodService {
    @POST("food")
    @Multipart
    Call<LoginModel> addNewFood(@Part MultipartBody.Part mainImage,
                                @Part MultipartBody.Part[] stepImages,
                                @Part("data") RequestBody data);
}

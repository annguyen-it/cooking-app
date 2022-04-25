package com.example.cookingapp.service.http;

import com.example.cookingapp.data.dto.RateDto;
import com.example.cookingapp.data.model.FoodModel;
import com.example.cookingapp.data.model.LoginModel;
import com.example.cookingapp.data.model.RateModel;
import com.example.cookingapp.data.model.SearchResponseModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("food/me")
    Call<List<FoodModel>> getMyFood(@Query("page_num") int pageNum, @Query("page_size") int pageSize);

    @GET("food/rated")
    Call<List<FoodModel>> getRatedFood();

    @GET("food/{id}")
    Call<FoodModel> getFoodById(@Path("id") int id);

    @POST("food/{id}/rating")
    Call<RateModel> rateFood(@Path("id") int id, @Body RateDto rateDto);

    @GET("search")
    Call<SearchResponseModel> searchFood(@Query("q") String q,
                                         @Query("isVegetarian") Boolean isVegetarian,
                                         @Query("country") String country);

    @GET("food/{id}/rating")
    Call<List<RateModel>> getRate(@Path("id") int id);
}

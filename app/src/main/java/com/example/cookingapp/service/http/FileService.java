package com.example.cookingapp.service.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FileService {
    @GET("file")
    Call<ResponseBody> getByName(@Query("name") String name);
}

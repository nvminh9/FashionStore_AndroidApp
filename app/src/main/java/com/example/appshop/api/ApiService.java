package com.example.appshop.api;

import com.example.appshop.models.ListNhaSanXuat;
import com.example.appshop.models.NhaSanXuat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    //Link api mẫu: https://localhost:44347/api/nhasanxuat

    // khởi tạo Gson
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://cougar-inviting-distinctly.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/nhasanxuat?id=1")
    Call<NhaSanXuat> getNhaSanXuat();
}

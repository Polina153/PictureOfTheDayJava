package com.example.pictureofthedayjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NASAApiService {
    @GET("planetary/apod")
    Call<PictureOfDayResponse> getPictureOfTheDay(@Query("api_key") String apiKey);
}

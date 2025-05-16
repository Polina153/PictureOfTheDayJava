package com.example.pictureofthedayjava;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NASARepository {
    private static final String BASE_URL = "https://api.nasa.gov/";
    private final String DEMO_KEY = "DEMO_KEY";
    private final NASAApiService service;

    public NASARepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NASAApiService.class);
    }

    public Call<PictureOfDayResponse> fetchPictureOfDay() {
        return service.getPictureOfTheDay(DEMO_KEY); // Замени DEMO_KEY на твой собственный!
    }
}
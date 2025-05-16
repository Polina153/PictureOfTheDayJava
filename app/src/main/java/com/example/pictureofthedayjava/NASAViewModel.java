package com.example.pictureofthedayjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NASAViewModel extends AndroidViewModel {

    private final MutableLiveData<PictureOfDayResponse> mPictureOfDay = new MutableLiveData<>();
    private final NASARepository repository;

    public NASAViewModel(@NonNull Application application) {
        super(application);
        repository = new NASARepository(); // Создание репозитория
    }

    LiveData<PictureOfDayResponse> getPictureOfDay() {
        return mPictureOfDay;
    }

    public void fetchPictureOfDay() {
        repository.fetchPictureOfDay().enqueue(new retrofit2.Callback<PictureOfDayResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PictureOfDayResponse> call, retrofit2.Response<PictureOfDayResponse> response) {
                if (response.isSuccessful()) {
                    mPictureOfDay.postValue(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PictureOfDayResponse> call, Throwable t) {
                mPictureOfDay.postValue(null); // Обработка ошибок должна быть доработана позже
            }
        });
    }
}
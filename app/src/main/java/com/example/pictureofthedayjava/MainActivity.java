package com.example.pictureofthedayjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageView pictureOfDayImage;
    TextView descriptionTextView;
    private final String DEMO_KEY = "DEMO_KEY";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pictureOfDayImage = findViewById(R.id.pictureOfDayImage);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        loadData();
    }

    private void loadData() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Включаем логирование запросов и ответов

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NASAApiService service = retrofit.create(NASAApiService.class);

        Call<PictureOfDayResponse> call = service.getPictureOfTheDay(DEMO_KEY);
        call.enqueue(new Callback<PictureOfDayResponse>() {
            @Override
            public void onResponse(Call<PictureOfDayResponse> call, Response<PictureOfDayResponse> response) {
                if(response.isSuccessful()) {
                    PictureOfDayResponse data = Objects.requireNonNull(response.body());
                    Picasso.get().load(data.getImageUrl()).into(pictureOfDayImage);
                    descriptionTextView.setText(data.getExplanation());
                }
            }

            @Override
            public void onFailure(Call<PictureOfDayResponse> call, Throwable t) {
                descriptionTextView.setText(t.getMessage()); // выводим ошибку
            }
        });
    }
}
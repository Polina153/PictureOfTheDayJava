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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView pictureOfDayImage;
    TextView descriptionTextView;
    NASAViewModel viewModel;

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

        // Получение экземпляра ViewModel
        viewModel = new ViewModelProvider(this).get(NASAViewModel.class);

        // Подписываемся на изменение данных
        viewModel.getPictureOfDay().observe(this, new Observer<PictureOfDayResponse>() {
            @Override
            public void onChanged(PictureOfDayResponse response) {
                if (response != null) {
                    Picasso.get().load(response.getImageUrl()).into(pictureOfDayImage);
                    descriptionTextView.setText(response.getExplanation());
                }
            }
        });

        // Запрашиваем загрузку фотографии дня
        viewModel.fetchPictureOfDay();
    }
}
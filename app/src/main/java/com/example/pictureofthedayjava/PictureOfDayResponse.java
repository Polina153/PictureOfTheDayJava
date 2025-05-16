package com.example.pictureofthedayjava;

import com.google.gson.annotations.SerializedName;

public class PictureOfDayResponse {
    private String title;
    private String explanation;
    @SerializedName("url")
    private String imageUrl;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}


package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

public class Review {
    public String author;
    public String content;
    @SerializedName("create_at")
    public String createAt;
    public String url;
    public String rating;
    @SerializedName("avatar_path")
    public String avatarPath;
}

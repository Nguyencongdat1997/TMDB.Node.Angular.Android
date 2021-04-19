package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

public class Item {
    public String id;
    public String title;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public String category;
    public boolean isInWatchlist;
}

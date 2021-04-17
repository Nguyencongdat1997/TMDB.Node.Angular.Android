package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

public class ItemDetail {
    public String id;
    public String category;
    public String title;
    public String genres;
    @SerializedName("spoken_language")
    public String spokenLanguage;
    public String date;
    public String runtime;
    public String overview;
    @SerializedName("vote_average")
    public String voteAverage;
    public String tagline;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("backdrop_path")
    public String backdropPath;
    public ItemDetail (String id,
                       String category,
                       String title,
                       String genres,
                       String spokenLanguage,
                       String date,
                       String runtime,
                       String overview,
                       String voteAverage,
                       String tagline,
                       String posterPath,
                       String backdropPath
    ) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.genres = genres;
        this.spokenLanguage = spokenLanguage;
        this.date = date;
        this.runtime = runtime;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.tagline = tagline;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }
}
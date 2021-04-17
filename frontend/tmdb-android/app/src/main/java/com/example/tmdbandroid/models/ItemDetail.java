package com.example.tmdbandroid.models;

public class ItemDetail {
    public String id;
    public String category;
    public String title;
    public String genres;
    public String spokenLanguage;
    public String date;
    public String runtime;
    public String overview;
    public String voteAverate;
    public String tagline;
    public String posterPath;
    public String backdropPath;
    public ItemDetail (String id,
                       String category,
                       String title,
                       String genres,
                       String spokenLanguage,
                       String date,
                       String runtime,
                       String overview,
                       String voteAverate,
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
        this.voteAverate = voteAverate;
        this.tagline = tagline;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }
}
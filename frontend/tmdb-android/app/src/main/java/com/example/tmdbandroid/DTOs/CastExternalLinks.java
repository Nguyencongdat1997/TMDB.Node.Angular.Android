package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

public class CastExternalLinks {
    @SerializedName("imdb_id")
    public String imdbID;
    @SerializedName("insta_id")
    public String instaID;
    @SerializedName("fb_id")
    public String fbID;
    @SerializedName("twiter_id")
    public String twiterID;
}

package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

public class CastDetail {
    public String id;
    public String name;
    @SerializedName("image_url")
    public String imageUrl;
    @SerializedName("birth_date")
    public String birthDate;
    @SerializedName("birth_place")
    public String birthPlace;
    public String gender;
    public String knownFor;
    public String otherNames;
    public String homepage;
    @SerializedName("external_links")
    public CastExternalLinks externalLinks;
    public String biography;
}

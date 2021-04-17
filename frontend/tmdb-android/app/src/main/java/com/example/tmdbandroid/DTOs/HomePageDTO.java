package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageDTO {
    @SerializedName("carousel_list")
    public List<Item> carouselList;
    @SerializedName("popular_movies")
    public List<Item> popularMovies;
    @SerializedName("topRated_movies")
    public List<Item> topRatedMovies;
    @SerializedName("trending_movies")
    public List<Item> trendingMovies;
    @SerializedName("popular_tvs")
    public List<Item> popularTvs;
    @SerializedName("topRated_tvs")
    public List<Item> topRatedTvs;
    @SerializedName("trending_tvs")
    public List<Item> trendingTvs;
}

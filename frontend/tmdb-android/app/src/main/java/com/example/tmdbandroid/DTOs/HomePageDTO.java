package com.example.tmdbandroid.DTOs;

import com.example.tmdbandroid.models.Item;

import java.util.List;

public class HomePageDTO {
    public List<Item> carouselList;
    public List<Item> popularMovies;
    public List<Item> topRatedMovies;
    public List<Item> trendingMovies;
    public List<Item> popularTvs;
    public List<Item> topRatedTvs;
    public List<Item> trendingTvs;
}

package com.example.tmdbandroid.models;

public class Item {
    public String id;
    public String title;
    public String posterPath;
    public String backdropPath;
    public String category;
    public Item(String id, String title, String posterPath, String backdropPath, String category) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.category = category;
    }
}

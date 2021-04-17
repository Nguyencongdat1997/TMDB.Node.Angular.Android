package com.example.tmdbandroid.models;

public class Cast {
    public String id;
    public String name;
    public String character;
    public String profilePath;

    public Cast(String id, String name, String character, String profilePath) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
    }
}

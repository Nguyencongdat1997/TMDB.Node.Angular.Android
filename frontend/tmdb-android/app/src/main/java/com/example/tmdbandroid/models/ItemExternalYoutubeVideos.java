package com.example.tmdbandroid.models;

public class ItemExternalYoutubeVideos {
    public String site;
    public String type;
    public String name;
    public String key;
    public String url;

    public ItemExternalYoutubeVideos(String site, String type, String name, String key) {
        this.site = site;
        this.type = type;
        this.name = name;
        this.key = key;
        this.url = "https://www.youtube.com/watch?v=" + key;
    }
}

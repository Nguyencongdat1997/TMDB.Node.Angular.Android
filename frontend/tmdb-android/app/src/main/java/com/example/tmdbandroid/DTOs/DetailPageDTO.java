package com.example.tmdbandroid.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPageDTO {
    @SerializedName("item_detail")
    public ItemDetail itemDetail;
    public List<Cast> casts;
    public List<Review> reviews;
    public List<Item> recommendations;
    @SerializedName("similars")
    public List<Item> similarItems;
    @SerializedName("youtube_video")
    public ItemExternalYoutubeVideos chosenYoutubeVideo;
}

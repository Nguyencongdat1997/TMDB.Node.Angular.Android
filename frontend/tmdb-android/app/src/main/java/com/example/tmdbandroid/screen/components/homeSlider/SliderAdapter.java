package com.example.tmdbandroid.screen.components.homeSlider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.detail.DetailActivity;
import com.example.tmdbandroid.utils.BlurTransformation;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<Item> movieItems;
    private Context context;

    // Constructor
    public SliderAdapter(Context context, List<Item> sliderDataArrayList) {
        this.movieItems = sliderDataArrayList;
        this.context = context;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeslider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final Item sliderItem = movieItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.posterPath)
                .into(viewHolder.imageViewInsider);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.posterPath)
                .override(400, 450)
                .transform( new MultiTransformation<>(
                        new CenterCrop(),
                        new jp.wasabeef.glide.transformations.BlurTransformation(12, 2)
                ))
                .into(viewHolder.imageViewContainer);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("itemId","" + sliderItem.id);
                context.startActivity(i);
            }
        });
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return movieItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewInsider;
        ImageView imageViewContainer;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewInsider = itemView.findViewById(R.id.homeSliderImageInsider);
            imageViewContainer = itemView.findViewById(R.id.homeSliderImageContainer);
            this.itemView = itemView;
        }
    }
}
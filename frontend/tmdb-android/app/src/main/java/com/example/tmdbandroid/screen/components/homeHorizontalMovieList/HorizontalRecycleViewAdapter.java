package com.example.tmdbandroid.screen.components.homeHorizontalMovieList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;

import java.util.List;


public class HorizontalRecycleViewAdapter
        extends RecyclerView.Adapter<HorizontalRecycleViewAdapter.MyView> {
    private List<Item> list;

    public class MyView
            extends RecyclerView.ViewHolder {

        ImageView movieItemImageView;

        public MyView(View view)
        {
            super(view);
            movieItemImageView = (ImageView) view
                    .findViewById(R.id.homeHorizontalListMovieItemImage);
        }
    }

    public HorizontalRecycleViewAdapter(List<Item> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.home_movie_item_layout,
                        parent,
                        false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView viewHolder,
                                 final int position)
    {
        final Item sliderItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.posterPath)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(viewHolder.movieItemImageView);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
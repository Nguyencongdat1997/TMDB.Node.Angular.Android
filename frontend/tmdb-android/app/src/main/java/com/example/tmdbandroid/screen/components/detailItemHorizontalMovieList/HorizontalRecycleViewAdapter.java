package com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.detail.DetailActivity;

import java.util.List;

public class HorizontalRecycleViewAdapter extends RecyclerView.Adapter<com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList.HorizontalRecycleViewAdapter.MyView> {
    private List<Item> list;
    private Context context;

    public class MyView
            extends RecyclerView.ViewHolder {

        ImageView movieItemImageView;

        public MyView(View view)
        {
            super(view);
            movieItemImageView = (ImageView) view
                    .findViewById(R.id.detailItemHorizontalListItemBackground);
        }
    }

    public HorizontalRecycleViewAdapter(Context context, List<Item> horizontalList)
    {
        this.context = context;
        this.list = horizontalList;
    }

    @Override
    public com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList.HorizontalRecycleViewAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                                                                                                    int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.detail_item_recommended_movie_item_layout,
                        parent,
                        false);
        return new com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList.HorizontalRecycleViewAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList.HorizontalRecycleViewAdapter.MyView viewHolder,
                                 final int position)
    {
        final Item selectedItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(selectedItem.posterPath)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(viewHolder.movieItemImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("itemId","" + selectedItem.id);
                i.putExtra("itemCategory","" + selectedItem.category);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
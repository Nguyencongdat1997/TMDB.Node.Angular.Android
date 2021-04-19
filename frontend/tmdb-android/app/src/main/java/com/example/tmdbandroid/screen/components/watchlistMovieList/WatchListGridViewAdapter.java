package com.example.tmdbandroid.screen.components.watchlistMovieList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.main.WatchlistViewModel;

import java.util.List;

public class WatchListGridViewAdapter extends BaseAdapter {
    Context context;
    List<Item> watchLists;
    LayoutInflater inflter;
    WatchlistViewModel viewModel;
    public WatchListGridViewAdapter(Context applicationContext, List<Item> watchLists, WatchlistViewModel viewModel) {
        this.context = applicationContext;
        this.watchLists = watchLists;
        this.viewModel = viewModel;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return watchLists.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item selectedItem = watchLists.get(i);
        view = inflter.inflate(R.layout.watchlist_movie_item_layout, null);

        ImageView imageBackground = (ImageView) view.findViewById(R.id.watchListGridViewItemImageView);
        Glide.with(view)
                .load(selectedItem.posterPath)
                .error(R.drawable.movie_placeholder)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(imageBackground);

        TextView  txtView = (TextView) view.findViewById(R.id.watchListGridViewItemCategory);
        String txtCategory = selectedItem.category.equals("movie") ? "Movie" : "TV";
        txtView.setText(txtCategory);

        ImageButton removeBtn = (ImageButton) view.findViewById(R.id.watchListGridViewItemRemoveBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "\"" + selectedItem.title + "\" was removed from favorites", Toast.LENGTH_SHORT).show();
                viewModel.removeItemFromWatchList(selectedItem);
            }
        });
        return view;
    }
}

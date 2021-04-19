package com.example.tmdbandroid.screen.components.watchlistMovieList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.main.WatchlistViewModel;

import java.util.List;

public class WatchListRecyclerViewAdapter extends RecyclerView.Adapter<WatchListRecyclerViewAdapter.MyView> {
    private List<Item> list;
    private Context context;
    private WatchlistViewModel viewModel;

    public class MyView
            extends RecyclerView.ViewHolder {

        ImageView imageBackground;
        TextView txtView;
        ImageButton removeBtn;

        public MyView(View view)
        {
            super(view);
            imageBackground = (ImageView) view.findViewById(R.id.watchListGridViewItemImageView);
            txtView = (TextView) view.findViewById(R.id.watchListGridViewItemCategory);
            removeBtn = (ImageButton) view.findViewById(R.id.watchListGridViewItemRemoveBtn);
        }
    }

    public WatchListRecyclerViewAdapter(Context context, List<Item> horizontalList, WatchlistViewModel viewModel)
    {
        this.context = context;
        this.list = horizontalList;
        this.viewModel = viewModel;
    }

    @Override
    public WatchListRecyclerViewAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                                  int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.watchlist_movie_item_layout,
                        parent,
                        false);
        return new WatchListRecyclerViewAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final WatchListRecyclerViewAdapter.MyView viewHolder,
                                 final int position)
    {
        final Item selectedItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(selectedItem.posterPath)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(viewHolder.imageBackground);

        String txtCategory = selectedItem.category.equals("movie") ? "Movie" : "TV";
        viewHolder.txtView.setText(txtCategory);

        viewHolder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "\"" + selectedItem.title + "\" was removed from favorites", Toast.LENGTH_SHORT).show();
                viewModel.removeItemFromWatchList(selectedItem);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
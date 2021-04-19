package com.example.tmdbandroid.screen.components.searchResultList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.databinding.SearchResultItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter
        extends RecyclerView.Adapter<com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter.MyView> {
    private List<Item> list;
    private Context context;

    public class MyView
            extends RecyclerView.ViewHolder {

        ImageView movieItemImageView;
        TextView titleTextView;
        TextView rateTextView;
        TextView categoryAndTimeTextView;

        public MyView(View view)
        {
            super(view);
            movieItemImageView = (ImageView) view
                    .findViewById(R.id.searchItemImage);
            titleTextView = (TextView) view
                    .findViewById(R.id.searchItemTitle);
            rateTextView = (TextView) view
                    .findViewById(R.id.searchItemRate);
            categoryAndTimeTextView = (TextView) view
                    .findViewById(R.id.searchItemCategory);
        }
    }

    public SearchResultsAdapter(Context context, List<Item> horizontalList)
    {
        this.context = context;
        this.list = horizontalList;
    }

    @Override
    public com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                                                                                                    int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_result_item_layout,
                        parent,
                        false);
        return new com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter.MyView viewHolder,
                                 final int position)
    {
        final Item selectedItem = list.get(position);
        if (selectedItem == null){
            return;
        }
        Glide.with(viewHolder.itemView)
                .load(selectedItem.backdropPath)
                .error(R.drawable.movie_placeholder)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(viewHolder.movieItemImageView);
        viewHolder.titleTextView.setText(selectedItem.title);
        viewHolder.rateTextView.setText("10"); // TODO: update this in backend
        String txtCategory = selectedItem.category == "movie" ? "movie" : "tv shows";
        String year = "2010"; // TODO: update this in backend
        viewHolder.categoryAndTimeTextView.setText(selectedItem.category + " (" + year + ")");
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
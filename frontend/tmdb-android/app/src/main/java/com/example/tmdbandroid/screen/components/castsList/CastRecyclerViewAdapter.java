package com.example.tmdbandroid.screen.components.castsList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Cast;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.components.homeHorizontalMovieList.HorizontalRecycleViewAdapter;
import com.example.tmdbandroid.screen.detail.DetailActivity;
import com.example.tmdbandroid.screen.main.HomeViewModel;

import java.util.List;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.MyView> {
private List<Cast> list;
private Context context;

public class MyView
        extends RecyclerView.ViewHolder {

    ImageView castAvatar;
    TextView castName;

    public MyView(View view)
    {
        super(view);
        castAvatar = (ImageView) view
                .findViewById(R.id.castAvatar);
        castName = (TextView) view.findViewById(R.id.castName);
    }
}

    public CastRecyclerViewAdapter(Context context, List<Cast> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public CastRecyclerViewAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                                  int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.detail_item_cast_item_layout,
                        parent,
                        false);
        return new CastRecyclerViewAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final CastRecyclerViewAdapter.MyView viewHolder,
                                 final int position)
    {
        final Cast selectedItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(selectedItem.profilePath)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new CircleCrop()
                ))
                .into(viewHolder.castAvatar);
        viewHolder.castName.setText(selectedItem.name);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
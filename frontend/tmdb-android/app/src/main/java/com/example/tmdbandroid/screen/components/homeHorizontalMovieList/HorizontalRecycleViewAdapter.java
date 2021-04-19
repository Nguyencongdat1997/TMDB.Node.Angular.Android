package com.example.tmdbandroid.screen.components.homeHorizontalMovieList;

import android.content.Context;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.main.HomeFragment;
import com.example.tmdbandroid.screen.main.HomeViewModel;
import com.example.tmdbandroid.screen.main.SearchFragment;
import com.example.tmdbandroid.screen.main.WatchlistFragment;

import java.util.List;


public class HorizontalRecycleViewAdapter
        extends RecyclerView.Adapter<HorizontalRecycleViewAdapter.MyView> {
    private List<Item> list;
    private Context context;
    private HomeViewModel viewModel;

    public class MyView
            extends RecyclerView.ViewHolder {

        ImageView movieItemImageView;
        ImageButton moreBtn;

        public MyView(View view)
        {
            super(view);
            movieItemImageView = (ImageView) view
                    .findViewById(R.id.homeHorizontalListMovieItemImage);
            moreBtn = (ImageButton) view.findViewById(R.id.homeHorizontalListMovieItemMoreBtn);
        }
    }

    public HorizontalRecycleViewAdapter(Context context, List<Item> horizontalList, HomeViewModel viewModel)
    {
        this.context = context;
        this.list = horizontalList;
        this.viewModel = viewModel;
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
        final Item selectedItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(selectedItem.posterPath)
                .transform(new MultiTransformation<>(
                        new CenterCrop(), new RoundedCorners(20)
                ))
                .into(viewHolder.movieItemImageView);

        viewHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.moreBtn);

                popupMenu.getMenuInflater().inflate(R.menu.home_movie_item_popup, popupMenu.getMenu());
                MenuItem addToWatchListMenuItem = popupMenu.getMenu().findItem(R.id.movieItemPopupAddtoWatchListBtn);
                if (selectedItem.isInWatchlist){
                    addToWatchListMenuItem.setTitle("Remove from watchlist");
                }
                else{
                    addToWatchListMenuItem.setTitle("Add to watchlist");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.movieItemPopupTMDBBtn:
                                Toast.makeText(context, "You Clicked " + menuItem.getTitle() + " with Id " + menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.movieItemPopupFbBtn:
                                Toast.makeText(context, "You Clicked " + menuItem.getTitle() + " with Id " + menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.movieItemPopupTwitterBtn:
                                Toast.makeText(context, "You Clicked " + menuItem.getTitle() + " with Id " + menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.movieItemPopupAddtoWatchListBtn:
                                if (selectedItem.isInWatchlist){
                                    menuItem.setTitle("Add to watchlist");
                                    viewModel.removeItemFromWatchList(selectedItem);
                                }
                                else{
                                    menuItem.setTitle("Remove from watchlist");
                                    viewModel.addItemToWatchList(selectedItem);
                                }
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
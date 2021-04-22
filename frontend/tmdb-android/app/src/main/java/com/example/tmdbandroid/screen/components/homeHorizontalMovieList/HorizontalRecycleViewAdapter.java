package com.example.tmdbandroid.screen.components.homeHorizontalMovieList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.detail.DetailActivity;
import com.example.tmdbandroid.screen.main.HomeFragment;
import com.example.tmdbandroid.screen.main.HomeViewModel;
import com.example.tmdbandroid.screen.main.SearchFragment;
import com.example.tmdbandroid.screen.main.WatchlistFragment;
import com.example.tmdbandroid.screen.review.ReviewActivity;

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
                        new CenterCrop(), new RoundedCorners((int) context.getResources().getDimension(R.dimen.card_radius))
                ))
                .into(viewHolder.movieItemImageView);

        viewHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.popupMenuStyle);
                PopupMenu popupMenu = new PopupMenu(wrapper, viewHolder.moreBtn);

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
                        String tmdpUrl = "https://www.themoviedb.org/" + selectedItem.category + "/" + selectedItem.id;
                        switch (menuItem.getItemId()) {
                            case R.id.movieItemPopupTMDBBtn:
                                Intent openTMDBPage = new Intent(Intent.ACTION_VIEW, Uri.parse(tmdpUrl));
                                context.startActivity(openTMDBPage);
                                break;
                            case R.id.movieItemPopupFbBtn:
                                String fbUrl = "https://www.facebook.com/sharer/sharer.php?u=" + tmdpUrl;
                                Intent openFBPage = new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl));
                                context.startActivity(openFBPage);
                                break;
                            case R.id.movieItemPopupTwitterBtn:
                                String twUrl = "https://twitter.com/intent/tweet?text=" + tmdpUrl;
                                Intent openTwitterPage = new Intent(Intent.ACTION_VIEW, Uri.parse(twUrl));
                                context.startActivity(openTwitterPage);
                                break;
                            case R.id.movieItemPopupAddtoWatchListBtn:
                                if (selectedItem.isInWatchlist){
                                    menuItem.setTitle("Add to watchlist");
                                    viewModel.removeItemFromWatchList(selectedItem);
                                    Toast toast = Toast.makeText(context, "\"" + selectedItem.title + "\" was removed from Watchlist", Toast.LENGTH_SHORT);
                                    View view =toast.getView();
                                    view.setBackgroundColor(Color.TRANSPARENT);
                                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                    toastMessage.setTextColor(Color.BLACK);
                                    toastMessage.setBackground(context.getDrawable(R.drawable.custom_toast));
                                    toast.show();
                                }
                                else{
                                    menuItem.setTitle("Remove from watchlist");
                                    viewModel.addItemToWatchList(selectedItem);
                                    Toast toast = Toast.makeText(context, "\"" + selectedItem.title + "\" was added to Watchlist", Toast.LENGTH_SHORT);
                                    View view =toast.getView();
                                    view.setBackgroundColor(Color.TRANSPARENT);
                                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                    toastMessage.setTextColor(Color.BLACK);
                                    toastMessage.setBackground(context.getDrawable(R.drawable.custom_toast));
                                    toast.show();
                                }
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

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
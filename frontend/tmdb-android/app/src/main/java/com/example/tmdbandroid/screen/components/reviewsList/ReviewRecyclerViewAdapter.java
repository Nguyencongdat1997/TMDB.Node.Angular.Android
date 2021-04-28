package com.example.tmdbandroid.screen.components.reviewsList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.tmdbandroid.DTOs.Cast;
import com.example.tmdbandroid.DTOs.Review;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.components.castsList.CastRecyclerViewAdapter;
import com.example.tmdbandroid.screen.detail.DetailActivity;
import com.example.tmdbandroid.screen.review.ReviewActivity;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.MyView> {
    private List<Review> list;
    private Context context;

    public class MyView
            extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView rateText;
        TextView contentText;

        public MyView(View view)
        {
            super(view);
            titleText = (TextView) view.findViewById(R.id.itemDetailReviewTitle);
            rateText = (TextView) view.findViewById(R.id.itemDetailReviewRate);
            contentText = (TextView) view.findViewById(R.id.itemDetailReviewContent);
        }
    }

    public ReviewRecyclerViewAdapter(Context context, List<Review> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ReviewRecyclerViewAdapter.MyView onCreateViewHolder(ViewGroup parent,
                                                             int viewType)
    {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.detail_item_review_item_layout,
                        parent,
                        false);
        return new ReviewRecyclerViewAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final ReviewRecyclerViewAdapter.MyView viewHolder,
                                 final int position)
    {
        final Review selectedItem = list.get(position);

        String dateTxt = " on ";
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(selectedItem.createAt.replaceAll("Z$", "+0000")); //2020-12-20T20:40:50.737Z
            SimpleDateFormat txtDateFormater = new SimpleDateFormat("EEE, MMM dd yyyy", Locale.ENGLISH);
            dateTxt += (txtDateFormater.format(date));;
        }
        catch (Exception e){
            dateTxt = "";
        }
        String titleTxt = "by " + selectedItem.author + dateTxt;
        viewHolder.titleText.setText(titleTxt);

        String rateTxt = "" + Math.round(Double.parseDouble(selectedItem.rating)/2) + "/5";
        viewHolder.rateText.setText(rateTxt);

        viewHolder.contentText.setText(selectedItem.content);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ReviewActivity.class);
                i.putExtra("reviewTitle", titleTxt);
                i.putExtra("reviewRate", rateTxt);
                i.putExtra("reviewContent", selectedItem.content);
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
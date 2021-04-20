package com.example.tmdbandroid.screen.review;

import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdbandroid.R;
import com.example.tmdbandroid.databinding.ActivityReviewBinding;

public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ActivityReviewBinding binding = ActivityReviewBinding.inflate(getLayoutInflater());

        Intent passedIntent = getIntent(); // gets the previously created intent
        binding.setReviewTitle(passedIntent.getStringExtra("reviewTitle"));
        binding.setReviewRate(passedIntent.getStringExtra("reviewRate"));
        binding.setReviewContent(passedIntent.getStringExtra("reviewContent"));

        View view = binding.getRoot();
        setContentView(view);
    }
}
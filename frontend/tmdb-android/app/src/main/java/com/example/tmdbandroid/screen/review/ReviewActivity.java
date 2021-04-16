package com.example.tmdbandroid.screen.review;

import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tmdbandroid.R;

public class ReviewActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent passedIntent = getIntent(); // gets the previously created intent
        String screenName = passedIntent.getStringExtra("screenName");

        mTextView = (TextView) findViewById(R.id.reviewText);
        mTextView.setText(screenName);
    }
}
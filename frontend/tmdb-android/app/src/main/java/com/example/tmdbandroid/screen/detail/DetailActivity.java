package com.example.tmdbandroid.screen.detail;

import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.main.MainActivity;
import com.example.tmdbandroid.screen.review.ReviewActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button toReivewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent passedIntent = getIntent(); // gets the previously created intent
        String screenName = passedIntent.getStringExtra("screenName");

        mTextView = (TextView) findViewById(R.id.detailText);
        mTextView.setText(screenName);

        toReivewBtn = findViewById(R.id.toReviewBtn);
        toReivewBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(DetailActivity.this, ReviewActivity.class);
                        i.putExtra("screenName","REVIEW");
                        startActivity(i);
                    }
                }
        );
    }
}
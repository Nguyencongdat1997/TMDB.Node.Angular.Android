package com.example.tmdbandroid.screen.detail;

import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.main.MainActivity;
import com.example.tmdbandroid.screen.main.SearchFragment;
import com.example.tmdbandroid.screen.review.ReviewActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button toReivewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent passedIntent = getIntent(); // gets the previously created intent
        String itemId = passedIntent.getStringExtra("itemId");
        String itemCategory = passedIntent.getStringExtra("itemCategory");

        Fragment selectedFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemId", itemId);
        bundle.putString("itemCategory", itemCategory);
        selectedFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container,
                selectedFragment).commit();
    }
}
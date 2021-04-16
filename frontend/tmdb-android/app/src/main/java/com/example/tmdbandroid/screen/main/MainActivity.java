package com.example.tmdbandroid.screen.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.detail.DetailActivity;

public class MainActivity extends AppCompatActivity {

    private Button toDetailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDetailBtn = findViewById(R.id.toDetailBtn);
        toDetailBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, DetailActivity.class);
                        i.putExtra("screenName","DETAIL");
                        startActivity(i);
                    }
                }
        );
    }
}
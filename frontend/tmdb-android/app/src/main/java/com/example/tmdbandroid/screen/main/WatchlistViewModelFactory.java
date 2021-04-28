package com.example.tmdbandroid.screen.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WatchlistViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Context context;


    public WatchlistViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel > T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WatchlistViewModel.class)) {
            return (T) new WatchlistViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
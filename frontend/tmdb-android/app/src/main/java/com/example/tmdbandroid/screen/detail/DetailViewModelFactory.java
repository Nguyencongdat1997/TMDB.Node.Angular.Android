package com.example.tmdbandroid.screen.detail;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmdbandroid.screen.main.HomeViewModel;

public class DetailViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private String itemId;
    private String itemCategory;

    public DetailViewModelFactory(Application application, String itemId, String itemCategory) {
        this.application = application;
        this.itemId = itemId;
        this.itemCategory = itemCategory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(application, itemId, itemCategory);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
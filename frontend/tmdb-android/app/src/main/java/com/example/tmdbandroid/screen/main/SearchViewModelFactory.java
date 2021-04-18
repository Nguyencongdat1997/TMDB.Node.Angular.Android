package com.example.tmdbandroid.screen.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Context context;


    public SearchViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}

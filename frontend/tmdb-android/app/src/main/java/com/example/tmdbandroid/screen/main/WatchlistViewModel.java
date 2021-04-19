package com.example.tmdbandroid.screen.main;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WatchlistViewModel extends ViewModel {
    private MutableLiveData<List<Item>> _watchList;
    public LiveData<List<Item>> getWatchList() {
        return _watchList;
    }

    Application application;
    Gson gson;

    public WatchlistViewModel(Application application){
        this.application = application;
        gson = new Gson();

        _watchList = new MutableLiveData<List<Item>>();
        LocalStorageConnector localStorageConnector = new LocalStorageConnector(application.getApplicationContext());
        List<Item> watchList = localStorageConnector.getWatchList();
        _watchList.setValue(watchList);
    }
}
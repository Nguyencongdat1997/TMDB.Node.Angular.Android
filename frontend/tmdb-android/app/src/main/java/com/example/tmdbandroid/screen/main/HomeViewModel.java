package com.example.tmdbandroid.screen.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.constant.APIEndpoints;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> _status;
    public LiveData<String> getStatus() {
        return _status;
    }

    private MutableLiveData<HomePageDTO> _homepageDto;
    public LiveData<HomePageDTO> getHomepageDto() {
        return _homepageDto;
    }

    private MutableLiveData<List<Item>> _localWatchlist;
    public LiveData<List<Item>> getLocalWatchlist() {
        return _localWatchlist;
    }
    private boolean isInWatchList(Item item, List<Item>  watchList){
        for (int i =0; i<watchList.size(); i++){
            if (item.id.equals(watchList.get(i).id)){
                return true;
            }
        }
        return false;
    }
    public void setLocalWatchlist(List<Item> watchlist){
        HomePageDTO homepageDtoData = _homepageDto.getValue();
        homepageDtoData.carouselList.forEach(x -> {x.isInWatchlist=isInWatchList(x, watchlist);});
        homepageDtoData.topRatedMovies.forEach(x -> {x.isInWatchlist=isInWatchList(x, watchlist);});
        homepageDtoData.popularMovies.forEach(x -> {x.isInWatchlist=isInWatchList(x, watchlist);});
        homepageDtoData.topRatedTvs.forEach(x -> {x.isInWatchlist=isInWatchList(x, watchlist);});
        homepageDtoData.popularTvs.forEach(x -> {x.isInWatchlist=isInWatchList(x, watchlist);});
        _homepageDto.postValue(homepageDtoData);

        _localWatchlist.setValue(watchlist);
    }
    public void addItemToWatchList(Item item){
        ArrayList<Item> watchList = new ArrayList<>(_localWatchlist.getValue());

        if (!isInWatchList(item, watchList)){
            // Add to watchlist
            int currentIndex = -1;
            for (int i =0; i<watchList.size(); i++){
                if (item.id.equals(watchList.get(i).id)){
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex >= 0 && currentIndex < watchList.size()){
                watchList.remove(currentIndex);
            }
            watchList.add(0, item);
            _localWatchlist.setValue(watchList);

            // Update status
            HomePageDTO homepageDtoData = _homepageDto.getValue();
            assert homepageDtoData != null;
            homepageDtoData.carouselList.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=true;} });
            homepageDtoData.topRatedMovies.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=true;} });
            homepageDtoData.popularMovies.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=true;} });
            homepageDtoData.topRatedTvs.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=true;} });
            homepageDtoData.popularTvs.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=true;} });
            _homepageDto.postValue(homepageDtoData);
        }
    }
    public void removeItemFromWatchList(Item item){
        ArrayList<Item> watchList = new ArrayList<>(_localWatchlist.getValue());
        assert watchList != null;
        if (isInWatchList(item, watchList)){
            // Remove from watchlist
            int currentIndex = -1;
            for (int i =0; i<watchList.size(); i++){
                if (item.id.equals(watchList.get(i).id)){
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex >= 0 && currentIndex < watchList.size()){
                watchList.remove(currentIndex);
            }
            _localWatchlist.setValue(watchList);

            // Update status
            HomePageDTO homepageDtoData = _homepageDto.getValue();
            assert homepageDtoData != null;
            homepageDtoData.carouselList.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=false;} });
            homepageDtoData.topRatedMovies.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=false;} });
            homepageDtoData.popularMovies.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=false;} });
            homepageDtoData.topRatedTvs.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=false;} });
            homepageDtoData.popularTvs.forEach(x -> {if (x.id.equals(item.id)){x.isInWatchlist=false;} });
            _homepageDto.postValue(homepageDtoData);
        }
    }


    private MutableLiveData<Boolean> _movieTabOpened;
    public LiveData<Boolean> getMovieTabOpened() {
        return _movieTabOpened;
    }
    public void setMovieTabOpened(boolean newValue) { this._movieTabOpened.setValue(newValue); }


    Context context;
    Application application;
    Gson gson;

    public HomeViewModel(Application application){
        this.application = application;
        gson = new Gson();

        _status = new MutableLiveData<String>();
        _homepageDto = new MutableLiveData<HomePageDTO>();
        _movieTabOpened = new MutableLiveData<Boolean>();

        _movieTabOpened.setValue(true);
        _status.setValue("Hello Home");
        updateData();
    }

    public void updateData(){
        String url = APIEndpoints.HomeUrl;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        _status.setValue("Successful");
                        HomePageDTO object = gson.fromJson(response.toString(), HomePageDTO.class);
                        _homepageDto.setValue(object);

                        if (_localWatchlist==null){
                            _localWatchlist = new MutableLiveData<List<Item>>();
                            LocalStorageConnector localStorageConnector = new LocalStorageConnector(application.getApplicationContext());
                            List<Item> watchList = localStorageConnector.getWatchList();
                            setLocalWatchlist(watchList);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _status.setValue("Failed");
                        // TODO: Handle error
                    }
                });
        VolleyQueueSingletonManager.getInstance(application).addToRequestQueue(jsonObjectRequest);
    }
}
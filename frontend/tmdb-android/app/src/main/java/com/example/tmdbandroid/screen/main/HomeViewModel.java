package com.example.tmdbandroid.screen.main;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.constant.APIEndpoints;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;
import com.google.gson.Gson;

import org.json.JSONObject;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> _status;
    public LiveData<String> getStatus() {
        return _status;
    }

    private MutableLiveData<HomePageDTO> _homepageDto;
    public LiveData<HomePageDTO> getHomepageDto() {
        return _homepageDto;
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
                        _homepageDto.postValue(object);
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
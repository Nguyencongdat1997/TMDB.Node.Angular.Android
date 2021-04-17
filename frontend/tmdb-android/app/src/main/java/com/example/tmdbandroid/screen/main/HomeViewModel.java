package com.example.tmdbandroid.screen.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;

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

    Context context;

    public HomeViewModel(Context context){
        this.context = context;

        _status = new MutableLiveData<String>();
        _homepageDto = new MutableLiveData<HomePageDTO>();

        _status.postValue("Hello Home");
        updateData();
    }

    public void updateData(){
        String url = "https://innate-legacy-310016.wl.r.appspot.com/api/v1/home";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        _status.postValue(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        VolleyQueueSingletonManager.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
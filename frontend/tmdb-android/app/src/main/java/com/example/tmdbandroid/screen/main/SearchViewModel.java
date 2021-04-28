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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.constant.APIEndpoints;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<String> _status;
    public LiveData<String> getStatus() {
        return _status;
    }

    private MutableLiveData<List<Item>> _searchDto;
    public LiveData<List<Item>> getSearchDto() {
        return _searchDto;
    }

    Context context;
    Application application;
    Gson gson;

    public SearchViewModel(Application application){
        this.application = application;
        gson = new Gson();

        _status = new MutableLiveData<String>();
        _searchDto = new MutableLiveData<>();
        _searchDto.setValue(new ArrayList<Item>());
    }

    public void updateData(String keyword){
        String url = null;
        try {
            url = APIEndpoints.SearchUrl + "?keyword=" + URLEncoder.encode(keyword.trim(), String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        _status.postValue("Successful");
                        Item[] objects = (gson.fromJson(response.toString(), Item[].class));
                        List<Item> items = new ArrayList<Item>();
                        for(Item item :objects) {
                            if (item != null){
                                items.add(item);
                            }
                        }
                        _searchDto.postValue(items);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _status.postValue("Failed");
                        _searchDto.postValue(new ArrayList<Item>());
                        // TODO: Handle error
                    }
                });
        VolleyQueueSingletonManager.getInstance(application.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
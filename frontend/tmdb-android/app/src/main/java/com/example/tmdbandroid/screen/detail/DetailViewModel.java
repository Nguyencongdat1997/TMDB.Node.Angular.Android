package com.example.tmdbandroid.screen.detail;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tmdbandroid.DTOs.DetailPageDTO;
import com.example.tmdbandroid.constant.APIEndpoints;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;
import com.google.gson.Gson;

import org.json.JSONObject;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<String> _status;
    public LiveData<String> getStatus() {
        return _status;
    }

    private MutableLiveData<DetailPageDTO> _detailDto;
    public LiveData<DetailPageDTO> getDetailDto() {
        return _detailDto;
    }
    private MutableLiveData<String> _youtubeKey;
    public LiveData<String> getYoutubeKey() {
        return _youtubeKey;
    }

    Context context;
    Application application;
    Gson gson;
    String itemId;
    String itemCategory;

    public DetailViewModel(Application application, String itemId, String itemCategory){
        this.application = application;
        this.itemId = itemId;
        this.itemCategory = itemCategory;
        gson = new Gson();

        _status = new MutableLiveData<String>();
        _detailDto = new MutableLiveData<>();
        _detailDto.setValue(DetailPageDTO.getEmptyDetailPageDto());
        _youtubeKey = new MutableLiveData<>();

        updateData(itemId);
    }

    public void updateData(String keyword){
        String url = APIEndpoints.ItemUrl + "/" + itemCategory + "/" + itemId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        _status.postValue("Successful");
                        DetailPageDTO object = (gson.fromJson(response.toString(), DetailPageDTO.class));
                        _detailDto.setValue(object);
                        _youtubeKey.setValue(object.chosenYoutubeVideo.key);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _status.postValue("Failed");
                        _detailDto.setValue(DetailPageDTO.getEmptyDetailPageDto());
                    }
                });
        VolleyQueueSingletonManager.getInstance(application.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
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
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.DTOs.ItemDetail;
import com.example.tmdbandroid.constant.APIEndpoints;
import com.example.tmdbandroid.services.network.VolleyQueueSingletonManager;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

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

    private MutableLiveData<Boolean> _isItemStored;
    public LiveData<Boolean> getIdItemStored() {
        return _isItemStored;
    }

    Context context;
    Application application;
    Gson gson;
    String itemId;
    String itemCategory;
    LocalStorageConnector localStorageConnector;

    public DetailViewModel(Application application, String itemId, String itemCategory){
        this.application = application;
        this.itemId = itemId;
        this.itemCategory = itemCategory;
        gson = new Gson();
        localStorageConnector = new LocalStorageConnector(application.getApplicationContext());

        _status = new MutableLiveData<String>();
        _detailDto = new MutableLiveData<>();
        _detailDto.setValue(DetailPageDTO.getEmptyDetailPageDto());
        _youtubeKey = new MutableLiveData<>();
        _isItemStored = new MutableLiveData<>();

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


                        List<Item> watchList = localStorageConnector.getWatchList();
                        _isItemStored.setValue(false);
                        for (int i =0; i< watchList.size(); i++){
                            if (watchList.get(i).id.equals(object.itemDetail.id)){
                                _isItemStored.setValue(true);
                            }
                        }
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

    public void addItemToLocalWatchList(){
        ItemDetail itemDetail  = _detailDto.getValue().itemDetail;
        Item item = new Item(itemDetail.id, itemDetail.title, itemDetail.posterPath, itemDetail.backdropPath, itemDetail.category, true);
        localStorageConnector.addToWatchList(item);
        _isItemStored.setValue(true);
    }

    public void removeItemFromLocalWatchList(){
        localStorageConnector.removeFromWatchList(itemId, itemCategory);
        _isItemStored.setValue(false);
    }
}
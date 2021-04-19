package com.example.tmdbandroid.screen.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.databinding.FragmentSearchBinding;
import com.example.tmdbandroid.databinding.FragmentWatchlistBinding;
import com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter;
import com.example.tmdbandroid.screen.components.watchlistMovieList.ItemMoveCallback;
import com.example.tmdbandroid.screen.components.watchlistMovieList.WatchListGridViewAdapter;
import com.example.tmdbandroid.screen.components.watchlistMovieList.WatchListRecyclerViewAdapter;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;

import java.util.List;

public class WatchlistFragment extends Fragment {

    private WatchlistViewModel viewModel;
    private FragmentWatchlistBinding binding;
    private Context context;

    RecyclerView watchlistGridView;
    WatchListGridViewAdapter gridViewAdapter;

    public static WatchlistFragment newInstance() {
        return new WatchlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWatchlistBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        WatchlistViewModelFactory viewModelFactory = new WatchlistViewModelFactory(getActivity().getApplication());
        viewModel = (new ViewModelProvider(this, viewModelFactory)).get(WatchlistViewModel.class);
        binding.setViewModel(viewModel);

        context = getContext();

        viewModel.getWatchList().observe(this, watchListUpdateObserver);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalStorageConnector localStorageConnector = new LocalStorageConnector(getContext());
        List<Item> watchList = viewModel.getWatchList().getValue();
        localStorageConnector.saveWatchList(watchList);
        binding = null;
    }

    private Observer<List<Item>> watchListUpdateObserver = new Observer<List<Item>>() {
        @Override
        public void onChanged(List<Item> watchList) {
            watchlistGridView = (RecyclerView) binding.watchlistGridView;

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
            watchlistGridView.setLayoutManager(gridLayoutManager);

            WatchListRecyclerViewAdapter adapter = new WatchListRecyclerViewAdapter(getContext(), watchList, viewModel);
            watchlistGridView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(watchlistGridView);


        }
    };

}
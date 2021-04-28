package com.example.tmdbandroid.screen.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.databinding.FragmentHomeBinding;

import com.example.tmdbandroid.screen.components.homeHorizontalMovieList.HorizontalRecycleViewAdapter;
import com.example.tmdbandroid.screen.components.homeSlider.SliderAdapter;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private Context context;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeViewModelFactory viewModelFactory = new HomeViewModelFactory(getActivity().getApplication());
        viewModel = (new ViewModelProvider(this, viewModelFactory)).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        context = getContext();
        binding.setViewModel(viewModel);

        binding.movieTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setMovieTabOpened(true);
            }
        });
        binding.tvTabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setMovieTabOpened(false);
            }
        });

        binding.footerTMDB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent openTMDBPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
                startActivity(openTMDBPage);
                return true;
            }
        });

        viewModel.getStatus().observe(this, homeGettingStatusUpdateObserver);
        viewModel.getHomepageDto().observe(this, homePageUpdateObserver);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.updateData();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalStorageConnector localStorageConnector = new LocalStorageConnector(getContext());
        List<Item> watchList = viewModel.getLocalWatchlist().getValue();
        localStorageConnector.saveWatchList(watchList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private final Observer<String> homeGettingStatusUpdateObserver = new Observer<String>() {
        @Override
        public void onChanged(String status) {
            if (status.equals("Successful")){
                binding.homeLoadingScreen.setVisibility(View.GONE);
                binding.homeLayout.setVisibility(View.VISIBLE);
            }
            else {
                binding.homeLoadingScreen.setVisibility(View.VISIBLE);
                binding.homeLayout.setVisibility(View.GONE);
            }
        }
    };

    private final Observer<HomePageDTO> homePageUpdateObserver = new Observer<HomePageDTO>() {
        @Override
        public void onChanged(HomePageDTO homePageDTO) {
            updateMovieSlider(homePageDTO);
            updateTvSlider(homePageDTO);
            updateItemLists(homePageDTO);
        }
    };

    private void updateMovieSlider(HomePageDTO homePageDTO){
        SliderAdapter homeSliderApdater = new SliderAdapter(context, homePageDTO.movieCarouselList.subList(0,Math.min(6, homePageDTO.movieCarouselList.size())));
        binding.homeMovieSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.homeMovieSlider.setSliderAdapter(homeSliderApdater);
        binding.homeMovieSlider.setScrollTimeInSec(3);
        binding.homeMovieSlider.setAutoCycle(true);
        binding.homeMovieSlider.startAutoCycle();
    }

    private void updateTvSlider(HomePageDTO homePageDTO){
        SliderAdapter homeSliderApdater = new SliderAdapter(context, homePageDTO.tvCarouselList.subList(0,Math.min(6, homePageDTO.tvCarouselList.size())));
        binding.homeTvSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.homeTvSlider.setSliderAdapter(homeSliderApdater);
        binding.homeTvSlider.setScrollTimeInSec(3);
        binding.homeTvSlider.setAutoCycle(true);
        binding.homeTvSlider.startAutoCycle();
    }

    private void updateItemLists(HomePageDTO homePageDTO){
        setHorizontalRecycleView(binding.homePopularMovieList, homePageDTO.popularMovies.subList(0,Math.min(10, homePageDTO.popularMovies.size())));
        setHorizontalRecycleView(binding.homeTopRatedMovieList, homePageDTO.topRatedMovies.subList(0,Math.min(10, homePageDTO.topRatedMovies.size())));
        setHorizontalRecycleView(binding.homePopularTvList, homePageDTO.popularTvs.subList(0,Math.min(10, homePageDTO.popularTvs.size())));
        setHorizontalRecycleView(binding.homeTopRatedTvList, homePageDTO.topRatedTvs.subList(0,Math.min(10, homePageDTO.topRatedTvs.size())));
    }

    private void setHorizontalRecycleView(RecyclerView recyclerView, List<Item> list){
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        HorizontalRecycleViewAdapter horizontalListApdater = new HorizontalRecycleViewAdapter(context, list, viewModel);
        recyclerView.setAdapter(horizontalListApdater);
    }

}
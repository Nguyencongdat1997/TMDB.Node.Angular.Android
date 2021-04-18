package com.example.tmdbandroid.screen.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.databinding.FragmentHomeBinding;

import com.example.tmdbandroid.R;
import com.example.tmdbandroid.screen.components.homeHorizontalMovieList.HorizontalRecycleViewAdapter;
import com.example.tmdbandroid.screen.components.homeSlider.SliderAdapter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        context = getContext();

        HomeViewModelFactory viewModelFactory = new HomeViewModelFactory(getActivity().getApplication());
        viewModel = (new ViewModelProvider(this, viewModelFactory)).get(HomeViewModel.class);
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

        viewModel.getHomepageDto().observe(this, homePageUpdateObserver);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Observer<HomePageDTO> homePageUpdateObserver = new Observer<HomePageDTO>() {
        @Override
        public void onChanged(HomePageDTO homePageDTO) {
            updateMovieSlider(homePageDTO);
            updatePopularMovieList(homePageDTO);
        }
    };

    private void updateMovieSlider(HomePageDTO homePageDTO){
        SliderAdapter homeSliderApdater = new SliderAdapter(context, homePageDTO.carouselList.subList(0,6));
        binding.homeSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        binding.homeSlider.setSliderAdapter(homeSliderApdater);
        binding.homeSlider.setScrollTimeInSec(3);
        binding.homeSlider.setAutoCycle(true);
        binding.homeSlider.startAutoCycle();
    }

    private void updatePopularMovieList(HomePageDTO homePageDTO){
        setHorizontalRecycleView(binding.homePopularMovieList, homePageDTO.popularMovies.subList(0,10));
        setHorizontalRecycleView(binding.homeTopRatedMovieList, homePageDTO.topRatedMovies.subList(0,10));
    }

    private void setHorizontalRecycleView(RecyclerView recyclerView, List<Item> list){
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        HorizontalRecycleViewAdapter horizontalListApdater = new HorizontalRecycleViewAdapter(list);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        recyclerView.setAdapter(horizontalListApdater);
    }

}
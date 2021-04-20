package com.example.tmdbandroid.screen.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmdbandroid.DTOs.DetailPageDTO;
import com.example.tmdbandroid.databinding.DetailFragmentBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class DetailFragment extends Fragment {

    private String itemId;
    private String itemCategory;
    private DetailViewModel viewModel;
    private DetailFragmentBinding binding;
    private Context context;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailFragmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        itemId =  this.getArguments().getString("itemId");
        itemCategory =  this.getArguments().getString("itemCategory");
        context = getContext();

        DetailViewModelFactory viewModelFactory = new DetailViewModelFactory(getActivity().getApplication(), itemId, itemCategory);
        viewModel = (new ViewModelProvider(this, viewModelFactory)).get(DetailViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getDetailDto().observe(this, detailPageUpdateObserver);
        viewModel.getYoutubeKey().observe(this, youtubePlayerUpdateObserver);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //YouTubePlayerView youTubePlayerView = getView().findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(binding.youtubePlayerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Observer<DetailPageDTO> detailPageUpdateObserver = new Observer<DetailPageDTO>() {
        @Override
        public void onChanged(DetailPageDTO detailPageDTO) {
            binding.itemIdTxt.setText(detailPageDTO.itemDetail.title);
        }
    };

    private Observer<String> youtubePlayerUpdateObserver = new Observer<String>() {
        @Override
        public void onChanged(String youtubeKey) {
            AbstractYouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(youtubeKey, 0);
                }
            };
            binding.youtubePlayerView.initialize(listener);
        }
    };
}
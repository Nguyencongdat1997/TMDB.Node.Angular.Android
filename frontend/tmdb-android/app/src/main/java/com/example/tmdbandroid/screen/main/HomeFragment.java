package com.example.tmdbandroid.screen.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tmdbandroid.databinding.FragmentHomeBinding;

import com.example.tmdbandroid.R;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater);
        binding.setLifecycleOwner(this);

        HomeViewModelFactory viewModelFactory = new HomeViewModelFactory(this.getContext());
        binding.setViewModel(new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class));

//        return inflater.inflate(R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
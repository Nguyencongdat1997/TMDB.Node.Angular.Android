package com.example.tmdbandroid.screen.main;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.tmdbandroid.DTOs.HomePageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.databinding.FragmentHomeBinding;
import com.example.tmdbandroid.databinding.FragmentSearchBinding;
import com.example.tmdbandroid.screen.components.homeHorizontalMovieList.HorizontalRecycleViewAdapter;
import com.example.tmdbandroid.screen.components.searchResultList.SearchResultsAdapter;
import com.example.tmdbandroid.services.storage.LocalStorageConnector;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private Context context;
    private SearchResultsAdapter adapter;
    private SearchView searchBar;

    List<String> arrayList= new ArrayList<>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        context = getContext();

        SearchViewModelFactory viewModelFactory = new SearchViewModelFactory(getActivity().getApplication());
        viewModel = (new ViewModelProvider(this, viewModelFactory)).get(SearchViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getSearchDto().observe(this, searchResultsUpdateObserver);

        searchBar = binding.searchBar;
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search movie and TV");
        searchBar.onActionViewExpanded();
        AutoCompleteTextView search_text = searchBar.findViewById(R.id.search_src_text);
        // search_text.setTextColor(Color.WHITE); // This has been implemented in @style/AppTheme.androidx.appcompat.widget.SearchView. And this will NOT set the text-color of hint
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));
        // search_text.setPadding(getResources().getDimensionPixelSize(R.dimen.text_margin), 0, 0, 0);
        searchBar.setIconified(false);
        // searchBar.clearFocus();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // viewModel.updateData(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.updateData(newText);
                return false;
            }
        });

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Observer<List<Item>> searchResultsUpdateObserver = new Observer<List<Item>>() {
        @Override
        public void onChanged(List<Item> searchResults) {
            RecyclerView recyclerView = binding.searchResults;

            RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);

            adapter = new SearchResultsAdapter(context, searchResults.subList(0,Math.min(20, searchResults.size())));

            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false);
            recyclerView.setLayoutManager(horizontalLayoutManager);

            recyclerView.setAdapter(adapter);
        }
    };

}
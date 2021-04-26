package com.example.tmdbandroid.screen.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tmdbandroid.DTOs.Cast;
import com.example.tmdbandroid.DTOs.DetailPageDTO;
import com.example.tmdbandroid.DTOs.Item;
import com.example.tmdbandroid.DTOs.Review;
import com.example.tmdbandroid.R;
import com.example.tmdbandroid.databinding.DetailFragmentBinding;
import com.example.tmdbandroid.screen.components.castsList.CastRecyclerViewAdapter;
import com.example.tmdbandroid.screen.components.detailItemHorizontalMovieList.HorizontalRecycleViewAdapter;
import com.example.tmdbandroid.screen.components.reviewsList.ReviewRecyclerViewAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        String tmdpUrl = "https://www.themoviedb.org/" + itemCategory + "/" + itemId;
        binding.detailItemFbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fbUrl = "https://www.facebook.com/sharer/sharer.php?u=" + tmdpUrl;
                Intent openFBPage = new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl));
                context.startActivity(openFBPage);
            }
        });
        binding.detailItemTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twUrl = "https://twitter.com/intent/tweet?text=" + tmdpUrl;
                Intent openTwitterPage = new Intent(Intent.ACTION_VIEW, Uri.parse(twUrl));
                context.startActivity(openTwitterPage);
            }
        });
        binding.detailItemAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addItemToLocalWatchList();
                Toast toast = Toast.makeText(context, "\""
                        + viewModel.getDetailDto().getValue().itemDetail.title
                        + "\" was added to Watchlist", Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor(Color.TRANSPARENT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.BLACK);
                toastMessage.setBackground(context.getDrawable(R.drawable.custom_toast));
                toast.setGravity(Gravity.BOTTOM,0,10);
                toast.show();
            }
        });
        binding.detailItemRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeItemFromLocalWatchList();
                Toast toast = Toast.makeText(context, "\""
                        + viewModel.getDetailDto().getValue().itemDetail.title
                        + "\" was removed from Watchlist", Toast.LENGTH_SHORT);
                View view =toast.getView();
                view.setBackgroundColor(Color.TRANSPARENT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.BLACK);
                toastMessage.setBackground(context.getDrawable(R.drawable.custom_toast));
                toast.setGravity(Gravity.BOTTOM,0,10);
                toast.show();
            }
        });

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            if (detailPageDTO.itemDetail != null && !detailPageDTO.itemDetail.id.equals("")){
                String year = "";
                try {
                    Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(detailPageDTO.itemDetail.date);
                    year = (new SimpleDateFormat("yyyy").format(date));
                }
                catch (Exception e){
                    year = "";
                }
                binding.detailYear.setText(year);
            }

            if (detailPageDTO.casts != null && detailPageDTO.casts.size()>0){
                setCastRecyclerView(detailPageDTO.casts);
            }

            if (detailPageDTO.reviews != null && detailPageDTO.reviews.size()>0){
                setReviewListView(detailPageDTO.reviews);
                binding.reviewsSectionTitle.setVisibility(View.VISIBLE);
            }
            else{
                binding.reviewsSectionTitle.setVisibility(View.GONE);
            }

            if (detailPageDTO.recommendations != null && detailPageDTO.recommendations.size()>0){
                setHorizontalRecycleView(binding.detailItemRecommendations, detailPageDTO.recommendations.subList(0,Math.min(10, detailPageDTO.recommendations.size())));
            }
        }
    };

    private Observer<String> youtubePlayerUpdateObserver = new Observer<String>() {
        @Override
        public void onChanged(String youtubeKey) {
            if (youtubeKey!= null && !youtubeKey.equals("")){
                binding.youtubePlayerView.setVisibility(View.VISIBLE);
                binding.itemDetailBackdrop.setVisibility(View.GONE);

                AbstractYouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(youtubeKey, 0);
                    }
                };
                binding.youtubePlayerView.initialize(listener);
            }
            else{
                binding.youtubePlayerView.setVisibility(View.GONE);
                binding.itemDetailBackdrop.setVisibility(View.VISIBLE);

                Glide.with(binding.getRoot())
                        .load(viewModel.getDetailDto().getValue().itemDetail.backdropPath)
                        .placeholder(R.drawable.movie_placeholder)
                        .transform(new MultiTransformation<>(
                                new FitCenter(), new RoundedCorners(1)
                        ))
                        .into(binding.itemDetailBackdrop);
            }

        }
    };

    private void setCastRecyclerView(List<Cast> castList){
        castList = castList.subList(0, Math.min(6, castList.size()));
        RecyclerView castsRecyclerView = (RecyclerView) binding.castsRecyclerView;
        castsRecyclerView.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        castsRecyclerView.setLayoutManager(gridLayoutManager);

        CastRecyclerViewAdapter adapter = new CastRecyclerViewAdapter(getContext(), castList);
        castsRecyclerView.setAdapter(adapter);
    }

    private void setReviewListView(List<Review> list){
        List<Review> limitedList = list.subList(0, Math.min(3, list.size()));
        RecyclerView recyclerView = (RecyclerView) binding.reviewsRecylerView;
        recyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ReviewRecyclerViewAdapter adapter = new ReviewRecyclerViewAdapter(getContext(), limitedList);
        recyclerView.setAdapter(adapter);
    }


    private void setHorizontalRecycleView(RecyclerView recyclerView, List<Item> list){
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        HorizontalRecycleViewAdapter horizontalListApdater = new HorizontalRecycleViewAdapter(context, list);
        recyclerView.setAdapter(horizontalListApdater);
    }
}
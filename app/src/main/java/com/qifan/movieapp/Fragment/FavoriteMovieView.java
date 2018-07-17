package com.qifan.movieapp.Fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qifan.movieapp.FavoriteMovieAdapter;
import com.qifan.movieapp.MainViewModel;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.MyApplication;
import com.qifan.movieapp.R;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;

import java.util.List;

/**
 * A fragment with a Google +1 button.
 */
public class FavoriteMovieView extends Fragment {
    MovieAdapter movieAdapter;
    public static List<MovieEntry> favoriteMovieList;
    FavoriteMovieAdapter favoriteMovieAdapter;
    private final String LOG_TAG=FavoriteMovieView.this.getClass().getSimpleName();


    public FavoriteMovieView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie_view, container, false);

        favoriteMovieAdapter=new FavoriteMovieAdapter(this.getContext());
        setUpViewModel();
        //movieAdapter.setList();
        GridView gridView=view.findViewById(R.id.gridView_favorite);

        gridView.setAdapter(favoriteMovieAdapter);

        return view;
    }


    public void setUpViewModel() {
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final LiveData<List<MovieEntry>> movieList = viewModel.getmTasks();
        movieList.observe(this.getActivity(), new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                // movieList.removeObserver(this);
                if (favoriteMovieAdapter != null) {
                    favoriteMovieList=movieEntries;
                    //notify the adapter that the data has changed
                    favoriteMovieAdapter.setTask(movieEntries);
                    LogUtil.d(LOG_TAG, "Movie Adapter Set");
                } else {
                    LogUtil.d(LOG_TAG, "MovieEntry is NULL");
                }

            }
        });


    }



}
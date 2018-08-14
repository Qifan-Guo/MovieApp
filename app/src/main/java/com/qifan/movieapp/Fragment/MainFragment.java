package com.qifan.movieapp.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qifan.movieapp.AppExecutors;
import com.qifan.movieapp.MainActivity;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.MovieDetails;
import com.qifan.movieapp.OnHttpListener;
import com.qifan.movieapp.R;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieEntry;
import com.qifan.movieapp.onSwitchChangeListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private GridView gridView;
    @Nullable
    public String SORT_BY;
    @Nullable
    Context context;
    private final String LOG_TAG = MainFragment.this.getClass().getSimpleName();
    private final String DEFAULT_VIEW = "popular";
    AppExecutors appExecutors;
    @Nullable
    private MovieAdapter movieAdapter;
    private OnHttpListener onHttpListener;
    private final String QUEUE_MAIN="main";
    private Parcelable mState;
    private String REVIEW_LIST_STATE="list_state";
    private  Bundle state;
    private String grid_position="grid_position";
    int mCurrentPosition;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        //get Context from MainActivity
        context = getContext();
        state=savedInstanceState;
        gridView = rootView.findViewById(R.id.gridView);
        appExecutors = AppExecutors.getInstance();
        //get Saved Instance from MainActivity Menu Selection
        Bundle bundle = getArguments();

        getSavedInstance(bundle);
        //Get Data from API and Load the Data Into Database
        URL url = getURL();
        LogUtil.d(LOG_TAG, "GETs DATA FROM API");
        setOnHttpListener();
        httpRequest(url);

        LogUtil.d(LOG_TAG, "Create");
        onSwitchChange();
        if(savedInstanceState!=null){mCurrentPosition=savedInstanceState.getInt(grid_position);}

        return rootView;


    }


    public void SetUpView() {
        movieAdapter = new MovieAdapter(context);
        gridView.setAdapter(movieAdapter);
        gridView.smoothScrollToPosition(mCurrentPosition+4);
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mCurrentPosition=gridView.getFirstVisiblePosition();
        outState.putInt(grid_position,mCurrentPosition);
    }

    public void httpRequest(URL url1) {
        final URL url = url1;

        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                HttpUtil.httpRequest(url, onHttpListener,QUEUE_MAIN);
            }
        });


    }


    private URL getURL() {
        URL url = null;
        if (SORT_BY == "popular" || SORT_BY == "top_rated") {
            url = MainActivity.sortURL(SORT_BY);
        }
        return url;
    }

    public void getSavedInstance(@Nullable Bundle bundle) {
        if (bundle != null) {
            SORT_BY = bundle.getString("SORT_BY");
            LogUtil.d(LOG_TAG, SORT_BY);


        } else {
            SORT_BY = DEFAULT_VIEW;
        }
    }

    public void onSwitchChange() {

        MovieDetails movieDetails = new MovieDetails();
        onSwitchChangeListener onSwitchChangeListener = new onSwitchChangeListener() {
            @Override
            public void onChange() {
                movieAdapter.notifyDataSetChanged();
            }
        };
        movieDetails.setOnSwitchChangeListener(onSwitchChangeListener);


    }

    public void setOnHttpListener() {
        onHttpListener = new OnHttpListener() {
            @Override
            public void onHttpResponse(final String data,final String QUEUE) {
                appExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtil.parseJSONwithJSONObject(data, SORT_BY, QUEUE, onHttpListener);
                        HttpUtil.checkIfisInDatabase();
                    }
                });


            }

            @Override
            public void onParseFinish(String WhichList) {
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        SetUpView();
                    }
                });


            }
        };

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        onSwitchChange();
//    }
    //LifeCycle
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        LogUtil.d("LIFECYCLE","ON START");
//    }
//
//    @Override
//    public void onResume() {
//        LogUtil.d("LIFECYCLE","ON RESUME");
//        super.onResume();
//    }
//
//
//    @Override
//    public void onPause() {
//        LogUtil.d("LIFECYCLE","ON PAUSE");
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        LogUtil.d("LIFECYCLE","ON STOP");
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {
//        LogUtil.d("LIFECYCLE","ON DESTROY");
//        super.onDestroy();
//    }


}

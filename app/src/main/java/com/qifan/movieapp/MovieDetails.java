package com.qifan.movieapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.qifan.movieapp.Fragment.FavoriteMovieView;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    private AppExecutors appExecutors;
    private TextView overview;
    private final String QUEUE_VIDEO = "videos";
    private final String QUEUE_REVIEWS = "reviews";
    private RecyclerView recyclerView;
    private Context mContext;
    private ReviewListAdapter reviewListAdapter;
    private TextView detail_info;
    private TextView title;
    private ImageView imageView;
    private RecyclerView watch;
    private Switch aSwitch;
    private final String LOG_TAG = MovieDetails.this.getClass().getSimpleName();
    private final MovieDatabase movieDatabase = MovieDatabase.getInstance(MyApplication.getContext());
    private static onSwitchChangeListener onSwitchChangeListener;
    private static OnHttpListener onHttpListener;
    private static ArrayList<String> key;
    private MovieEntry movie_obj;
    private TrailerListViewAdapter TrailerAdapter;
    private LinearLayoutManager reviewListLayoutManager;
    private Parcelable mState;
    private String REVIEW_LIST_STATE="list_state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_view);

        initView();
        mContext = MovieDetails.this;
        setOnHttpListener();

        if (getIntent() != null) {
            int position = getIntent().getIntExtra("Entry_Position", 0);
            boolean isFavorite=getIntent().getBooleanExtra("Favorite",false);
            if(isFavorite==true){
                    movie_obj= FavoriteMovieView.favoriteMovieList.get(position);
            }else{
                 movie_obj = HttpUtil.list.get(position);
            }
            setUpView(movie_obj);

            getYoutubeURL(movie_obj);

        } else {
            LogUtil.d(LOG_TAG, "Intent is Null");
        }


    }


    public void initView() {
        title = findViewById(R.id.Movie_Title);
        overview = findViewById(R.id.overview);
        detail_info = findViewById(R.id.detail_Info);
        imageView = findViewById(R.id.detail_poster);
        aSwitch = findViewById(R.id.switch1);
        watch = findViewById(R.id.watch_trailer);
        recyclerView = findViewById(R.id.reviews_list);
        reviewListLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reviewListLayoutManager);
        recyclerView.setFocusable(false);
        reviewListAdapter = new ReviewListAdapter(this);
        recyclerView.setAdapter(reviewListAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        watch.setLayoutManager(linearLayoutManager1);
        TrailerAdapter=new TrailerListViewAdapter(this);
        watch.setAdapter(TrailerAdapter);
    }

    public void setUpView(final MovieEntry movie_obj) {
        //Formatting section
        String overview_format = "OVERVIEW: \n" + movie_obj.getOverview();
        String detail_info_format = movie_obj.getTopRate1() + "\n\n"
                + movie_obj.getPopularity1() + "\n\n" + "Release Date: "
                + movie_obj.getReleaseDate() + "\n\n" + "Language: " + movie_obj.getLanguage();

        title.setText(movie_obj.getTitle());
        overview.setText(overview_format);
        detail_info.setText(detail_info_format);
        Picasso.get().load(MovieInfo.base_img_url + MovieInfo.large_img_size + movie_obj.getPosterURL())
                .into(imageView);
        aSwitch.setChecked(movie_obj.getFavorite());
        onSwitchChange_MovieDetail(movie_obj);
        getReviewURL(movie_obj);


    }


    public void onSwitchChange_MovieDetail(final MovieEntry movie_obj) {
        //if the switch is switch then add it to the favorite database
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (isChecked) {
                            movie_obj.setFavorite(true);
                            movieDatabase.movieDao().insertTask(movie_obj);
                            runOnUiThread(NotifyAdapter());
                            LogUtil.d(LOG_TAG, "INSERTING INTO DATABASE" + String.valueOf(movie_obj.getId()));

                        } else {
                            movie_obj.setFavorite(false);
                            movieDatabase.movieDao().deleteTask(movie_obj.getTitle());
                            runOnUiThread(NotifyAdapter());
                            LogUtil.d(LOG_TAG, "REMOVE FROM DATABASE" + String.valueOf(movie_obj.getId()));
                        }


                    }
                });


            }
        });

    }

    public Runnable NotifyAdapter() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange();
                }
            }
        };

        return runnable;

    }


    public void setOnSwitchChangeListener(onSwitchChangeListener onSwitchChangeListener) {
        MovieDetails.onSwitchChangeListener = onSwitchChangeListener;
    }

    public void setOnHttpListener() {
        onHttpListener = new OnHttpListener() {
            @Override
            public void onHttpResponse(String data, String QUEUE) {
                HttpUtil.parseJSONwithJSONObject(data, "", QUEUE, onHttpListener);
            }

            @Override
            public void onParseFinish(String WhichList) {
                if (WhichList == QUEUE_VIDEO) {
                    int size=HttpUtil.VideokeyList.size();
                    key=new ArrayList<>();
                    while(size!=0){
                        key.add(HttpUtil.VideokeyList.get(size-1));
                        size--;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TrailerAdapter.setData(key);

                        }
                    });

                } else if (WhichList == QUEUE_REVIEWS) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reviewListAdapter.setData();
                        }
                    });

                }

            }
        };
    }

    public void getYoutubeURL(MovieEntry movie_obj) {
        String requestWebsite = MovieInfo.buildBaseWebAddress(movie_obj.getMovieID(), QUEUE_VIDEO);
        //this is the url that contains key to the video
        URL URL = MovieInfo.buildURL1(requestWebsite);
        AppExecutors.getInstance().networkIO().execute(parseVideoKey(URL));

    }

    public void getReviewURL(MovieEntry movie_obj) {
        String requestReviewURL = MovieInfo.buildBaseWebAddress(movie_obj.getMovieID(), QUEUE_REVIEWS);
        URL URL = MovieInfo.buildURL1(requestReviewURL);
        AppExecutors.getInstance().networkIO().execute(parseReviewList(URL));
    }


    public Runnable parseReviewList(final URL url) {
        Runnable parseReviews = new Runnable() {
            @Override
            public void run() {
                HttpUtil.httpRequest(url, onHttpListener, QUEUE_REVIEWS);
            }
        };
        return parseReviews;
    }


    public Runnable parseVideoKey(final URL url) {

        Runnable paserVideoKey = new Runnable() {
            @Override
            public void run() {
                HttpUtil.httpRequest(url, onHttpListener, QUEUE_VIDEO);

            }
        };
        return paserVideoKey;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mState=reviewListLayoutManager.onSaveInstanceState();
        outState.putParcelable(REVIEW_LIST_STATE,mState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            mState=savedInstanceState.getParcelable(REVIEW_LIST_STATE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mState!=null){
            reviewListLayoutManager.onRestoreInstanceState(mState);
        }
    }
}

package com.qifan.movieapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.qifan.movieapp.Fragment.MainFragment;
import com.qifan.movieapp.Fragment.FavoriteMovieView;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.this.getClass().getSimpleName();
    private GridView gridView;
    @NonNull
    private MainFragment MainFragment;
    private FavoriteMovieView favoriteMovieView;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    public final String POPULARITY = "popular";
    public final String TOP_RATE = "top_rated";
    public final String FAVORITE = "favorite";
    MovieDatabase movieDatabase;
    FragmentManager fragmentManager;
    private static Boolean isInMainFragment = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment = new MainFragment();
        favoriteMovieView = new FavoriteMovieView();
        fragmentManager = getSupportFragmentManager();
        SetUpFragment(MainFragment);
        movieDatabase = MovieDatabase.getInstance(this.getApplicationContext());


    }

    public void SetUpFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragment, "").commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String sortBy = "";

        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

        switch (id) {

            case R.id.mostPopularSort:
                sortBy = POPULARITY;
                bundle.putString("SORT_BY", sortBy);
                if (isInMainFragment) {
                    fragmentTransaction1.detach(MainFragment);
                    MainFragment.setArguments(bundle);
                    fragmentTransaction1.attach(MainFragment);
                    fragmentTransaction1.commit();
                } else {
                    MainFragment.setArguments(bundle);
                    SetUpFragment(MainFragment);
                    isInMainFragment = true;
                }
                Log.d("debugg", "POPULARITY option selected");
                break;
            case R.id.topRatedSort:
                sortBy = TOP_RATE;
                bundle.putString("SORT_BY", sortBy);
                if (isInMainFragment) {
                    fragmentTransaction1.detach(MainFragment);
                    MainFragment.setArguments(bundle);
                    fragmentTransaction1.attach(MainFragment);
                    fragmentTransaction1.commit();
                } else {
                    MainFragment.setArguments(bundle);
                    SetUpFragment(MainFragment);
                    isInMainFragment = true;
                }

                Log.d(LOG_TAG, "TopRate option selected");
                break;
            case R.id.favorite:
                sortBy = FAVORITE;
                isInMainFragment = false;
                bundle.putString("SORT_BY", sortBy);
                MainFragment.setArguments(bundle);
                SetUpFragment(favoriteMovieView);
                Log.d(LOG_TAG, "Favorite option selected");
                break;
        }
        return true;
    }


    @Nullable
    public static URL sortURL(String sortBy) {
        URL url = null;
        if (sortBy == MovieInfo.popularity_desc) {
            url = MovieInfo.buildURL(MovieInfo.popularity_desc);

        } else if (sortBy == MovieInfo.topRate) {
            url = MovieInfo.buildURL(MovieInfo.topRate);
        }
        LogUtil.d("URL","Sort URL: "+url.toString());
        return url;
    }
}

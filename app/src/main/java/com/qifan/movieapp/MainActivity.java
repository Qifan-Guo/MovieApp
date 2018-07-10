package com.qifan.movieapp;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.Fragment.MainFragment;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG=MainActivity.this.getClass().getSimpleName();
    private GridView gridView;
    @NonNull
    private ArrayList<MovieObj> list = new ArrayList<>();
    private MainFragment MainFragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    public final String POPULARITY ="popular";
    public final String TOP_RATE ="top_rated";
    public final String FAVORITE="favorite";
    MovieDatabase movieDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment =new MainFragment();
       fragmentTransaction =getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.mainFragment, MainFragment,"").commit();
        LogUtil.d(LOG_TAG,"Database Create in main Thread");
        movieDatabase=MovieDatabase.getInstance(this.getApplicationContext());


    }

    @Nullable
    public static URL sortURL(String sortBy){
        URL url=null;
        if(sortBy== MovieInfo.popularity_desc){
            url= MovieInfo.buildURL(MovieInfo.popularity_desc);

        }else
            if(sortBy== MovieInfo.topRate){
            url= MovieInfo.buildURL(MovieInfo.topRate);
            }
            return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movie_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String sortBy="";
        Bundle bundle=new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();



        switch (id){
            case R.id.mostPopularSort:
                sortBy= POPULARITY;
                bundle.putString("SORT_BY",sortBy);
                fragmentTransaction1.detach(MainFragment);
                MainFragment.setArguments(bundle);
                fragmentTransaction1.attach(MainFragment);
                fragmentTransaction1.commit();



                Log.d("debugg","POPULARITY option selected");
                break;
            case R.id.topRatedSort:
                sortBy= TOP_RATE;
                bundle.putString("SORT_BY",sortBy);
                fragmentTransaction1.detach(MainFragment);
                MainFragment.setArguments(bundle);
                fragmentTransaction1.attach(MainFragment);
                fragmentTransaction1.commit();

                Log.d(LOG_TAG,"TopRate option selected");
               break;
            case R.id.favorite:
                sortBy= FAVORITE;
                bundle.putString("SORT_BY",sortBy);
                fragmentTransaction1.detach(MainFragment);
                MainFragment.setArguments(bundle);
                fragmentTransaction1.attach(MainFragment);
                fragmentTransaction1.commit();

                Log.d(LOG_TAG,"Favorite option selected");
                break;
        }
        return true;
    }
}

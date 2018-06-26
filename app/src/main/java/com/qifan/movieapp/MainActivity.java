package com.qifan.movieapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.qifan.movieapp.Beans.Movie_Obj;
import com.qifan.movieapp.Fragment.main_fragment;
import com.qifan.movieapp.Utility.HttpCallbackListener;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    HttpCallbackListener httpCallbackListener=new HttpCallbackListener() {
//        @Override
//        public void onFinish(String response) {
//            list = HttpUtil.parseJSONwithJSONObject(response);
//            if(!list.isEmpty()) {
//                LogUtil.d("debugg", "List Filled");
//            }
//
//        }
//        @Override
//        public void onError(Exception e) {
//
//        }
//    };
    private GridView gridView;
    private ArrayList<Movie_Obj> list = new ArrayList<>();
    private main_fragment main_fragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_fragment=new main_fragment();
       fragmentTransaction =getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.mainFragment,main_fragment,"").commit();



    }

    public static URL sortURL(String sortBy){
        URL url=null;
        if(sortBy==Info_MovieDB.popularity_desc){
            url=Info_MovieDB.buildURL(Info_MovieDB.popularity_desc);

        }else
            if(sortBy==Info_MovieDB.voter_avg_desc){
            url=Info_MovieDB.buildURL(Info_MovieDB.voter_avg_desc);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String sortBy="";
        Bundle bundle=new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();



        switch (id){
            case R.id.mostPopularSort:
                sortBy="popularity.desc";
                bundle.putString("sortBy",sortBy);
                fragmentTransaction1.detach(main_fragment);
                main_fragment.setArguments(bundle);
                fragmentTransaction1.attach(main_fragment);
                fragmentTransaction1.commit();



                Log.d("debugg","popularity option selected");
                break;
            case R.id.topRatedSort:
                sortBy="vote_average.desc";
                bundle.putString("sortBy",sortBy);
                fragmentTransaction1.detach(main_fragment);
                main_fragment.setArguments(bundle);
                fragmentTransaction1.attach(main_fragment);
                fragmentTransaction1.commit();

                Log.d("debugg","TopRate option selected");
               break;
        }
        return true;
    }
}

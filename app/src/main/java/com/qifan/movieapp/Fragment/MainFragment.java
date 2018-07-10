package com.qifan.movieapp.Fragment;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.qifan.movieapp.AppExecutors;

import com.qifan.movieapp.MainActivity;
import com.qifan.movieapp.MainViewModel;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.MyApplication;
import com.qifan.movieapp.R;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;

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
    private final String LOG_TAG=MainFragment.this.getClass().getSimpleName();
    private final String DEFAULT_VIEW="favorite";
    AppExecutors appExecutors;
    @Nullable
    private MovieAdapter movieAdapter;
    private static int DATA_READY=0;
    private ArrayList<MovieEntry> arrayList;
    //Is Favorite Selected?
    private static Boolean isFavorite=false;

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

        View rootView=inflater.inflate(R.layout.fragment_main_fragment, container, false);
        //get Context from MainActivity
        context=getContext();
        gridView=rootView.findViewById(R.id.gridView);
        appExecutors=AppExecutors.getInstance();
        //get Saved Instance from MainActivity Menu Selection
        Bundle bundle=getArguments();

        getSavedInstance(bundle);
        //Get Data from API and Load the Data Into Database
        URL url =getURL();
        displayView(url);


        LogUtil.d(LOG_TAG,"Create");
        Toast.makeText(this.getContext(),"On Create",Toast.LENGTH_SHORT).show();
        setUpViewModel();
        return rootView;


    }

    public void displayView(@Nullable URL url){
        if(url!=null){
            //get Data from API
            LogUtil.d(LOG_TAG,"GETs DATA FROM API");
            httpRequest(url);

        }else{
            //get Data from Database

            SetUpView();

            LogUtil.d(LOG_TAG,"You have Choose Favorite");

        }
    }


    public void SetUpView(){
        movieAdapter=new MovieAdapter(context);
        gridView.setAdapter(movieAdapter);


    }

    public void setUpViewModel(){
        MainViewModel viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getmTasks().observe(this.getActivity(), new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                if(movieAdapter!=null){
                    //notify the adapter that the data has changed
                movieAdapter.setTask(movieEntries);
                }
                else{
                    LogUtil.d(LOG_TAG,"MovieEntry is NULL");
                }

            }
        });


    }





    public void httpRequest(URL url1){
        final URL  url= url1;

        appExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                try{

                    httpURLConnection=(HttpURLConnection) url.openConnection();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("//a");
                    boolean hasInput=scanner.hasNext();
                    if(hasInput){
                        HttpUtil.parseJSONwithJSONObject(scanner.next(), SORT_BY);
                        LogUtil.d(LOG_TAG,"Data Received");
                        appExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                SetUpView();
                            }
                        });


                    }else{
                        LogUtil.d(LOG_TAG,"doInTheBackground data Not Exist");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    if (httpURLConnection!=null){
                        httpURLConnection.disconnect();
                    }
                }


            }
        });


    }

    public void loadToDatabase(){
        if(HttpUtil.list!=null) {
            appExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    MovieDatabase movieDatabase=MovieDatabase.getInstance(MyApplication.getContext());
                    movieDatabase.movieDao().deleteALL();
                    List<MovieEntry> list =HttpUtil.list;
                    for(MovieEntry movieEntry : list){
                        movieDatabase.movieDao().insertTask(movieEntry);
                        }

                }
            });
        }
    }




    private URL getURL(){
        URL url=null;
             if(SORT_BY =="favorite"){
                 return null;
                 }if(SORT_BY =="popular"|| SORT_BY =="top_rated"){
            url=MainActivity.sortURL(SORT_BY);
            }
            return url;
    }

    public void getSavedInstance(@Nullable Bundle bundle){
        if(bundle!=null) {
            SORT_BY = bundle.getString("SORT_BY");
            LogUtil.d(LOG_TAG,SORT_BY);


        }else{
            SORT_BY =DEFAULT_VIEW;
        }
    }


//LifeCycle

//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(this.getContext(), "onStart", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(this.getContext(), "onResume", Toast.LENGTH_SHORT).show();
//    }
//
//
//    @Override
//    public void onPause() {
//        Toast.makeText(context, "onPause", Toast.LENGTH_SHORT).show();
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        Toast.makeText(context, "onStop", Toast.LENGTH_SHORT).show();
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {
//        Toast.makeText(context, "onDestroy", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }



}

package com.qifan.movieapp.Fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.MainActivity;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.MovieInfo;
import com.qifan.movieapp.R;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private GridView gridView;
    private String sortBy;
    Context context;

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
        gridView=rootView.findViewById(R.id.gridView);
        Bundle bundle=getArguments();
        context=getContext();
        if(bundle!=null){
        sortBy=bundle.getString("sortBy");
            new myAsyncTask(URLsortBy(sortBy)).execute();
        }else {
            new myAsyncTask(URLsortBy("popular")).execute();}
        // Inflate the layout for this fragment
        LogUtil.d("time","onCreate");
        return rootView;


    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("time","onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("time","onResume");
    }

    @Override
    public void onStart() {
        LogUtil.d("time","onStart");
        super.onStart();

    }



    public URL URLsortBy(String sortBy){
        URL url= MainActivity.sortURL(sortBy);
        LogUtil.d("URL","URL formed"+url.toString());
        return url;
    }

    class myAsyncTask extends AsyncTask<Void,Integer,String>{


        private URL url=null;
        ArrayList<MovieObj> list= new ArrayList<>();

        public myAsyncTask( URL url1){
            if(url1!=null){
            this.url=url1;}
        }
        @Override
        protected String doInBackground(Void...voids) {
            HttpURLConnection httpURLConnection=null;
            String data=null;
            try{
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("//a");
                boolean hasInput=scanner.hasNext();
                if(hasInput){
                    data=scanner.next();
                    LogUtil.d("debugg","doInTheBackground data Received");
                }else{
                    LogUtil.d("debugg","doInTheBackground data Not Exist");
                }
                } catch (Exception e){
                e.printStackTrace();
                }
            finally {
                if (httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }
            return data;
        }


        @Override
        protected void onPostExecute(String data) {
            LogUtil.d("debugg","onPostExecute Running");
            if(data!=null) {
                list = HttpUtil.parseJSONwithJSONObject(data);
                if (sortBy == "popular") {
                    MovieAdapter.Sortby = 1;
                    //1 for popularity
                } else {
                    MovieAdapter.Sortby = 0;
                    //0 for top rate
                }
                MovieAdapter movieAdapter = new MovieAdapter(context, list);
                gridView.setAdapter(movieAdapter);
            }

        }
    }

}
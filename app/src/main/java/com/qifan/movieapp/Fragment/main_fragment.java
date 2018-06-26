package com.qifan.movieapp.Fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Beans.Movie_Obj;
import com.qifan.movieapp.MainActivity;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.R;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class main_fragment extends Fragment {
    private GridView gridView;
    private static Activity activity1;


    public main_fragment() {
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
        activity1=getActivity();

        new myAsyncTask(getActivity(),URLsortBy("vote_average.desc"));
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public URL URLsortBy(String sortBy){
        URL url= MainActivity.sortURL(sortBy);
        return url;
    }

    class myAsyncTask extends AsyncTask<Void,Integer,String>{

        private final Activity activity;
        private final URL url;
        ArrayList<Movie_Obj> list= new ArrayList<>();

        public myAsyncTask(Activity activity, URL url){
            this.activity=activity;
            this.url=url;
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
            list=HttpUtil.parseJSONwithJSONObject(data);
            MovieAdapter movieAdapter=new MovieAdapter(getContext(),list);
            gridView.setAdapter(movieAdapter);

        }
    }

}

package com.qifan.movieapp.Utility;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridView;

import com.qifan.movieapp.Beans.Movie_Obj;
import com.qifan.movieapp.MovieAdapter;
import com.qifan.movieapp.MyApplication;
import com.qifan.movieapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class HttpUtil{

    public static void sendHttpRequest(final URL url, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                try{
                    URL url1=url;
                    httpURLConnection=(HttpURLConnection) url.openConnection();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("//a");
                    boolean hasInput=scanner.hasNext();
                    if(hasInput&&listener!=null){
                        listener.onFinish(scanner.next());
                        LogUtil.d("debugg","Got Data Back From API");
                    }
                    } catch (Exception e){
                    e.printStackTrace();
                    if(listener!=null){
                    listener.onError(e);
                }
                }
                finally {
                    if (httpURLConnection!=null){
                        httpURLConnection.disconnect();
                    }
                }

            }
        }).start();
    }



    public static ArrayList<Movie_Obj> parseJSONwithJSONObject(String jsonData){
            ArrayList<Movie_Obj> list=new ArrayList<>();
        try{
            JSONObject jsonObject1=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject1.getJSONArray("results");
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String popularity=jsonObject.getString("popularity");
                String img_path=jsonObject.getString("poster_path");
                String vote_average=jsonObject.getString("vote_average");
                list.add(new Movie_Obj(img_path,popularity,vote_average));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;

    }
}

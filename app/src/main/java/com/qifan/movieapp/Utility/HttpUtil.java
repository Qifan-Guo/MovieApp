package com.qifan.movieapp.Utility;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.qifan.movieapp.AppExecutors;
import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.MyApplication;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class HttpUtil{
    public static List<MovieEntry> list;
    private final String LOG_TAG=HttpUtil.this.getClass().getSimpleName();

    public static void parseJSONwithJSONObject(@Nullable String jsonData, String sortby){
        list = new ArrayList<>();

        if(jsonData!=null) {
            try {
                JSONObject jsonObject1 = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject1.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String popularity = jsonObject.getString("popularity");
                    String img_path = jsonObject.getString("poster_path");
                    String topRate = jsonObject.getString("vote_average");
                    String title = jsonObject.getString("original_title");
                    String language = jsonObject.getString("original_language");
                    String overview = jsonObject.getString("overview");
                    String date = jsonObject.getString("release_date");
                    if (img_path != "null") {
                        list.add(new MovieEntry(title,img_path,topRate,popularity,language,false,date,overview,sortby));
                    }

                }
                LogUtil.d("Http", "Data To List Successfully");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{ LogUtil.d("Http","Null JsonData");
            Toast.makeText(MyApplication.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

    }






}

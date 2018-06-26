package com.qifan.movieapp;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Info_MovieDB {

    //Here Enter your API Key
    final static String API_Key = "";
    final static String websiteAdress = "https://api.themoviedb.org/3/discover/movie?";
    final static String API_PARAM="api_key";
    final static String Sort_by="sort_by";
    final static String title="original_title";
    final static String popularity_desc="popularity.desc";
    final static String voter_avg_desc="vote_average.desc";
    final static String base_img_url="https://image.tmdb.org/t/p";
    final static String small_img_size ="/w185";
    final static String large_img_size="/w780";


    public static URL buildURL(String sort_param){
        Uri builtUri=Uri.parse(websiteAdress).buildUpon()
                .appendQueryParameter(API_PARAM,API_Key)
                .appendQueryParameter(Sort_by,sort_param).build();

        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
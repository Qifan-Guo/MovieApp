package com.qifan.movieapp;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MovieInfo {

    //Here Enter your API Key
    final static String API_Key = "Type Your API KEY HERE";


    final static String websiteAdress = "https://api.themoviedb.org/3/movie/";
    final static String API_PARAM="api_key";
    final static String title="original_title";
    final static String popularity_desc="popular";
    final static String topRate ="top_rated";
    final static String base_img_url="https://image.tmdb.org/t/p";
    final static String small_img_size ="/w185";
    final static String large_img_size="/w780";


    public static URL buildURL(String sort_param){
        Uri builtUri=Uri.parse(websiteAdress+sort_param).buildUpon()
                .appendQueryParameter(API_PARAM,API_Key).build();

        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
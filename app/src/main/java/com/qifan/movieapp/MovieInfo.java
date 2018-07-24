package com.qifan.movieapp;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MovieInfo {

    //Here Enter your API Key
    final static String API_Key = "Enter Your API Key Here";


    final static String BaseWebAdress = "https://api.themoviedb.org/3/movie/";
    final static String API_PARAM="api_key";
    final static String title="original_title";
    final static String popularity_desc="popular";
    final static String topRate ="top_rated";
    final static String base_img_url="https://image.tmdb.org/t/p";
    final static String small_img_size ="/w185";
    final static String large_img_size="/w780";


    @Nullable
    public static URL buildURL(String sort_param){
        Uri builtUri=Uri.parse(BaseWebAdress+sort_param).buildUpon()
                .appendQueryParameter(API_PARAM,API_Key).build();

        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;

    }

    public static String buildBaseWebAddress(int movieID, String queue){
        String ID=String.valueOf(movieID);
        String requestWebAdress=BaseWebAdress+ID+"/"+queue;
        return requestWebAdress;

    }
    @Nullable
    public static URL buildURL1(String requestWebAdress){
        Uri builtUri=Uri.parse(requestWebAdress).buildUpon()
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
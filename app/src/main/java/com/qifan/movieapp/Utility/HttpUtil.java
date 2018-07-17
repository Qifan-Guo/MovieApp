package com.qifan.movieapp.Utility;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.qifan.movieapp.MovieReviewsPOJO;
import com.qifan.movieapp.MyApplication;
import com.qifan.movieapp.OnHttpListener;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class HttpUtil {
    private final static String QUEUE_VIDEO="videos";
    private final static String QUEUE_REVIEWS="reviews";
    private final static String QUEUE_MAIN="main";
    public static List<MovieEntry> list;
    public static ArrayList<String> VideokeyList;
    public static List<MovieReviewsPOJO> ReviewerList;
    private String LOG_TAG = HttpUtil.this.getClass().getSimpleName();


    public static void checkIfisInDatabase() {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(MyApplication.getContext());
        for (int i = 0; i < list.size(); i++) {
            String title = list.get(i).getTitle();
            MovieEntry movieEntry = movieDatabase.movieDao().loadMovieByTitle(title);
            if (movieEntry != null) {
                list.get(i).setFavorite(true);
            }


        }

    }

    public static void httpRequest(URL url1, OnHttpListener onHttpListener,@Nullable String QUEUE) {
        final URL url = url1;
        HttpURLConnection httpURLConnection = null;
        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("//a");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                onHttpListener.onHttpResponse(scanner.next(),QUEUE);

            } else {
                LogUtil.d("HttpRequest", "doInTheBackground data Not Exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

    }





    public static void parseJSONwithJSONObject(@Nullable String jsonData, String sortby,String whichList,OnHttpListener onHttpListener) {


        if (jsonData != null) {
            try {
                JSONObject jsonObject1 = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject1.getJSONArray("results");
                if(whichList==QUEUE_MAIN) {
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String popularity = jsonObject.getString("popularity");
                        String img_path = jsonObject.getString("poster_path");
                        String topRate = jsonObject.getString("vote_average");
                        String title = jsonObject.getString("original_title");
                        String language = jsonObject.getString("original_language");
                        String overview = jsonObject.getString("overview");
                        String date = jsonObject.getString("release_date");
                        int movieID = jsonObject.getInt("id");
                        if (img_path != "null") {
                            list.add(new MovieEntry(title, img_path, topRate, popularity, language, false, date, overview, sortby, movieID));
                        } }
                        onHttpListener.onParseFinish(QUEUE_MAIN);
                } else if(whichList==QUEUE_VIDEO){
                    VideokeyList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String videoKey=jsonObject.getString("key");
                        VideokeyList.add(videoKey); }
                    onHttpListener.onParseFinish(QUEUE_VIDEO);
                }else if(whichList==QUEUE_REVIEWS){
                    ReviewerList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String author=jsonObject.getString("author");
                        String content=jsonObject.getString("content");
                        if(author!=null&&content!=null){
                            ReviewerList.add(new MovieReviewsPOJO(author,content));
                        }

                    }
                    onHttpListener.onParseFinish(QUEUE_REVIEWS);
                    }

                    LogUtil.d("Http", "Data To List Successfully");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.d("Http", "Null JsonData");
            Toast.makeText(MyApplication.getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


}

package com.qifan.movieapp.Utility;



import com.qifan.movieapp.Beans.Movie_Obj;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


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
        ArrayList<Movie_Obj> list = new ArrayList<>();
        if(jsonData!=null) {
            try {
                JSONObject jsonObject1 = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject1.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String popularity = jsonObject.getString("popularity");
                    String img_path = jsonObject.getString("poster_path");
                    String vote_average = jsonObject.getString("vote_average");
                    String title = jsonObject.getString("original_title");
                    String language = jsonObject.getString("original_language");
                    String overview = jsonObject.getString("overview");
                    String date = jsonObject.getString("release_date");
                    if (img_path != "null") {
                        list.add(new Movie_Obj(img_path, popularity, vote_average, title, language, overview, date));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{ LogUtil.d("debugg","Null JsonData");}
        return list;

    }
}

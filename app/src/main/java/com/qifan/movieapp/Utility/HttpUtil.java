package com.qifan.movieapp.Utility;
import android.widget.Toast;
import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.MyApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;



public class HttpUtil{


    public static ArrayList<MovieObj> parseJSONwithJSONObject(String jsonData){
        ArrayList<MovieObj> list = new ArrayList<>();
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
                        list.add(new MovieObj(img_path, popularity, vote_average, title, language, overview, date));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{ LogUtil.d("debugg","Null JsonData");
            Toast.makeText(MyApplication.getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
        return list;

    }
}

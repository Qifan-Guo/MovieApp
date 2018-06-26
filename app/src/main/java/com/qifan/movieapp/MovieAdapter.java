package com.qifan.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Beans.Movie_Obj;
import com.qifan.movieapp.Utility.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter <Movie_Obj>{

    //This will decide which TextView to display, 1 for popularity.
    //0 for top rate .
    //see getView method for details
    public static int Sortby=1;


    public MovieAdapter(@NonNull Context context, List<Movie_Obj> objects) {
        super(context, R.layout.movie_item, objects);
        LogUtil.d("debugg","Adapter Constructer called");
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LogUtil.d("debugg","GetView Called");

        Movie_Obj movie_obj=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.movie_item,parent,false);

        }
        ImageView imageView=convertView.findViewById(R.id.poster);
        TextView title=convertView.findViewById(R.id.title_text);
        TextView detail=convertView.findViewById(R.id.detail_text);
        title.setText(movie_obj.getTitle());

        //decise which textView content to display based on the sortby Value
        if(Sortby==1){
        detail.setText(movie_obj.getPopularity());}
        if(Sortby==0){
            detail.setText(movie_obj.getTopRate());
        }
        Picasso.get()
                .load(Info_MovieDB.base_img_url+Info_MovieDB.img_size+movie_obj.getPosterURL())
                .into(imageView);
        return convertView;
    }


}

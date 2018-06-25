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


    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie_Obj> objects) {
        super(context, resource, objects);
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
        TextView textView=convertView.findViewById(R.id.short_text);
        textView.setText(movie_obj.getPopularity());
        Picasso.get()
                .load(Info_MovieDB.base_img_url+Info_MovieDB.img_size+movie_obj.getPosterURL())
                .into(imageView);

        LogUtil.d("debugg",Info_MovieDB.base_img_url+Info_MovieDB.img_size+movie_obj.getPosterURL());
        return convertView;
    }


}

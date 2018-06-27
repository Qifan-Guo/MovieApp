package com.qifan.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.Utility.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter <MovieObj>{

    //This will decide which TextView to display, 1 for popularity.
    //0 for top rate .
    //see getView method for details
    public static int Sortby=1;
    private final Context context;


    public MovieAdapter(@NonNull Context context, List<MovieObj> objects) {
        super(context, R.layout.movie_item, objects);
        this.context=context;
        LogUtil.d("debugg","Adapter Constructer called");
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LogUtil.d("debugg","GetView Called");

        final MovieObj movie_obj=getItem(position);
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
                .load(MovieInfo.base_img_url+ MovieInfo.small_img_size +movie_obj.getPosterURL())
                .into(imageView);
        //add onclick Listener to Image View
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),movie_obj.getTitle(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context,MovieDetails.class);
                        intent.putExtra("Movie_obj",movie_obj);
                        context.startActivity(intent);
            }
        });



        return convertView;
    }


}

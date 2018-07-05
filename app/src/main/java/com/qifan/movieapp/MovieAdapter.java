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
import android.widget.Toast;

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
    viewHolder viewHolder;


    public MovieAdapter(@NonNull Context context, List<MovieObj> objects) {
        super(context, R.layout.movie_item, objects);
        this.context=context;
        LogUtil.d("debugg","Adapter Constructer called");
    }

    class viewHolder{
        ImageView emptyStar;
        TextView title;
        TextView detail;
        ImageView poster;
        boolean isStarEmpty=true;

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LogUtil.d("debugg","GetView Called");
        View view;


        final MovieObj movie_obj=getItem(position);
        if(convertView==null){
           view= LayoutInflater.from(getContext()).inflate(R.layout.movie_item,parent,false);
           viewHolder=new viewHolder();
            viewHolder.emptyStar=view.findViewById(R.id.empty_star);
            viewHolder.title=view.findViewById(R.id.title_text);
            viewHolder.detail=view.findViewById(R.id.detail_text);
            viewHolder.poster=view.findViewById(R.id.poster);
            view.setTag(viewHolder);

        }
        else{
            view=convertView;
            viewHolder=(viewHolder) view.getTag();
        }
        viewHolder.title.setText(movie_obj.getTitle());

        //Decide which textView content to display based on the sortby Value
        if(Sortby==1){
        viewHolder.detail.setText(movie_obj.getPopularity());}
        if(Sortby==0){
            viewHolder.detail.setText(movie_obj.getTopRate());
        }
        Picasso.get()
                .load(MovieInfo.base_img_url+ MovieInfo.small_img_size +movie_obj.getPosterURL())
                .into(viewHolder.poster);
        viewHolder.emptyStar.setImageResource(R.drawable.empty_star);

        //add onclick Listener to Image View
        viewHolder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent=new Intent(context,MovieDetails.class);
                        intent.putExtra("Movie_obj",movie_obj);
                        context.startActivity(intent);
            }
        });

        viewHolder.emptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.isStarEmpty==true){

                    viewHolder.emptyStar.setImageResource(R.drawable.gold_star);
                    viewHolder.isStarEmpty=false;
                }else if(viewHolder.isStarEmpty=false){
                    viewHolder.emptyStar.setImageResource(R.drawable.empty_star);
                    viewHolder.isStarEmpty=true;
                }


                Toast.makeText(context,movie_obj.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }


}

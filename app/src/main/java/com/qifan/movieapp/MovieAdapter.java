package com.qifan.movieapp;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.Fragment.MainFragment;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.qifan.movieapp.MyApplication.getContext;

public class MovieAdapter extends BaseAdapter{

    //This will decide which TextView to display, 1 for POPULARITY.
    //0 for top rate .
    //see getView method for details
    public static String SORT_BY;
    @NonNull
    private final Context context;
    private List<MovieEntry> list;
    @Nullable
    viewHolder viewHolder;
    private final String LOG_TAG=MovieAdapter.this.getClass().getSimpleName();

    public MovieAdapter(@NonNull Context context) {
        if(HttpUtil.list!=null){
            this.list= HttpUtil.list;
        }
        this.context=context;
        LogUtil.d(LOG_TAG,"First Adapter Constructer called");

    }


//    public MovieAdapter(@NonNull Context context,List<MovieEntry> list) {
//        this.context=context;
//        this.list=list;
//        LogUtil.d(LOG_TAG,"Second Adapter Constructer called");
//
//    }

    class viewHolder{
        ImageView emptyStar;
        TextView title;
        TextView detail;
        ImageView poster;
        boolean isStarEmpty=true;
        public viewHolder(View view){
           this.emptyStar=view.findViewById(R.id.empty_star);
            this.title=view.findViewById(R.id.title_text);
            this.detail=view.findViewById(R.id.detail_text);
            this.poster=view.findViewById(R.id.poster);
        }

    }
    public void setTask(List<MovieEntry> task){
        HttpUtil.list=task;
        this.list=HttpUtil.list;
        notifyDataSetChanged();
        LogUtil.d(LOG_TAG,"????? ");
        LogUtil.d(LOG_TAG,this.list.get(0).getTitle()+" From Adapter");

    }


    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }else{
        return list.size();}
    }

    @Override
    public Object getItem(int position) {
        if(list==null){
            return null;
        }else{
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        if(list==null){
            return 0;
        }else{
            return position;}
    }




    @Nullable
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;


        final MovieEntry movie_obj=list.get(position);
        if(convertView==null){
           view= LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
           viewHolder=new viewHolder(view);
           view.setTag(viewHolder);

        }
        else{
            view=convertView;
            viewHolder=(viewHolder) view.getTag();
        }
        viewHolder.title.setText(movie_obj.getTitle());
        SORT_BY=movie_obj.getSortBy();
        //Decide which textView content to display based on the sortby Value
        if(SORT_BY=="popular"){
        viewHolder.detail.setText(movie_obj.getPopularity1());}
        if(SORT_BY=="top_rated"){
            viewHolder.detail.setText(movie_obj.getTopRate1());
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
                        intent.putExtra("Entry_Position",position);
                        String position1=String.valueOf(position);
                        LogUtil.d("Position", position1);
                        context.startActivity(intent);
            }
        });



        viewHolder.emptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (viewHolder.isStarEmpty==true){
//
//                    viewHolder.emptyStar.setImageResource(R.drawable.gold_star);
//                    viewHolder.isStarEmpty=false;
//                }else if(viewHolder.isStarEmpty=false){
//                    viewHolder.emptyStar.setImageResource(R.drawable.empty_star);
//                    viewHolder.isStarEmpty=true;
//                }
//
//
//                Toast.makeText(context,movie_obj.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }


}

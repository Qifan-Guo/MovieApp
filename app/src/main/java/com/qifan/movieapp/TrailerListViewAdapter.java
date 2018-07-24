package com.qifan.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerListViewAdapter extends RecyclerView.Adapter<TrailerListViewAdapter.viewHolder> {
    private ArrayList<String> keys;
    private final Context mContext;

    public TrailerListViewAdapter(Context Context) {
        this.mContext = Context;
    }

    public void setData(ArrayList<String> data){
        this.keys=data;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.trailer_item, parent, false);

        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
       holder.textView.setText("Cick to Play Trailer Number "+(position+1));
        holder.playButtonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=keys.get(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key));
                mContext.startActivity(browserIntent);
            }
        });


    }



    @Override
    public int getItemCount() {
        if(keys!=null) {
            return keys.size();
        } else {
            return 0;
        }}



    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView playButtonImg;
        TextView textView;
        public viewHolder(View view){
            super(view);
            playButtonImg=view.findViewById(R.id.playButton);
            textView=view.findViewById(R.id.trailerText);
        }
    }




}

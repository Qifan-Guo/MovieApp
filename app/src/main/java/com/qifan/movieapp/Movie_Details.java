package com.qifan.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Beans.Movie_Obj;
import com.squareup.picasso.Picasso;

public class Movie_Details extends AppCompatActivity {
    private TextView overview;
    private TextView detail_info;
    private TextView title;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__details);
        title=findViewById(R.id.Movie_Title);
        overview=findViewById(R.id.overview);
        detail_info=findViewById(R.id.detail_Info);
        imageView=findViewById(R.id.detail_poster);
        Movie_Obj movie_obj=(Movie_Obj)getIntent().getSerializableExtra("Movie_obj");

        //Formatting section
        String overview_format="OVERVIEW: \n"+movie_obj.getOverview();
        String detail_info_format=movie_obj.getTopRate()+"\n\n\n"
                +movie_obj.getPopularity()+"\n\n\n"+"Release Date: "
                +movie_obj.getRelease_date()+"\n\n\n"+"Language: "+movie_obj.getLanguage();





        title.setText(movie_obj.getTitle());
        overview.setText(overview_format);
        detail_info.setText(detail_info_format);
        Picasso.get().load(Info_MovieDB.base_img_url+Info_MovieDB.large_img_size +movie_obj.getPosterURL())
                .into(imageView);



    }
}

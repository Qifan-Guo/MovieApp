package com.qifan.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Beans.MovieObj;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private TextView overview;
    private TextView detail_info;
    private TextView title;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_view);
        title=findViewById(R.id.Movie_Title);
        overview=findViewById(R.id.overview);
        detail_info=findViewById(R.id.detail_Info);
        imageView=findViewById(R.id.detail_poster);
        MovieObj movie_obj= getIntent().getParcelableExtra("Movie_obj");

        //Formatting section
        String overview_format="OVERVIEW: \n"+movie_obj.getOverview();
        String detail_info_format=movie_obj.getTopRate()+"\n\n"
                +movie_obj.getPopularity()+"\n\n"+"Release Date: "
                +movie_obj.getRelease_date()+"\n\n"+"Language: "+movie_obj.getLanguage();

        title.setText(movie_obj.getTitle());
        overview.setText(overview_format);
        detail_info.setText(detail_info_format);
        Picasso.get().load(MovieInfo.base_img_url+ MovieInfo.large_img_size +movie_obj.getPosterURL())
                .into(imageView);



    }
}

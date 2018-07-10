package com.qifan.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    private TextView overview;
    private TextView detail_info;
    private TextView title;
    private ImageView imageView;
    private Switch aSwitch;
    private final String LOG_TAG=MovieDetails.this.getClass().getSimpleName();
    private AppExecutors appExecutors;
    private final MovieDatabase movieDatabase=MovieDatabase.getInstance(MyApplication.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_view);
        title=findViewById(R.id.Movie_Title);
        overview=findViewById(R.id.overview);
        detail_info=findViewById(R.id.detail_Info);
        imageView=findViewById(R.id.detail_poster);
        aSwitch=findViewById(R.id.switch1);
        int position= getIntent().getIntExtra("Entry_Position",0);
        final MovieEntry movie_obj= HttpUtil.list.get(position);
        //Formatting section
        String overview_format="OVERVIEW: \n"+movie_obj.getOverview();
        String detail_info_format=movie_obj.getTopRate1()+"\n\n"
                +movie_obj.getPopularity1()+"\n\n"+"Release Date: "
                +movie_obj.getReleaseDate()+"\n\n"+"Language: "+movie_obj.getLanguage();

        title.setText(movie_obj.getTitle());
        overview.setText(overview_format);
        detail_info.setText(detail_info_format);
        Picasso.get().load(MovieInfo.base_img_url+ MovieInfo.large_img_size +movie_obj.getPosterURL())
                .into(imageView);
        aSwitch.setChecked(movie_obj.getFavorite());


//if the switch is switch then add it to the favorite database
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                appExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(isChecked){
                            movie_obj.setFavorite(true);
                            movieDatabase.movieDao().insertTask(movie_obj);
                            LogUtil.d(LOG_TAG,"INSERTING INTO DATABASE"+String.valueOf(movie_obj.getId()));

                        }else{
                            movie_obj.setFavorite(false);
                            movieDatabase.movieDao().deleteTask(movie_obj.getTitle());
                            LogUtil.d(LOG_TAG,"REMOVE FROM DATABASE"+String.valueOf(movie_obj.getId()));
                        }



                    }
                });


            }
        });

//        appExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                movieDatabase.movieDao().insertTask(movie_obj);
//                LogUtil.d(LOG_TAG,"INSERTING INTO DATABASE");
//            }
//        });



    }


    public void ToDatabase(Boolean isFavorite){
        if(isFavorite==true){

        }else
        {

        }
    }
}

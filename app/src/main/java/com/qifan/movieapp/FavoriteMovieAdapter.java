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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qifan.movieapp.Utility.HttpUtil;
import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.qifan.movieapp.MyApplication.getContext;

public class FavoriteMovieAdapter extends BaseAdapter {

    //This will decide which TextView to display, 1 for POPULARITY.
    //0 for top rate .
    //see getView method for details
    public static String SORT_BY;
    @NonNull
    private final Context context;
    private List<MovieEntry> list;
    private AppExecutors appExecutors;
    private MovieDatabase movieDatabase;
    @Nullable
    viewHolder viewHolder;
    private final String LOG_TAG = FavoriteMovieAdapter.this.getClass().getSimpleName();

    public FavoriteMovieAdapter(@NonNull Context context) {
        movieDatabase = MovieDatabase.getInstance(MyApplication.getContext());
        this.context = context;
        LogUtil.d(LOG_TAG, "First Adapter Constructer called");

    }


    class viewHolder {
        ImageView emptyStar;
        TextView title;
        TextView detail;
        ImageView poster;


        public viewHolder(View view) {
            this.emptyStar = view.findViewById(R.id.empty_star);
            this.title = view.findViewById(R.id.title_text);
            this.detail = view.findViewById(R.id.detail_text);
            this.poster = view.findViewById(R.id.poster);
        }

    }

    public void setTask(List<MovieEntry> task) {
        this.list=task;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        if (list == null) {
            return 0;
        } else {
            return position;
        }
    }


    @Nullable
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;


        final MovieEntry movie_obj = list.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
            viewHolder = new viewHolder(view);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (viewHolder) view.getTag();
        }
        viewHolder.title.setText(movie_obj.getTitle());
        SORT_BY = movie_obj.getSortBy();
        //Decide which textView content to display based on the sortby Value
        if (SORT_BY == "popular") {
            viewHolder.detail.setText(movie_obj.getPopularity1());
        }
        if (SORT_BY == "top_rated") {
            viewHolder.detail.setText(movie_obj.getTopRate1());
        }
        Picasso.get()
                .load(MovieInfo.base_img_url + MovieInfo.small_img_size + movie_obj.getPosterURL())
                .into(viewHolder.poster);
        if (movie_obj.getFavorite()) {
            viewHolder.emptyStar.setImageResource(R.drawable.gold_star);
        } else {
            viewHolder.emptyStar.setImageResource(R.drawable.empty_star);
        }


        //add onclick Listener to Image View
        viewHolder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("Entry_Position", position);
                intent.putExtra("Favorite",true);
                String position1 = String.valueOf(position);
                LogUtil.d("Position", position1);
                context.startActivity(intent);
            }
        });


        viewHolder.emptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie_obj.getFavorite() == false) {
                    LogUtil.d(LOG_TAG, "CHANGE TO GOLD STAR");
                    viewHolder.emptyStar.setImageResource(R.drawable.gold_star);
                    movie_obj.setFavorite(true);
                    notifyDataSetChanged();

                    AppExecutors.getInstance().networkIO().execute(addToDatabase(movie_obj));


                } else {
                    LogUtil.d(LOG_TAG, "CHANGE TO EMPTY STAR");
                    viewHolder.emptyStar.setImageResource(R.drawable.empty_star);
                    movie_obj.setFavorite(false);
                    notifyDataSetChanged();
                    AppExecutors.getInstance().networkIO().execute(removeToDatabase(movie_obj));
                }
            }
        });


        return view;
    }

    public Runnable addToDatabase(final MovieEntry movie_obj) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                movieDatabase.movieDao().insertTask(movie_obj);
                LogUtil.d(LOG_TAG, "INSERTING INTO DATABASE" + String.valueOf(movie_obj.getId()));
            }
        };
        return runnable;

    }

    public Runnable removeToDatabase(final MovieEntry movie_obj) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                movieDatabase.movieDao().deleteTask(movie_obj.getTitle());
                LogUtil.d(LOG_TAG, "REMOVE FROM DATABASE" + String.valueOf(movie_obj.getId()));
            }
        };
        return runnable;

    }


}

package com.qifan.movieapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;

import com.qifan.movieapp.Utility.LogUtil;
@Database(entities = {MovieEntry.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie_database";
    private static MovieDatabase movieDatabase;
    public static MovieDatabase getInstance(@NonNull Context context){
        if(movieDatabase==null){

            synchronized (LOCK){
                LogUtil.d(LOG_TAG,"Creating new database");
                movieDatabase= Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,DATABASE_NAME)
                        .fallbackToDestructiveMigration().build();
            }

        }

        LogUtil.d(LOG_TAG,"Just getting the database instance");
        return movieDatabase;



    }
    public abstract MovieDao movieDao();

}

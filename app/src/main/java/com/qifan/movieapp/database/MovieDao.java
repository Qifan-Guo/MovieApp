package com.qifan.movieapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;
//this is the interface that would manipulate the database
@Dao
public interface MovieDao {

        @Query("SELECT * FROM movie ORDER BY title")
        LiveData<List<MovieEntry>> loadAllTasks();

        @Query("SELECT * FROM movie ORDER BY title")
        List<MovieEntry> loadAll();

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertTask(MovieEntry taskEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateTask(MovieEntry movieEntry);

        @Query("DELETE FROM movie where title= :title")
        void deleteTask(String title);

        @Query("SELECT * FROM movie WHERE title = :title")
        MovieEntry loadMovieByTitle(String title);

        @Query("DELETE FROM movie")
        void deleteALL();
    }



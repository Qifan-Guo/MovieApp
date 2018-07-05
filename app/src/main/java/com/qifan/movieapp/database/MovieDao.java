package com.qifan.movieapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
//this is the interface that would manipulate the database
@Dao
public interface MovieDao {

        @Query("SELECT * FROM movie ORDER BY priortiy")
        LiveData<List<MovieEntry>> loadAllTasks();

        @Insert
        void insertTask(MovieEntry taskEntry);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void updateTask(MovieEntry movieEntry);

        @Delete
        void deleteTask(MovieEntry movieEntry);

        @Query("SELECT * FROM movie WHERE id = :id")
        LiveData<MovieEntry> loadMovieById(int id);
    }



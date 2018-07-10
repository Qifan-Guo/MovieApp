package com.qifan.movieapp;

import android.app.Application;
import android.app.ListActivity;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.qifan.movieapp.Utility.LogUtil;
import com.qifan.movieapp.database.MovieDatabase;
import com.qifan.movieapp.database.MovieEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel
{
    private final String LOG_TAG=MainViewModel.this.getClass().getSimpleName();
    LiveData<List<MovieEntry>> mTasks;
    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase movieDatabase=MovieDatabase.getInstance(this.getApplication());
        mTasks= movieDatabase.movieDao().loadAllTasks();
    }

    public LiveData<List<MovieEntry>> getmTasks() {

        if(mTasks!=null){
            LogUtil.d(LOG_TAG,"mTASK is Not Null");
            return mTasks;
            }else{
            LogUtil.d(LOG_TAG,"mTASK is Null");
            return null;
        }

    }
}

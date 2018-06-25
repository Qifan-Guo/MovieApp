package com.qifan.movieapp.Utility;

import android.util.Log;

public class LogUtil {

    public static final int VERVOSE =1;
    public static final int DEBUG=2;
    public static final int INFO=3;
    public static final int WARN =4;
    public static final int ERROR=5;
    public static final int Level=VERVOSE;

    public static void v(String tag, String msg){
        if(Level<=VERVOSE){
            Log.v(tag,msg);
        }
    }
    public static void d(String tag, String msg){
        if(Level<=DEBUG){
            Log.d(tag,msg);
        }
    }    public static void i(String tag, String msg){
        if(Level<=INFO){
            Log.i(tag,msg);
        }
    }    public static void w(String tag, String msg){
        if(Level<=WARN){
            Log.v(tag,msg);
        }
    }    public static void e(String tag, String msg){
        if(Level<=ERROR){
            Log.v(tag,msg);
        }
    }



}

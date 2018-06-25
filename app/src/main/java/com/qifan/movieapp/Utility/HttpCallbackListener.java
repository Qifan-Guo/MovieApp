package com.qifan.movieapp.Utility;

public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}

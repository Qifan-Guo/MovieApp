package com.qifan.movieapp;

public interface OnHttpListener {
    void onHttpResponse(String data,String QUEUE);
    void onParseFinish(String WhichList);
}

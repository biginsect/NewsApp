package com.lipeng.newapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by lipeng-ds3 on 2017/11/6.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco初始化
        Fresco.initialize(this);
    }
}

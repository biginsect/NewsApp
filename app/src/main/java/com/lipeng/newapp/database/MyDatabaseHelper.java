package com.lipeng.newapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lipeng-ds3 on 2017/10/28.
 * 用于创建和管理数据库的类
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    /**
     * 创建数据库*/
    private static final String CREATE_USER = "create table user(" +
            "account text primary key," +
            "password text)";
    private static final String CREATE_NEWS = "create table news(" +
            "id integer primary key," +
            "imageUrl text," +
            "title text," +
            "content text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//第一次调用getWritableDatabase() 或者getReadableDatabase()执行该方法
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_NEWS);
        //创建完user立即插入数据
        db.execSQL("insert into user (account, password) values(?, ?)",new String[]{"lipeng-ds3", "123"});
        //db.execSQL("insert into user (account, password) values(?, ?)",new String[]{"lipeng", "123"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//可以对数据库进行升级，需要先drop对应的表

    }
}

package com.lipeng.newapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lipeng.newapp.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 * {@link User}对应的数据库操作类，主要方法是{@link #getUser()}用于获取用户的内容-账号和密码
 */

public class UserDatabase {
    //数据库名
    private static final String DB_NAME = "user";
    //数据库版本
    private static final int VERSION = 1;
    //单例模式
    private static UserDatabase mUserDatabase;
    private SQLiteDatabase mDatabase;

    private UserDatabase(Context context){
        MyDatabaseHelper helper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
        mDatabase = helper.getWritableDatabase();
    }

    public synchronized static UserDatabase getInstance(Context context){//单例模式
        if (mUserDatabase == null){
            mUserDatabase = new UserDatabase(context);
        }
        return mUserDatabase;
    }

    public List<User> getUser(){//在数据库中获取账号密码
        List<User> userList = new ArrayList<>();
        Cursor cursor = mDatabase.query("user", null, null, null ,null ,null, null);
        if (cursor.moveToFirst()){
            do {
                User user =new User();
                user.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userList.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return userList;
    }
}

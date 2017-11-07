package com.lipeng.newapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lipeng.newapp.bean.News;
import com.lipeng.newapp.bean.TopStories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 * {@link News}对应的数据库操作类，主要有数据的存储方法{@link #saveNews(News)}和数据的读取方法{@link #loadNews()}
 */

public  class NewsDatabase {
    private static final String TAG = "NewsDatabase";

    //数据库名字
    private static final String DB_NAME = "MyNews";
    //数据库版本
    private static final int DB_VERSION = 1;
    //单例模式
    private static NewsDatabase mNewsDataBase;
    private SQLiteDatabase mDatabase;

    private NewsDatabase(Context context){
        MyDatabaseHelper helper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
        //获取一个可进行读写操作的数据库，磁盘满了后此方法会抛出异常
        mDatabase = helper.getWritableDatabase();
    }

    public synchronized static NewsDatabase getInstance(Context context){
        if (mNewsDataBase == null){
            mNewsDataBase = new NewsDatabase(context);
        }
        return mNewsDataBase;
    }

    /**
     * 将数据存放到数据库中
     * @param news 需要保存的News对象
     * */
    public void saveNews(News news){
        if (news != null){
            ContentValues values = new ContentValues();
            values.put("id", news.getNewsId());
            values.put("imageUrl",news.getNewsImageUrl());
            values.put("title",news.getNewsTitle());
            values.put("content",news.getNewsContent());
            Log.d(TAG, "++++++++++++++++++" + news.getNewsContent());
            mDatabase.insertWithOnConflict("news", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    // 从数据库读取内容并存放到List中
    public List<News> loadNews(){
        List<News> newsList = new ArrayList<>();
        Cursor cursor = mDatabase.query("news", null, null, null, null, null, null);
//        String start = "</span>\n</div>\n\n<div class=\"content\">\n<p>";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){//数据库中有记录
            News news = new News();
            news.setNewsId(cursor.getInt(cursor.getColumnIndex("id")));
            news.setNewsImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            news.setNewsTitle(cursor.getString(cursor.getColumnIndex("title")));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            content = content.replaceAll("[^\u4E00-\u9FA5]","");
            news.setNewsContent(content);
//            Log.d(TAG, news.getNewsImageUrl());
            newsList.add(news);
        }
        if (cursor != null)
                cursor.close();
        return newsList;
    }

    //存储到数据库中
    public void saveStories(TopStories stories){
        if (stories != null){
            ContentValues values = new ContentValues();
            values.put("id", stories.getStoryId());
            values.put("title", stories.getStoryTitle());
            values.put("imageUrl", stories.getStoryImageUrl());
            mDatabase.insertWithOnConflict("top_stories", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    //从数据库中读取存储的内容
    public List<TopStories> loadStories(){
        List<TopStories> storiesList = new ArrayList<>();
        Cursor cursor = mDatabase.query("top_stories", null, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TopStories stories = new TopStories();
            stories.setStoryId(cursor.getInt(cursor.getColumnIndex("id")));
            stories.setStoryImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            stories.setStoryTitle(cursor.getString(cursor.getColumnIndex("title")));
            storiesList.add(stories);
        }
        cursor.close();

        return storiesList;
    }

}

package com.lipeng.newapp.utils;

import android.util.Log;

import com.lipeng.newapp.bean.TopStories;
import com.lipeng.newapp.database.NewsDatabase;
import com.lipeng.newapp.bean.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 *
 * 主要用于处理请求返回的数据，服务器返回的是键值对类型，使用JSON对其进行解析
 * 解析完成则对其内容进行拆分，之后存储到数据库中
 */

public final class ResolveResponseUseJson {
    private final static String TAG = "ResolveResponseUseJson";
    /**
     * 服务器返回的是两个键值对类型数据stories 和top_stories
     * 需要分开处理
     * */
    public synchronized static void handleResponse(NewsDatabase database, String response){
        if (response == null){
            Log.d(TAG, " handleResponse: response is null");
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
            String[] stories = new String[jsonArrayStories.length()];
            for (int i = 0; i < stories.length; i++){
                stories[i] = jsonArrayStories.getString(i);
            }
            //解析完成，将子项分开并存放到数据库中
            handleJSONArray(database, stories);
            //处理轮播图TopStories
            JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
            String[] topStories = new String[jsonArrayTopStories.length()];
            for (int i = 0; i < topStories.length; i++){
                topStories[i] = jsonArrayTopStories.getString(i);
            }

            handleJSONArrayTopStories(database, topStories);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

/*
    //处理轮播图TopStories
    public synchronized static void handleTopStoriesResponse(NewsDatabase database, String response){

       if (response == null)
           return;
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
            String[] topStories = new String[jsonArrayTopStories.length()];
            for (int i = 0; i < topStories.length; i++){
                topStories[i] = jsonArrayTopStories.getString(i);
            }
            ////解析完成，将子项分开并存放到数据库中
            handleJSONArrayTopStories(database, topStories);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
*/

    /**
     * {@link #handleResponse(NewsDatabase, String)}返回两种数据，stories和top_stories
     * 由于stories 返回的图片url对于的键是images 而top_stories 返回的是image，需要进行不同的处理
     * 处理完成立即存储到数据库中
     * */
    public synchronized static void handleJSONArray(NewsDatabase dataBase, String[] arr){
        int i = 0;
        while (i != arr.length){
            try{
                News news = new News();
                JSONObject jsonObject = new JSONObject(arr[i]);
                JSONArray jsonArray = jsonObject.getJSONArray("images");
                String newsImageUrl = jsonArray.get(0).toString();
                String newsId = jsonObject.getString("id");
                String newsTitle = jsonObject.getString("title");
                String newsContent = NetworkUtil.getContentFromURLAndId(NetworkUtil.URL_HAS_NOT_ID, Integer.parseInt(newsId));
                news.setNewsImageUrl(newsImageUrl);
                news.setNewsId(Integer.parseInt(newsId));
                news.setNewsTitle(newsTitle);
                news.setNewsContent(newsContent);
//                Log.d(TAG, "newsImageUrl "+ newsImageUrl + "\n" + "newsId" + newsId);
                dataBase.saveNews(news);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }

    //处理top_stories返回的数据
    public synchronized static void handleJSONArrayTopStories(NewsDatabase dataBase, String[] arr){
        int i = 0;
        while (i != arr.length){
            try{
                TopStories stories = new TopStories();
                JSONObject jsonObject = new JSONObject(arr[i]);
                String imageUrl = jsonObject.getString("image");
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                stories.setStoryImageUrl(imageUrl);
                stories.setStoryId(Integer.parseInt(id));
                stories.setStoryTitle(title);
//                Log.d(TAG, "newsImageUrl "+ newsImageUrl + "\n" + "newsId" + newsId);
                dataBase.saveStories(stories);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }

    /**
     * 对于新闻详情页面url请求时，返回两种数据，分别是html代码和css代码
     * 使用JSON提取，存放到数组中并返回
     * */
    public synchronized static String[] handleWebViewResponse(String response){
        String[] code = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(response);
            code[0] = jsonObject.getString("body");
            code[1] = jsonObject.getString("css");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return code;
    }

}

package com.lipeng.newapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lipeng.newapp.database.NewsDatabase;
import com.lipeng.newapp.bean.News;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lipeng-ds3 on 2017/10/28.
 *
 * 网络请求的工具类，主要用于发起同步或异步的网络请求
 */

public final class NetworkUtil {
    //最新新闻的url
    public static final String NEWS_URL = "https://news-at.zhihu.com/api/4/news/latest";
    //某一id对应的url，需要在后面加上对应的id才能访问对应资源
    public static final String URL_HAS_NOT_ID = "https://news-at.zhihu.com/api/4/news/";
    //设置全局client，设置请求超时，读取超时，写超时时间
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();

    /**
     * 方法主要用于请求{@link #NEWS_URL}的数据
     * @param database 数据库操作实例，用于调用数据库的新闻存储方法{@link NewsDatabase#saveNews(News)}
     * @param url 请求的url*/
    public static void getContentFromURL(final NewsDatabase database, String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.code());
                ResolveResponseUseJson.handleResponse(database, response.body().string());
            }
        });
    }

    /**
     * 从{@link com.lipeng.newapp.activity.MainActivity }跳转到WebView的时候，传入一个当前item对应的url
     * 请求当前url的详情内容，完成之后直接保存html代码以及css代码
     * */
    public static void loadWebViewFromURL(final Context context,String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String[] code = ResolveResponseUseJson.handleWebViewResponse(response.body().string());
                    saveWebViewCode(context, code);
                }
            }
        });
    }

    //根据new对应id去请求content并返回
    public static String getContentFromURLAndId(String url,final int id){
        Request request = new Request.Builder()
                .url(url + id)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用SharedPreferences 存储html代码和css代码
     * */
    public synchronized static void saveWebViewCode(Context context, String[] code){
        if (code.length > 1){
            SharedPreferences htmlPreferences = context.getSharedPreferences("html", Context.MODE_PRIVATE);
            SharedPreferences.Editor htmlEditor = htmlPreferences.edit();
            //富文本设置
//            String richCodeSetting = "<head><style>img{max-width:100% !important;} table{max-width:100% !important;}</style></head>";
            htmlEditor.putString("html",code[0] );
            htmlEditor.apply();

            SharedPreferences cssPreferences = context.getSharedPreferences("css.css", Context.MODE_PRIVATE);
            SharedPreferences.Editor cssEditor = cssPreferences.edit();
            cssEditor.putString("css", code[1]);
            cssEditor.apply();
        }
    }

    //检查网络状态，标准写法
    public static boolean isNetworkAvailable(Activity activity){
        Context context = activity.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info.getState() == NetworkInfo.State.CONNECTED)
                return true;
        }
        return false;
    }
}
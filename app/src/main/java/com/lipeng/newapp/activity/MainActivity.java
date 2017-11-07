package com.lipeng.newapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.adapter.MyAdapter;
import com.lipeng.newapp.adapter.TopStoriesAdapter;
import com.lipeng.newapp.bean.TopStories;
import com.lipeng.newapp.database.NewsDatabase;
import com.lipeng.newapp.bean.News;
import com.lipeng.newapp.utils.NetworkUtil;
import com.lipeng.newapp.utils.NetworkUtil.RequestCompleteCallBack;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * App主界面，从url请求数据之后将相关数据显示在页面上
 * 主要有RecyclerView显示新闻的title、content和image，点击item能跳转到新闻详情页WebView
 * 第一次url请求得到的是image的url，需要再次请求才能得到图片
 * */

public class MainActivity extends AppCompatActivity implements RequestCompleteCallBack {
    private final static int MESSAGE_CODE = 0x101;
    private final static String TAG = "MainActivity";
    @BindView(R.id.rv_news) RecyclerView mRecyclerView;

    private MyAdapter mRecyclerAdapter;
    private NewsDatabase mDatabase;

    private News mNews;
    private int mNewsId;
    private List<News> mNewsList = new ArrayList<>();

    /************轮播图所需内容**********/
    @BindView(R.id.vp_top_stories) ViewPager mViewPage;
    @BindView(R.id.top_stories_selector)  RadioGroup groupSeletor;
    @BindView(R.id.tv_top_stories_title)  TextView topStoriesTitle;
    private TopStoriesAdapter mTopAdapter;
    private List<TopStories> mStoriesList = new ArrayList<>();
    //当前轮播页面
    private static int currentItem = 0;
    //是否启用自动轮播
    private ScheduledExecutorService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!NetworkUtil.isNetworkAvailable(MainActivity.this))
            Toast.makeText(this, "No Available Network!",Toast.LENGTH_LONG).show();
        else
            Log.d(TAG, "Network is available!");

        init();
    }

    private void init(){//初始化控件，加载数据等
        ButterKnife.bind(this);
        //存放news的List
        mDatabase = NewsDatabase.getInstance(this);

        //请求数据,完成后将数据存储到数据库中
        NetworkUtil.getContentFromURL(mDatabase, NetworkUtil.NEWS_URL, this); // 1s
        //在主线程进行数据库读取操作，会阻塞主线程
        mRecyclerAdapter = new MyAdapter(MainActivity.this.getApplicationContext(), mNewsList); // 0.0001s
        mTopAdapter = new TopStoriesAdapter(MainActivity.this, mStoriesList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //保持RecyclerView固定的大小
        mRecyclerView.setHasFixedSize(true);

//        Log.d(TAG, "mNews size is " + mNewsList.size());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mViewPage.setAdapter(mTopAdapter);

        mRecyclerAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, News position) {
                //点击启动详情页面
                mNews = position;
                //获取news 的id，添加到url中，跳转到对应的详情页面
                mNewsId = mNews.getNewsId();
                Log.d(TAG, mNewsId + "");
                //使用Intent完成activity之间的跳转
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), WebViewActivity.class);
                intent.putExtra("address",NetworkUtil.URL_HAS_NOT_ID + mNewsId);
                startActivity(intent);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //从数据库中加载数据
//            mNewsList.addAll(mDatabase.loadNews());
            //数据请求完成 通知adapter更新视图
//            mRecyclerAdapter.notifyDataSetChanged();

            if(msg.what == MESSAGE_CODE){
                //为保证职责单一原则，需要让adapter自行加载数据并更新视图
                mRecyclerAdapter.addData(mDatabase.loadNews());
                mTopAdapter.addData(mDatabase.loadStories());
                Log.d(TAG, "----------------2222--22----" + mDatabase.loadStories().size());
            }
        }
    };

    @Override
    public void response() {
        handler.sendEmptyMessage(MESSAGE_CODE);
    }

    private void startAutoScroll(){
        stopAutoScroll();
        service = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                selectNextItem();
            }

            private void selectNextItem(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewPage.setCurrentItem(++ currentItem);
                    }
                });
            }
        };
        int delay = 2;
        int period = 2;
        TimeUnit unit = TimeUnit.SECONDS;
        service.scheduleAtFixedRate(runnable, delay, period, unit);
    }

    private void stopAutoScroll(){
        if (service != null)
            service.shutdownNow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScroll();
    }
}

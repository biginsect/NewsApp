package com.lipeng.newapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.bean.News;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 * RecyclerView的适配器，对后端数据进行处理，然后完成在UI上面的显示
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener{
    private List<News> mNews = null;
    private OnItemClickListener mOnItemClickListener = null;
    private Context mContext;

    public MyAdapter(Context context, List<News> news){
        System.out.println("----------------context = [" + context + "], news = [" + news.size() + "]");
        mNews = news;
        this.mContext = context;
    }

    //用于回调，响应RecyclerView item的点击事件
    public  interface OnItemClickListener{
        void onItemClick(View view, News position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MyAdapter","-------------onCreateViewHolder");
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.news_rv_item, parent, false);
        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d("MyAdapter","-------------onBindViewHolder");
        News news = mNews.get(position);
        holder.newsTitle.setText(news.getNewsTitle());
        holder.newsContent.setText(news.getNewsContent());
        //使用fresco，通过Url请求图片，会自动缓存
        holder.newsImage.setImageURI(Uri.parse(news.getNewsImageUrl()));
        //存储每一个itemView的相关内容，不需要重复加载
        holder.itemView.setTag(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        System.out.println("----------------MyAdapter.getItemCount"  + mNews.size());
        return mNews.size();
    }

    @Override
    public void onClick(View v) {//对itemView点击时，进行回调，从而完成从MainActivity到WebViewActivity的跳转
        if (mOnItemClickListener != null){
            //getTag 获取position
            mOnItemClickListener.onItemClick(v, (News) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    //自定义ViewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView newsImage;
        TextView newsTitle;
        TextView newsContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            newsImage = (SimpleDraweeView)itemView.findViewById(R.id.image_new);
            newsTitle = (TextView)itemView.findViewById(R.id.tv_news_title);
            newsContent = (TextView)itemView.findViewById(R.id.tv_news_content);
        }
    }

}

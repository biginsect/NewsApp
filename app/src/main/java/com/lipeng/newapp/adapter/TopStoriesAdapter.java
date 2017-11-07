package com.lipeng.newapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lipeng.newapp.bean.TopStories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/11/6.
 */

public class TopStoriesAdapter extends PagerAdapter {
    //存放top_stories
    private List<TopStories> mStoriesList;
    private Context mContext;
    //存放图片
    private List<SimpleDraweeView> draweeViews = new ArrayList<>();

    public TopStoriesAdapter(Context context, List<TopStories> stories){
        mContext = context;
        if (stories == null || stories.size() == 0){
            mStoriesList = new ArrayList<>();
        }else {
            mStoriesList = stories;
            Log.d("1111111111-------", "size is   "+stories.size());
        }
    }

    public void addData(List<TopStories> stories){
        for (int i = 0; i < stories.size(); i++) {//通过url加载图片并存储到list中
            SimpleDraweeView image = new SimpleDraweeView(mContext);
            Uri uri = Uri.parse(stories.get(i).getStoryImageUrl());
            image.setImageURI(uri);
            draweeViews.add(image);
        }
        mStoriesList.addAll(stories);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStoriesList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position < draweeViews.size()) {
            SimpleDraweeView tmp = draweeViews.get(position);
            container.addView(tmp);
            return tmp;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position < draweeViews.size())
            container.removeView(draweeViews.get(position));
    }
}

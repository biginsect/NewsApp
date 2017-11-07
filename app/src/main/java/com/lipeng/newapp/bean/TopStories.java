package com.lipeng.newapp.bean;

/**
 * Created by lipeng-ds3 on 2017/11/6.
 * 主页轮播图所需的实体类
 */

public class TopStories {
    private int storyId;
    private String storyImageUrl;
    private String storyTitle;

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getStoryImageUrl() {
        return storyImageUrl;
    }

    public void setStoryImageUrl(String storyImageUrl) {
        this.storyImageUrl = storyImageUrl;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }
}

package com.lipeng.newapp.bean;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 * 新闻实体类
 */

public class News {
    //新闻id
    private int newsId;
    //新闻图片的url
    private String newsImageUrl;
    //新闻的title
    private String newsTitle;
    //新闻的content
    private String newsContent;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}

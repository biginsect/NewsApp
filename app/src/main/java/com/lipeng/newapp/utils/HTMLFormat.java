package com.lipeng.newapp.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by lipeng-ds3 on 2017/10/31.
 * 处理HTML代码图片显示问题
 */

public final class HTMLFormat {

    // 用于处理WebView加载html代码时，图片显示模糊问题
    public static String setWebViewType(String htmltext){
        Document document = Jsoup.parse(htmltext);
        //返回带有指定标签名（ima）的对象的集合，元素的顺序不变
        Elements elements = document.getElementsByTag("ima");
        for (Element element : elements){
            //将匹配到的”width“键的元素的值设置为100%，”height“键的元素的值设置为auto
            element.attr("width","100%").attr("height","auto");
        }

        return document.toString();
    }
}

package com.lipeng.newapp.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lipeng-ds3 on 2017/11/7.
 */

public class MyImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String)path);
        imageView.setImageURI(uri);
    }

    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView draweeView = new SimpleDraweeView(context);
        return draweeView;
    }
}

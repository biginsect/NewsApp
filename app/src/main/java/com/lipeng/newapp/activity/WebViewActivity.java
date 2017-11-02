package com.lipeng.newapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.utils.HTMLFormat;
import com.lipeng.newapp.utils.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 * email:  lipeng-ds3@gomeplus.com
 * 新闻详情页面，需要检查当前网路状态
 * 从{@link MainActivity}页面点击item跳转后的页面
 * 由于向API请求时返回的数据有html代码以及css代码，在加载WebView时需要将这些代码传入
 * 图片处理还未能做到适度缩放以适应当前屏幕
 */

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    @BindView(R.id.wv_news_detail)  WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);
        //检查网络状态
        if (!NetworkUtil.isNetworkAvailable(WebViewActivity.this))
            Toast.makeText(this, "No Available Network!",Toast.LENGTH_LONG).show();
        else{
            Log.d(TAG, "Network is available!");
        }
        init();
    }

    private String loadHtmlCode(){//读取存储在SharedPreferences中的html代码
        SharedPreferences preferences = getSharedPreferences("html", MODE_PRIVATE);
        return preferences.getString("html","");
    }

    private void init(){//初始化，加载数据和布局
        //当前news的url
        String address = getIntent().getStringExtra("address");
        NetworkUtil.loadWebViewFromURL(this, address);
//        Log.d(TAG, "  " + address);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        //---/data/data/com.example.lipeng_ds3/shared_prefs/css.css此路径是css代码存放的路径
        mWebView.loadDataWithBaseURL("/data/data/com.lipeng/shared_prefs/css.css",
                HTMLFormat.setWebViewType(loadHtmlCode()), "text/html", "UTF-8", null);
    }
}

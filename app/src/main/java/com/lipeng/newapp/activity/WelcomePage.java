package com.lipeng.newapp.activity;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.lipeng.myapplication.R;
import com.lipeng.newapp.view.WelcomeActivityAnimView;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //动态生成view
        WelcomeActivityAnimView animView = new WelcomeActivityAnimView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        animView.setLayoutParams(params);

        //第二个自定义view需要延迟播放
        WelcomeActivityAnimView animView1 = new WelcomeActivityAnimView(this);
        animView1.setLayoutParams(params);
        animView1.setDelay(1000);

        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_welcome_page, null);
        viewGroup.setBackgroundColor(Color.parseColor("#8B8878"));
        viewGroup.addView(animView);
        viewGroup.addView(animView1);
        setContentView(viewGroup);

    }

}

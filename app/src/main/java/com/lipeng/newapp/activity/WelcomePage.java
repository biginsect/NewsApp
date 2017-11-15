package com.lipeng.newapp.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.view.Point;
import com.lipeng.newapp.view.WelcomeActivityAnimView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        ButterKnife.bind(this);


    }

}

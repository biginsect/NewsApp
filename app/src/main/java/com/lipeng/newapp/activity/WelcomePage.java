package com.lipeng.newapp.activity;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.view.Point;
import com.lipeng.newapp.view.WelcomeActivityAnimView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePage extends AppCompatActivity {

    @BindView(R.id.my_view) WelcomeActivityAnimView mAnim;
    @BindView(R.id.my_view2) WelcomeActivityAnimView mAnim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        ButterKnife.bind(this);

//        init();
    }

    private void init(){
/*        mAnim.animate()
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000)
                .translationX(600)
                .translationY(900);
        mAnim2.animate().setStartDelay(300)
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000)
                .translationX(600)
                .translationY(900);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnim, View.ALPHA, 600f, 900f);
        animator.setDuration(2000)
                .setInterpolator(new BounceInterpolator());
        animator.setRepeatCount(-1);
        animator.start();*/
    }
}

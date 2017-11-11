package com.lipeng.newapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

import com.lipeng.newapp.view.evaluator.PointEvaluator;

/**
 * Created by lipeng-ds3 on 2017/11/8.
 * 自定义view，实现属性动画
 */

public class WelcomeActivityAnimView extends View{
    //圆形动画的半径
    public static final float RADIUS = 50f;
    //坐标实例
    private Point currentPoint;
    //画笔
    private Paint mPaint;
    private int count = 1;

    public WelcomeActivityAnimView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#551A8B"));
    }

    @Override
    protected void onDraw(Canvas canvas) {//绘制
        if (count % 2 != 0) {
            if (currentPoint == null) {
                currentPoint = new Point(RADIUS, RADIUS);
                drawCircle(canvas);
                startAnimation();
            } else {
                drawCircle(canvas);
            }
        }else {
            //求出屏幕对角线的长度
            float radius = getWidth()*getWidth()+getHeight()*getHeight();
            //重新绘制
            canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), 100,mPaint);
//            startScaleAnimation();
        }
    }

    private void drawCircle(Canvas canvas){
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public void startScaleAnimation(){//缩放动画
        ScaleAnimation animation = new ScaleAnimation(RADIUS, 50f, RADIUS, 50f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        this.setAnimation(animation);
        Log.d("111111111111111111","         -----");
        startAnimation(animation);
    }

    private void startAnimation(){
        ValueAnimator animator = getAnimator();
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(3000);
        animator.setRepeatCount(3);
        //反向
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //第一次动画结束，计数加一
                count ++;
                //重新绘制
                invalidate();
            }
        });
        animator.start();
    }

    private ValueAnimator getAnimator(){
        //动画起始坐标
        Point startPoint = new Point(RADIUS, RADIUS);
        //动画结束坐标
        Point endPoint = new Point( RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(new Point(2*(getWidth()-RADIUS),getHeight()/2 -RADIUS)), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {//监听坐标值是否有变化
                //重新赋值currentPoint
                currentPoint = (Point)animation.getAnimatedValue();
                //由于point（坐标）值改变，调用invalidate方法后onDraw方法会重新被调用
                invalidate();
            }
        });
        return animator;
    }
}

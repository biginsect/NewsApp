package com.lipeng.newapp.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

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

    public WelcomeActivityAnimView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#551A8B"));
    }

    @Override
    protected void onDraw(Canvas canvas) {//绘制
        if (currentPoint == null) {
                currentPoint = new Point(RADIUS, RADIUS);
                drawCircle(canvas);
                startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas){
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation(){
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
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        //反向
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

}

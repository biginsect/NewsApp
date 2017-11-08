package com.lipeng.newapp.view;

import android.animation.AnimatorSet;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.lipeng.newapp.view.evaluator.PointEvaluator;

/**
 * Created by lipeng-ds3 on 2017/11/8.
 */

public class WelcomeActivityAnimView extends View{
    //圆形动画的半径
    public static final float RADIUS = 50f;
    //坐标实例
    private Point currentPoint;
    private Paint mPaint;

    public WelcomeActivityAnimView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.MAGENTA);
    }

    @Override
    protected void onDraw(Canvas canvas) {//绘制
        if (currentPoint == null){
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else {
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
        Point startPoint = new Point((getWidth() - RADIUS) / 2, RADIUS);
        //动画结束坐标
        Point endPoint = new Point((getWidth() - RADIUS) / 2, (getHeight() - RADIUS) / 2);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {//监听坐标值是否有变化
                //重新赋值currentPoint
                currentPoint = (Point)animation.getAnimatedValue();
                //由于point（坐标）值改变，调用invalidate方法后onDraw方法会重新被调用
                invalidate();
            }
        });
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(800).setRepeatCount(-1);
        animator.start();
    }
}

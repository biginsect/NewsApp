package com.lipeng.newapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
    //延迟播放动画的时间
    private long delay;

    public WelcomeActivityAnimView(Context context){//不能直接调用super(context)，否则canvas未初始化报空指针
        this(context, null);
    }

    public WelcomeActivityAnimView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {//绘制
        Log.d("log", "onDraw");
        if (currentPoint == null) {
                currentPoint = new Point(RADIUS, RADIUS);
                drawCircle(canvas);
                startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    public void setDelay(long delay){//设置动画延迟播放
        this.delay = delay;
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
        if (delay != 0L){
            animator.setStartDelay(delay);
            Log.d("MMMMM", delay + "");
        }
//        animator.setRepeatCount(-1);
        //反向
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("log", "onLayout");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("log","onWindowFocusChange" + " " + hasWindowFocus );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("log", "onMeasure");
    }
}

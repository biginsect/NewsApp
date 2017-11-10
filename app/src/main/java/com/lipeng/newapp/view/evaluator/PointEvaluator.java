package com.lipeng.newapp.view.evaluator;

import android.animation.TypeEvaluator;

import com.lipeng.newapp.view.Point;

/**
 * Created by lipeng-ds3 on 2017/11/8.
 */

public class PointEvaluator implements TypeEvaluator<Point> {
    //贝塞尔曲线，第三个点作为辅助点用于做切线
    private Point mPoint;

    public PointEvaluator(Point point){
        mPoint = point;
    }

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = (1-fraction)*(1-fraction)*startValue.getX()+2*fraction*(1-fraction)*mPoint.getX() + fraction * fraction*endValue.getX();
        float y = (1-fraction)*(1-fraction)*startValue.getY()+2*fraction*(1-fraction)*mPoint.getY() + fraction * fraction*endValue.getY();
        return new Point(x, y);
    }
}

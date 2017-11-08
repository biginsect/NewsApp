package com.lipeng.newapp.view.evaluator;

import android.animation.TypeEvaluator;

import com.lipeng.newapp.view.Point;

/**
 * Created by lipeng-ds3 on 2017/11/8.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point)endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        return new Point(x, y);
    }
}

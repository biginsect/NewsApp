package com.lipeng.newapp.view;

/**
 * Created by lipeng-ds3 on 2017/11/8.
 * //用于记录坐标的位置，x代表x轴，y代表y轴
 */

public class Point {
    private float x;
    private float y;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

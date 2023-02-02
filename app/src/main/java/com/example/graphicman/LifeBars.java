package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LifeBars {
    private int food;
    private int energy;
    private int health;
    private int length = 400;
    private int width = 56;
    private int x = 100;
    private int y = 100;

    public LifeBars(int food, int energy, int health) {
        this.food = food;
        this.energy = energy;
        this.health = health;
    }

    public void draw(Canvas canvas) {
        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));
        canvas.drawRect(x, y, x+length, y+width, backPaint);
//        canvas.drawRect(x, y, x+length, y+width, backPaint);
//        canvas.drawRect(x, y, x+length, y+width, backPaint);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(250, 0, 0));

        canvas.drawRect(x+5, y+5, x+health*10, y+width-10, paint);
//        canvas.drawRect(x, y, x+length, y+width, paint);
//        canvas.drawRect(x, y, x+length, y+width, paint);

    }



}

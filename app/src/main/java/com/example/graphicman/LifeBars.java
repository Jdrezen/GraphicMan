package com.example.graphicman;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

public class LifeBars {
    private int food;
    private int energy;
    private int health;
    private int height;
    private int width;
    private float ratio = 10;


    private int barLength = 0;
    private int barWidth = 0;

    public LifeBars(Context context, int food, int energy, int health, int height, int width) {
        this.food = food;
        this.energy = energy;
        this.health = health;
        this.height = height;
        this.width = width;
        this.barLength = (int) (width*0.85);
        this.barWidth = (int) (height*0.02);
        this.ratio = ((float)(barLength-5)) /100;
    }

    public void addFood(int value){
        if(this.food + value >100){
            this.food = 100;
            subHealth(5);
        }
        this.food +=value;
    }
    public void subFood(int value){
        if(this.food - value < 0){
            this.food = 0;
        }
        this.food -=value;
    }
    public void addEnegy(int value){
        if(this.energy + value >100){
            this.energy = 100;
        }
        this.energy +=value;
    }
    public void subEnegy(int value){
        if(this.energy - value < 0){
            this.energy = 0;
        }
        this.energy -=value;
    }
    public void addHealth(int value){
        if(this.health + value >100){
            this.health = 100;
        }
        this.health +=value;
    }
    public void subHealth(int value){
        if(this.health - value < 0){
            this.health = 0;
        }
        this.health -=value;
    }

    public void drawPoulew(Canvas canvas){
        int x = 20;
        int y = 325;

        Paint brownPaint = new Paint();
        brownPaint.setColor(Color.rgb(153,76,0));
        Paint wightPaint = new Paint();
        wightPaint.setColor(Color.rgb(255,255,255));
        //os
        canvas.drawRect(x+18, y, x+32, y+80,  wightPaint);
        y+=15;
        //meet
        canvas.drawRect(x, y, x+50, y+50, brownPaint);
    }
    public void drawEnergy(Canvas canvas){
        int x = 20;
        int y = 250;

        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0,128,255));
        canvas.drawRect(x+15, y-5, x+30, y, bluePaint);

        canvas.drawRect(x, y, x+50, y+15, bluePaint);
        y+=20;
        canvas.drawRect(x, y, x+50, y+15, bluePaint);
        y+=20;
        canvas.drawRect(x, y, x+50, y+15, bluePaint);
    }
    public void drawEarth(Canvas canvas){
        int x = 20;
        int y = 170;

        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(204,0,0));

        canvas.drawRect(x+5, y, x+15, y+10, redPaint);
        canvas.drawRect(x+35, y, x+45, y+10, redPaint);
        y+=10;
        canvas.drawRect(x, y, x+55, y+15, redPaint);
        y+=15;
        canvas.drawRect(x+5, y, x+45, y+15, redPaint);
        y+=15;
        canvas.drawRect(x+17, y, x+33, y+10, redPaint);
    }
    public void drawCross(Canvas canvas) {
        int x = 20;
        int y = 170;

        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(204, 0, 0));

        canvas.drawRect(x+15, y, x + 35, y+50, redPaint);
        y+=16;
        canvas.drawRect(x, y, x + 50, y + 20, redPaint);
    }


    public void drawBars(Canvas canvas){
        int x = 100;
        int y = 200;

        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(250, 0, 0));

        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.rgb(102, 204, 0));

        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.rgb(0, 102, 204));

        canvas.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvas.drawRect(x+5, y+5, x+(int)(health*ratio), y+barWidth-5, redPaint);
        y = y+70;
        canvas.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvas.drawRect(x+5, y+5,  x+(int)(energy*ratio), y+barWidth-5, bluePaint);
        y = y+70;
        canvas.drawRect(x, y, x+barLength, y+barWidth, backPaint);
        canvas.drawRect(x+5, y+5,  x+(int)(food*ratio), y+barWidth-5, greenPaint);
    }

    public void draw(Canvas canvas) {
        Paint backGroundPaint = new Paint();
        backGroundPaint.setColor(Color.rgb(192,192,192));

        canvas.drawRect(0, 0, width, height/5, backGroundPaint);

        drawPoulew(canvas);
        drawEnergy(canvas);
//        drawEarth(canvas);
        drawBars(canvas);
        drawCross(canvas);




    }



}

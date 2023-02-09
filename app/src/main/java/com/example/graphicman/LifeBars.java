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
    private int x = 100;
    private int y = 100;
    private float ratio = 10;

    private Bitmap heartImage;

    private int barLength = 0;
    private int barWidth = 0;

    public LifeBars(Context context, int food, int energy, int health, int height, int width, Bitmap heartImage) {
        this.food = food;
        this.energy = energy;
        this.health = health;
        this.height = height;
        this.width = width;
        this.barLength = (int) (width*0.85);
        this.barWidth = (int) (height*0.02);
        this.ratio = ((float)(barLength-5)) /100;


                this.heartImage = heartImage;
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

    public void draw(Canvas canvas) {
        x = 100;
        y = 100;

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



}

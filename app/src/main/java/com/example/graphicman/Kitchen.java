package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;

public class Kitchen extends AppCompatActivity {
    private int height;
    private int width;
    private ArrayList<Point> eatablesRain = new ArrayList<>();
    private CanvasWrapper canvasWrapper;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Kitchen( int height, int width) {
        this.sensorManager = sensorManager;
        this.height = height;
        this.width = width;
        this.canvasWrapper = new CanvasWrapper(width,height);
    }

    public boolean onDrag(DragEvent dragEvent) {
        return false;
    }

    public void drawEatable(Canvas canvas, int x, int y) {

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

    public void drawGraphicMan(Canvas canvas,int x,int y) {
        // body
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(x,y,x+400,y+400,blackPaint);
        // eye
        Paint wightPaint = new Paint();
        wightPaint.setColor(Color.rgb(250, 250, 250));
        x+=150;
        y+=50;
        canvasWrapper.drawRect(x,y,x+100,y+100,wightPaint);
        x+=40;
        y+=0;
        canvasWrapper.drawRect(x,y,x+20,y+50,blackPaint);

        //mouthe

    }


    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        drawGraphicMan(canvas,500,1500);
    }

    public void update() {

    }


    public void goToGym(View view) {
        Intent intent = new Intent(this, Gym.class);
        startActivity(intent);
    }

    public void goToBedroom(View view) {
        Intent intent = new Intent(this, Bedroom.class);
        startActivity(intent);
    }
}
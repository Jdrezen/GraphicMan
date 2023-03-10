package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class Kitchen extends AppCompatActivity implements SensorEventListener {
    private int height;
    private int width;
    private boolean running;
    private Handler eatableHandler;
    private ArrayList<Point> eatablesRain = new ArrayList<>();
    private CanvasWrapper canvasWrapper;
    private SensorManager sensorManager;
    private LifeBars lifeBars;
    private EState state;
    private int xGraphicman = 500;
    private int yGraphicman = 1500;
    private int dirrection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Kitchen(SensorManager sensorManager, EState state, LifeBars lifeBars, int height, int width) {
        this.sensorManager = sensorManager;
        this.state = state;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
        this.lifeBars = lifeBars;
        this.height = height;
        this.width = width;
        this.canvasWrapper = new CanvasWrapper(width,height);
        this.running = true;
        this.eatableHandler = new Handler();
        this.eatableHandler.postDelayed(addEatable , 10);
    }

    public void setState(EState state) {
        this.state = state;
    }

    public boolean isRunning(){
        return running;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        dirrection = (int)(sensorEvent.values[0] *(-2));
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private Runnable addEatable = new Runnable() {
        public void run() {
            if(state == EState.KITCHEN) {
                Random rand = new Random();
                int obstaclex = (int) (Math.random() * (width + 1));
                int obstacley = 0;
                eatablesRain.add(new Point(obstaclex, obstacley));
                eatableHandler.postDelayed(this, 1000);
            }else {
                eatableHandler.postDelayed(this, 1000);
            }
        }
    };

    public void drawEatable(Canvas canvas, int x, int y) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.rgb(0, 0, 0));

        Paint brownPaint = new Paint();
        brownPaint.setColor(Color.rgb(153,76,0));

        Paint wightPaint = new Paint();
        wightPaint.setColor(Color.rgb(255,255,204));
        //os
        canvasWrapper.drawRect(x+16, y-2, x+38, y+88,  blackPaint);
        canvasWrapper.drawRect(x+18, y, x+32, y+80,  wightPaint);
        y+=15;
        //meet
        canvasWrapper.drawRect(x, y, x+50, y+50, brownPaint);
    }

    public void drawGraphicMan(Canvas canvas,int x,int y) {
        int xbouche = x;
        int ybouche = y;
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
        Paint redPaint = new Paint();
        redPaint.setColor(Color.rgb(250, 0, 0));


        xbouche+=50;
        ybouche+=250;
        canvasWrapper.drawRect(xbouche,ybouche,xbouche+300,ybouche+100,redPaint);
        xbouche-=50;
        ybouche-=25;

        Paint rederPaint = new Paint();
        rederPaint.setColor(Color.rgb(160, 0, 0));
        xbouche+=125;
        ybouche+=50;
        canvasWrapper.drawRect(xbouche,ybouche,xbouche+150,ybouche+100,rederPaint);
        xbouche+=65;
        ybouche+=0;
        canvasWrapper.drawRect(xbouche,ybouche,xbouche+20,ybouche+50,redPaint);

    }

    public void drawBotomLip(Canvas canvas,int x,int y) {
        y+=350;
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.rgb(0, 0, 0));

        Paint wightPaint = new Paint();
        wightPaint.setColor(Color.rgb(250, 250, 250));

        canvasWrapper.drawRect(x,y,x+400,y+50,blackPaint);
        canvasWrapper.drawRect(x,y+50,x+400,y+350,wightPaint);

    }

    public void removeOldEatables(){
        ArrayList<Point> eatablesToKeep = new ArrayList<>();
        for(int i=0;i<eatablesRain.size();i++){
            if( eatablesRain.get(i).y <= 2154){
                eatablesToKeep.add(eatablesRain.get(i));
            }
        }
        eatablesRain =  eatablesToKeep;
    }


    public void update(){
        if(xGraphicman + dirrection  >= 0 && xGraphicman + dirrection  <=1080-400 ){
            xGraphicman+= dirrection;
        }
        for(int i=0;i<eatablesRain.size();i++){
           eatablesRain.get(i).y+=15;
           if(eatablesRain.get(i).y <= 1865 && eatablesRain.get(i).y >= 1850 && eatablesRain.get(i).x > xGraphicman && eatablesRain.get(i).x < xGraphicman+400){
               lifeBars.addFood(5);
           }
        }
        removeOldEatables();
        if(lifeBars.isDead()){
            running = false;
        }
    }


    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);
        drawGraphicMan(canvas,xGraphicman,1500);
        //
        for(int i=0;i<eatablesRain.size();i++){
            drawEatable(canvas, eatablesRain.get(i).x, eatablesRain.get(i).y);
        }
        drawBotomLip(canvas,xGraphicman,1500);
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
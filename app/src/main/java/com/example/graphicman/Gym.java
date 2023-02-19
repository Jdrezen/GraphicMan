package com.example.graphicman;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

public class Gym implements SensorEventListener {
    private int height, width;
    private boolean running;
    private float max;
    private Activity mother;
    private Queue<PullUpPose> animationFrames = new LinkedList<PullUpPose>();
    private CanvasWrapper canvasWrapper;
    private SensorManager sensorManager;
    private LifeBars lifeBars;

    public Gym(SensorManager sensorManager, LifeBars lifeBars, int height, int width) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.lifeBars = lifeBars;
        this.height = height;
        this.width = width;
        this.animationFrames.add(PullUpPose.DOWN);
        this.canvasWrapper = new CanvasWrapper(width,height);
        this.running = true;
    }

    public boolean isRunning(){
        return running;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(animationFrames.isEmpty() && max > 1.2) {

            if(max > 3){
                addAnimationFrames(1);
            }
            else if(2 < max && max < 3){
                addAnimationFrames(3);
            }
            else if(1.2 < max && max < 2){
                addAnimationFrames(5);
            }
            max = 0;
        }
        else{
            // check if the device is shaking
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            //float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            //Random pour tester
            float gForce = (float) (Math.random() * 10);

            max = Math.max(gForce, max);
        }
    }

    public void addAnimationFrames(int number){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < number; j++) {
                animationFrames.add(PullUpPose.values()[i]);
            }
        }
    }

    public void draw(Canvas canvas){
        canvasWrapper.setCanvas(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(125, 125, 125));

        //Barre transversale
        canvasWrapper.drawRect(100, 600, 980, 650, paint);

        //Barre de gauche
        canvasWrapper.drawRect(130, 650, 180, 2154, paint);

        //Barre de droite
        canvasWrapper.drawRect(900, 650, 950, 2154, paint);

        if (animationFrames.isEmpty()){
            animationFrames.add(PullUpPose.DOWN);
        }

        switch (animationFrames.poll()){
            case DOWN:
                drawFirstPose(paint, canvas);
                break;

            case MID:
                drawSecondPose(paint, canvas);
                break;

            case UP:
                drawThirdPose(paint, canvas);
                lifeBars.addHealth(5);
                lifeBars.subEnegy(10);
                break;
        }

        if(lifeBars.isDead()){
            running = false;
        }
    }

    public void drawFirstPose(Paint paint, Canvas canvas){
        //Graphic man body
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(340, 950, 740, 1350, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(490, 1000, 590, 1100, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(530, 1040, 550, 1100, paint);

        //Graphic man arms
        canvasWrapper.drawRect(300, 600, 340, 1150, paint);
        canvasWrapper.drawRect(740, 600, 780, 1150, paint);

    }

    public void drawSecondPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));

        //Graphic man body
        canvasWrapper.drawRect(340, 750, 740, 1150, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(490, 800, 590, 900, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(530, 840, 550, 900, paint);

        //Graphic man left arm
        canvasWrapper.drawLine(200, 875, 350, 950, paint);
        canvasWrapper.drawLine(200, 900, 330, 600, paint);

        //Graphic man right arm
        canvasWrapper.drawLine(880, 875, 730, 950, paint);
        canvasWrapper.drawLine(880, 900, 750, 600, paint);
    }

    public void drawThirdPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));

        //Graphic man body
        canvasWrapper.drawRect(340, 550, 740, 950, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(490, 600, 590, 700, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(530, 640, 550, 700, paint);

        //Graphic man left arm
        canvasWrapper.drawLine(200, 875, 350, 750, paint);
        canvasWrapper.drawLine(200, 875, 330, 600, paint);

        //Graphic man right arm
        canvasWrapper.drawLine(880, 875, 730, 750, paint);
        canvasWrapper.drawLine(880, 875, 750, 600, paint);
    }
}
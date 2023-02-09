package com.example.graphicman;

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
    private SensorManager sensorManager;
    int height, width;
    float testVibration;
    Queue<PullUpPose> animationFrames = new LinkedList<PullUpPose>();
    float max;

    public Gym(SensorManager sensorManager, int height, int width) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.height = height;
        this.width = width;
        this.animationFrames.add(PullUpPose.DOWN);
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
            testVibration = max;
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
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            //Random pour tester
            //float gForce = (float) (Math.random() * 10);
            max = gForce > max ? gForce : max;
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
//        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(125, 125, 125));
        paint.setTextSize(300);

        //GForce
        canvas.drawText( testVibration+"", 100, 200, paint);

        //Barre transversale
        canvas.drawRect(100,600,width - 100,650,paint);

        //Barre de gauche
        canvas.drawRect(150,600,200,height - 200,paint);

        //Barre de droite
        canvas.drawRect(width - 200,600,width - 150,height - 200,paint);

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
                break;
        }
    }

    public void drawFirstPose(Paint paint, Canvas canvas){
        //Graphic man body
        paint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(width/2 - 200,height - 1200,width/2 + 200,height - 800,paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvas.drawRect(width/2 - 50,height - 1150,width/2 + 50,height - 1050,paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(width/2 - 10,height - 1110,width/2 + 10,height - 1010,paint);

        //Graphic man arms
        canvas.drawRect(width/2 - 220,600,width/2 - 180,height - 1000,paint);
        canvas.drawRect(width/2 + 180,600,width/2 + 220,height - 1000,paint);

    }

    public void drawSecondPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setStrokeWidth(50);

        //Graphic man left arm
        canvas.drawLine(200, height - 1300, width/2 - 150, height - 1200, paint);
        canvas.drawLine(200, height - 1280, width/2 - 220, 600, paint);

        //Graphic man right arm
        canvas.drawLine(width/2 + 150, height - 1200, width - 180, height - 1300, paint);
        canvas.drawLine(width - 200, height - 1280, width/2 + 220, 600, paint);

        //Graphic man body
        canvas.drawRect(width/2 - 200,height - 1400,width/2 + 200,height - 1000,paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvas.drawRect(width/2 - 50,height - 1350,width/2 + 50,height - 1250,paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(width/2 - 10,height - 1310,width/2 + 10,height - 1210,paint);

    }

    public void drawThirdPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setStrokeWidth(50);

        //Graphic man left arm
        canvas.drawLine(200, height - 1280, width/2 - 150, height - 1400, paint);
        canvas.drawLine(200, height - 1280, width/2 - 220, 600, paint);

        //Graphic man right arm
        canvas.drawLine(width/2 + 150, height - 1400, width - 180, height - 1300, paint);
        canvas.drawLine(width - 180, height - 1300, width/2 + 220, 600, paint);

        //Graphic man body
        canvas.drawRect(width/2 - 200,height - 1600,width/2 + 200,height - 1200,paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvas.drawRect(width/2 - 50,height - 1550,width/2 + 50,height - 1450,paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(width/2 - 10,height - 1510,width/2 + 10,height - 1410,paint);

    }
}
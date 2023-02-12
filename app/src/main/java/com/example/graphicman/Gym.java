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
    private int height, width;
    private float max;
    private Queue<PullUpPose> animationFrames = new LinkedList<PullUpPose>();
    private CanvasWrapper canvasWrapper;
    private SensorManager sensorManager;

    public Gym(SensorManager sensorManager, int height, int width) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.height = height;
        this.width = width;
        this.animationFrames.add(PullUpPose.DOWN);
        this.canvasWrapper = new CanvasWrapper(width,height);
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
        canvasWrapper.setCanvas(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(125, 125, 125));

        //Barre transversale
        canvasWrapper.drawRect(100, 280, 900, 303, paint);

        //Barre de gauche
        canvasWrapper.drawRect(130, 280, 180, 980, paint);

        //Barre de droite
        canvasWrapper.drawRect(820, 280, 870, 980, paint);

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
        canvasWrapper.drawRect(310, 440, 690, 630, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(450, 460, 550, 510, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(490, 480, 510, 510, paint);

        //Graphic man arms
        canvasWrapper.drawRect(275, 280, 310, 535, paint);
        canvasWrapper.drawRect(690, 280, 725, 535, paint);

    }

    public void drawSecondPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));

        //Graphic man left arm
        canvasWrapper.drawLine(185, 395, 320, 430, paint);
        canvasWrapper.drawLine(300, 280, 185, 405, paint);

        //Graphic man right arm
        canvasWrapper.drawLine(680, 430, 820, 395, paint);
        canvasWrapper.drawLine(690, 280, 830, 405, paint);

        //Graphic man body
        canvasWrapper.drawRect(310, 350, 690, 540, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(450, 370, 550, 420, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(490, 390, 510, 420, paint);

    }

    public void drawThirdPose(Paint paint, Canvas canvas){
        paint.setColor(Color.rgb(0, 0, 0));

        //Graphic man left arm
        canvasWrapper.drawLine(185, 400, 320, 350, paint);
        canvasWrapper.drawLine(300, 280, 185, 405, paint);

        //Graphic man right arm
        canvasWrapper.drawLine(680, 350, 820, 395, paint);
        canvasWrapper.drawLine(700, 280, 830, 400, paint);

        //Graphic man body
        canvasWrapper.drawRect(310, 250, 690, 440, paint);

        //Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(450, 270, 550, 320, paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(490, 290, 510, 320, paint);

    }
}
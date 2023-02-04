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

public class Gym implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView xText, y, z;
    boolean shaking;
    int height, width;
    int lastAnimation = 2;

    public Gym(SensorManager sensorManager, int height, int width) {
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.height = height;
        this.width = width;
    }

    public void setSensorManager(SensorManager sensorManager){
        this.sensorManager = sensorManager;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // check if the device is shaking
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement
        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > 1.2) {
            shaking = true;
        } else{
            shaking = false;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(125, 125, 125));
        paint.setTextSize(300);
        canvas.drawText(shaking +"", 100, 100, paint);

        //Barre transversale
        canvas.drawRect(100,600,width - 100,650,paint);

        //Barre de gauche
        canvas.drawRect(150,600,200,height - 200,paint);

        //Barre de droite
        canvas.drawRect(width - 200,600,width - 150,height - 200,paint);

        shaking = true;

        if(shaking){
            switch (lastAnimation){
                case 0:
                    drawFirstPose(paint, canvas);
                    lastAnimation = 1;
                    break;

                case 1:
                    drawSecondPose(paint, canvas);
                    lastAnimation = 2;
                    break;

                case 2:
                    drawThirddPose(paint, canvas);
                    lastAnimation = 0;
            }
        }
        else {
            drawFirstPose(paint, canvas);
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

    public void drawThirddPose(Paint paint, Canvas canvas){
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
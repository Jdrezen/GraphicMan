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
        paint.setColor(Color.rgb(250, 0, 0));
        paint.setTextSize(300);
        canvas.drawText(shaking +"", 100, 100, paint);

        canvas.drawRect(100,600,width - 100,650,paint);

        paint.setColor(Color.rgb(0, 250, 0));
        canvas.drawRect(150,600,200,height - 200,paint);

        paint.setColor(Color.rgb(0, 0, 250));
        canvas.drawRect(width - 200,600,width - 150,height - 200,paint);

        paint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(width/2 - 200,height - 1200,width/2 + 200,height - 800,paint);

        paint.setColor(Color.rgb(250, 250, 250));
        canvas.drawRect(width/2 - 150,height - 1150,width/2 - 130,height - 1100,paint);


    }
}
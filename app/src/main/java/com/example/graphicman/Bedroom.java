package com.example.graphicman;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;


public class Bedroom implements SensorEventListener {
    private int height;
    private int width;
    private SensorManager sensorManager;
    private int barWidth;
    private Sensor lightSensor;
    private int lightValue;

    public Bedroom(SensorManager sensorManager, int height, int width) {
        this.height = height;
        this.width = width;
        this.barWidth = (int) (height * 0.02);
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void draw(Canvas canvas) {


        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.rgb(250, 250, 0));

        int barHeight = (height * 3/4 - 5) - (height * 1/4 + 35);
        // border
        canvas.drawRect(width - 50, height * 1/4 + 30, width - 50 + barWidth, height * 3/4, backPaint);
        // bar
        canvas.drawRect(width - 45, height * 3/4 - 5 - barHeight * lightValue / 100, width - 55 + barWidth, height * 3/4 - 5, yellowPaint);


        // sun
        canvas.drawCircle(width - 60, height * 1/4 - 25, 20, yellowPaint);
        canvas.drawCircle(width - 20, height * 1/4 - 50, 10, yellowPaint);
        canvas.drawCircle(width - 100, height * 1/4 - 50, 10, yellowPaint);
        canvas.drawCircle(width - 100, height * 1/4 - 0, 10, yellowPaint);
        canvas.drawCircle(width - 20, height * 1/4 - 0, 10, yellowPaint);

        canvas.drawCircle(width - 60, height * 1/4 - 70, 10, yellowPaint);
        canvas.drawCircle(width - 60, height * 1/4 + 20, 10, yellowPaint);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightLevel = event.values[0];
        Log.d("light", "light " + lightLevel);
//        lightLevelText.setText(String.valueOf(lightLevel));
        int maxRange = (int) lightSensor.getMaximumRange();

        lightValue = lightLevel > 500 ? 100 : (int) lightLevel / 5;
        int color = (255 * (int) lightLevel) / maxRange;

//        progressBar.getProgressDrawable().setColorFilter(
//                Color.argb(255, Math.min(color, 255), Math.min(color, 255), 0), android.graphics.PorterDuff.Mode.SRC_IN);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

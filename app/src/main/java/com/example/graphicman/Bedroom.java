package com.example.graphicman;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.LiveData;


public class Bedroom implements SensorEventListener {
    private int height;
    private int width;
    private SensorManager sensorManager;
    private int barWidth;
    private Sensor lightSensor;
    private int lightValue;
    private CanvasWrapper canvasWrapper;
    private int dificulty;
    private int mouvement = 600;
    private boolean mvtDirection = true;
    private LifeBars lifeBars;

    public Bedroom(SensorManager sensorManager, int height, int width, LifeBars lifeBars) {
        this.height = height;
        this.width = width;
        this.barWidth = (int) (2154 * 0.02);
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.canvasWrapper = new CanvasWrapper(width, height);
        this.lifeBars = lifeBars;

        dificulty = 1;
    }

    public void draw(Canvas canvas) {

        canvasWrapper.setCanvas(canvas);
        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.rgb(250, 250, 0));

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);

        int barHeight = 2154 * 3/4 + 200 - 700 - 10;
        // border
        canvasWrapper.drawRect(1080 - 80,  700, 1080 - 80 + barWidth, 2154 * 3/4 + 200, backPaint);
        // light bar
        canvasWrapper.drawRect(1080 - 75, 2154 * 3/4 + 195 - (barHeight * lightValue / 100), 1080 - 85 + barWidth, 2154 * 3/4 + 195, yellowPaint);


        int lower = 2154 * 3/4 + 195 - 50;
        int selectorSize = lower - 200 / dificulty;
        int offset = moveSelector();

        // selector (red bars)
        canvasWrapper.drawRect(1080 - 100, lower - offset, 1080 - 20, lower + 5 - offset, redPaint);
        canvasWrapper.drawRect(1080 - 100, selectorSize - offset, 1080 - 20, selectorSize + 5 - offset, redPaint);

        // sun
        canvasWrapper.drawCircle(1080 - 60, 2154/4 + 55, 20, yellowPaint);
        canvasWrapper.drawCircle(1080 - 20, 2154 * 1/4 + 25, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 100, 2154 * 1/4 + 25, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 100, 2154 * 1/4 + 75, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 20, 2154 * 1/4 + 75, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 60, 2154 * 1/4 + 5, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 60, 2154 * 1/4 + 105, 10, yellowPaint);

        drawGraphicMan();
        drawBed();


        computeLifeBar(selectorSize - offset, lower - offset, 2154 * 3/4 + 195 - (barHeight * lightValue / 100));
    }

    private void drawGraphicMan() {
        Paint paint = new Paint();
        // Body
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(340, 950, 740, 1350, paint);

        // Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(490, 1000, 590, 1100, paint);

        // Pupil
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(530, 1040, 550, 1100, paint);
    }

    private void drawBed() {
        Paint paint = new Paint();
        // Body
        paint.setColor(Color.parseColor("#964213"));
        canvasWrapper.drawRect(224, 1592, 224+588, 1592+135, paint);
        paint.setColor(Color.parseColor("#AB6842"));
        canvasWrapper.drawRect(235, 1727, 235+55, 1727+135, paint);
        canvasWrapper.drawRect(743, 1727, 743+55, 1727+135, paint);
        canvasWrapper.drawRect(208, 1592, 208+618, 1592+38, paint);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightLevel = event.values[0];
        Log.d("light", "light " + lightLevel);
        int maxRange = (int) lightSensor.getMaximumRange();

        lightValue = lightLevel > 500 ? 100 : (int) lightLevel / 5;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // unused
    }

    private int moveSelector() {
        if (mouvement <= 0) {
            mouvement += dificulty;
            mvtDirection = true;
        } else if (mouvement > 600) {
            mouvement -= dificulty;
            mvtDirection = false;
        } else {
            mouvement = mouvement + (mvtDirection ?  dificulty : -dificulty) * 5;
        }
        return mouvement;
    }

    private void computeLifeBar(int high, int low, int light) {
        Log.d("tes", "monte : " + high + ", " + low + ", " + light);
        if (light > high && light < low) {
            lifeBars.addEnegy(5);
        }

    }

}

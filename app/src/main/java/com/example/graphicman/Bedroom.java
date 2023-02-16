package com.example.graphicman;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class Bedroom implements SensorEventListener {
    private int height;
    private int width;
    private SensorManager sensorManager;
    private int barWidth;
    private Sensor lightSensor;
    private int lightValue;
    private CanvasWrapper canvasWrapper;

    public Bedroom(SensorManager sensorManager, int height, int width) {
        this.height = height;
        this.width = width;
        this.barWidth = (int) (2154 * 0.02);
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.canvasWrapper = new CanvasWrapper(width, height);
    }

    public void draw(Canvas canvas) {

        canvasWrapper.setCanvas(canvas);
        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.rgb(250, 250, 0));

        int barHeight = (2154 * 3/4 - 5) - (2154 * 1/4 + 35);
        // border
        canvasWrapper.drawRect(1080 - 80,  700, 1080 - 80 + barWidth, 2154 * 3/4 + 200, backPaint);
        // bar
        canvasWrapper.drawRect(1080 - 95, 2154 * 3/4 - 5 - barHeight * lightValue / 100, 1080 - 105 + barWidth, 2154 * 3/4 - 5, yellowPaint);

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        // selecter
        canvasWrapper.drawRect(1080 - 100, 1400, 1080 - 20, 1405, redPaint);
        // sun
        canvasWrapper.drawCircle(1080 - 60, 2154/4 + 55, 20, yellowPaint);
        canvasWrapper.drawCircle(1080 - 20, 2154 * 1/4 - 50, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 100, 2154 * 1/4 - 50, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 100, 2154 * 1/4 - 0, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 20, 2154 * 1/4 - 0, 10, yellowPaint);

        canvasWrapper.drawCircle(1080 - 60, 2154 * 1/4 - 70, 10, yellowPaint);
        canvasWrapper.drawCircle(1080 - 60, 2154 * 1/4 + 20, 10, yellowPaint);

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
        // unused
    }
}

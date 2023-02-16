package com.example.graphicman;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Bedroom implements SensorEventListener {
    private int height;
    private int width;
    private SensorManager sensorManager;
    private int barLength;
    private int barWidth;

    public Bedroom(SensorManager sensorManager, int height, int width) {
        this.height = height;
        this.width = width;
        this.barLength = (int) (width*0.85);
        this.barWidth = (int) (height*0.02);
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void draw(Canvas canvas) {


        Paint backPaint = new Paint();
        backPaint.setColor(Color.rgb(128,128,128));

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.rgb(250, 250, 0));

        Paint circlePaint = new Paint();
        canvas.drawRect(width - 50, height * 1/4 + 30, width - 50 + barWidth, height * 3/4, backPaint);
        canvas.drawRect(width - 45, height * 1/4 + 35, width - 55 + barWidth, height * 3/4 - 5, yellowPaint);


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
    public void onSensorChanged(SensorEvent sensorEvent) {
//        sensorEvent.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

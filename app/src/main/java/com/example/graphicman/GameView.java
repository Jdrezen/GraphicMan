package com.example.graphicman;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private boolean running = true;
    private SensorManager sensorManager;
    private Gym gym;
    private LifeBars lifebars;

    public boolean isRunning() {
        return running;
    }


    public GameView(Context context, SensorManager sensorManager) {
        super(context);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity )context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gym = new Gym(sensorManager, displayMetrics.heightPixels, displayMetrics.widthPixels);
        lifebars = new LifeBars(context,10,10,100,displayMetrics.heightPixels, displayMetrics.widthPixels, BitmapFactory.decodeResource(getResources(), R.drawable.heart));
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            gym.draw(canvas);
            lifebars.draw(canvas);
        }
    }

}

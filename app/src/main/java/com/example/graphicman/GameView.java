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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private boolean running = true;
    private SensorManager sensorManager;
    private Gym gym;
    private Kitchen kitchen;
    private LifeBars lifebars;
    private Button button;
    private int screenHeight;
    private int screenWidth;
    private EState state;

    public boolean isRunning() {
        switch (state) {
            case GYM:
                return gym.isRunning();
            case BEDROOM:
                return true;
            case KITCHEN:
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
    }


    public GameView(Context context, SensorManager sensorManager) {
        super(context);
        state = EState.KITCHEN;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity )context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        lifebars = new LifeBars(context,100,100,10, screenHeight, screenWidth);
        gym = new Gym(sensorManager, lifebars, screenHeight, screenWidth);
        kitchen = new Kitchen(sensorManager, lifebars, screenHeight, screenWidth);
        button = new Button(screenHeight, screenWidth);

        getHolder().addCallback(this);
        thread = new GameThread(context, getHolder(), this);
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
            switch (state) {
                case GYM:
                    gym.draw(canvas);
                    break;
                case BEDROOM:
                    break;
                case KITCHEN:
                    kitchen.draw(canvas);
                    break;
            }
            lifebars.draw(canvas);
            button.draw(canvas, state);
        }
    }
    public void update() {
            switch (state) {
                case GYM:
                    break;
                case BEDROOM:
                    break;
                case KITCHEN:
                    kitchen.update();
                    break;
            }
    }

    public void onTouch(MotionEvent motionEvent) {
        state = button.buttonTouch(motionEvent, state);
    }

}

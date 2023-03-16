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
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.List;

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
    private Bedroom bedroom;
    private EState state;
    private MediaPlayer gymMusic;
    private MediaPlayer kichenMusic;
    private MediaPlayer bedroomMusic;
    private DamageHit damageHit;

    public boolean isRunning() {
        kitchen.setState(state);
        switch (state) {
            case GYM:
                return gym.isRunning();
            case BEDROOM:
                return bedroom.isRunning();
            case KITCHEN:
                return kitchen.isRunning();
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    public LifeBars getLifebars() {
        return lifebars;
    }

    public GameView(Context context, SensorManager sensorManager) {
        super(context);
        state = EState.KITCHEN;

        gymMusic = MediaPlayer.create(context,R.raw.phonk);
        kichenMusic = MediaPlayer.create(context,R.raw.kichen);
        bedroomMusic = MediaPlayer.create(context,R.raw.bedroom);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity )context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        damageHit = new DamageHit(screenWidth, screenHeight);
        lifebars = new LifeBars(context,100,100,100, screenHeight, screenWidth, damageHit);
        gym = new Gym(sensorManager, lifebars, screenHeight, screenWidth, gymMusic);
        kitchen = new Kitchen(sensorManager,state, lifebars, screenHeight, screenWidth, kichenMusic);
        bedroom = new Bedroom(sensorManager, screenHeight, screenWidth, lifebars, bedroomMusic, damageHit);
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
            canvas.drawColor(Color.parseColor("#F5F5F5"));
            switch (state) {
                case GYM:
                    gym.draw(canvas);
                    break;
                case BEDROOM:
                    bedroom.draw(canvas);
                    break;
                case KITCHEN:
                    kitchen.draw(canvas);
                    break;
            }
            lifebars.draw(canvas);
            button.draw(canvas, state);
            damageHit.draw(canvas);
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
        switch (state) {
            case GYM:
                if(kichenMusic.isPlaying()){
                    kichenMusic.pause();
                }
                if(bedroomMusic.isPlaying()){
                    bedroomMusic.pause();
                }
                break;
            case BEDROOM:
                if(gymMusic.isPlaying()){
                    gymMusic.pause();
                }
                if(kichenMusic.isPlaying()){
                    kichenMusic.pause();
                }
                break;
            case KITCHEN:
                if(gymMusic.isPlaying()){
                    gymMusic.pause();
                }
                if(bedroomMusic.isPlaying()){
                    bedroomMusic.pause();
                }
                break;
        }
    }

    public void stopMusic(){
        if(gymMusic.isPlaying()){
            gymMusic.pause();
        }
        if(kichenMusic.isPlaying()){
            kichenMusic.pause();
        }
        if(bedroomMusic.isPlaying()){
            bedroomMusic.pause();
        }
    }

}

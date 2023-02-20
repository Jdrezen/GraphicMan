package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gameView = new GameView(this, sensorManager);

        gameView.setOnDragListener(this);
        gameView.setOnTouchListener(this);


        setContentView(gameView);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gameView.onTouch(motionEvent);
        return false;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        Log.d("tad","dragact");
        switch (dragEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("TAG", "onTouch: MotionEvent.ACTION_DOWN" );
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "onTouch: MotionEvent.ACTION_UP" );
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "onTouch: MotionEvent.ACTION_MOVE" );
                break;
        }
        gameView.onDrag(dragEvent);
        return false;
    }
}
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
    private int dificulty;
    private int mouvement = 600;
    private boolean mvtDirection = true;
    private LifeBars lifeBars;
    private EBedroomState bedroomState = EBedroomState.AWAKE;
    private boolean isRunning;
    private int showzzz = 0;

    public Bedroom(SensorManager sensorManager, int height, int width, LifeBars lifeBars) {
        this.height = height;
        this.width = width;
        this.barWidth = (int) (2154 * 0.02);
        this.sensorManager = sensorManager;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.canvasWrapper = new CanvasWrapper(width, height);
        this.lifeBars = lifeBars;
        this.isRunning = true;

        dificulty = 1;
    }

    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);

        switch (bedroomState) {
            case AWAKE:
                drawMonster(false);
                drawBedBackground();
                drawBedForeground();
                drawGraphicMan();
                break;
            case HORROR:
                canvas.drawColor(Color.parseColor("#282828"));
                drawMonster(true);
                drawBedBackground();
                drawGraphicManSleep(true);
                drawBedForeground();
                break;
            case ASLEEP:
                canvas.drawColor(Color.parseColor("#696969"));
                drawMonster(false);
                drawBedBackground();
                drawGraphicManSleep(false);
                drawBedForeground();
                break;
        }
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


        computeSleepingState(selectorSize - offset, lower - offset, 2154 * 3/4 + 195 - (barHeight * lightValue / 100));
        if (lifeBars.isDead()) {
            isRunning = false;
        }
    }

    private void drawMonster(boolean isMonster) {
        Paint paint = new Paint();
        // Ombre
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(223, 1659, 223+592, 1659+191, paint);

        if (isMonster) {
            // Yeux rouge
            paint.setColor(Color.parseColor("#FF0000"));
            canvasWrapper.drawRect(320, 1778, 320+55, 1778+38, paint);
            canvasWrapper.drawRect(626, 1778, 626+55, 1778+38, paint);
            // Pupille jaune
            paint.setColor(Color.parseColor("#EBFF00"));
            canvasWrapper.drawRect(343, 1778, 343+6, 1778+38, paint);
            canvasWrapper.drawRect(651, 1778, 651+6, 1778+38, paint);
            // Bouche
            paint.setColor(Color.parseColor("#FF0101"));
            canvasWrapper.drawRect(446, 1789, 446+147, 1789+33, paint);
            // Fond bouche
            paint.setColor(Color.parseColor("#B71818"));
            canvasWrapper.drawRect(494, 1797, 494+50, 1797+19, paint);
            // Langue
            paint.setColor(Color.parseColor("#FF0101"));
            canvasWrapper.drawRect(518, 1789, 518+8, 1789+17, paint);
            // Dents
            paint.setColor(Color.parseColor("#D9D9D9"));
            canvasWrapper.drawRect(455, 1755, 455+14, 1755+67, paint);
            canvasWrapper.drawRect(472, 1789, 472+14, 1789+46, paint);
            canvasWrapper.drawRect(548, 1789, 548+14, 1789+46, paint);
            canvasWrapper.drawRect(565, 1755, 565+14, 1755+67, paint);
            // Pattes
            paint.setColor(Color.parseColor("#000000"));
            canvasWrapper.drawRect(303, 1835, 303+114, 1835+64, paint);
            canvasWrapper.drawRect(594, 1848, 594+114, 1848+62, paint);
            // Griffres
            paint.setColor(Color.parseColor("#D9D9D9"));
            canvasWrapper.drawRect(311, 1861, 311+20, 1861+68, paint);
            canvasWrapper.drawRect(350, 1861, 350+20, 1861+68, paint);
            canvasWrapper.drawRect(388, 1861, 388+20, 1861+68, paint);
            canvasWrapper.drawRect(602, 1861, 602+20, 1861+68, paint);
            canvasWrapper.drawRect(641, 1861, 641+20, 1861+68, paint);
            canvasWrapper.drawRect(679, 1861, 679+20, 1861+68, paint);
        }
    }

    private void drawGraphicMan() {
        Paint paint = new Paint();
        // Body
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(298, 739, 400+298, 400+739, paint);

        // Graphic man eye
        paint.setColor(Color.rgb(250, 250, 250));
        canvasWrapper.drawRect(448, 790, 100+448, 100+790, paint);

        // Pupil
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(488, 830, 20+488, 830+60, paint);
    }

    private void drawGraphicManSleep(boolean redEye) {
        Paint paint = new Paint();
        // Body
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(298, 739, 400+298, 400+739, paint);

        if (redEye) {
            // Graphic man red eye
            paint.setColor(Color.parseColor("#FF9696"));
            canvasWrapper.drawRect(448, 790, 100+448, 100+790, paint);
        } else {
            // Graphic man eye closed
            paint.setColor(Color.rgb(250, 250, 250));
            canvasWrapper.drawRect(449, 842, 449+100, 842+10, paint);
            // Z Z Z
            paint.setColor(Color.rgb(0, 0, 0));
            canvasWrapper.drawText("Z", 560, 700, paint, 100);
            if (showzzz > 5) {
                canvasWrapper.drawText("Z", 650, 514+117, paint, 100);
            }
            if (showzzz > 10) {
                canvasWrapper.drawText("Z", 740, 570, paint, 100);
            }
            showzzz++;
            if (showzzz > 15) {
                showzzz = 0;
            }

        }
        // Pupil
        paint.setColor(Color.rgb(0, 0, 0));
        canvasWrapper.drawRect(488, 830, 20+488, 830+60, paint);
    }

    private void drawBedForeground() {
        Paint paint = new Paint();

        // Couverture Rouge
        paint.setColor(Color.parseColor("#C53C3C"));
        canvasWrapper.drawRect(177, 1017, 177+655, 1017+623, paint);
        // Couverture Grise
        paint.setColor(Color.parseColor("#BBBBBB"));
        canvasWrapper.drawRect(151, 871, 151+704, 871+146, paint);
        // Saumier bas
        paint.setColor(Color.parseColor("#964213"));
        canvasWrapper.drawRect(144, 1560, 144+721, 1560+168, paint);
        paint.setColor(Color.parseColor("#AB6842"));
        // Pied bas + saumier
        canvasWrapper.drawRect(124, 1559, 124+758, 1559+47, paint);
        canvasWrapper.drawRect(157, 1728, 157+68, 1728+166, paint);
        canvasWrapper.drawRect(780, 1728, 780+68, 1728+166, paint);
    }

    private void drawBedBackground() {
        Paint paint = new Paint();
        // Pieds Bas
        paint.setColor(Color.parseColor("#AB6842"));
        canvasWrapper.drawRect(157, 682, 157+66, 682+135, paint);
        canvasWrapper.drawRect(786, 695, 786+69, 695+135, paint);
        // Saumier haut
        paint.setColor(Color.parseColor("#964213"));
        canvasWrapper.drawRect(141, 584, 141+720, 584+113, paint);
        // Drap
        paint.setColor(Color.parseColor("#F0F0F0"));
        canvasWrapper.drawRect(180, 682, 180+655, 682+189, paint);
        // Oreiller
        paint.setColor(Color.parseColor("#BBBBBB"));
        canvasWrapper.drawRect(228, 648, 228+270, 648+182, paint);
        canvasWrapper.drawRect(540, 641, 540+270, 641+182, paint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lightLevel = event.values[0];
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

    private void computeSleepingState(int high, int low, int light) {
        if (light < high) {
            bedroomState = EBedroomState.AWAKE;
        } else if (light > high && light < low) {
            lifeBars.addEnegy(1);
            bedroomState = EBedroomState.ASLEEP;
        } else {
            bedroomState = EBedroomState.HORROR;
        }
//        bedroomState = EBedroomState.HORROR;
    }

    public boolean isRunning() {
        return isRunning;
    }
}

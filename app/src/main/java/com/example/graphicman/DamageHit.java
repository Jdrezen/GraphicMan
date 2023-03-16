package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DamageHit {

    private int count = 0;
    private CanvasWrapper canvasWrapper;

    public DamageHit(int width, int height) {
        this.canvasWrapper = new CanvasWrapper(width, height);
    }

    public void draw(Canvas canvas) {
        canvasWrapper.setCanvas(canvas);

        if (count > 2) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setAlpha(50);
            canvasWrapper.drawRect(0, 0, 1080, 2154, p);
        } else {

        }
        count = count < 1 ? 0 : count-1;
    }

    public void damageHit() {
        if (count == 0) {
            count = 4;
        }
    }
}

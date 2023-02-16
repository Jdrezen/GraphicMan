package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CanvasWrapper {

    private Canvas canvas;
    private float widthRatio;
    private float heightRatio;
    public CanvasWrapper(int width, int height){
        this.widthRatio = width/1080f;
        this.heightRatio = height/2154f;
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }



    public void drawRect(float left, float top, float right, float bottom, Paint paint){
        canvas.drawRect(left*widthRatio,top*heightRatio,right*widthRatio,bottom*heightRatio,paint);
    }

    public void drawLine(float starX, float starY, float stopX, float stopY, Paint paint){
        paint.setStrokeWidth(50*widthRatio);
        canvas.drawLine(starX*widthRatio,starY*heightRatio,stopX*widthRatio,stopY*heightRatio,paint);
    }
}

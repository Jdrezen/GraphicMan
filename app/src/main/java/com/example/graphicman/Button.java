package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Button {
    int height, width;
    CanvasWrapper canvasWrapper;

    public Button(int height, int width) {
        this.height = height;
        this.width = width;
        this.canvasWrapper = new CanvasWrapper(width, height);
    }

    public void draw(Canvas canvas, EState state) {
        canvasWrapper.setCanvas(canvas);
        switch (state) {
            case GYM:
                drawGymButtons(canvasWrapper);
                break;
            case BEDROOM:
                drawBedroomButtons(canvasWrapper);
                break;
            case KITCHEN:
                drawKitchenButtons(canvasWrapper);
                break;
        }
    }

    private void drawKitchenButtons(CanvasWrapper canvasWrapper) {
        Paint paint = new Paint();

        // GYM
        String gymLabel = "Gym";
        paint.setColor(Color.rgb(50, 50, 50));
        canvasWrapper.drawRect(20, 20, 300, 120, paint);
        paint.setColor(Color.GRAY);
        canvasWrapper.drawText(gymLabel, 110, 85, paint, 50);

        // BEDROOM
        String bedroomLabel = "Bedroom";
        paint.setColor(Color.rgb(50, 50, 50));
        canvasWrapper.drawRect(780, 20, 1060, 120, paint);
        paint.setColor(Color.GRAY);
        canvasWrapper.drawText(bedroomLabel, 825, 85, paint, 50);
    }

    private void drawGymButtons(CanvasWrapper canvasWrapper) {
        Paint paint = new Paint();

        // BEDROOM
        String kitchenLabel = "Kitchen";
        paint.setColor(Color.rgb(50, 50, 50));
        canvasWrapper.drawRect(780, 20, 1060, 120, paint);
        paint.setColor(Color.GRAY);
        canvasWrapper.drawText(kitchenLabel, 840, 85, paint, 50);
    }

    private void drawBedroomButtons(CanvasWrapper canvasWrapper) {
        Paint paint = new Paint();

        // KITCHEN
        String kitchenLabel = "Kitchen";
        paint.setColor(Color.rgb(50, 50, 50));
        canvasWrapper.drawRect(20, 20, 300, 120, paint);
        paint.setColor(Color.GRAY);
        canvasWrapper.drawText(kitchenLabel, 75, 85, paint, 50);
    }

    public EState buttonTouch(MotionEvent event, EState state) {
        Rect kitchenBoundsLeft = getBounds(true);
        Rect kitchenBoundsRight = getBounds(false);
        Rect gymBounds = getBounds(true);
        Rect bedroomBounds = getBounds(false);

        if (state == EState.KITCHEN) {
            if (gymBounds.top < event.getY() && gymBounds.bottom > event.getY()
                    && gymBounds.left < event.getX() && gymBounds.right > event.getX()) {
                return EState.GYM;
            } else if (bedroomBounds.top < event.getY() && bedroomBounds.bottom > event.getY()
                    && bedroomBounds.left < event.getX() && bedroomBounds.right > event.getX()) {
                return EState.BEDROOM;
            }
        } else if (state == EState.BEDROOM) {
            if (kitchenBoundsLeft.top < event.getY() && kitchenBoundsLeft.bottom > event.getY()
                    && kitchenBoundsLeft.left < event.getX() && kitchenBoundsLeft.right > event.getX()) {
                return EState.KITCHEN;
            }
        } else if (state == EState.GYM) {
            if (kitchenBoundsRight.top < event.getY() && kitchenBoundsRight.bottom > event.getY()
                    && kitchenBoundsRight.left < event.getX() && kitchenBoundsRight.right > event.getX()) {
                return EState.KITCHEN;
            }
        }
        return state;
    }

    private Rect getBounds(boolean left) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        // Bouton à gauche ou à droite
        if (left) {
            return canvasWrapper.getRect(20, 20, 300, 120);
        } else {
            return canvasWrapper.getRect(780, 20, 1060, 120);
        }
    }
}
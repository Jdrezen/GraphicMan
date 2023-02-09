package com.example.graphicman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Button {
    int height, width;

    public Button(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void draw(Canvas canvas, EState state) {
        switch (state) {
            case GYM:
                drawGymButtons(canvas);
                break;
            case BEDROOM:
                drawBedroomButtons(canvas);
                break;
            case KITCHEN:
                drawKitchenButtons(canvas);
                break;
        }
    }

    private void drawKitchenButtons(Canvas canvas) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        // GYM
        String gymLabel = "Gym";
        paint.setColor(Color.rgb(50, 50, 50));
        paint.setTextSize(70);
        paint.getTextBounds(gymLabel, 0, gymLabel.length(), bounds);
        canvas.drawRect(20, 50, bounds.width() + 50, 150, paint);
        paint.setColor(Color.GRAY);
        canvas.drawText(gymLabel, 30, 120, paint);

        // BEDROOM
        String bedroomLabel = "Bedroom";
        paint.setColor(Color.rgb(50, 50, 50));
        paint.setTextSize(70);
        paint.getTextBounds(bedroomLabel, 0, bedroomLabel.length(), bounds);
        canvas.drawRect(width - bounds.width() - 40, 50, width - 10, 150, paint);
        paint.setColor(Color.GRAY);
        canvas.drawText(bedroomLabel, width - bounds.width() - 30, 120, paint);
    }

    private void drawGymButtons(Canvas canvas) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        // KITCHEN
        String kitchenLabel = "Kitchen";
        paint.setColor(Color.rgb(50, 50, 50));
        paint.setTextSize(70);
        paint.getTextBounds(kitchenLabel, 0, kitchenLabel.length(), bounds);
        canvas.drawRect(width - bounds.width() - 40, 50, width - 10, 150, paint);
        paint.setColor(Color.GRAY);
        canvas.drawText(kitchenLabel, width - bounds.width() - 30, 120, paint);
    }

    private void drawBedroomButtons(Canvas canvas) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        // KITCHEN
        String kitchenLabel = "Kitchen";
        paint.setColor(Color.rgb(50, 50, 50));
        paint.setTextSize(70);
        paint.getTextBounds(kitchenLabel, 0, kitchenLabel.length(), bounds);
        canvas.drawRect(20, 50, bounds.width() + 50, 150, paint);
        paint.setColor(Color.GRAY);
        canvas.drawText(kitchenLabel, 30, 120, paint);
    }

    public EState buttonTouch(MotionEvent event, EState state) {
        Rect kitchenBoundsLeft = getBounds("Kitchen", true);
        Rect kitchenBoundsRight = getBounds("Kitchen", false);
        Rect gymBounds = getBounds("Gym", true);
        Rect bedroomBounds = getBounds("Bedroom", false);
        Log.d("but", "event : " + event.getX() + ", " + event.getY());
        Log.d("but", "rect : " + gymBounds.top + ", " + gymBounds.left + ", " + gymBounds.bottom + ", " + gymBounds.right + ", " );

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

    private Rect getBounds(String label, boolean left) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setColor(Color.rgb(50, 50, 50));
        paint.setTextSize(70);
        paint.getTextBounds(label, 0, label.length(), bounds);

        // Bouton à gauche ou à droite
        if (left) {
            return new Rect(20, 50, bounds.width() + 50, 150);
        } else {
            return new Rect(width - bounds.width() - 40, 50, width - 10, 150);
        }
    }
}
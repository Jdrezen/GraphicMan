package com.example.graphicman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Boolean running;
    private Canvas canvas;
    private Handler h = new Handler();
    private Context context;
    private Chrono chrono;

    public GameThread(Context context, SurfaceHolder surfaceHolder, GameView gameView) {

        super();
        this.context = context;
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.chrono = new Chrono();

    }
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        chrono.start();
        h.postDelayed(runDraw , 100);
        h.postDelayed(runUpdate , 100);
    }
    private Runnable runDraw = new Runnable() {
        public void run() {
            if (gameView.isRunning()) {
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gameView.draw(canvas);
                    }
                    h.postDelayed(this, 100);
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                gameView.stopMusic();
                chrono.stop();
                Intent intent = new Intent(context, ScoreActivity.class);
                intent.putExtra("score", chrono.getTime());
                intent.putExtra("deathCause", gameView.getLifebars().getDeathCause());
                context.startActivity(intent);
            }
        }
    };

    private Runnable runUpdate = new Runnable() {
        public void run() {
            if (gameView.isRunning()) {
                Log.d("runUpdate", "threadLog");
                gameView.update();
                h.postDelayed(this, 10);
            }
        }
    };

}

package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private long score;
    private long highScore;
    private String deathCause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = getIntent().getLongExtra("score", 0L);

        //restartHighScore();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        highScore = sharedPref.getLong("highScore", score);

        TextView highScoreView = findViewById(R.id.highScore);
        highScoreView.setText(Chrono.getDureeTxt(highScore));

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(Chrono.getDureeTxt(score));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("highScore", Math.max(score, highScore));
        editor.apply();

        deathCause = String.valueOf(getIntent().getStringExtra("deathCause"));
        ImageView imageMort = findViewById(R.id.deathImage);
        TextView deathText = findViewById(R.id.deathText);

        switch (deathCause){
            case "FOOD":
                imageMort.setImageResource(R.drawable.gramaigre);
                deathText.setText("Vous êtes mort de faim");
                break;
            case "ENERGY":
                imageMort.setImageResource(R.drawable.grapleur);
                deathText.setText("Vous êtes mort de fatigue");
                break;
            case "HEALTH":
                imageMort.setImageResource(R.drawable.gragro);
                deathText.setText("Vous êtes mort d'obésité");
                break;
        }
    }

    public void recommencerPartie(View view){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void restartHighScore(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("highScore",0L);
        editor.apply();
    }

}
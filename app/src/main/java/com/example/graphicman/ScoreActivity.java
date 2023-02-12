package com.example.graphicman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private int score;
    private int highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = getIntent().getIntExtra("score",0);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        highScore = sharedPref.getInt("highScore", score);

        TextView highScoreView = findViewById(R.id.highScore);
        highScoreView.setText(highScore+"");

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText(score + "");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("highScore", score > highScore ? score : highScore);
        editor.apply();
    }
}
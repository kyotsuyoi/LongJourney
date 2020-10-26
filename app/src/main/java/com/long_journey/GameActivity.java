package com.long_journey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Intent intentMusic;
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this, point.x, point.y);

        setContentView(gameView);
        intentMusic = new Intent(this, BackgroundSoundService.class);
        isMute = getIntent().getBooleanExtra("mute",false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        stopService(intentMusic);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        if(!isMute) {
            startService(intentMusic);
        }
    }
}

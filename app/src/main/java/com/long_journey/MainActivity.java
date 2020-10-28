package com.long_journey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    private boolean isMute;
    private Intent intentMusic;
    private TextView textView;
    private ImageView imageViewVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        settings = getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);
        textView = findViewById(R.id.textView_Score);
        imageViewVolume = findViewById(R.id.button_volume);

        isMute = settings.getBoolean("mute", false);
        intentMusic = new Intent(MainActivity.this, BackgroundSoundService.class);

        if(isMute) {
            imageViewVolume.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }else {
            startService(intentMusic);
            imageViewVolume.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }

        SetButtons();
    }

    @Override
    protected void onResume() {
        if(!isMute) {
            startService(intentMusic);
        }
        int score = settings.getInt("high_score", 0);
        textView.setText("Melhor pontuação: "+score);
        super.onResume();
    }

    @Override
    protected void onPause() {
        stopService(intentMusic);
        super.onPause();
    }

    private void SetButtons(){
        imageViewVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if (isMute) {
                    imageViewVolume.setImageResource(R.drawable.ic_volume_off_black_24dp);
                    stopService(intentMusic);
                }else{
                    startService(intentMusic);
                    imageViewVolume.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("mute", isMute);
                editor.apply();
            }
        });

        findViewById(R.id.button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("mute",isMute);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_controls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ControlsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button_upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
                startActivity(intent);
            }
        });
    }

}

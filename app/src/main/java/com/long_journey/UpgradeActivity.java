package com.long_journey;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private int redOrb, blueOrb, metal, gunLevel, bullet, armor;
    private TextView textViewRedOrb, textViewBlueOrb, textViewGunLevel, textViewMetal, textViewBullet, textViewArmor;
    private SoundPool soundPoolUpgrade;
    private int upgradeSound;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        settings = getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);
        editor = settings.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPoolUpgrade = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPoolUpgrade = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        upgradeSound = soundPoolUpgrade.load(this, R.raw.heart, 1);

        blueOrb = settings.getInt("blue_orb",0);
        redOrb = settings.getInt("red_orb",0);
        metal = settings.getInt("metal",0);
        gunLevel = settings.getInt("gun_level",1);
        bullet = settings.getInt("bullet",3);
        armor = settings.getInt("armor",1);

        textViewBlueOrb = findViewById(R.id.textView_BlueOrb);
        textViewRedOrb = findViewById(R.id.textView_RedOrb);
        textViewMetal = findViewById(R.id.textView_Metal);
        textViewGunLevel = findViewById(R.id.textView_GunLevel);
        textViewBullet = findViewById(R.id.textView_Bullet);
        textViewArmor = findViewById(R.id.textView_Armor);

        textViewBlueOrb.setText(String.valueOf(blueOrb));
        textViewRedOrb.setText(String.valueOf(redOrb));
        textViewMetal.setText(String.valueOf(metal));
        textViewGunLevel.setText(String.valueOf(gunLevel));
        textViewBullet.setText(String.valueOf(bullet));
        textViewArmor.setText(String.valueOf(armor));

        SetButtons();
    }

    private void SetButtons(){
        findViewById(R.id.button_GunLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blueOrb<10){
                    Toast.makeText(getApplicationContext(),"Não tem Orb Azul suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(metal<1){
                    Toast.makeText(getApplicationContext(),"Não tem metal rochoso suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(gunLevel>=3){
                    Toast.makeText(getApplicationContext(),"Arma no máximo",Toast.LENGTH_SHORT).show();
                    return;
                }
                blueOrb-=10;
                metal--;
                gunLevel++;
                textViewGunLevel.setText(String.valueOf(gunLevel));
                textViewMetal.setText(String.valueOf(metal));
                textViewBlueOrb.setText(String.valueOf(blueOrb));

                editor.putInt("blue_orb", blueOrb);
                editor.putInt("metal", metal);
                editor.putInt("gun_level", gunLevel);
                editor.apply();
                if (!settings.getBoolean("mute", false)) {
                    soundPoolUpgrade.play(upgradeSound, 1, 1, 0, 0, 1);
                }
            }
        });

        findViewById(R.id.button_Bullet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blueOrb<8){
                    Toast.makeText(getApplicationContext(),"Não tem Orb Azul suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(bullet>=199){
                    Toast.makeText(getApplicationContext(),"Munição está no máximo",Toast.LENGTH_SHORT).show();
                    return;
                }
                blueOrb-=8;
                bullet++;
                textViewBullet.setText(String.valueOf(bullet));
                textViewBlueOrb.setText(String.valueOf(blueOrb));

                editor.putInt("blue_orb", blueOrb);
                editor.putInt("bullet", bullet);
                editor.apply();
                if (!settings.getBoolean("mute", false)) {
                    soundPoolUpgrade.play(upgradeSound, 1, 1, 0, 0, 1);
                }
            }
        });

        findViewById(R.id.button_Armor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(redOrb<10){
                    Toast.makeText(getApplicationContext(),"Não tem Orb Vermelho suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(armor>=99){
                    Toast.makeText(getApplicationContext(),"Armadura está no máximo",Toast.LENGTH_SHORT).show();
                    return;
                }
                redOrb-=10;
                armor++;
                textViewRedOrb.setText(String.valueOf(redOrb));
                textViewArmor.setText(String.valueOf(armor));

                editor.putInt("red_orb", redOrb);
                editor.putInt("armor", armor);
                editor.apply();
                if (!settings.getBoolean("mute", false)) {
                    soundPoolUpgrade.play(upgradeSound, 1, 1, 0, 0, 1);
                }
            }
        });

        findViewById(R.id.button_Return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


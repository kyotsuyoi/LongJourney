package com.long_journey;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences.Editor editor;
    private int orb, metal, gunLevel, bullet;
    private TextView textViewOrb, textViewGunLevel, textViewMetal, textViewBullet;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        SharedPreferences settings = getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);
        editor = settings.edit();

        orb = settings.getInt("blue_orb",0);
        metal = settings.getInt("metal",0);
        gunLevel = settings.getInt("gunLevel",1);
        bullet = settings.getInt("bullet",3);

        textViewOrb = findViewById(R.id.textView_Orb);
        textViewMetal = findViewById(R.id.textView_Metal);
        textViewGunLevel = findViewById(R.id.textView_GunLevel);
        textViewBullet = findViewById(R.id.textView_Bullet);

        textViewOrb.setText(String.valueOf(orb));
        textViewMetal.setText(String.valueOf(metal));
        textViewGunLevel.setText(String.valueOf(gunLevel));
        textViewBullet.setText(String.valueOf(bullet));

        SetButtons();
    }

    private void SetButtons(){
        findViewById(R.id.button_GunLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orb<10){
                    Toast.makeText(getApplicationContext(),"Não tem blue_orb suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(metal<1){
                    Toast.makeText(getApplicationContext(),"Não tem metal rochoso suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                orb-=10;
                metal--;
                gunLevel++;
                textViewGunLevel.setText(String.valueOf(gunLevel));
                textViewMetal.setText(String.valueOf(metal));
                textViewOrb.setText(String.valueOf(orb));

                editor.putInt("blue_orb", orb);
                editor.putInt("metal", metal);
                editor.putInt("gunLevel", gunLevel);
                editor.apply();
            }
        });

        findViewById(R.id.button_Bullet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orb<8){
                    Toast.makeText(getApplicationContext(),"Não tem blue_orb suficiente",Toast.LENGTH_SHORT).show();
                    return;
                }
                orb-=8;
                bullet++;
                textViewBullet.setText(String.valueOf(bullet));
                textViewOrb.setText(String.valueOf(orb));

                editor.putInt("blue_orb", orb);
                editor.putInt("bullet", bullet);
                editor.apply();
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


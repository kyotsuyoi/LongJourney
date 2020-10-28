package com.long_journey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    private int orb, metal, gunLevel, bullet;
    private TextView textViewOrb, textViewGunLevel, textViewMetal, textViewBullet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        settings = getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);

        orb = settings.getInt("orb",0);
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
                    Toast.makeText(getApplicationContext(),"Não tem orb suficiente",Toast.LENGTH_LONG);
                    return;
                }
                if(metal<1){
                    Toast.makeText(getApplicationContext(),"Não tem metal rochoso suficiente",Toast.LENGTH_LONG);
                    return;
                }
                orb-=10;
                metal--;
                gunLevel++;
                textViewGunLevel.setText(String.valueOf(gunLevel));
            }
        });

        findViewById(R.id.button_Bullet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orb<8){
                    Toast.makeText(getApplicationContext(),"Não tem orb suficiente",Toast.LENGTH_LONG);
                    return;
                }
                orb-=8;
                bullet++;
                textViewBullet.setText(String.valueOf(bullet));
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


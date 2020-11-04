package com.long_journey;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class GameView extends SurfaceView implements Runnable {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint, paintText, paintAstronaut;
    private Astronaut astronaut;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private List<Bullet> bullets;
    private SoundPool soundPoolShot, soundPoolHit,soundPoolCharge, soundPoolHeart, soundPoolPierceShot, soundPoolTripleShot, soundPoolSOS;
    private int shootSound, hitSound, chargeSound, heartSound, pierceShotSound, tripleShotSound, SOS_Sound;
    private GameActivity activity;
    private Background background1, background2;
    private HealthPoint displayHP;
    private boolean isMoreAsteroid = false;
    private int MoreAsteroidCounter = 0;
    private int MoreAsteroidCounterLimit = 19;
    private int HoldTime = 0;
    private boolean isHolding = false;
    private int WaveLevel=1;
    private int backgroundSpeed = 2;
    private int WaitingTime = 200;
    private boolean isWaiting = true;
    private int Wave = 1;
    private int SpeedBound=20;
    private AsteroidSizes asteroidSizes;

    private int blueOrb, redOrb, metal;

    float[] x = new float[10]; //Position on the x axis of each finger
    float[] y = new float[10]; //Position on the y axis of each finger
    boolean[] isTouch = new boolean[10]; //When a finger is touching or not

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        try {
            this.activity = activity;
            settings = activity.getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build();

                soundPoolShot = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolHit = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolCharge = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolHeart = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolPierceShot = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolTripleShot = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
                soundPoolSOS = new SoundPool.Builder()
                        .setAudioAttributes(audioAttributes)
                        .build();
            }else{
                soundPoolShot = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolHit = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolCharge = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolHeart = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolPierceShot = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolTripleShot = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolSOS = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            }

            shootSound = soundPoolShot.load(activity, R.raw.shot, 1);
            hitSound = soundPoolHit.load(activity, R.raw.hit, 1);
            chargeSound = soundPoolCharge.load(activity, R.raw.charge, 1);
            heartSound = soundPoolHeart.load(activity, R.raw.heart, 1);
            pierceShotSound = soundPoolPierceShot.load(activity, R.raw.pierce_shot, 1);
            tripleShotSound = soundPoolTripleShot.load(activity, R.raw.triple_shot, 1);
            SOS_Sound = soundPoolSOS.load(activity, R.raw.sos, 1);

            this.screenX = screenX;
            this.screenY = screenY;
            //screenRatioX = 1920f / screenX;
            //screenRatioY = 1080f / screenY;
            screenRatioX = screenX/(screenX*1.65f);
            screenRatioY = screenY/(screenY*1.65f);
            //screenRatioX = 0.6f;
            //screenRatioY = 0.6f;

            backgroundSpeed = (int)(10*screenRatioX);

            if(backgroundSpeed<1){
                backgroundSpeed=1;
            }

            background1 = new Background(screenX, screenY, getResources());
            background2 = new Background(screenX, screenY, getResources());
            displayHP = new HealthPoint(40, 40, getResources());

            astronaut = new Astronaut(this, screenY, getResources());

            bullets = new ArrayList<>();

            background2.x = screenX;

            paint = new Paint();
            paint.setTextSize(screenX/50f);
            paint.setColor(Color.WHITE);

            paintText = new Paint();
            paintText.setTextSize((screenX/100f)*6);
            paintText.setColor(Color.WHITE);

            paintAstronaut = new Paint();

            //random = new Random();
            if (!settings.getBoolean("mute", false)) {
                soundPoolSOS.play(SOS_Sound, 1, 1, 0, 0, 1);
            }

            astronaut.bulletLimiter = settings.getInt("bullet",3);
            astronaut.GunLevel = settings.getInt("gun_level",1);
            astronaut.HP = settings.getInt("armor",1);
            blueOrb = settings.getInt("blue_orb",0);
            redOrb = settings.getInt("red_orb",0);
            metal = settings.getInt("metal",0);

            asteroidSizes = new AsteroidSizes(getResources());
        }catch (Exception ignored){}
    }

    @Override
    public void run(){
        while(isPlaying){
            Update();
            Draw();
            Sleep();
        }
    }

    private void Update(){
        CheckWave();
        BackgroundMovement();
        AstronautMovement();
        BulletIntersects();
        if (astronaut.getEtherealTime()==0){
            astronaut.isEthereal=false;
        }else{
            astronaut.isEthereal=true;
        }

        if(isMoreAsteroid&&!isWaiting){
            WaveLevel++;
            MoreAsteroid();
            if(WaveLevel>8){
                SpeedBound=40;
            }else if(WaveLevel >= 5){
                SpeedBound=30;
            }else{
                SpeedBound=20;
            }
        }

        if(WaveLevel > 10){
            isWaiting=true;
            WaitingTime=200;
            if(asteroids.size()==0) {
                WaveLevel = 1;
                Wave++;
                if (!settings.getBoolean("mute", false)) {
                    soundPoolSOS.play(SOS_Sound, 1, 1, 0, 0, 1);
                }
            }
        }

        AsteroidIntersects();
    }

    private void Draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background2.getBackground(), background2.x, background2.y, paint);
            canvas.drawBitmap(background1.getBackground(), background1.x, background1.y, paint);

            for (Asteroid asteroid : asteroids) {
                canvas.drawBitmap(asteroid.getAsteroid(), asteroid.x, asteroid.y, paint);
            }

            canvas.drawText(("Pontos: "+score), screenX/2f, screenY/20f, paint);
            canvas.drawText("Bullets: "+bullets.size()+"/"+ astronaut.bulletLimiter, screenX/3f, screenY/20f, paint);
            canvas.drawText("AQ: "+asteroids.size(), screenX/4f*3, screenY/20f, paint);
            canvas.drawText("WL: "+ WaveLevel, screenX/4f*3, screenY/20f+20, paint);

            canvas.drawBitmap(displayHP.Heart, screenX - ((screenX/100f)*4), screenY/100f, paint);
            canvas.drawText(astronaut.HP+"/"+settings.getInt("armor",1), screenX - ((screenX/100f)*10), screenY/20f, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(astronaut.getDead(), astronaut.x, astronaut.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                waitBeforeExiting();
                return;
            }
            paintText.setAlpha(100);

            if(astronaut.isEthereal){
                paintAstronaut.setAlpha(80);
            }else{
                paintAstronaut.setAlpha(200);
            }
            canvas.drawBitmap(astronaut.getFlight(), astronaut.x, astronaut.y, paintAstronaut);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.getBullet(), bullet.x, bullet.y, paint);

            if(isWaiting && asteroids.size()==0){
                if(Wave==1) {
                    canvas.drawText("PRIMEIRA ONDA", 20f,  screenY / 2f, paintText);
                }else if(Wave==2){
                    canvas.drawText("SEGUNDA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==3){
                    canvas.drawText("TERCEIRA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==4){
                    canvas.drawText("QUARTA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==5){
                    canvas.drawText("QUINTA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==6){
                    canvas.drawText("SEXTA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==7){
                    canvas.drawText("SETIMA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==8){
                    canvas.drawText("OITAVA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==9){
                    canvas.drawText("NONA ONDA", 20f, screenY / 2f, paintText);
                }else if(Wave==10){
                    canvas.drawText("ONDA FINAL", 20f, screenY / 2f, paintText);
                }else if(Wave==11){
                    canvas.drawText("DESTRUA O ULTRA ASTEROID", 20f, screenY / 2f, paintText);
                }else{
                    canvas.drawText("MISSÂO CONCLUÍDA", 20f, screenY / 2f, paintText);
                }
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExiting(){
        try {
            setHighScore();
            Thread.sleep(3000);
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setHighScore(){
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getInt("high_score", 0) < score) {
            editor.putInt("high_score", score);
        }
        editor.putInt("blue_orb", blueOrb);
        editor.putInt("red_orb", redOrb);
        editor.putInt("metal", metal);
        editor.apply();
    }

    private void Sleep(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);

        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                isTouch[pointerId] = true;
                x[pointerId] = (int)event.getX(pointerIndex);
                y[pointerId] = (int)event.getY(pointerIndex);

                if (x[pointerId] < screenX / 2f && y[pointerId] < screenY / 2f) {
                    astronaut.isGoingUp = true;
                    astronaut.isGoingDown = false;
                }else if (x[pointerId] < screenX / 2f && y[pointerId] > screenY / 2f) {
                    astronaut.isGoingDown = true;
                    astronaut.isGoingUp = false;
                }else if(x[pointerId] > screenX/2f){
                    isHolding = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouch[pointerId] = false;
                x[pointerId] = (int)event.getX(pointerIndex);
                y[pointerId] = (int)event.getY(pointerIndex);

                if (x[pointerId] < screenX / 2f && y[pointerId] < screenY / 2f) {
                    astronaut.isGoingUp = false;
                }else if (x[pointerId] < screenX / 2f && y[pointerId] > screenY / 2f) {
                    astronaut.isGoingDown = false;
                }

                if(x[pointerId] > screenX/2f && y[pointerId] < screenY/2f){
                    if(HoldTime > 10) {
                        astronaut.isCharge = true;
                        astronaut.chargedShootType = 1;
                        astronaut.toShoot++;
                    }else{
                        if(bullets.size() < astronaut.bulletLimiter-1 && astronaut.toShoot < astronaut.bulletLimiter){
                            astronaut.toShoot++;
                        }
                    }
                    isHolding = false;
                }
                if(x[pointerId] > screenX/2f && y[pointerId] > screenY/2f){
                    if(HoldTime > 10) {
                        astronaut.isCharge = true;
                        astronaut.chargedShootType = 0;
                        astronaut.toShoot++;
                    }else{
                        if(bullets.size() < astronaut.bulletLimiter-1 && astronaut.toShoot < astronaut.bulletLimiter){
                            astronaut.toShoot++;
                        }
                    }
                    isHolding = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*int pointerCount = event.getPointerCount();
                for (int i = 0; i<pointerCount;i++){
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
                    x[pointerId] = (int)event.getX(pointerIndex);
                    y[pointerId] = (int)event.getY(pointerIndex);
                }*/
                break;
        }
        return true;
    }

    public void newBullet() {
        if (!settings.getBoolean("mute", false)) {
            soundPoolShot.play(shootSound, 0.6f, 0.6f, 0, 0, 1);
        }

        if(astronaut.GunLevel==1) {
            Bullet bullet = new Bullet(getResources());
            bullet.x = astronaut.x + astronaut.width;
            bullet.y = astronaut.y + (astronaut.height / 3);
            bullets.add(bullet);
        }else if(astronaut.GunLevel==2){
            Bullet bullet1 = new Bullet(getResources());
            bullet1.x = astronaut.x + astronaut.width;
            bullet1.y = astronaut.y + ((astronaut.height / 3)/2);
            bullets.add(bullet1);

            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + astronaut.width;
            bullet2.y = astronaut.y + (((astronaut.height / 3)+((astronaut.height / 3)/2)));
            bullets.add(bullet2);
        }else if(astronaut.GunLevel==3){
            Bullet bullet1 = new Bullet(getResources());
            bullet1.x = astronaut.x + astronaut.width;
            bullet1.y = astronaut.y + (astronaut.height / 3)/2;
            bullets.add(bullet1);

            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + astronaut.width;
            bullet2.y = astronaut.y + (astronaut.height / 3);
            bullets.add(bullet2);

            Bullet bullet3 = new Bullet(getResources());
            bullet3.x = astronaut.x + astronaut.width;
            bullet3.y = astronaut.y + (((astronaut.height / 3)+((astronaut.height / 3)/2)));
            bullets.add(bullet3);
        }else if(astronaut.GunLevel==4){
            Bullet bullet1 = new Bullet(getResources());
            bullet1.x = astronaut.x + astronaut.width;
            bullet1.y = astronaut.y + (astronaut.height / 3)/4;
            bullets.add(bullet1);

            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + astronaut.width;
            bullet2.y = astronaut.y + (astronaut.height / 3);
            bullets.add(bullet2);

            Bullet bullet3 = new Bullet(getResources());
            bullet3.x = astronaut.x + astronaut.width;
            bullet3.y = astronaut.y + (((astronaut.height / 3)+((astronaut.height / 2)/2)));
            bullets.add(bullet3);

            Bullet bullet4 = new Bullet(getResources());
            bullet4.x = astronaut.x + astronaut.width + (astronaut.width/2);
            bullet4.y = astronaut.y + (astronaut.height / 3);
            bullets.add(bullet4);
        }else if(astronaut.GunLevel==5){
            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + (astronaut.width+astronaut.width/8);
            bullet2.y = astronaut.y;
            bullets.add(bullet2);

            Bullet bullet3 = new Bullet(getResources());
            bullet3.x = astronaut.x + (astronaut.width+astronaut.width/4);
            bullet3.y = astronaut.y + (astronaut.height / 3)/2;
            bullets.add(bullet3);

            Bullet bullet4 = new Bullet(getResources());
            bullet4.x = astronaut.x + (astronaut.width+astronaut.width/3);
            bullet4.y = astronaut.y + (astronaut.height / 3);
            bullets.add(bullet4);

            Bullet bullet5 = new Bullet(getResources());
            bullet5.x = astronaut.x + (astronaut.width+astronaut.width/4);
            bullet5.y = astronaut.y + (((astronaut.height / 3)+((astronaut.height / 3)/2)));
            bullets.add(bullet5);

            Bullet bullet6 = new Bullet(getResources());
            bullet6.x = astronaut.x + (astronaut.width+astronaut.width/8);
            bullet6.y = astronaut.y + ((astronaut.height / 3)*2);
            bullets.add(bullet6);
        }
    }

    public void newShotGunBullet() {
        if (!settings.getBoolean("mute", false)) {
            soundPoolTripleShot.play(tripleShotSound, 1, 1, 0, 0, 1);
        }
        if(astronaut.GunLevel==1) {
            setNewShotGunBullet(0);
        }else if(astronaut.GunLevel==2){
            setNewShotGunBullet(0);
            setNewShotGunBullet(astronaut.width/4);
        }else if(astronaut.GunLevel==3){
            setNewShotGunBullet(0);
            setNewShotGunBullet(astronaut.width/4);
            setNewShotGunBullet((astronaut.width/4)*2);
        }else if(astronaut.GunLevel==4){
            setNewShotGunBullet(0);
            setNewShotGunBullet(astronaut.width/4);
            setNewShotGunBullet((astronaut.width/4)*2);
            setNewShotGunBullet((astronaut.width/4)*3);
        }else if(astronaut.GunLevel==5){
            setNewShotGunBullet(0);
            setNewShotGunBullet(astronaut.width/4);
            setNewShotGunBullet((astronaut.width/4)*2);
            setNewShotGunBullet((astronaut.width/4)*3);
            setNewShotGunBullet(astronaut.width);
        }
    }

    private void setNewShotGunBullet(int x){

        Bullet bullet8 = new Bullet(getResources());
        bullet8.x = astronaut.x + astronaut.width+x;
        bullet8.y = astronaut.y - (astronaut.height/3);
        bullets.add(bullet8);

        Bullet bullet1 = new Bullet(getResources());
        bullet1.x = astronaut.x +(astronaut.width+astronaut.width/16)+x;
        bullet1.y = astronaut.y - (astronaut.height / 3)/2;
        bullets.add(bullet1);

        Bullet bullet2 = new Bullet(getResources());
        bullet2.x = astronaut.x + (astronaut.width+astronaut.width/8)+x;
        bullet2.y = astronaut.y;
        bullets.add(bullet2);

        Bullet bullet3 = new Bullet(getResources());
        bullet3.x = astronaut.x + (astronaut.width+astronaut.width/4)+x;
        bullet3.y = astronaut.y + (astronaut.height / 3)/2;
        bullets.add(bullet3);

        Bullet bullet4 = new Bullet(getResources());
        bullet4.x = astronaut.x + (astronaut.width+astronaut.width/3)+x;
        bullet4.y = astronaut.y + (astronaut.height / 3);
        bullets.add(bullet4);

        Bullet bullet5 = new Bullet(getResources());
        bullet5.x = astronaut.x + (astronaut.width+astronaut.width/4)+x;
        bullet5.y = astronaut.y + (((astronaut.height / 3)+((astronaut.height / 3)/2)));
        bullets.add(bullet5);

        Bullet bullet6 = new Bullet(getResources());
        bullet6.x = astronaut.x + (astronaut.width+astronaut.width/8)+x;
        bullet6.y = astronaut.y + ((astronaut.height / 3)*2);
        bullets.add(bullet6);

        Bullet bullet7 = new Bullet(getResources());
        bullet7.x = astronaut.x + (astronaut.width+astronaut.width/16)+x;
        bullet7.y = astronaut.y + ((astronaut.height / 3)*2)+((astronaut.height / 3)/2);
        bullets.add(bullet7);

        Bullet bullet9 = new Bullet(getResources());
        bullet9.x = astronaut.x + astronaut.width+x;
        bullet9.y = astronaut.y + ((astronaut.height / 3)*2)+((astronaut.height / 3)/2)+((astronaut.height / 3)/2);
        bullets.add(bullet9);
    }

    public void newPierceBullet() {
        if (!settings.getBoolean("mute", false)) {
            soundPoolPierceShot.play(pierceShotSound, 1, 1, 0, 0, 1);
        }

        int count=0;
        if(astronaut.GunLevel==1) {
            int sum=10;
            setNewPierceBullet(count);
            for (int i = 0; i < 4; i++) {
                setNewPierceBullet(count+=sum);
            }
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel==2){
            int sum=6;
            setNewPierceBullet(count);
            for (int i = 0; i < 10; i++) {
                setNewPierceBullet(count+=sum);
            }
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel==3){
            int sum=4;
            setNewPierceBullet(count);
            for (int i = 0; i < 22; i++) {
                setNewPierceBullet(count+=sum);
            }
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel==4){
            int sum=4;
            setNewPierceBullet(count);
            for (int i = 0; i < 46; i++) {
                setNewPierceBullet(count+=sum);
            }
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel==5){
            int sum=4;
            setNewPierceBullet(count);
            for (int i = 0; i < 94; i++) {
                setNewPierceBullet(count+=sum);
            }
            setNewPierceBullet(count +sum);
        }
    }

    private void setNewPierceBullet(int x){
        Bullet bullet = new Bullet(getResources());
        bullet.x = astronaut.x + astronaut.width + x;
        bullet.y = astronaut.y + (astronaut.height / 3);
        bullets.add(bullet);
    }

    private void MoreAsteroid(){
        Asteroid new_asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
        asteroids.add(new_asteroid);
        isMoreAsteroid=false;
    }

    private void AsteroidsLevel(int Level){
        asteroidSizes.setArrayAsteroidTypeLevel(Level);
    }

    private void CheckWave(){
        if(!isWaiting&&asteroids.size()==0) {
            if(Wave==1){
                for (int i = 0; i < 5; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=19;
            }else if(Wave==2){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 6; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=19;
            }else if(Wave==3){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 7; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=19;
            }else if(Wave==4){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 8; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=24;
            }else if(Wave==5){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 9; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=25;
            }else if(Wave==6) {
                AsteroidsLevel(Wave);
                for (int i = 0; i < 10; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter = 30;
            }else if(Wave==7){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 10; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=30;
            }else if(Wave==8){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 12; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=30;
            }else if(Wave==9){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 13; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=30;
            }else if(Wave==10){
                AsteroidsLevel(Wave);
                for (int i = 0; i < 15; i++) {
                    Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                    asteroid.Reset(SpeedBound,screenX,screenY);
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=30;
            }else if(Wave==11){
                WaveLevel=11;
                AsteroidsLevel(Wave);
                Asteroid asteroid = new Asteroid(getResources(), asteroidSizes,getContext());
                asteroid.Reset(SpeedBound,screenX,screenY);
                asteroid.speed=1;
                asteroid.x=screenX;
                asteroid.y=screenY/2 - asteroid.height/2;
                asteroids.add(asteroid);
                backgroundSpeed=1;
            }else{
                isGameOver=true;
            }
        }else{
            WaitingTime--;
            if(WaitingTime<1){
                isWaiting=false;
            }
        }
    }

    private void BulletIntersects(){
        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50 * screenRatioX;

            for (Asteroid asteroid : asteroids) {
                if (Rect.intersects(asteroid.getCollisionShape(),bullet.getCollisionShape()) && !asteroid.isReward()){
                    asteroid.HP--;
                    score++;
                    bullet.x = screenX + 500;
                    if(asteroid.HP < 1){
                        /*if (!settings.getBoolean("mute", false)) {
                            soundPoolExplosion.play(explosionSound, 1, 1, 0, 0, 1);
                        }*/
                        asteroid.getExplosionSound(screenX);
                        if(MoreAsteroidCounter >= MoreAsteroidCounterLimit){//Set more asteroids
                            MoreAsteroidCounter = 0;
                            isMoreAsteroid = true;
                        }else{
                            MoreAsteroidCounter++;
                        }

                        if(asteroid.Size == 0.6){
                            asteroid.setRandomReward();
                        }
                        if(asteroid.Size == 0.4 && asteroid.getAsteroidType() == 3){
                            asteroid.setRandomReward();
                        }

                        if (!asteroid.isReward()){
                            asteroid.x = -500;
                            asteroid.wasShot = true;
                        }else{
                            asteroid.speed = (int) (10 * screenRatioX);
                        }
                    }
                }
            }
        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);
    }

    private void AsteroidIntersects(){
        List<Asteroid> trash = new ArrayList<>();

        for (Asteroid asteroid : asteroids) {
            asteroid.x -= asteroid.speed;

            if (asteroid.x + asteroid.width < 0) {
                if (!asteroid.wasShot && !asteroid.isReward()) {
                    if(asteroid.getAsteroidType()==4){
                        isGameOver=true;
                        return;
                    }
                    score--;
                }

                if(!isWaiting) {
                    /*int bound = (int) (SpeedBound * screenRatioX);
                    asteroid.speed = random.nextInt(bound);

                    if (asteroid.speed < 10 * screenRatioX)
                        asteroid.speed = (int) (10 * screenRatioX);

                    asteroid.x = screenX;
                    asteroid.y = random.nextInt(screenY - asteroid.height);*/
                    asteroid.Reset(SpeedBound,screenX,screenY);
                }
            }

            if (Rect.intersects(asteroid.getCollisionShape(), astronaut.getCollisionShape())) {
                if (asteroid.isReward()){
                    asteroid.x = -500;
                    asteroid.wasShot = true;
                    if(asteroid.getRewardType()==1) {
                        redOrb++;
                        score+=10;
                    }else if(asteroid.getRewardType()==2){
                        metal++;
                        score+=5;
                    }else if(asteroid.getRewardType()==3){
                        blueOrb++;
                        score+=2;
                    }
                    if (!settings.getBoolean("mute", false)) {
                        soundPoolHeart.play(heartSound, 1, 1, 0, 0, 1);
                    }
                }else{
                    if(!astronaut.isEthereal) {
                        astronaut.setDamage(asteroid.getAsteroidType(), asteroid.Size);
                        astronaut.setEtherealTime(30);
                        if (!settings.getBoolean("mute", false)) {
                            soundPoolHit.play(hitSound, 1, 1, 0, 0, 1);
                        }
                    }
                    if(astronaut.HP > 0){
                    /*if(asteroid.getAsteroidType()==4){
                        isGameOver=true;
                        return;
                    }*/
                        //asteroid.x += astronaut.x+ astronaut.width+(astronaut.width/2);
                        //asteroid.speed/=2;
                    }else{
                        isGameOver = true;
                        return;
                    }
                }
            }

            if(isWaiting && asteroid.x + asteroid.width < 0){
                trash.add(asteroid);
            }
        }

        for(Asteroid asteroid : trash){
            asteroids.remove(asteroid);
        }
    }

    private void BackgroundMovement(){
        //background1.x -= 10 * screenRatioX;
        //background2.x -= 10 * screenRatioX;

        background1.x -= backgroundSpeed;
        background2.x -= backgroundSpeed;

        if (background1.x + background1.getBackground().getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.getBackground().getWidth() < 0) {
            background2.x = screenX;
        }
    }

    private void AstronautMovement(){
        if (astronaut.isGoingUp)
            astronaut.y -= 20 * screenRatioY;
        if (astronaut.isGoingDown)
            astronaut.y += 20 * screenRatioY;

        if (astronaut.y < 0)
            astronaut.y = 0;

        if (astronaut.y >= screenY - astronaut.height)
            astronaut.y = screenY - astronaut.height;

        if(isHolding){
            if(HoldTime==10){
                if (!settings.getBoolean("mute", false)) {
                    soundPoolCharge.play(chargeSound, 1, 1, 0, 0, 1);
                }
            }
            HoldTime++;
        }else{
            HoldTime=0;
        }
    }

}
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

public class GameView extends SurfaceView implements Runnable {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint, paintText;
    private Astronaut astronaut;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private List<Bullet> bullets;
    private Random random;
    private SoundPool soundPoolShot, soundPoolExplosion,soundPoolCharge, soundPoolHeart, soundPoolPierceShot, soundPoolTripleShot, soundPoolSOS;
    private int shootSound, explosionSound, chargeSound, heartSound, pierceShotSound, tripleShotSound, SOS_Sound;
    private GameActivity activity;
    private Background background1, background2;
    private HealthPoint displayHP;
    private boolean isMoreAsteroid = false;
    private int MoreAsteroidCounter = 0;
    private int MoreAsteroidCounterLimit = 3;
    private int HoldTime = 0;
    private boolean isHolding = false;
    private int WaveLevel=1;
    private int backgroundSpeed = 2;
    private int WaitingTime = 200;
    private boolean isWaiting = true;
    private int Wave = 1;
    private int SpeedBound=20;

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
                soundPoolExplosion = new SoundPool.Builder()
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
                soundPoolExplosion = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolCharge = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolHeart = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolPierceShot = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolTripleShot = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
                soundPoolSOS = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            }

            shootSound = soundPoolShot.load(activity, R.raw.shot, 1);
            explosionSound = soundPoolExplosion.load(activity, R.raw.explosion, 1);
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
            paint.setTextSize(screenX/50);
            paint.setColor(Color.WHITE);

            paintText = new Paint();
            paintText.setTextSize(60);
            paintText.setAlpha(20);
            paintText.setColor(Color.WHITE);

            random = new Random();
            if (!settings.getBoolean("mute", false)) {
                soundPoolSOS.play(SOS_Sound, 1, 1, 0, 0, 1);
            }
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

        if(isMoreAsteroid&&!isWaiting){
            WaveLevel++;
            MoreAsteroid();
            if(WaveLevel>8){
                SpeedBound=30;
                MoreAsteroid();
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
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Asteroid asteroid : asteroids) {
                canvas.drawBitmap(asteroid.getAsteroid(), asteroid.x, asteroid.y, paint);
            }

            canvas.drawText(String.valueOf(score), screenX/2f, screenY/20f, paint);
            canvas.drawText("Bullets: "+bullets.size()+"/"+ astronaut.bulletLimiter, screenX/3f, screenY/20f, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(astronaut.getDead(), astronaut.x, astronaut.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                setHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(astronaut.getFlight(), astronaut.x, astronaut.y, paint);

            switch (astronaut.HP){
                case 1:
                    canvas.drawBitmap(displayHP.Heart, screenX - 50, 10, paint);
                    break;
                case 2:
                    canvas.drawBitmap(displayHP.Heart, screenX - 50, 10, paint);
                    canvas.drawBitmap(displayHP.Heart, screenX - 100, 10, paint);
                    break;
                case 3:
                    canvas.drawBitmap(displayHP.Heart, screenX - 50, 10, paint);
                    canvas.drawBitmap(displayHP.Heart, screenX - 100, 10, paint);
                    canvas.drawBitmap(displayHP.Heart, screenX - 150, 10, paint);
                    break;
            }

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
                    canvas.drawText("ONDA FINAL", 20f, screenY / 2f, paintText);
                }else if(Wave>6){
                    canvas.drawText("CONCLUÍDO", 20f, screenY / 2f, paintText);
                }
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExiting(){
        try {
            Thread.sleep(3000);
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setHighScore(){
        if (settings.getInt("high_score", 0) < score) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("high_score", score);
            editor.apply();
        }
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

                if (x[pointerId] < screenX / 2 && y[pointerId] < screenY / 2) {
                    astronaut.isGoingUp = true;
                    astronaut.isGoingDown = false;
                }else if (x[pointerId] < screenX / 2 && y[pointerId] > screenY / 2) {
                    astronaut.isGoingDown = true;
                    astronaut.isGoingUp = false;
                }else if(x[pointerId] > screenX/2){
                    isHolding = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouch[pointerId] = false;
                x[pointerId] = (int)event.getX(pointerIndex);
                y[pointerId] = (int)event.getY(pointerIndex);

                if (x[pointerId] < screenX / 2 && y[pointerId] < screenY / 2) {
                    astronaut.isGoingUp = false;
                }else if (x[pointerId] < screenX / 2 && y[pointerId] > screenY / 2) {
                    astronaut.isGoingDown = false;
                }

                if(x[pointerId] > screenX/2 && y[pointerId] < screenY/2){
                    if(HoldTime > 10) {
                        astronaut.isCharge = true;
                        astronaut.chargedShootType = 1;
                        astronaut.toShoot++;
                    }else{
                        if(bullets.size() < astronaut.bulletLimiter){
                            astronaut.toShoot++;
                        }
                    }
                    isHolding = false;
                }
                if(x[pointerId] > screenX/2 && y[pointerId] > screenY/2){
                    if(HoldTime > 10) {
                        astronaut.isCharge = true;
                        astronaut.chargedShootType = 0;
                        astronaut.toShoot++;
                    }else{
                        if(bullets.size() < astronaut.bulletLimiter){
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
            bullet.y = astronaut.y + (astronaut.height / 4);
            bullets.add(bullet);
        }else if(astronaut.GunLevel==2){
            Bullet bullet1 = new Bullet(getResources());
            bullet1.x = astronaut.x + astronaut.width;
            bullet1.y = astronaut.y + (astronaut.height / 10);
            bullets.add(bullet1);

            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + astronaut.width;
            bullet2.y = astronaut.y + (astronaut.height / 2);
            bullets.add(bullet2);
        }else if(astronaut.GunLevel>=3){
            Bullet bullet1 = new Bullet(getResources());
            bullet1.x = astronaut.x + astronaut.width;
            bullet1.y = astronaut.y + (astronaut.height / 70);
            bullets.add(bullet1);

            Bullet bullet3 = new Bullet(getResources());
            bullet3.x = astronaut.x + astronaut.width;
            bullet3.y = astronaut.y + (astronaut.height / 4);
            bullets.add(bullet3);

            Bullet bullet2 = new Bullet(getResources());
            bullet2.x = astronaut.x + astronaut.width;
            bullet2.y = astronaut.y + (astronaut.height / 2);
            bullets.add(bullet2);
        }
    }

    public void newTripleBullet() {
        if (!settings.getBoolean("mute", false)) {
            soundPoolTripleShot.play(tripleShotSound, 1, 1, 0, 0, 1);
        }
        if(astronaut.GunLevel==1) {
            setNewTripleBullet(0);
        }else if(astronaut.GunLevel==2){
            setNewTripleBullet(0);
            setNewTripleBullet(10);
        }else if(astronaut.GunLevel>=3){
            setNewTripleBullet(0);
            setNewTripleBullet(10);
            setNewTripleBullet(30);
        }
    }

    private void setNewTripleBullet(int x){
        Bullet bullet1 = new Bullet(getResources());
        bullet1.x = astronaut.x + astronaut.width+x+10;
        bullet1.y = astronaut.y + (astronaut.height / 4);
        bullets.add(bullet1);

        Bullet bullet2 = new Bullet(getResources());
        bullet2.x = astronaut.x + astronaut.width+x+5;
        bullet2.y = astronaut.y + (astronaut.height / 2);
        bullets.add(bullet2);

        Bullet bullet3 = new Bullet(getResources());
        bullet3.x = astronaut.x + astronaut.width+x+5;
        bullet3.y = astronaut.y;
        bullets.add(bullet3);

        Bullet bullet4 = new Bullet(getResources());
        bullet4.x = astronaut.x + astronaut.width+x;
        bullet4.y = bullet1.y+55;
        bullets.add(bullet4);

        Bullet bullet5 = new Bullet(getResources());
        bullet5.x = astronaut.x + astronaut.width+x;
        bullet5.y = bullet1.y-55;
        bullets.add(bullet5);
    }

    public void newPierceBullet() {
        if (!settings.getBoolean("mute", false)) {
            soundPoolPierceShot.play(pierceShotSound, 1, 1, 0, 0, 1);
        }

        int count=0;
        if(astronaut.GunLevel==1) {
            int sum=10;
            setNewPierceBullet(count);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel==2){
            int sum=6;
            setNewPierceBullet(count);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count +sum);
        }else if(astronaut.GunLevel>=3){
            int sum=4;
            setNewPierceBullet(count);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count+=sum);
            setNewPierceBullet(count +sum);
        }
    }

    private void setNewPierceBullet(int x){
        Bullet bullet = new Bullet(getResources());
        bullet.x = astronaut.x + astronaut.width + x;
        bullet.y = astronaut.y + (astronaut.height / 4);
        bullets.add(bullet);
    }

    private void MoreAsteroid(){
        Asteroid new_asteroid = new Asteroid(getResources());
        asteroids.add(new_asteroid);
        isMoreAsteroid=false;
    }

    private void AsteroidsLevel(int Level){
        for(Asteroid asteroid : asteroids){
            asteroid.setArrayAsteroidTypeLevel(Level);
        }
    }

    private void CheckWave(){
        if(!isWaiting&&asteroids.size()==0) {
            if(Wave==1){
                for (int i = 0; i < 5; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                MoreAsteroidCounter=19;
            }else if(Wave==2){
                for (int i = 0; i < 6; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                AsteroidsLevel(2);
                MoreAsteroidCounter=19;
            }else if(Wave==3){
                for (int i = 0; i < 7; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                AsteroidsLevel(3);
                MoreAsteroidCounter=19;
            }else if(Wave==4){
                for (int i = 0; i < 8; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                AsteroidsLevel(4);
                MoreAsteroidCounter=24;
            }else if(Wave==5){
                for (int i = 0; i < 9; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                AsteroidsLevel(5);
                MoreAsteroidCounter=25;
            }else if(Wave==6){
                for (int i = 0; i < 10; i++) {
                    Asteroid asteroid = new Asteroid(getResources());
                    asteroids.add(asteroid);
                }
                AsteroidsLevel(6);
                MoreAsteroidCounter=30;
            }else if(Wave>6){
                activity.finish();
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
                        if (!settings.getBoolean("mute", false)) {
                            soundPoolExplosion.play(explosionSound, 1, 1, 0, 0, 1);
                        }
                        if(MoreAsteroidCounter >= 19){
                            MoreAsteroidCounter = 0;
                            isMoreAsteroid = true;
                        }else{
                            MoreAsteroidCounter++;
                        }

                        if(asteroid.Size == 0.6){
                            asteroid.setRandomReward();
                        }

                        if (!asteroid.isReward()){
                            asteroid.x = -500;
                            asteroid.wasShot = true;
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
                    score--;
                }

                if(!isWaiting) {
                    int bound = (int) (SpeedBound * screenRatioX);
                    asteroid.speed = random.nextInt(bound);

                    if (asteroid.speed < 10 * screenRatioX)
                        asteroid.speed = (int) (10 * screenRatioX);

                    asteroid.x = screenX;
                    asteroid.y = random.nextInt(screenY - asteroid.height);

                    asteroid.wasShot = false;
                    asteroid.Reset(getResources());
                }
            }

            if (Rect.intersects(asteroid.getCollisionShape(), astronaut.getCollisionShape())) {
                if (asteroid.isReward()){
                    asteroid.x = -500;
                    asteroid.wasShot = true;
                    if(asteroid.getRewardType()==1) {
                        if (astronaut.HP < 3) {
                            astronaut.HP++;
                        }
                        score++;
                    }else if(asteroid.getRewardType()==2){
                        astronaut.GunLevel++;
                        score+=2;
                    }else if(asteroid.getRewardType()==3){
                        astronaut.bulletLimiter++;
                        score+=2;
                    }
                    if (!settings.getBoolean("mute", false)) {
                        soundPoolHeart.play(heartSound, 1, 1, 0, 0, 1);
                    }
                }else if(astronaut.HP > 0){
                    asteroid.x += astronaut.x+ astronaut.width+(astronaut.width/2);
                    asteroid.speed/=2;
                    astronaut.setDamage();
                }else{
                    isGameOver = true;
                    return;
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

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
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
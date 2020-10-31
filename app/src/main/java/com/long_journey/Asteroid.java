package com.long_journey;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.lang.reflect.Type;
import java.util.Random;

import static com.long_journey.GameView.screenRatioX;
import static com.long_journey.GameView.screenRatioY;

public class Asteroid {

    private static final String LONG_JOURNEY_PREFERENCES = "LONG_JOURNEY_PREFERENCES";
    private SharedPreferences settings;
    public int speed = 20;
    public int HP = 1;
    public boolean wasShot = true;
    public int x = 0, y, width, height, asteroidCounter = 1;
    private Bitmap red_orb;
    private Bitmap blue_orb;
    private Bitmap metal;
    public double Size;
    private boolean Reward = false;
    private int RewardType = 0;
    private int RotationSpeed = 0;
    private int AsteroidType = 1; //Standard type is 1
    //private int[] arrayAsteroidType = {3}; //Starts with only type 1
    private AsteroidSizes asteroidSizes;
    private Random random = new Random();

    private SoundPool soundPoolExplosion;
    private int explosionSound;

    public Asteroid(Resources res, AsteroidSizes asteroidSizes, Context context) {
        red_orb = BitmapFactory.decodeResource(res, R.drawable.red_orb);
        blue_orb = BitmapFactory.decodeResource(res, R.drawable.blue_orb);
        metal = BitmapFactory.decodeResource(res, R.drawable.metal);

        red_orb = Bitmap.createScaledBitmap(
                red_orb, (int) (red_orb.getWidth() * screenRatioX * 2),
                (int) (red_orb.getHeight() * screenRatioY * 2),
                false
        );
        blue_orb = Bitmap.createScaledBitmap(
                blue_orb, (int) (blue_orb.getWidth() * screenRatioX * 2),
                (int) (blue_orb.getHeight() * screenRatioY * 2),
                false
        );
        metal = Bitmap.createScaledBitmap(
                metal, (int) (metal.getWidth() * screenRatioX * 0.6),
                (int) (metal.getHeight() * screenRatioY * 0.6),
                false
        );

        y =- height;

        this.asteroidSizes = asteroidSizes;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPoolExplosion = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            soundPoolExplosion = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        settings = context.getSharedPreferences(LONG_JOURNEY_PREFERENCES, 0);
        explosionSound = soundPoolExplosion.load(context, R.raw.explosion, 1);
    }

    public Bitmap getAsteroid () {
        if(isReward()){
            if(RewardType==1) {
                return red_orb;
            }else if(RewardType==2){
                return metal;
            }else{
                return blue_orb;
            }
        }

        if (asteroidCounter == 1 || asteroidCounter == 2) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,1);
        }
        if (asteroidCounter == 3 || asteroidCounter == 4) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,2);
        }
        if (asteroidCounter == 5 || asteroidCounter == 6) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,3);
        }
        if (asteroidCounter == 7 || asteroidCounter == 8) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,4);
        }
        if (asteroidCounter == 9 || asteroidCounter == 10) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,5);
        }
        if (asteroidCounter == 11 || asteroidCounter == 12) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,6);
        }
        if (asteroidCounter == 13 || asteroidCounter == 14) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,7);
        }
        if (asteroidCounter == 15) {
            asteroidCounter += RotationSpeed;
            return asteroidSizes.getAsteroid(AsteroidType,Size,8);
        }
        asteroidCounter = 1;
        return asteroidSizes.getAsteroid(AsteroidType,Size,8);
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    private double getRandomSize() {
        if(AsteroidType==4){
            return 2;
        }
        double[] arraySize = {0.2,0.3,0.3,0.3,0.4,0.4,0.6};
        int random = new Random().nextInt(arraySize.length);
        return arraySize[random];
    }

    private void setRandomRotationSpeed() {
        int[] array = {0,1,2};
        int random = new Random().nextInt(array.length);
        RotationSpeed = array[random];
    }

    private void setRandomSpeedAndStartPosition(int SpeedBound, int screenX, int screenY) {
        if(AsteroidType!=4){
            int bound = (int) (SpeedBound * screenRatioX);
            speed = random.nextInt(bound);

            if (speed < 10 * screenRatioX)
                speed = (int) (10 * screenRatioX);

            x = screenX;
            int r = random.nextInt(screenY);
            if(r+height>screenY){
                y = screenY-height;
            }else {
                y = r;
            }
        }else {
            speed = 1;
        }
    }

    private void setAsteroidType() {
        int random = new Random().nextInt(asteroidSizes.getArrayAsteroidTypeLength());
        AsteroidType = asteroidSizes.getArrayAsteroidType(random);
    }

    public void setRandomReward(){
        boolean[] array;
        int[] _array;
        if(Size == 0.6){
            if(AsteroidType==1) {
                array = new boolean[]{true, false, false, false, false};
                int random = new Random().nextInt(array.length);
                Reward = array[random];
                if (Reward) {
                    _array = new int[]{2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    random = new Random().nextInt(_array.length);
                    RewardType = _array[random];
                }
            }
            if(AsteroidType==2) {
                array = new boolean[]{true, false, false};
                int random = new Random().nextInt(array.length);
                Reward = array[random];
                if (Reward) {
                    _array = new int[]{1, 2, 3, 3, 3, 3};
                    random = new Random().nextInt(_array.length);
                    RewardType = _array[random];
                }
            }
            if(AsteroidType==3) {
                array = new boolean[]{true, false};
                int random = new Random().nextInt(array.length);
                Reward = array[random];
                if (Reward) {
                    _array = new int[]{1, 2};
                    random = new Random().nextInt(_array.length);
                    RewardType = _array[random];
                }
            }
        }
        if(Size == 0.4){
            if(AsteroidType<10) {
                array = new boolean[]{true, false};
                int random = new Random().nextInt(array.length);
                Reward = array[random];
                if (Reward) {
                    _array = new int[]{3};
                    random = new Random().nextInt(_array.length);
                    RewardType = _array[random];
                }
            }
        }
    }

    public boolean isReward(){
        return Reward;
    }

    private void setHP(){
        if(AsteroidType == 1) {
            if (Size == 0.4) {
                HP = 2;
            } else if (Size == 0.6) {
                HP = 3;
            } else {
                HP = 1;
            }
        }
        if(AsteroidType == 2){
            if (Size == 0.4) {
                HP = 4;
            } else if (Size == 0.6) {
                HP = 6;
            } else {
                HP = 2;
            }
        }
        if(AsteroidType == 3){
            if (Size == 0.4) {
                HP = 8;
            } else if (Size == 0.6) {
                HP = 12;
            } else {
                HP = 4;
            }
        }
        if(AsteroidType == 4){
            HP = 1200;
        }
    }

    public int getRewardType() {
        return RewardType;
    }

    public void Reset(int SpeedBound, int screenX, int screenY){
        wasShot = false;
        Reward = false;
        RewardType=0;
        setAsteroidType();
        Size = getRandomSize();
        setRandomRotationSpeed();
        setRandomSpeedAndStartPosition(SpeedBound,screenX,screenY);
        setHP();
        width=asteroidSizes.getWidth(Size);
        height=asteroidSizes.getHeight(Size);
    }

    public int getAsteroidType(){
        return AsteroidType;
    }

    public void getExplosionSound(int screenX){
        if (!settings.getBoolean("mute", false)) {
            if (x<screenX/5) {
                soundPoolExplosion.play(explosionSound, 1, 0.2f, 0, 0, 1);
            }else if (x<screenX/5*2){
                soundPoolExplosion.play(explosionSound, 1, 0.5f, 0, 0, 1);
            }else if (x<screenX/5*3){
                soundPoolExplosion.play(explosionSound, 1, 1, 0, 0, 1);
            }else if (x<screenX/5*4){
                soundPoolExplosion.play(explosionSound, 0.5f, 1, 0, 0, 1);
            }else if (x<screenX/5*5){
                soundPoolExplosion.play(explosionSound, 0.2f, 1, 0, 0, 1);
            }
        }
    }
}

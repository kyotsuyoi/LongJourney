package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.long_journey.GameView.screenRatioX;
import static com.long_journey.GameView.screenRatioY;

public class Asteroid {

    public int speed = 20;
    public int HP = 1;
    public boolean wasShot = true;
    public int x = 0, y, width, height, asteroidCounter = 1;
    private int res_width, res_height;
    private Bitmap asteroid1, asteroid2, asteroid3, asteroid4;
    private Bitmap asteroid5, asteroid6, asteroid7, asteroid8;
    private Bitmap heart;
    private Bitmap bullet;
    private Bitmap gun;
    public double Size;
    private boolean Reward = false;
    private int RewardType = 0;
    private int RotationSpeed = 0;
    private int AsteroidType = 1; //Standard type is 1
    private int[] arrayAsteroidType = {1}; //Starts with only type 1

    public Asteroid(Resources res) {
        heart = BitmapFactory.decodeResource(res, R.drawable.heart);
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        gun = BitmapFactory.decodeResource(res, R.drawable.gun);

        heart = Bitmap.createScaledBitmap(
                heart, (int) (heart.getWidth() * screenRatioX * 0.1),
                (int) (heart.getHeight() * screenRatioY * 0.1),
                false
        );
        bullet = Bitmap.createScaledBitmap(
                bullet, (int) (bullet.getWidth() * screenRatioX * 0.3),
                (int) (bullet.getHeight() * screenRatioY * 0.3),
                false
        );
        gun = Bitmap.createScaledBitmap(
                gun, (int) (gun.getWidth() * screenRatioX * 3),
                (int) (gun.getHeight() * screenRatioY * 3),
                false
        );

        y =- height;
    }

    public Bitmap getAsteroid () {
        if(isReward()){
            if(RewardType==1) {
                return heart;
            }else if(RewardType==2){
                return gun;
            }else{
                return bullet;
            }
        }

        if (asteroidCounter == 0) {
            return asteroid1;
        }
        if (asteroidCounter == 1 || asteroidCounter == 2) {
            asteroidCounter += RotationSpeed;
            return asteroid1;
        }
        if (asteroidCounter == 3 || asteroidCounter == 4) {
            asteroidCounter += RotationSpeed;
            return asteroid2;
        }
        if (asteroidCounter == 5 || asteroidCounter == 6) {
            asteroidCounter += RotationSpeed;
            return asteroid3;
        }
        if (asteroidCounter == 7 || asteroidCounter == 8) {
            asteroidCounter += RotationSpeed;
            return asteroid4;
        }
        if (asteroidCounter == 9 || asteroidCounter == 10) {
            asteroidCounter += RotationSpeed;
            return asteroid5;
        }
        if (asteroidCounter == 11 || asteroidCounter == 12) {
            asteroidCounter += RotationSpeed;
            return asteroid6;
        }
        if (asteroidCounter == 13 || asteroidCounter == 14) {
            asteroidCounter += RotationSpeed;
            return asteroid7;
        }
        if (asteroidCounter == 15) {
            asteroidCounter += RotationSpeed;
            return asteroid8;
        }
        asteroidCounter = 1;
        return asteroid8;
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    private double getRandomSize() {
        double[] arraySize = {0.2,0.3,0.3,0.3,0.4,0.4,0.6};
        int random = new Random().nextInt(arraySize.length);
        return arraySize[random];
    }

    private void setRandomRotationSpeed() {
        int[] array = {0,1,2};
        int random = new Random().nextInt(array.length);
        RotationSpeed = array[random];
    }

    private void setAsteroidType() {
        int random = new Random().nextInt(arrayAsteroidType.length);
        AsteroidType = arrayAsteroidType[random];
    }

    public void setRandomReward(){
        if(Size == 0.6){
            boolean[] array = {true,false};
            int random = new Random().nextInt(array.length);
            Reward = array[random];
            if(Reward==true) {
                int[] _array = {1,2,2,3,3,3,3};
                random = new Random().nextInt(_array.length);
                RewardType = _array[random];
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
    }

    public int getRewardType() {
        return RewardType;
    }

    private void setSize(Resources res){
        try {
            if(AsteroidType==1) {
                asteroid1 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_1);
                asteroid2 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_2);
                asteroid3 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_3);
                asteroid4 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_4);
                asteroid5 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_5);
                asteroid6 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_6);
                asteroid7 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_7);
                asteroid8 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1_8);

            }else if(AsteroidType==2){
                asteroid1 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_1);
                asteroid2 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_2);
                asteroid3 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_3);
                asteroid4 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_4);
                asteroid5 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_5);
                asteroid6 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_6);
                asteroid7 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_7);
                asteroid8 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2_8);
            }

            res_width = asteroid1.getWidth();
            res_height = asteroid1.getHeight();

            Size = getRandomSize();
            width = (int) (res_width * Size);
            height = (int) (res_height * Size);

            width = (int) (width * screenRatioX);
            height = (int) (height * screenRatioY);

            if(AsteroidType==1) {
                asteroid1 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
                asteroid2 = Bitmap.createScaledBitmap(asteroid2, width, height, false);
                asteroid3 = Bitmap.createScaledBitmap(asteroid3, width, height, false);
                asteroid4 = Bitmap.createScaledBitmap(asteroid4, width, height, false);
                asteroid5 = Bitmap.createScaledBitmap(asteroid5, width, height, false);
                asteroid6 = Bitmap.createScaledBitmap(asteroid6, width, height, false);
                asteroid7 = Bitmap.createScaledBitmap(asteroid7, width, height, false);
                asteroid8 = Bitmap.createScaledBitmap(asteroid8, width, height, false);
            }else if(AsteroidType==2) {
                asteroid1 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
                asteroid2 = Bitmap.createScaledBitmap(asteroid2, width, height, false);
                asteroid3 = Bitmap.createScaledBitmap(asteroid3, width, height, false);
                asteroid4 = Bitmap.createScaledBitmap(asteroid4, width, height, false);
                asteroid5 = Bitmap.createScaledBitmap(asteroid5, width, height, false);
                asteroid6 = Bitmap.createScaledBitmap(asteroid6, width, height, false);
                asteroid7 = Bitmap.createScaledBitmap(asteroid7, width, height, false);
                asteroid8 = Bitmap.createScaledBitmap(asteroid8, width, height, false);
            }

        }catch (Exception e){
            e.getMessage();
        }

    }

    public void Reset(Resources res){
        wasShot = false;
        Reward = false;
        RewardType=0;
        setAsteroidType();
        setSize(res);
        setRandomRotationSpeed();
        setHP();
        //setRandomReward();
    }

    public void setArrayAsteroidTypeLevel(int Level){
        if(Level==1) {
            arrayAsteroidType = new int[]{1};
        }else if(Level==2){
            arrayAsteroidType = new int[]{1,1,1,2};
        }else if(Level==3){
            arrayAsteroidType = new int[]{1,1,2};
        }else if(Level==4){
            arrayAsteroidType = new int[]{1,2};
        }else if(Level==5){
            arrayAsteroidType = new int[]{1,2,2};
        }else if(Level>=6){
            arrayAsteroidType = new int[]{2};
        }
    }
}

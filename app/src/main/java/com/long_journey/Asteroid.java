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

    public Asteroid(Resources res, AsteroidSizes asteroidSizes) {
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
                metal, (int) (metal.getWidth() * screenRatioX * 0.8),
                (int) (metal.getHeight() * screenRatioY * 0.8),
                false
        );

        y =- height;

        this.asteroidSizes = asteroidSizes;
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

    private void setRandomSpeed(int SpeedBound, int screenX, int screenY) {
        if(AsteroidType!=4){
            int bound = (int) (SpeedBound * screenRatioX);
            speed = random.nextInt(bound);

            if (speed < 10 * screenRatioX)
                speed = (int) (10 * screenRatioX);

            x = screenX;
            y = random.nextInt(screenY - height*2);
        }else {
            speed = 1;
        }
    }

    private void setAsteroidType() {
        int random = new Random().nextInt(asteroidSizes.getArrayAsteroidTypeLength());
        AsteroidType = asteroidSizes.getArrayAsteroidType(random);
    }

    public void setRandomReward(){
        if(Size == 0.6){
            boolean[] array = {true,false};
            int random = new Random().nextInt(array.length);
            Reward = array[random];
            if(Reward) {
                int[] _array = {1,2,3,3,3,3,3,3,3,3,3,3};
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
            HP = 500;
        }
    }

    public int getRewardType() {
        return RewardType;
    }

    public void Reset(int SpeedBound, int screenX, int screenY){
        wasShot = false;
        Reward = false;
        RewardType=0;
        setRandomSpeed(SpeedBound,screenX,screenY);
        setAsteroidType();
        Size = getRandomSize();
        setRandomRotationSpeed();
        setHP();
        width=asteroidSizes.getWidth(Size);
        height=asteroidSizes.getHeight(Size);
    }

    public int getAsteroidType(){
        return AsteroidType;
    }
}

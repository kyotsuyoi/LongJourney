package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.long_journey.GameView.screenRatioX;
import static com.long_journey.GameView.screenRatioY;

public class Astronaut {

    public int toShoot = 0;
    public int HP = 0;
    public int GunLevel = 1;
    public boolean isCharge = false;
    public int chargedShootType = 0;
    public boolean isGoingUp, isGoingDown = false;
    public int x, y, width, height, dashCounter = 0, shootCounter = 1, damageCounter=0;
    private Bitmap astronaut1, astronaut2, astronautUP, astronautDown, shoot1, shoot2, shoot3, shoot4, shoot5, damage1, damage2;
    private GameView gameView;
    private boolean damage;
    public int bulletLimiter = 30;
    public boolean isEthereal = false;
    private int etherealTime = 0;

    public Astronaut(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;

        astronaut1 = BitmapFactory.decodeResource(res, R.drawable.astronaut);
        astronaut2 = BitmapFactory.decodeResource(res, R.drawable.astronaut);
        astronautUP = BitmapFactory.decodeResource(res, R.drawable.astronaut_up);
        astronautDown = BitmapFactory.decodeResource(res, R.drawable.astronaut_down);

        width = astronaut1.getWidth();
        height = astronaut1.getHeight();

        width /= 2;
        height /= 2;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        astronaut1 = Bitmap.createScaledBitmap(astronaut1, width, height, false);
        astronaut2 = Bitmap.createScaledBitmap(astronaut2, width, height, false);
        astronautUP = Bitmap.createScaledBitmap(astronautUP, width, height, false);
        astronautDown = Bitmap.createScaledBitmap(astronautDown, width, height, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.astronaut_shot);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.astronaut_shot);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.astronaut_shot);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.astronaut_shot);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.astronaut_shot);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);

        damage1 = BitmapFactory.decodeResource(res, R.drawable.astronaut_damage1);
        damage2 = BitmapFactory.decodeResource(res, R.drawable.astronaut_damage2);
        damage1 = Bitmap.createScaledBitmap(damage1, width, height, false);
        int damage2Width = (int)(damage2.getWidth() /2 *screenRatioY);
        damage2 = Bitmap.createScaledBitmap(damage2, damage2Width, height, false);

        y = screenY/2 - height/2;
        x = (int) (64 * screenRatioX);
    }

    public Bitmap getFlight () {
        if(damage){
            damageCounter++;
            if(damageCounter>8){
                damage=false;
                damageCounter=0;
            }else if (damageCounter>2 && damageCounter<6){
                return damage2;
            }else{
                return damage1;
            }
        }

        if (toShoot != 0 && toShoot <= bulletLimiter) {
            if (shootCounter == 1) {
                shootCounter++;
                return shoot1;
            }
            if (shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }
            if (shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }
            if (shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toShoot--;
            if(isCharge && bulletLimiter > 3) {
                if(chargedShootType == 0) {
                    gameView.newShotGunBullet();
                }else if(chargedShootType == 1){
                    gameView.newPierceBullet();
                }
                bulletLimiter--;
                isCharge=false;
            }else{
                gameView.newBullet();
            }

            return shoot5;
        }

        if(isGoingUp){
            return  astronautUP;
        }else if (isGoingDown){
            return astronautDown;
        }

        if (dashCounter == 0) {
            dashCounter++;
            return astronaut1;
        }
        dashCounter--;

        return astronaut2;
    }

    public Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    public Bitmap getDead() {
        return damage2;
    }

    public void setDamage(int AsteroidType, double Size) {
        damage=true;
        if (AsteroidType==1){
            if(Size==0.2 || Size==0.3){
                HP--;
            }else if(Size==0.4){
                HP-=2;
            }else if(Size==0.6){
                HP-=3;
            }
        }
        if (AsteroidType==2){
            if(Size==0.2 || Size==0.3){
                HP-=2;
            }else if(Size==0.4){
                HP-=4;
            }else if(Size==0.6){
                HP-=6;
            }
        }
        if (AsteroidType==3){
            if(Size==0.2 || Size==0.3){
                HP-=3;
            }else if(Size==0.4){
                HP-=6;
            }else if(Size==0.6){
                HP-=8;
            }
        }
        if (AsteroidType==4){
            HP=0;
        }
        if(HP<0){
            HP=0;
        }
    }

    public int getEtherealTime(){
        if(etherealTime>0){
            etherealTime--;
        }
        return etherealTime;
    }

    public void setEtherealTime(int etherealTime) {
        this.etherealTime = etherealTime;
    }
}

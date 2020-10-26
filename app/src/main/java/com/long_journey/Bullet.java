package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.long_journey.GameView.screenRatioX;
import static com.long_journey.GameView.screenRatioY;

public class Bullet {

    int x, y, width, height;
    private Bitmap bullet;

    public Bullet (Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public Bitmap getBullet(){return bullet;}
}
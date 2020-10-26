package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HealthPoint {

    public Bitmap Heart;

    HealthPoint(int screenX, int screenY, Resources res) {
        Heart = BitmapFactory.decodeResource(res, R.drawable.heart);
        Heart = Bitmap.createScaledBitmap(Heart, screenX, screenY, false);
    }

}

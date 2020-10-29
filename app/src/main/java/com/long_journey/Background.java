package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x = 0, y = 0;
    private Bitmap background;

    public Background (int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.back_test04);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
        background.setHasAlpha(true);
    }

    public Bitmap getBackground(){
        return background;
    }
}
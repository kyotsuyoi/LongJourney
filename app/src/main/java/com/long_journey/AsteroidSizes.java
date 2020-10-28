package com.long_journey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.long_journey.GameView.screenRatioX;
import static com.long_journey.GameView.screenRatioY;

public class AsteroidSizes {

    private Resources resources;

    private int[] arrayAsteroidType = {1};

    private Bitmap asteroid_1_1_tiny, asteroid_1_2_tiny, asteroid_1_3_tiny, asteroid_1_4_tiny;
    private Bitmap asteroid_1_5_tiny, asteroid_1_6_tiny, asteroid_1_7_tiny, asteroid_1_8_tiny;
    private Bitmap asteroid_1_1_small, asteroid_1_2_small, asteroid_1_3_small, asteroid_1_4_small;
    private Bitmap asteroid_1_5_small, asteroid_1_6_small, asteroid_1_7_small, asteroid_1_8_small;
    private Bitmap asteroid_1_1_medium, asteroid_1_2_medium, asteroid_1_3_medium, asteroid_1_4_medium;
    private Bitmap asteroid_1_5_medium, asteroid_1_6_medium, asteroid_1_7_medium, asteroid_1_8_medium;
    private Bitmap asteroid_1_1_large, asteroid_1_2_large, asteroid_1_3_large, asteroid_1_4_large;
    private Bitmap asteroid_1_5_large, asteroid_1_6_large, asteroid_1_7_large, asteroid_1_8_large;

    private Bitmap asteroid_2_1_tiny, asteroid_2_2_tiny, asteroid_2_3_tiny, asteroid_2_4_tiny;
    private Bitmap asteroid_2_5_tiny, asteroid_2_6_tiny, asteroid_2_7_tiny, asteroid_2_8_tiny;
    private Bitmap asteroid_2_1_small, asteroid_2_2_small, asteroid_2_3_small, asteroid_2_4_small;
    private Bitmap asteroid_2_5_small, asteroid_2_6_small, asteroid_2_7_small, asteroid_2_8_small;
    private Bitmap asteroid_2_1_medium, asteroid_2_2_medium, asteroid_2_3_medium, asteroid_2_4_medium;
    private Bitmap asteroid_2_5_medium, asteroid_2_6_medium, asteroid_2_7_medium, asteroid_2_8_medium;
    private Bitmap asteroid_2_1_large, asteroid_2_2_large, asteroid_2_3_large, asteroid_2_4_large;
    private Bitmap asteroid_2_5_large, asteroid_2_6_large, asteroid_2_7_large, asteroid_2_8_large;

    private Bitmap asteroid_3_1_tiny, asteroid_3_2_tiny, asteroid_3_3_tiny, asteroid_3_4_tiny;
    private Bitmap asteroid_3_5_tiny, asteroid_3_6_tiny, asteroid_3_7_tiny, asteroid_3_8_tiny;
    private Bitmap asteroid_3_1_small, asteroid_3_2_small, asteroid_3_3_small, asteroid_3_4_small;
    private Bitmap asteroid_3_5_small, asteroid_3_6_small, asteroid_3_7_small, asteroid_3_8_small;
    private Bitmap asteroid_3_1_medium, asteroid_3_2_medium, asteroid_3_3_medium, asteroid_3_4_medium;
    private Bitmap asteroid_3_5_medium, asteroid_3_6_medium, asteroid_3_7_medium, asteroid_3_8_medium;
    private Bitmap asteroid_3_1_large, asteroid_3_2_large, asteroid_3_3_large, asteroid_3_4_large;
    private Bitmap asteroid_3_5_large, asteroid_3_6_large, asteroid_3_7_large, asteroid_3_8_large;

    private Bitmap asteroid_3_1_ultra, asteroid_3_2_ultra, asteroid_3_3_ultra, asteroid_3_4_ultra;
    private Bitmap asteroid_3_5_ultra, asteroid_3_6_ultra, asteroid_3_7_ultra, asteroid_3_8_ultra;

    public AsteroidSizes(Resources res){
        this.resources = res;
        ResizeTyneOfType1();
        ResizeSmallOfType1();
        ResizeMediumOfType1();
        ResizeLargeOfType1();

        ResizeTyneOfType2();
        ResizeSmallOfType2();
        ResizeMediumOfType2();
        ResizeLargeOfType2();

        ResizeTyneOfType3();
        ResizeSmallOfType3();
        ResizeMediumOfType3();
        ResizeLargeOfType3();

        ResizeUltra();
    }

    private Bitmap resize(Bitmap bitmap, double ratio){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        bitmap = Bitmap.createScaledBitmap(
                bitmap, (int) (width * screenRatioX * ratio),
                (int) (height * screenRatioY * ratio),
                false
        );
        return bitmap;
    }

    public Bitmap getAsteroid(int Type, double Size, int Position){
        if(Type == 1){
            if(Size == 0.2){
                switch (Position){
                    case 1:
                        return asteroid_1_1_tiny;
                    case 2:
                        return asteroid_1_2_tiny;
                    case 3:
                        return asteroid_1_3_tiny;
                    case 4:
                        return asteroid_1_4_tiny;
                    case 5:
                        return asteroid_1_5_tiny;
                    case 6:
                        return asteroid_1_6_tiny;
                    case 7:
                        return asteroid_1_7_tiny;
                    case 8:
                        return asteroid_1_8_tiny;
                }
            }
            if(Size == 0.3){
                switch (Position){
                    case 1:
                        return asteroid_1_1_small;
                    case 2:
                        return asteroid_1_2_small;
                    case 3:
                        return asteroid_1_3_small;
                    case 4:
                        return asteroid_1_4_small;
                    case 5:
                        return asteroid_1_5_small;
                    case 6:
                        return asteroid_1_6_small;
                    case 7:
                        return asteroid_1_7_small;
                    case 8:
                        return asteroid_1_8_small;
                }
            }
            if(Size == 0.4){
                switch (Position){
                    case 1:
                        return asteroid_1_1_medium;
                    case 2:
                        return asteroid_1_2_medium;
                    case 3:
                        return asteroid_1_3_medium;
                    case 4:
                        return asteroid_1_4_medium;
                    case 5:
                        return asteroid_1_5_medium;
                    case 6:
                        return asteroid_1_6_medium;
                    case 7:
                        return asteroid_1_7_medium;
                    case 8:
                        return asteroid_1_8_medium;
                }
            }
            if(Size == 0.6){
                switch (Position){
                    case 1:
                        return asteroid_1_1_large;
                    case 2:
                        return asteroid_1_2_large;
                    case 3:
                        return asteroid_1_3_large;
                    case 4:
                        return asteroid_1_4_large;
                    case 5:
                        return asteroid_1_5_large;
                    case 6:
                        return asteroid_1_6_large;
                    case 7:
                        return asteroid_1_7_large;
                    case 8:
                        return asteroid_1_8_large;
                }
            }
        }
        if(Type == 2){
            if(Size == 0.2){
                switch (Position){
                    case 1:
                        return asteroid_2_1_tiny;
                    case 2:
                        return asteroid_2_2_tiny;
                    case 3:
                        return asteroid_2_3_tiny;
                    case 4:
                        return asteroid_2_4_tiny;
                    case 5:
                        return asteroid_2_5_tiny;
                    case 6:
                        return asteroid_2_6_tiny;
                    case 7:
                        return asteroid_2_7_tiny;
                    case 8:
                        return asteroid_2_8_tiny;
                }
            }
            if(Size == 0.3){
                switch (Position){
                    case 1:
                        return asteroid_2_1_small;
                    case 2:
                        return asteroid_2_2_small;
                    case 3:
                        return asteroid_2_3_small;
                    case 4:
                        return asteroid_2_4_small;
                    case 5:
                        return asteroid_2_5_small;
                    case 6:
                        return asteroid_2_6_small;
                    case 7:
                        return asteroid_2_7_small;
                    case 8:
                        return asteroid_2_8_small;
                }
            }
            if(Size == 0.4){
                switch (Position){
                    case 1:
                        return asteroid_2_1_medium;
                    case 2:
                        return asteroid_2_2_medium;
                    case 3:
                        return asteroid_2_3_medium;
                    case 4:
                        return asteroid_2_4_medium;
                    case 5:
                        return asteroid_2_5_medium;
                    case 6:
                        return asteroid_2_6_medium;
                    case 7:
                        return asteroid_2_7_medium;
                    case 8:
                        return asteroid_2_8_medium;
                }
            }
            if(Size == 0.6){
                switch (Position){
                    case 1:
                        return asteroid_2_1_large;
                    case 2:
                        return asteroid_2_2_large;
                    case 3:
                        return asteroid_2_3_large;
                    case 4:
                        return asteroid_2_4_large;
                    case 5:
                        return asteroid_2_5_large;
                    case 6:
                        return asteroid_2_6_large;
                    case 7:
                        return asteroid_2_7_large;
                    case 8:
                        return asteroid_2_8_large;
                }
            }
        }
        if(Type == 3){
            if(Size == 0.2){
                switch (Position){
                    case 1:
                        return asteroid_3_1_tiny;
                    case 2:
                        return asteroid_3_2_tiny;
                    case 3:
                        return asteroid_3_3_tiny;
                    case 4:
                        return asteroid_3_4_tiny;
                    case 5:
                        return asteroid_3_5_tiny;
                    case 6:
                        return asteroid_3_6_tiny;
                    case 7:
                        return asteroid_3_7_tiny;
                    case 8:
                        return asteroid_3_8_tiny;
                }
            }
            if(Size == 0.3){
                switch (Position){
                    case 1:
                        return asteroid_3_1_small;
                    case 2:
                        return asteroid_3_2_small;
                    case 3:
                        return asteroid_3_3_small;
                    case 4:
                        return asteroid_3_4_small;
                    case 5:
                        return asteroid_3_5_small;
                    case 6:
                        return asteroid_3_6_small;
                    case 7:
                        return asteroid_3_7_small;
                    case 8:
                        return asteroid_3_8_small;
                }
            }
            if(Size == 0.4){
                switch (Position){
                    case 1:
                        return asteroid_3_1_medium;
                    case 2:
                        return asteroid_3_2_medium;
                    case 3:
                        return asteroid_3_3_medium;
                    case 4:
                        return asteroid_3_4_medium;
                    case 5:
                        return asteroid_3_5_medium;
                    case 6:
                        return asteroid_3_6_medium;
                    case 7:
                        return asteroid_3_7_medium;
                    case 8:
                        return asteroid_3_8_medium;
                }
            }
            if(Size == 0.6){
                switch (Position){
                    case 1:
                        return asteroid_3_1_large;
                    case 2:
                        return asteroid_3_2_large;
                    case 3:
                        return asteroid_3_3_large;
                    case 4:
                        return asteroid_3_4_large;
                    case 5:
                        return asteroid_3_5_large;
                    case 6:
                        return asteroid_3_6_large;
                    case 7:
                        return asteroid_3_7_large;
                    case 8:
                        return asteroid_3_8_large;
                }
            }
        }
        if(Type == 4){
            switch (Position){
                case 1:
                    return asteroid_3_1_ultra;
                case 2:
                    return asteroid_3_2_ultra;
                case 3:
                    return asteroid_3_3_ultra;
                case 4:
                    return asteroid_3_4_ultra;
                case 5:
                    return asteroid_3_5_ultra;
                case 6:
                    return asteroid_3_6_ultra;
                case 7:
                    return asteroid_3_7_ultra;
                case 8:
                    return asteroid_3_8_ultra;
            }
        }
        return null;
    }

    private void ResizeTyneOfType1(){
        asteroid_1_1_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_1),
                0.2
        );
        asteroid_1_2_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_2),
                0.2
        );
        asteroid_1_3_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_3),
                0.2
        );
        asteroid_1_4_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_4),
                0.2
        );
        asteroid_1_5_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_5),
                0.2
        );
        asteroid_1_6_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_6),
                0.2
        );
        asteroid_1_7_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_7),
                0.2
        );
        asteroid_1_8_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_8),
                0.2
        );
    }
    private void ResizeSmallOfType1(){

        asteroid_1_1_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_1),
                0.3
        );
        asteroid_1_2_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_2),
                0.3
        );
        asteroid_1_3_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_3),
                0.3
        );
        asteroid_1_4_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_4),
                0.3
        );
        asteroid_1_5_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_5),
                0.3
        );
        asteroid_1_6_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_6),
                0.3
        );
        asteroid_1_7_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_7),
                0.3
        );
        asteroid_1_8_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_8),
                0.3
        );
    }
    private void ResizeMediumOfType1(){
        asteroid_1_1_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_1),
                0.4
        );
        asteroid_1_2_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_2),
                0.4
        );
        asteroid_1_3_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_3),
                0.4
        );
        asteroid_1_4_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_4),
                0.4
        );
        asteroid_1_5_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_5),
                0.4
        );
        asteroid_1_6_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_6),
                0.4
        );
        asteroid_1_7_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_7),
                0.4
        );
        asteroid_1_8_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_8),
                0.4
        );
    }
    private void ResizeLargeOfType1(){
        asteroid_1_1_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_1),
                0.6
        );
        asteroid_1_2_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_2),
                0.6
        );
        asteroid_1_3_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_3),
                0.6
        );
        asteroid_1_4_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_4),
                0.6
        );
        asteroid_1_5_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_5),
                0.6
        );
        asteroid_1_6_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_6),
                0.6
        );
        asteroid_1_7_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_7),
                0.6
        );
        asteroid_1_8_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_1_8),
                0.6
        );
    }

    private void ResizeTyneOfType2(){
        asteroid_2_1_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_1),
                0.2
        );
        asteroid_2_2_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_2),
                0.2
        );
        asteroid_2_3_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_3),
                0.2
        );
        asteroid_2_4_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_4),
                0.2
        );
        asteroid_2_5_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_5),
                0.2
        );
        asteroid_2_6_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_6),
                0.2
        );
        asteroid_2_7_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_7),
                0.2
        );
        asteroid_2_8_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_8),
                0.2
        );
    }
    private void ResizeSmallOfType2(){

        asteroid_2_1_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_1),
                0.3
        );
        asteroid_2_2_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_2),
                0.3
        );
        asteroid_2_3_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_3),
                0.3
        );
        asteroid_2_4_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_4),
                0.3
        );
        asteroid_2_5_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_5),
                0.3
        );
        asteroid_2_6_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_6),
                0.3
        );
        asteroid_2_7_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_7),
                0.3
        );
        asteroid_2_8_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_8),
                0.3
        );
    }
    private void ResizeMediumOfType2(){
        asteroid_2_1_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_1),
                0.4
        );
        asteroid_2_2_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_2),
                0.4
        );
        asteroid_2_3_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_3),
                0.4
        );
        asteroid_2_4_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_4),
                0.4
        );
        asteroid_2_5_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_5),
                0.4
        );
        asteroid_2_6_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_6),
                0.4
        );
        asteroid_2_7_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_7),
                0.4
        );
        asteroid_2_8_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_8),
                0.4
        );
    }
    private void ResizeLargeOfType2(){
        asteroid_2_1_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_1),
                0.6
        );
        asteroid_2_2_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_2),
                0.6
        );
        asteroid_2_3_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_3),
                0.6
        );
        asteroid_2_4_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_4),
                0.6
        );
        asteroid_2_5_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_5),
                0.6
        );
        asteroid_2_6_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_6),
                0.6
        );
        asteroid_2_7_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_7),
                0.6
        );
        asteroid_2_8_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_2_8),
                0.6
        );
    }

    private void ResizeTyneOfType3(){
        asteroid_3_1_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_1),
                0.2
        );
        asteroid_3_2_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_2),
                0.2
        );
        asteroid_3_3_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_3),
                0.2
        );
        asteroid_3_4_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_4),
                0.2
        );
        asteroid_3_5_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_5),
                0.2
        );
        asteroid_3_6_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_6),
                0.2
        );
        asteroid_3_7_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_7),
                0.2
        );
        asteroid_3_8_tiny = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_8),
                0.2
        );
    }
    private void ResizeSmallOfType3(){

        asteroid_3_1_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_1),
                0.3
        );
        asteroid_3_2_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_2),
                0.3
        );
        asteroid_3_3_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_3),
                0.3
        );
        asteroid_3_4_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_4),
                0.3
        );
        asteroid_3_5_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_5),
                0.3
        );
        asteroid_3_6_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_6),
                0.3
        );
        asteroid_3_7_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_7),
                0.3
        );
        asteroid_3_8_small = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_8),
                0.3
        );
    }
    private void ResizeMediumOfType3(){
        asteroid_3_1_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_1),
                0.4
        );
        asteroid_3_2_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_2),
                0.4
        );
        asteroid_3_3_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_3),
                0.4
        );
        asteroid_3_4_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_4),
                0.4
        );
        asteroid_3_5_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_5),
                0.4
        );
        asteroid_3_6_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_6),
                0.4
        );
        asteroid_3_7_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_7),
                0.4
        );
        asteroid_3_8_medium = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_8),
                0.4
        );
    }
    private void ResizeLargeOfType3(){
        asteroid_3_1_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_1),
                0.6
        );
        asteroid_3_2_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_2),
                0.6
        );
        asteroid_3_3_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_3),
                0.6
        );
        asteroid_3_4_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_4),
                0.6
        );
        asteroid_3_5_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_5),
                0.6
        );
        asteroid_3_6_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_6),
                0.6
        );
        asteroid_3_7_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_7),
                0.6
        );
        asteroid_3_8_large = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_8),
                0.6
        );
    }

    private void ResizeUltra(){
        asteroid_3_1_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_1),
                2
        );
        asteroid_3_2_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_2),
                2
        );
        asteroid_3_3_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_3),
                2
        );
        asteroid_3_4_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_4),
                2
        );
        asteroid_3_5_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_5),
                2
        );
        asteroid_3_6_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_6),
                2
        );
        asteroid_3_7_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_7),
                2
        );
        asteroid_3_8_ultra = resize(
                BitmapFactory.decodeResource(resources, R.drawable.asteroid_3_8),
                2
        );
    }

    public int getWidth(double Size){
        if (Size==0.2){
            return asteroid_1_1_tiny.getWidth();
        }
        if (Size==0.3){
            return asteroid_1_1_small.getWidth();
        }
        if (Size==0.4){
            return asteroid_1_1_medium.getWidth();
        }
        if (Size==0.6){
            return asteroid_1_1_large.getWidth();
        }
        if (Size==2){
            return asteroid_3_1_ultra.getWidth();
        }
        return 0;
    }

    public int getHeight(double Size){
        if (Size==0.2){
            return asteroid_1_1_tiny.getHeight();
        }
        if (Size==0.3){
            return asteroid_1_1_small.getHeight();
        }
        if (Size==0.4){
            return asteroid_1_1_medium.getHeight();
        }
        if (Size==0.6){
            return asteroid_1_1_large.getHeight();
        }
        if (Size==2){
            return asteroid_3_1_ultra.getHeight();
        }
        return 0;
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
            arrayAsteroidType = new int[]{1,1,2,2,2,3};
        }else if(Level==6){
            arrayAsteroidType = new int[]{1,2,2,2,2,3};
        }else if(Level==7){
            arrayAsteroidType = new int[]{1,2,2,3,3};
        }else if(Level==8){
            arrayAsteroidType = new int[]{1,2,3,3};
        }else if(Level==9){
            arrayAsteroidType = new int[]{2,3,3};
        }else if(Level==10){
            arrayAsteroidType = new int[]{3};
        }else{
            arrayAsteroidType = new int[]{4};
        }
    }

    public int getArrayAsteroidTypeLength(){
        return arrayAsteroidType.length;
    }

    public int getArrayAsteroidType(int random){
        return arrayAsteroidType[random];
    }
}

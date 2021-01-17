
package com.example.birdv2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.view.Display;

import java.util.Random;


public class GameView extends View {



    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS=30;
    Bitmap background;
    Bitmap toptube,bottomtube;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect;

    Bitmap[] birds;

    int birdFrame =0;
    int velocity=0,gravity=3;
    int birdX,birdY;
    boolean gameState=false;
    int gap=300;
    int minTubeOffset,maxTubeOffset;
    int numberOfTube = 4;
    int distanceBetweenTubes;
    int[] tubeX = new int[numberOfTube];
    int[] topTubeY = new int[numberOfTube];


    int tubeVelocity = 8;

    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        toptube = BitmapFactory.decodeResource(getResources(),R.drawable.toptube);
        bottomtube = BitmapFactory.decodeResource(getResources(),R.drawable.botompipe);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWidth,dHeight);
        birds = new Bitmap[2];
        birds[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bird_one);
        birds[1] = BitmapFactory.decodeResource(getResources(),R.drawable.bird_three);
        birdX = dWidth/2 - birds[0].getWidth()/2;
        birdY = dHeight/2-birds[0].getHeight()/2;
        distanceBetweenTubes=(dWidth*3)/4;
        minTubeOffset = gap/2;
        maxTubeOffset = dHeight - minTubeOffset - gap;
       // random = new Random();
        final int random = new Random().nextInt(maxTubeOffset - minTubeOffset+1);
        for (int i=0;i<numberOfTube;i++){

            tubeX[i] = dWidth+i*distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random;

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(background,null,rect,null);
        final int random = new Random().nextInt(maxTubeOffset - minTubeOffset+1);
        if(birdFrame == 0){
            birdFrame=1;
        }else{
            birdFrame = 0;
        }
        if(gameState) {
            //velocity += gravity;//falling of the bird couse speeding up
            //birdY += velocity;
            if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity;
                birdY += velocity;
            }
            for(int i =0;i<numberOfTube;i++) {
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -toptube.getWidth()){

                    tubeX[i] +=numberOfTube * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random;

                }

                canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
                canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);
            }
        }
        //wyswietlanie ptaka na srodku
        canvas.drawBitmap(birds[birdFrame],birdX,birdY,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            velocity = -30;
            gameState=true;
        }
        return true;
    }
}




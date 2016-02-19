package com.example.dmytro.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dmytro on 2/18/16.
 */
public class Background {
    private Bitmap image;
    private int x, y, dx;

    //constructor
    public Background(Bitmap res){
        image = res;
        dx = GamePanel.MOVESPEED;

    }
    public void update(){

        x+=dx;  //change x position everytime update is called in game panel
        if(x<-GamePanel.WIDTH)  //when image completely moves off screen, goes back and starts moving again
        {
            x=0;
        }

    }
    public void draw(Canvas canvas){


        canvas.drawBitmap(image, x, y, null);
        //draw second image to take up space as first image moves off screen
        if(x<0){
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }




    }

}

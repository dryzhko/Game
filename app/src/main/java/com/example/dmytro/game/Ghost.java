package com.example.dmytro.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dmytro on 2/20/16.
 */
public class Ghost extends GameObject {

    private Bitmap spritesheet;
    private boolean playing;
    private Animation animation = new Animation();

    public Ghost(Bitmap res, int w, int h, int numFrames){
        x=50;
        //y = GamePanel.HEIGHT/2;
        y= GamePanel.HEIGHT-140;


        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for(int i = 0; i < image.length; i++){

            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);

        }

        animation.setFrames(image);
        animation.setDelay(50);

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
    public void update(){
        animation.update();
    }

    public boolean getPlaying()
    {
        return playing;
    }
}

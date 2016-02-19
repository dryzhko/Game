package com.example.dmytro.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Dmytro on 2/17/16.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 856; //dimensions, need to scale later
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -10;
    private MainThread thread;
    private Background bg;
    private Player player;

   //constructor
    public GamePanel(Context context)
    {
        super(context);

        //add callback to surface holder to intercept events on phone
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        //making the game panel focusable to handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
        public void surfaceDestroyed(SurfaceHolder holder){
        //stop loop when surface is destroyed
        boolean retry = true;
        while(retry)
        {
            try{        //try to stop thread and set to false, if fails goes to catch block and retry
                thread.setRunning(false);
                    thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
        public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background)); //instantiate background
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player), 100, 100, 5 );

        //start game loop when surface is created
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            player.setUp(true);
            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            return true;
        }


        return super.onTouchEvent(event);
    }

    public void update() {

        if (player.getPlaying())
        {
            bg.update();
            player.update();
        }

    }
    @Override
    public void draw(Canvas canvas){

        final float scaleFactorx = (float)getWidth()/WIDTH;    //gets width and height of phone screen and scales it accordingly
        final float scaleFactory = (float)getHeight()/HEIGHT;
        if(canvas!=null){
            final int savedState = canvas.save(); //before scaling
            canvas.scale(scaleFactorx, scaleFactory); //scaling happens
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState); //return to saved state to avoid infinite scaling

        }


    }


}

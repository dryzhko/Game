package com.example.dmytro.game;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Dmytro on 2/17/16.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

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
        //start game loop when surface is created
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void update(){

    }
}

package com.example.dmytro.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Dmytro on 2/17/16.
 */
public class MainThread extends Thread {
    private int FPS = 30;
    private double AverageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    //constructor
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        //cap fps at 30
        long startTime;
        long timeMS;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS; //each time you run through game loop, you want it to take this long

        while(running) {
            startTime = System.nanoTime(); //get start time
            canvas = null;

            //lock canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();  //everytime you go through game loop, update and draw once
                    this.gamePanel.draw(canvas);  //game loop should be called 30 times a second
                }
            } catch(Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){e.printStackTrace();}
                }
            }

            timeMS = (System.nanoTime() - startTime) / 1000000; //after going through 1 loop, want to get time in ms
            waitTime = targetTime - timeMS; //how long we wait to go through loop again

            try{
                this.sleep(waitTime); //make it wait that long
            } catch(Exception e){}
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS) //calculate average fps after 30 frames
            {
                AverageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(AverageFPS);  //print average fps to console every 30 frames
            }
        }

    }
    public void setRunning(boolean b){

        running = b;

    }

}

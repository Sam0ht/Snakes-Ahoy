package com.westmacott.tom.snakes.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;

/*
 * The paint thread concept here, and the way it runs the animation loop at a steady framerate, come from
 * http://blorb.tumblr.com/post/236799414/simple-java-android-game-loop
 */
public class PaintThread extends Thread implements BusModule {
	

	public static final String MSG_ACCELERATE = "Accelerate";
	
	public static final String MSG_INIT = "Initialise";
	
	private static final int LEVEL_START_SPEED_DELAY = 400;
	
	private SurfaceHolder mSurfaceHolder;
	
	private GameEngine gEngine;

	//for consistent rendering
	private long sleepTime;
	
	//amount of time to sleep for (in milliseconds)
	private long delay = LEVEL_START_SPEED_DELAY;

	//state of game (Running or Paused).
	int state = 1;
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;

	public PaintThread(SurfaceHolder surfaceHolder, Context context, Handler handler, GameEngine gEngineS) {

		//data about the screen
		mSurfaceHolder = surfaceHolder;

		gEngine=gEngineS;
		
		gEngineS.joinBus(this);
	}

	@Override
	public void join(MessageBus bus) {
		bus.subscribe(GameEngine.PAINTER_NAME, MSG_ACCELERATE, new MessageListener() {
			@Override
			public void recieve(String... data) {
				delay *= 0.93;
			}

			@Override
			public String id() {
				return "PainterAccelerate";
			}
		});
		bus.subscribe(GameEngine.PAINTER_NAME, MSG_INIT, new MessageListener() {
			@Override
			public void recieve(String... data) {
				delay = LEVEL_START_SPEED_DELAY;
			}

			@Override
			public String id() {
				return "PainterInit";
			}
		});
	}


	//This is the most important part of the code. It is invoked when the call to start() is
	//made from the SurfaceView class. It loops continuously until the game is finished or
	//the application is suspended.
	@Override
	public void run() {

		//UPDATE
		while (state==RUNNING) {
			//time before update
			long beforeTime = System.nanoTime();
			//This is where we update the game engine
			gEngine.update();
			//DRAW
			Canvas c = null;
			try {
				//lock canvas to allow us to write to it
				c = mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					c.drawARGB(255, 0, 0, 0);
					gEngine.draw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface locked
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}

			// Can request explicit GC here for debug purposes, to show number of objects created per loop:
			if (GameEngine.DEBUG) {
				System.gc();
			}
			
			//SLEEP
			//Sleep time. Time required to sleep to keep game consistent
			//This starts with the specified delay time (in milliseconds) then subtracts from that the
			//actual time it took to update and render the game. This allows our game to render smoothly.
			this.sleepTime = delay - ((System.nanoTime()-beforeTime)/1000000L);

			GameEngine.spareTime = this.sleepTime; 
			GameEngine.delayTime = delay;
			try {
				//actual sleep code
				if (sleepTime > 0){
					Thread.sleep(sleepTime);
				} 
				
			} catch (InterruptedException ex) {
				// nothing sensible to do here, probably won't happen and would only result in animation jumping forwards if it does.
			}
		}
	}

}

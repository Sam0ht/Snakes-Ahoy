package com.westmacott.tom.snakes.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

/*
 * The paint thread concept here, and the way it runs the animation loop at a steady framerate, come from
 * http://blorb.tumblr.com/post/236799414/simple-java-android-game-loop
 */
public class PaintThread extends Thread implements BusModule {
	
	private SurfaceHolder mSurfaceHolder;
	
	private GameEngine gameEngine;

	//for consistent rendering
	private long sleepTime;
	
	//state of game (Running or Paused).
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;
	int state = RUNNING;

	public PaintThread(SurfaceHolder surfaceHolder, GameEngine gEngineS) {

		//data about the screen
		mSurfaceHolder = surfaceHolder;

		gameEngine = gEngineS;
		
		gameEngine.joinBus(this);
		
		setName("Game Paint Thread");
	}

	@Override
	public void join(MessageBus bus) {
		
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
			gameEngine.update();
			//DRAW
			Canvas c = null;
			try {
				//lock canvas to allow us to write to it
				c = mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					c.drawARGB(255, 0, 0, 0);
					gameEngine.draw(c);
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
			this.sleepTime = gameEngine.delay() - ((System.nanoTime()-beforeTime)/1000000L);

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

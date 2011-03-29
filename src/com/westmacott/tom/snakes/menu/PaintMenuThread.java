package com.westmacott.tom.snakes.menu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
 * The paint thread concept here, and the way it runs the animation loop at a steady framerate, come from
 * http://blorb.tumblr.com/post/236799414/simple-java-android-game-loop
 */
public class PaintMenuThread extends Thread {
	

	public static final String MSG_ACCELERATE = "Accelerate";
	
	public static final String MSG_INIT = "Initialise";
	
	private SurfaceHolder mSurfaceHolder;
	
	//state of game (Running or Paused).
	int state = 1;
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;

	private final GameMenu menu;

	public PaintMenuThread(SurfaceHolder surfaceHolder, GameMenu menu) {
		//data about the screen
		mSurfaceHolder = surfaceHolder;
		this.menu = menu;
		setName("Menu Paint Thread");
	}

	//This is the most important part of the code. It is invoked when the call to start() is
	//made from the SurfaceView class. It loops continuously until the game is finished or
	//the application is suspended.
	@Override
	public void run() {

		//UPDATE
		while (state==RUNNING) {
			
			//This is where we update the game engine
			//DRAW
			Canvas c = null;
			try {
				//lock canvas to allow us to write to it
				c = mSurfaceHolder.lockCanvas();
				if (c != null) {
					synchronized (mSurfaceHolder) {
						c.drawARGB(255, 0, 0, 0);
						menu.draw(c);
					}
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface locked
				if (c != null) {
					mSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
			
		}
	}

}

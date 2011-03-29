package com.westmacott.tom.snakes.engine;


import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.westmacott.tom.snakes.NESWTouchListener;
import com.westmacott.tom.snakes.Properties;
import com.westmacott.tom.snakes.Snakes;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class GameEngineView extends SurfaceView implements SurfaceHolder.Callback
{
	private Snakes activity;

	//our Thread class which houses the game loop
	private PaintThread thread;

	private final MessageBus bus;

	//class constructors
	public GameEngineView(Snakes context, MessageBus messageBus){
		super(context, null);
		activity = context;
		this.bus = messageBus;
		
		GameEngine.INSTANCE.join(bus);
		
		initView();
	}

	private void initView(){
		SurfaceHolder holder = getHolder();
		holder.addCallback( this);
		setFocusable(true);
	}
	
	@Override 
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i("Game Engine View", "Surface Created");
		final Properties properties = new Properties(activity.getPreferences(Context.MODE_PRIVATE));
		
//		new RefreshHandler().join(bus);
		
		setOnTouchListener(NESWTouchListener.INSTANCE);
		NESWTouchListener.INSTANCE.join(bus);
		
		GameEngine gameEngine = GameEngine.INSTANCE;
		thread = new PaintThread(getHolder(), gameEngine);
		if (!GameEngine.isLevelInProgress()) {
			gameEngine.init(getWidth(), getHeight(), properties);
		}
		thread.start();
	}

	@Override 
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override 
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		//code to end gameloop
		thread.state=PaintThread.PAUSED;
		while (retry) {
			try {
				//code to kill Thread
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	
	
}




package com.westmacott.tom.snakes.engine;

import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.westmacott.tom.snakes.Snakes;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;

public class GameEngineView extends SurfaceView implements SurfaceHolder.Callback
{
	private GameEngine gEngine;

	private Snakes activity;

	//our Thread class which houses the game loop
	private PaintThread thread;

	//class constructors
	public GameEngineView(Snakes contextS, GameEngine engine){
		super(contextS, null);
		activity=contextS;
		gEngine = engine;
		initView();
	}
	
	private void initView(){
		//initialize our screen holder
		SurfaceHolder holder = getHolder();
		holder.addCallback( this);
		setFocusable(true);
	}
	
	//these methods are overridden from the SurfaceView super class. They are automatically called 
	//    when a SurfaceView is created, resumed or suspended.
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

	@Override 
	public void surfaceCreated(SurfaceHolder arg0) {
		thread = new PaintThread(getHolder(), activity, new Handler(), gEngine);
		gEngine.init(getWidth(), getHeight());
		gEngine.joinBus(new RefreshHandler());
		thread.start();
	}
	
    class RefreshHandler extends Handler implements BusModule {

    	@Override
    	public void handleMessage(Message msg) {
    		String toast = (String) msg.obj;
    		Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
    	}

    	@Override
    	public void join(MessageBus bus) {
    		bus.subscribe(GameEngine.TOASTER_NAME, GameEngine.MSG_SHOW_TOAST, new MessageListener() {
    			@Override
    			public void recieve(String... data) {
    				sendMessage(Message.obtain(RefreshHandler.this, 0, data[0]));
    			}
    			@Override
    			public String id() {
    				return "ActivityShowToast";
    			}
    		});
    	}
    };
	
}




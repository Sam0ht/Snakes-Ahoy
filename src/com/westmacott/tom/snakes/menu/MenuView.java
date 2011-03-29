package com.westmacott.tom.snakes.menu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Properties;
import com.westmacott.tom.snakes.Snakes;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;

public class MenuView extends SurfaceView implements SurfaceHolder.Callback {

	private final GameMenu menu;
	private PaintMenuThread thread;
	private final Snakes activity;
	
	public MenuView(Snakes context, MessageBus menuBus) {
		super(context);
		this.activity = context;
		final Properties properties = new Properties(context.getPreferences(Context.MODE_PRIVATE));
		menu = new GameMenu(menuBus, properties, false);
		new RefreshHandler().join(menuBus);
		initView();
	}
	
	private void initView(){
		getHolder().addCallback( this);
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("Menu View", "Surface Changed");
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("Menu View", "Surface Created");
		
		int textSize = (int)(getHeight() * 0.04);
		Colour.updateTextSize(textSize);
		
		menu.open(textSize, GameEngine.isLevelInProgress());
		
		setOnTouchListener(new MenuTouchListener(menu));
		
		startPaintThread();
	
		stopPaintThread();
	}

	private void wait100() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startPaintThread() {
		thread = new PaintMenuThread(getHolder(), menu);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("Menu View", "Surface Destroyed");
		stopPaintThread();
	}

	private void stopPaintThread() {
		if (thread == null) {
			return;
		}
		wait100();
		boolean retry = true;
		//code to end gameloop
		thread.state = PaintMenuThread.PAUSED;
		while (retry) {
			try {
				//code to kill Thread
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		thread = null;
	}

	public void setScrolling(boolean scrolling) {
		if (scrolling) {
			startPaintThread();
		} else {
			stopPaintThread();
		}
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
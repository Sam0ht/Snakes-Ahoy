package com.westmacott.tom.snakes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.engine.GameEngineView;
import com.westmacott.tom.snakes.menu.GameMenu;
import com.westmacott.tom.snakes.menu.MenuView;
import com.westmacott.tom.snakes.messagebus.GameBus;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;

public class Snakes extends Activity {
	
	private MessageBus messageBus = new GameBus();

	private GameEngineView gameEngineView;

	private MenuView menuView;
	
	public Snakes() {
		subscribeToBus();
	}
	
	private void subscribeToBus() {
		messageBus.subscribe(GameEngine.MENU_NAME, GameMenu.MSG_START_LEVEL, new MessageListener() {
			
			@Override
			public void recieve(String... data) {
				beginPlay();
			}
			
			@Override
			public String id() {
				return "ActivityLevelSelectedListener";
			}
		});
	}
	
	public void beginPlay() {
		setContentView(gameEngineView);
	}
	
	public void returnToMenu() {
		setContentView(menuView);
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		gameEngineView = new GameEngineView(this, messageBus);
		menuView = new MenuView(this, messageBus);
		setContentView(menuView);
    }
    
	@Override
	protected void onPause() {
		returnToMenu();
		super.onPause();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		returnToMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	
}
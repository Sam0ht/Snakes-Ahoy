package com.westmacott.tom.snakes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;

import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.engine.GameEngineView;

public class Snakes extends Activity {
	
    private MenuButtonListener menuListener = new MenuButtonListener(); 

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		startup();
    }
    
	@Override
	protected void onPause() {
		menuListener.returnToMenu();
		super.onPause();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menuListener.returnToMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	public void startup() {
		final GameEngine gEngine = new GameEngine(new Properties(getPreferences(MODE_PRIVATE)));
		
		GameEngineView gameEngineView = new GameEngineView(this, gEngine);
		setContentView(gameEngineView);

		NESWTouchListener listener = new NESWTouchListener();
		gEngine.joinBus(listener);
		gameEngineView.setOnTouchListener(listener);
		
		gEngine.joinBus(menuListener);
		
	}
	
	public static class Properties {
		private final SharedPreferences preferences;

		public Properties(SharedPreferences preferences) {
			this.preferences = preferences;
		}
		
		public boolean hasLevelBeenCompleted(int levelId) {
			return preferences.getBoolean(preferencesKey(levelId), false);
		}

		public void registerLevelCompleted(int levelId) {
			Editor editor = preferences.edit();
			editor.putBoolean(preferencesKey(levelId), true);
			editor.commit();
		}
		
		public String preferencesKey(int levelId) {
			return "SnakesLevel" + levelId + "Completed";
		}
	}

	
}
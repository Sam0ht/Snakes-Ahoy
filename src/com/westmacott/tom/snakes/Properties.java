package com.westmacott.tom.snakes;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Properties {
	private final SharedPreferences preferences;

	public Properties(SharedPreferences preferences) {
		this.preferences = preferences;
	}
	
	public boolean hasLevelBeenCompleted(int levelId) {
		return preferences.getBoolean(completionOf(levelId), false);
	}
	
	public int bestScore(int levelId) {
		return preferences.getInt(scoreFor(levelId), 0);
	}

	public void registerLevelCompleted(int levelId, int score) {
		Editor editor = preferences.edit();
		editor.putBoolean(completionOf(levelId), true);
		if (score > preferences.getInt(scoreFor(levelId), 0)) {
			editor.putInt(scoreFor(levelId), score);
		}
		editor.commit();
	}
	
	private String scoreFor(int levelId) {
		return "ScoreForLevel" + levelId;
	}

	public String completionOf(int levelId) {
		return "SnakesLevel" + levelId + "Completed";
	}
}
package com.westmacott.tom.snakes.engine;

import android.graphics.Canvas;

public class ScoreKeeper {
	private int score;
	private final int applesPerLevel;
	
	public ScoreKeeper(int applesPerLevel) {
		this.applesPerLevel = applesPerLevel;
	}
	public void draw(Canvas c) {
	}
	public boolean incrementScore() {
		score++;
		return score >= applesPerLevel;
	}
	public void resetScore() {
		score = 0;
	}
	public int getScore() {
		return score;
	}
}
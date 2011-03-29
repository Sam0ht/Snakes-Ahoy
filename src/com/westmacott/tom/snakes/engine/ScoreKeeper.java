package com.westmacott.tom.snakes.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.entities.Apple;
import com.westmacott.tom.snakes.entities.Grid;

public class ScoreKeeper {
	
	private static final int APPLES_PER_LEVEL = 10;
	

	private final RectF announceRect = new RectF(0,0,0,0);
	private final Rect infoBarRect = new Rect(0,0,0,0);
	
	private int apples;
	private int boost = 1;
	private int score;
	private int squareSizeX;
	private int squareSizeY;

	private final Paint messagePaint = new Paint();
	private final Paint backgroundPaint = new Paint();
	private final Paint scorePaint = new Paint();

	private int lastBonus = 0;
	private int showBonus = 0;


	private Location lastEatenWhere;
	
	public ScoreKeeper(int screenWidth, int screenHeight) {
		squareSizeX = screenWidth / Grid.COLS;
		squareSizeY = screenHeight / (Grid.ROWS + 1);
		scorePaint.setARGB(255, 0, 0, 150);
		scorePaint.setTextSize((float) (squareSizeY * 0.8));
		backgroundPaint.setARGB(150, 90, 0, 255);

		messagePaint.setARGB(255, 150, 150, 0);
		messagePaint.setTextSize((float) (squareSizeY * 1.5));
		messagePaint.setTextAlign(Align.CENTER);
		messagePaint.setStyle(Style.STROKE);
		messagePaint.setStrokeMiter(8);
		messagePaint.setStrokeJoin(Join.ROUND);
//		messagePaint.setStrokeCap(Cap.ROUND);
		
	}
	
	public void draw(Canvas c) {
		drawApplesEaten(c);
		final int boostBarX =  (int) (((float)Grid.COLS - ((float)boost / (float)10)) * (float)squareSizeX);
		final Rect rest = move(infoBarRect, APPLES_PER_LEVEL * squareSizeX, 0, (int)boostBarX, squareSizeY);
		c.drawRect(rest, Colour.LIGHT_GREY.paint);
		c.drawText(String.valueOf(score), (float) (Grid.COLS * squareSizeX * 0.5), squareSizeY, Colour.DARK_RED.paint);
		drawBoostBar(c, boostBarX);
		
		if (showBonus > 0) {
			showBonus--;
			int x = (lastEatenWhere.x + 1) * squareSizeX;
			int y = (lastEatenWhere.y - 1) * squareSizeY;
			int xSize = squareSizeX * 2;
			int ySize = squareSizeY;
			int rx = squareSizeX / 2; 
			int ry = squareSizeY / 2; 
			announceRect.set(x - xSize, y - ySize, x + xSize, y + ySize);
			c.drawRoundRect(announceRect, rx, ry, backgroundPaint);
			c.drawRoundRect(announceRect, rx, ry, messagePaint);
			c.drawText(String.valueOf(lastBonus), x, y + (squareSizeY / 2), messagePaint);
		}
	}

	private void drawBoostBar(Canvas c, final int boostBarX) {
		final Rect boostRect = move(infoBarRect, boostBarX, 0, Grid.COLS * squareSizeX, squareSizeY);
		c.drawRect(boostRect, Colour.RED.paint);
	}

	private void drawApplesEaten(Canvas c) {
		for(int i = 0; i < apples; i++) {
			final int left = i * squareSizeX;
			final Rect rect = move(infoBarRect, left, 0, left + squareSizeX - 1, squareSizeY);
			final Paint paint = Apple.COLOUR.paint;
			c.drawRect(rect, paint);
		}
	}
	
	public boolean appleEaten(Location where) {
		lastEatenWhere = where;
		lastBonus = 100 * boost;
		showBonus = 3;
		score += lastBonus; 
		apples++;
		boost += 20;
		if (boost > 100) {
			boost = 100;
		}
		return apples >= APPLES_PER_LEVEL;
	}
	public void reset() {
		apples = 0;
		boost = 1;
	}
	public void resetScore() {
		reset();
		score = 0;
	}
	public int getApples() {
		return apples;
	}
	public int getScore() {
		return score;
	}
	public void step() {
		if (boost > 1) {
			boost--;
		}
	}
	private static Rect move(Rect r, int left, int top, int right, int bottom) {
		r.set(left, top, right, bottom);
		return r;
	}
}
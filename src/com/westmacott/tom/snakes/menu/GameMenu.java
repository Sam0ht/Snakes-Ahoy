package com.westmacott.tom.snakes.menu;

import static com.westmacott.tom.snakes.menu.GameMenu.CompletionState.AVAILABLE;
import static com.westmacott.tom.snakes.menu.GameMenu.CompletionState.COMPLETED;
import static com.westmacott.tom.snakes.menu.GameMenu.CompletionState.FUTURE;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.Properties;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.engine.Level;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class GameMenu implements BusModule {

	public static final String MSG_START_LEVEL = "startLevel";
	
	private int leftMargin = 50;
	private int topMargin = 50;
	private int yOffset = 0;
	
	private MessageBus gameBus;
	private List<Option> options = new ArrayList<Option>();

	private final Properties properties;

	private int menuHeightPx;
	private int screenHeight;
	private int scrollLimit;
	private final Level[] levels = Level.all;
	private int textSize;
	
	private static final Paint suffixPaint = new Paint();
	static {
		suffixPaint.setARGB(255, 125, 125, 125);
		suffixPaint.setTextAlign(Align.LEFT);
	}
	
	public static enum CompletionState {
		COMPLETED(Colour.GREEN),
		AVAILABLE(Colour.YELLOW),
		FUTURE(Colour.LIGHT_GREY);
		public final Colour colour;
		private CompletionState(Colour colour) {
			this.colour = colour;
		}
	}
	
	public static abstract class Option {
		protected final int yPosition;
		protected final CompletionState completion;
		
		public Option (int position, CompletionState completion) {
			this.yPosition = position;
			this.completion = completion;
		}
		public abstract String name();
		public abstract String suffix();
		public abstract String levelId();
	}
	
	public static class LevelOption extends Option {
		private final Level level;
		private final int bestScore;
		public LevelOption(Level level, int position, CompletionState completion, int bestScore) {
			super(position, completion);
			this.level = level;
			this.bestScore = bestScore;
		}
		@Override
		public String name() {
			return level.name;
		}
		@Override
		public String levelId() {
			return String.valueOf(level.id);
		}
		@Override
		public String suffix() {
			return CompletionState.COMPLETED.equals(completion) ? "[ " + bestScore + " ]" : "";
		}
	}
	
	public static class ResumeOption extends Option {

		public static final String RESUME = "Resume";

		public ResumeOption(int position, CompletionState completion) {
			super(position, completion);
		}

		@Override
		public String name() {
			return "(resume current level)";
		}

		@Override
		public String levelId() {
			return RESUME;
		}

		@Override
		public String suffix() {
			return "";
		}
	}

	public GameMenu(MessageBus gameBus, Properties properties, boolean running) {
		this.properties = properties;
		join(gameBus);
	}
	
	// do this instead of creating a new menu
	public void open(int textSize, boolean running) {
		this.textSize = textSize;
		suffixPaint.setTextSize((float) (textSize * 0.8));
		leftMargin = textSize * 2;
		topMargin = textSize * 2;
		screenHeight = textSize * 25;
		createMenuEntries(running);
	}

	private void createMenuEntries(boolean running) {
		List<Option> newOptions = new ArrayList<Option>();
		int y = topMargin;
		final int yStep = (int)(textSize * 2);
		if (running) {
			newOptions.add(new ResumeOption(y += yStep, AVAILABLE));
		}
		boolean levelAvailable = true;
		for (Level level : this.levels) {
			final boolean levelCompleted = this.properties.hasLevelBeenCompleted(level.id);
			final CompletionState completion = levelCompleted ? COMPLETED : levelAvailable ? AVAILABLE : FUTURE;
			final int bestScore = this.properties.bestScore(level.id);
			newOptions.add(new LevelOption(level, y += yStep, completion, bestScore));
			levelAvailable = levelCompleted;
		}
		this.menuHeightPx = y;
		this.scrollLimit = Math.max(0, (menuHeightPx + (5 * topMargin)) - screenHeight);
		this.options = newOptions;
	}

	public void draw(Canvas c) {
		c.drawText("S N A K E S", leftMargin, topMargin + yOffset, Colour.GREEN.paint);
		for (Option level : this.options) {
			c.drawText(level.name(), leftMargin, level.yPosition + yOffset, level.completion.colour.paint);
			if (CompletionState.COMPLETED.equals(level.completion)) {
				c.drawText(level.suffix(), (float) (leftMargin * 1.2), level.yPosition + yOffset + textSize, suffixPaint);
			}
		}
	}
	
	protected void positionSelected(Location location) {
		Option selected = findSelectedLevel(location);
		if (selected.completion.equals(FUTURE) && !GameEngine.DEBUG) {
			Log.i("Level Access Denied", selected.levelId());
			this.gameBus.send(GameEngine.TOASTER_NAME, GameEngine.MSG_SHOW_TOAST, "Available once previous level cleared");
		} else {
			Log.i("Level Selected", selected.levelId());
			Log.i("Sending ", GameEngine.MENU_NAME + "," + MSG_START_LEVEL + " to bus " + gameBus);
			gameBus.send(GameEngine.MENU_NAME, MSG_START_LEVEL, String.valueOf(selected.levelId()));
		}
	}

	public Option findSelectedLevel(Location location) {
		int minDistance = 10000;
		Option selected = null;
		for (Option option : this.options) {
			int distance = Math.abs((option.yPosition + yOffset) - location.y);
			if (distance < minDistance) {
				minDistance = distance;
				selected = option;
			}
		}
		return selected;
	}
	
	public void drag(int yDiff) {
		Log.i("GameMenu", "Scroll by " + yDiff);
		yOffset += yDiff;
		if (yOffset > 0) {
			yOffset = 0;
		} else if (yOffset < -scrollLimit) {
			yOffset = -scrollLimit;
		}
	}

	@Override
	public void join(MessageBus bus) {
		this.gameBus = bus;
	}

}
package com.westmacott.tom.snakes.engine;

import static com.westmacott.tom.snakes.engine.GameMenu.CompletionState.AVAILABLE;
import static com.westmacott.tom.snakes.engine.GameMenu.CompletionState.COMPLETED;
import static com.westmacott.tom.snakes.engine.GameMenu.CompletionState.FUTURE;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.NESWTouchListener;
import com.westmacott.tom.snakes.Snakes.Properties;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.GameBus;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;

public class GameMenu implements BusModule {

	private static final String LISTENER_ID_MENU_LIFT = "MenuLift";

	public static final String MSG_START_LEVEL = "startLevel";
	
	private int leftMargin = 50;
	private int topMargin = 50;
	
	private MessageBus gameBus;
	private List<Option> options = new ArrayList<Option>();

	private final Properties properties;

	private final boolean running;

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
		public abstract String levelId();
	}
	
	public static class LevelOption extends Option {
		private final Level level;
		public LevelOption(Level level, int position, CompletionState completion) {
			super(position, completion);
			this.level = level;
		}
		@Override
		public String name() {
			return level.name;
		}
		@Override
		public String levelId() {
			return String.valueOf(level.id);
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
		
	}

	public GameMenu(Level[] levels, GameBus gameBus, Properties properties, int textSize, boolean running) {
		this.properties = properties;
		this.running = running;
		leftMargin = (int)(textSize * 2);
		topMargin = (int)(textSize * 2);
		
		createMenuEntries(levels, textSize);
		
		join(gameBus);
		
	}

	private void createMenuEntries(Level[] levels, int textSize) {
		List<Option> newOptions = new ArrayList<Option>();
		int y = textSize * 3;
		if (this.running) {
			newOptions.add(new ResumeOption(y += (int)(textSize * 2), AVAILABLE));
		}
		boolean levelAvailable = true;
		for (Level level : levels) {
			final boolean levelCompleted = this.properties.hasLevelBeenCompleted(level.id);
			final CompletionState completion = levelCompleted ? COMPLETED : levelAvailable ? AVAILABLE : FUTURE;
			newOptions.add(new LevelOption(level, y += (int)(textSize * 2), completion));
			levelAvailable = levelCompleted;
		}
		this.options = newOptions;
	}

	public void draw(Canvas c) {
		c.drawText("S N A K E S", leftMargin, topMargin, Colour.GREEN.paint);
		for (Option level : this.options) {
			c.drawText(level.name(), leftMargin, level.yPosition, level.completion.colour.paint);
		}
	}
	
	private void positionSelected(String position) {
		Location location = new Location(position);
		Option selected = findSelectedLevel(location);
		if (selected.completion.equals(FUTURE) && !GameEngine.DEBUG) {
			this.gameBus.send(GameEngine.TOASTER_NAME, GameEngine.MSG_SHOW_TOAST, "Available once previous level cleared");
		} else {
			gameBus.unSubscribe(LISTENER_ID_MENU_LIFT);
			gameBus.send(GameEngine.MENU_NAME, MSG_START_LEVEL, String.valueOf(selected.levelId()));
		}
	}

	public Option findSelectedLevel(Location location) {
		int minDistance = 10000;
		Option selected = null;
		for (Option option : this.options) {
			int distance = Math.abs(option.yPosition - location.y);
			if (distance < minDistance) {
				minDistance = distance;
				selected = option;
			}
		}
		return selected;
	}
	
	private void joinBus() {
		gameBus.subscribe(GameEngine.TOUCHSCREEN_NAME, NESWTouchListener.MSG_LIFT, new MessageListener() {
	
			@Override
			public void recieve(String...data) {
				positionSelected(data[0]);
			}
	
			@Override
			public String id() {
				return LISTENER_ID_MENU_LIFT;
			}
		});
	}

	@Override
	public void join(MessageBus bus) {
		this.gameBus = bus;
		this.gameBus.unSubscribe(LISTENER_ID_MENU_LIFT);
		joinBus();
	}
	
}
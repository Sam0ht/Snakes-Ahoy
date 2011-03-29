package com.westmacott.tom.snakes.engine;


import static com.westmacott.tom.snakes.NESWTouchListener.MSG_SWIPE;
import static com.westmacott.tom.snakes.engine.GameEngine.GameState.OVER;
import static com.westmacott.tom.snakes.engine.GameEngine.GameState.READY;
import static com.westmacott.tom.snakes.engine.GameEngine.GameState.RUNNING;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.Log;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Direction;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.Properties;
import com.westmacott.tom.snakes.entities.Apple;
import com.westmacott.tom.snakes.entities.Grid;
import com.westmacott.tom.snakes.entities.Snake;
import com.westmacott.tom.snakes.entities.Wall;
import com.westmacott.tom.snakes.menu.GameMenu;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;


public class GameEngine implements BusModule {
	
	public static final boolean DEBUG = false;
	
	public static final String SNAKE_NAME = "Snake";
	public static final String APPLE_NAME = "Apple";
	public static final String WALL_NAME = "Wall";
	public static final String PAINTER_NAME = "Painter";
	public static final String TOUCHSCREEN_NAME = "TouchScreen";
	public static final String MENU_NAME = "GameMenu";
	public static final String TOASTER_NAME = "MessageToaster";
	public static final String LEVELS_NAME = "GameLevels";
	
	public static final String MSG_SHOW_TOAST = "ShowToast";
	public static final String MSG_ACCELERATE = "Accelerate";
	public static final String MSG_INIT = "Initialise";
	
	private static final int LEVEL_START_SPEED_DELAY = 400;

	//amount of time to sleep for (in milliseconds)
	private long delay = LEVEL_START_SPEED_DELAY;

	
	public static enum GameState {
		READY,
		RUNNING,
		OVER
	}

	private static GameState state = GameState.READY;

	private static boolean levelInProgress = false;
	
	public static boolean isLevelInProgress() {
		return levelInProgress;
	}

	public static GameState state() {
		return state;
	}

	private final Paint messagePaint = new Paint();
	private final Paint backgroundPaint = new Paint();
	
	private Snake snake;
	
	public int screenHeight = 320;
	public int screenWidth = 240;
	
	private Grid grid;
	
	public static class UserMessage {
		public final String announcement;
		public final String instruction;

		private UserMessage(String announcement, String instruction) {
			this.announcement = announcement;
			this.instruction = instruction;
		}
		public static UserMessage of(String announcement, String instruction) {
			return new UserMessage(announcement, instruction);
		}
	}
	
	private UserMessage userMessage;
	private final int numApples = 3;
	
	private MessageBus gameBus;
	
	private int currentLevel = 0;
	
	private ScoreKeeper scoreKeeper;
	
	private final Level[] levels = Level.all;
	private Properties properties;
	private int textSize = 10;
	
	private final Rect announceRect = new Rect(0,0,0,0);
	
	public static final GameEngine INSTANCE = new GameEngine();
	
	private GameEngine() {
	}
	
	public void init(int width, int height, Properties properties) {
		this.properties = properties;
		
		screenWidth = width;
		screenHeight = height;
		textSize = (int)(height * 0.04);
		Colour.updateTextSize(textSize);
		
		messagePaint.setARGB(255, 150, 150, 0);
		messagePaint.setTextSize(textSize);
		messagePaint.setTextAlign(Align.CENTER);
		
		backgroundPaint.setARGB(210, 90, 0, 255);
		
		scoreKeeper = new ScoreKeeper(screenWidth, screenHeight);
		 
		reset();
	}
	
	@Override
	public void join(MessageBus bus) {
		this.gameBus = bus;
		subscribeToBus();
	}

	private void subscribeToBus() {
		gameBus.subscribe(TOUCHSCREEN_NAME, MSG_SWIPE, new MessageListener() {
	
			@Override
			public void recieve(String...data) {
				processSwipe(data[0]);
			}
	
			@Override
			public String id() {
				return "GameEngineSwipe";
			}
		});
		gameBus.subscribe(APPLE_NAME, Apple.MSG_EATEN, new MessageListener() {
	
			@Override
			public void recieve(String...data) {
				appleEaten(data[0]);
			}
	
			@Override
			public String id() {
				return "GameEngineAppleEaten";
			}
		});
		
		Log.i("Game Engine", "Subscribing to " + MENU_NAME + "," + GameMenu.MSG_START_LEVEL + " on bus " + gameBus);
		
		gameBus.subscribe(MENU_NAME, GameMenu.MSG_START_LEVEL, new MessageListener() {

			@Override
			public void recieve(String... data) {
				Log.i("Game Engine", "Starting Level " + data[0]);
				startLevel(data[0]);
			}

			@Override
			public String id() {
				return "GameEngineStartLevel";
			}
		});
		
		gameBus.subscribe(PAINTER_NAME, MSG_ACCELERATE, new MessageListener() {
			@Override
			public void recieve(String... data) {
				delay *= 0.93;
			}

			@Override
			public String id() {
				return "GameEngineAccelerate";
			}
		});
	}

	public void processSwipe(String directionString) {
		final Direction direction = Direction.valueOf(directionString);
		switch (state) {
		case READY:
			if (Direction.NORTH.equals(direction)) {
				collectGarbageAndWait(100);
				state = GameState.RUNNING;
				levelInProgress = true;
			}
			break;
		case OVER:
			if (Direction.SOUTH.equals(direction)) {
				scoreKeeper.resetScore(); 
				startCurrentLevel();
			}
			break;
		case RUNNING:
			snake.turn(direction);
			break;
		}
	}

	public void appleEaten(String where) {
		boolean levelCompleted = scoreKeeper.appleEaten(Location.fromString(where));
		
		if (levelCompleted) {
			state = GameState.OVER;
			levelInProgress = false;
			recordCompleted(levels[currentLevel].id, scoreKeeper.getScore());
			currentLevel++;
			if (currentLevel < levels.length) {
				userMessage = UserMessage.of("Level Completed :)", "Swipe down to continue");
			} else {
				currentLevel = 0;
				userMessage = UserMessage.of("Game Completed :D", "Well Done");
			}
		}
	}
	
	private static void collectGarbageAndWait(int millis) {
		System.gc();
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	private void recordCompleted(int levelId, int score) {
		this.properties.registerLevelCompleted(levelId, score);
	}

	public void reset() {
		if (this.scoreKeeper != null) {
			this.scoreKeeper.reset();
		}
		
		final Level level = levels[currentLevel];
		this.snake = new Snake(level.startLocation, 5, Direction.SOUTH, SNAKE_NAME);
		this.grid = new Grid(screenWidth, screenHeight);
		
		this.snake.join(gameBus);
		this.grid.join(gameBus);
		
		new Wall(WALL_NAME, level.data).takeUpPositionOn(grid);
		
		for (int i = 0; i < numApples ; i++) {
			Apple.randomlyPlaceOn(grid);
		}
		snake.takeUpPositionOn(grid);
		
		delay = LEVEL_START_SPEED_DELAY;
	}
	
	public void update() {
		if (RUNNING.equals(state)) {
			boolean died = snake.update(grid);
			if (died) {
				state = GameState.OVER;
				levelInProgress = false;
				userMessage = UserMessage.of("Game Over", "Swipe down to continue");
			}
			scoreKeeper.step();
		}
	}
	
	public void joinBus(BusModule module) {
		module.join(gameBus);
	}
	
	public long delay() {
		return delay;
	}

	public void draw(Canvas c) {
		grid.draw(c);
		scoreKeeper.draw(c);
		if (READY.equals(state) || OVER.equals(state)) {
			announcement(c);
		}
	}

	public void announcement(Canvas c) {
		int midHeight = screenHeight / 2;
		announceRect.set(textSize, midHeight - (int)(textSize * 2), screenWidth - textSize, midHeight + (3 * textSize));
		c.drawRect(announceRect, backgroundPaint);
		c.drawText(userMessage.announcement, screenWidth / 2, midHeight, messagePaint);
		c.drawText(userMessage.instruction, screenWidth / 2, midHeight + (textSize * 2), messagePaint);
	}

	public void startLevel(String levelId) {
		if (!levelId.equals(GameMenu.ResumeOption.RESUME)) {
			currentLevel = findLevelById(levelId);
			levelInProgress = false;
		}
		if (!levelInProgress) {
			reset();
		}
		announceLevel();
	}

	private void announceLevel() {
		state = GameState.READY;
		userMessage = UserMessage.of("\"" + levels[currentLevel].name + "\"", "Swipe Up to Start");
	}

	private void startCurrentLevel() {
		levelInProgress = false;
		reset();
		announceLevel();
	}

	public int findLevelById(String levelId) {
		for(int i = 0; i < levels.length; i++) {
			if (String.valueOf(levels[i].id).equals(levelId)) {
				return i;
			}
		}
		// this is an error condition, but better to do nothing than crash (!)
		return currentLevel;
	}
}
 
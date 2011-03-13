package com.westmacott.tom.snakes.engine;


import static com.westmacott.tom.snakes.NESWTouchListener.MSG_SWIPE;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Direction;
import com.westmacott.tom.snakes.Grid;
import com.westmacott.tom.snakes.MenuButtonListener;
import com.westmacott.tom.snakes.Snakes.Properties;
import com.westmacott.tom.snakes.entities.Apple;
import com.westmacott.tom.snakes.entities.Snake;
import com.westmacott.tom.snakes.entities.Wall;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.GameBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;


public class GameEngine {
	
	public static final String SNAKE_NAME = "Snake";
	public static final String APPLE_NAME = "Apple";
	public static final String WALL_NAME = "Wall";
	public static final String PAINTER_NAME = "Painter";
	public static final String TOUCHSCREEN_NAME = "TouchScreen";
	public static final String MENU_NAME = "GameMenu";
	public static final String TOASTER_NAME = "MessageToaster";
	public static final String LEVELS_NAME = "GameLevels";
	
	public static final int APPLES_PER_LEVEL = 10;
	

	private Paint messagePaint = new Paint();
	private Paint backgroundPaint = new Paint();
	
	private Snake snake;
	
	public static enum GameState {
		MENU,
		READY,
		RUNNING,
		OVER
	}
	
	private static GameState state = GameState.MENU;
	
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
	private int numApples = 3;
	
	private GameBus gameBus = new GameBus();
	
	private int currentLevel = 0;
	private boolean levelInProgress = false;
	
	private GameMenu menu;

	private final ScoreKeeper scoreKeeper = new ScoreKeeper(APPLES_PER_LEVEL);
	
	private final Level[] levels = Level.all;
	private final Properties properties;
	public static final String MSG_SHOW_TOAST = "ShowToast";
	public static final boolean DEBUG = false;
	private int textSize = 10;
	
	public static double spareTime;
	public static long delayTime;
	private Rect announceRect = new Rect(0,0,0,0);
	
	public GameEngine(Properties properties) {
		this.properties = properties;
	}
	
	public static GameState state() {
		return state;
	}

	public void init(int width, int height) {
		state = GameState.MENU; // resume to Menu
		
		screenWidth = width;
		screenHeight = height;
		textSize = (int)(height * 0.04);
		Colour.updateTextSize(textSize);
		this.menu = new GameMenu(levels, gameBus, properties, textSize, levelInProgress);
		
		messagePaint.setARGB(255, 150, 150, 0);
		messagePaint.setTextSize(textSize);
		messagePaint.setTextAlign(Align.CENTER);
		
		backgroundPaint.setARGB(210, 90, 0, 255);
		
		joinBus();
		
	}
	
	private void joinBus() {
		gameBus.subscribe(TOUCHSCREEN_NAME, MSG_SWIPE, new MessageListener() {
	
			@Override
			public void recieve(String...data) {
				nextGameState(data);
			}
	
			@Override
			public String id() {
				return "GameEngineSwipe";
			}
		});
		gameBus.subscribe(APPLE_NAME, Apple.MSG_EATEN, new MessageListener() {
	
			@Override
			public void recieve(String...data) {
				appleEaten();
			}
	
			@Override
			public String id() {
				return "GameEngineAppleEaten";
			}
		});
		gameBus.subscribe(MENU_NAME, MenuButtonListener.MSG_OPEN_MENU, new MessageListener() {
			
			@Override
			public void recieve(String...data) {
				state = GameState.MENU;
				menu = new GameMenu(levels, gameBus, properties, textSize, levelInProgress);  //TODO: rethink this one - should menu really activate/deactivate like this?
			}
			
			@Override
			public String id() {
				return "GameEngineOpenMenu";
			}
		});
		gameBus.subscribe(MENU_NAME, GameMenu.MSG_START_LEVEL, new MessageListener() {

			@Override
			public void recieve(String... data) {
				startLevel(data[0]);
			}

			@Override
			public String id() {
				return "GameEngineStartLevel";
			}
		});
	}

	public void nextGameState(String... data) {
		switch (state) {
		case READY:
			if (Direction.NORTH.equals(Direction.valueOf(data[0]))) {
				state = GameState.RUNNING;
				userMessage = UserMessage.of("", "");
				levelInProgress = true;
				collectGarbageAndWait(200);
			}
			break;
		case OVER:
			if (Direction.SOUTH.equals(Direction.valueOf(data[0]))) {
				startCurrentLevel();
			}
			break;
		case RUNNING:
		}
	}

	public void appleEaten() {
		boolean levelCompleted = scoreKeeper.incrementScore();
		
		if (levelCompleted) {
			recordCompleted(levels[currentLevel].id);
			currentLevel++;
			if (currentLevel < levels.length) {
				userMessage = UserMessage.of("Level Completed :)", "Swipe down to continue");
				state = GameState.OVER;
				levelInProgress = false;
			} else {
				userMessage = UserMessage.of("Game Completed :D", "Well Done");
				state = GameState.OVER;
				currentLevel = 0;
				levelInProgress = false;
			}
		}
	}
	
	private static void collectGarbageAndWait(int millis) {
		System.gc();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
	}

	private void recordCompleted(int levelId) {
		this.properties.registerLevelCompleted(levelId);
	}

	public void reset() {
		
		this.scoreKeeper.resetScore();
		
		final Level level = levels[currentLevel];
		this.snake = new Snake(level.startLocation, 5, Direction.SOUTH, SNAKE_NAME);
		this.grid = new Grid(screenWidth, screenHeight);
		
		this.snake.join(gameBus);
		this.grid.join(gameBus);
		joinBus();
		
		
		new Wall(WALL_NAME, level.data).takeUpPositionOn(grid);
		
		for (int i = 0; i < numApples ; i++) {
			Apple.randomlyPlaceOn(grid);
		}
		snake.takeUpPositionOn(grid);
		
		gameBus.send(PAINTER_NAME, PaintThread.MSG_INIT);
	}
	
	public void update() {
		switch (state) {
			case READY:
				break;
			case RUNNING:
				boolean died = snake.update(grid);
				if (died) {
					state = GameState.OVER;
					levelInProgress = false;
					userMessage = UserMessage.of("Game Over", "Swipe down to continue");
				}
				break;
			case OVER:
				break;
			default:
		}
	}
	
	public void joinBus(BusModule module) {
		module.join(gameBus);
	}

	public static int getRandomBetween(int min, int max) {
		return min + (int)(Math.random() * (max - min));
	}

	public void draw(Canvas c) {
		switch (state) {
		case MENU:
			this.menu.draw(c);
			break;
		case READY:
			drawGame(c);
			announcement(c);
			break;
		case RUNNING:
			drawGame(c);
			break;
		case OVER:
			drawGame(c);
			announcement(c);
			break;
		}
	}

	public void drawGame(Canvas c) {
		grid.showScore(scoreKeeper.getScore());
		grid.draw(c);
		if (DEBUG) {
			c.drawText(spareTime + "ms / " + delayTime + "ms", 10, 30, Colour.RED.paint);
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
 
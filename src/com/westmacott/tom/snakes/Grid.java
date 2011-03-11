package com.westmacott.tom.snakes;

import static com.westmacott.tom.snakes.engine.GameEngine.APPLE_NAME;
import static com.westmacott.tom.snakes.engine.GameEngine.PAINTER_NAME;
import static com.westmacott.tom.snakes.engine.GameEngine.SNAKE_NAME;
import static com.westmacott.tom.snakes.engine.PaintThread.MSG_ACCELERATE;
import static com.westmacott.tom.snakes.entities.Apple.MSG_EATEN;
import static com.westmacott.tom.snakes.entities.Snake.MSG_LENGTHEN;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.entities.Apple;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;


public class Grid implements BusModule {
	
	public static class Square {
		private boolean occupied = false;
		public int x;
		public int y;
		private Drawable occupiedBy;
		
		public Square(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void occupiedBy(Drawable occupant) {
			this.occupiedBy = occupant;
			this.occupied = true;
		}
		
		public void vacated() {
			this.occupied = false;
			this.occupiedBy = null;
		}
		
		public String getOccupiedByName() {
			return this.occupiedBy.getName();
		}
		
		public Drawable getOccupiedBy() {
			return this.occupiedBy;
		}

		public boolean isOccupied() {
			return occupied;
		}
	}
	
	private final int squareSizeX;
	private final int squareSizeY;
	
	private final Set<Square> occupiedSquares = Collections.synchronizedSet(new HashSet<Square>());
	
	private static final int COLS = 30;
	private static final int ROWS = 40;
	
	private final Square[][] squares;
	
	private MessageBus bus;
	private int score;

	
	public Grid(int xPixels, int yPixels) {
		squareSizeX = xPixels / COLS;
		squareSizeY = yPixels / (ROWS + 1);
		
		squares = new Square[COLS][ROWS];
		for(int i = 0; i < COLS; i++) {
			for(int j = 0; j < ROWS; j++) {
				squares[i][j] = new Square(i, j);
			}
		}
	}
	
	@Override
	public void join(MessageBus bus) {
		this.bus = bus;
	}

	public boolean occupy(int x, int y, Drawable occupant) {
		Square square = squares[x][y];
		synchronized (occupiedSquares) {
			this.occupiedSquares.add(square);
		}
		if (square.isOccupied()) {
			return handleCollisionBetween(square, square.getOccupiedBy(), occupant);
		}
		square.occupiedBy(occupant);
		return false;
	}
	

	private boolean handleCollisionBetween(Square square, Drawable oldOccupant, Drawable newOccupant) {
		if (SNAKE_NAME.equals(newOccupant.getName()) && APPLE_NAME.equals(oldOccupant.getName())) {
			// remove and replace apple
			square.occupiedBy(newOccupant);
			Apple.randomlyPlaceOn(this);
			
			bus.send(SNAKE_NAME, MSG_LENGTHEN);
			
			bus.send(PAINTER_NAME, MSG_ACCELERATE);
			
			bus.send(APPLE_NAME, MSG_EATEN);
			return false;
		}
		return true;
	}

	public void vacate(int x, int y) {
		Square square = squares[x][y];
		synchronized (occupiedSquares) {
			this.occupiedSquares.remove(square);
		}
		square.vacated();
	}
	
	private Set<Square> readOccupied() {
		synchronized (occupiedSquares) {
			return new HashSet<Square>(occupiedSquares);
		}
	}
	
	public void draw(Canvas c) {
		for(Square occupiedSquare : readOccupied()) {
			render(c, occupiedSquare);
		}
		for(int i = 0; i < score; i++) {
			final int left = i * squareSizeX;
			final Rect rect = new Rect(left, 0, left + squareSizeX - 1, squareSizeY);
			final Paint paint = Apple.COLOUR.paint;
			c.drawRect(rect, paint);
		}
		final Rect rest = new Rect(GameEngine.APPLES_PER_LEVEL * squareSizeX, 0, (getMaxColNum() + 1) * squareSizeX, squareSizeY);
		c.drawRect(rest, Colour.LIGHT_GREY.paint);
	}
	
	public void showScore(int score) {
		this.score = score;
	}

	private void render(Canvas c, Square square) {
		final int left = square.x * squareSizeX;
		final int top = (square.y + 1) * squareSizeY;
		final Rect rect = new Rect(left, top, left + squareSizeX, top + squareSizeY);
		final Paint paint = square.occupiedBy.getColour().paint;
		c.drawRect(rect, paint);
	}

	public int getMaxColNum() {
		return COLS - 1;
	}
	
	public int getMaxRowNum() {
		return ROWS - 1;
	}

	public Location getRandomFreeLocation() {
		Location newLocation;
		do {
			newLocation = getRandomLocation();
		} while (squares[newLocation.x][newLocation.y].occupied);
		return newLocation;
	}

	public Location getRandomLocation() {
		int maxX = getMaxColNum();
		int maxY = getMaxRowNum();
		int x = GameEngine.getRandomBetween(1, maxX - 1);
		int y = GameEngine.getRandomBetween(1, maxY - 1);
		return new Location(x, y);
	}

	public Location normalise(Location location) {
		int x = location.x;
		int y = location.y;
		if (x < 0) {
			x += COLS;
		}
		if (x >= COLS) {
			x -= COLS;
		}
		if (y < 0) {
			y += ROWS;
		}
		if (y >= ROWS) {
			y -= ROWS;
		}
		if (x != location.x || y != location.y) {
			return new Location(x, y);
		}
		return location;
	}

}

package com.westmacott.tom.snakes.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Direction;
import com.westmacott.tom.snakes.Drawable;
import com.westmacott.tom.snakes.Grid;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.NESWTouchListener;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.engine.GameEngine.GameState;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;
import com.westmacott.tom.snakes.messagebus.MessageListener;


public class Snake implements BusModule {
	
	public static final String MSG_LENGTHEN = "Lengthen";

	private List<Location> segments = new ArrayList<Location>();
	
	private Direction direction;

	private int intendedLength = 5;

	private final String snakeName;
	
	private Drawable drawable;
	
	private Queue<Direction> pendingTurns = new ConcurrentLinkedQueue<Direction>();

	public Snake(Location location, int initialLength, Direction initialDirection, final String snakeName) {
		this.direction = initialDirection;
		this.intendedLength = initialLength;
		this.snakeName = snakeName;
		this.drawable = new Drawable() {
			@Override
			public String getName() {
				return snakeName;
			}
			@Override
			public Colour getColour() {
				return Colour.SKY_BLUE;
			}
		};
		
		for(int seg = 0; seg < intendedLength; seg++) {
			location = this.direction.nextLeft().nextLeft().moveFrom(location);
			this.segments.add(location);
		}
	}

	public void takeUpPositionOn(Grid grid) {
		for(Location location : segments) {
			grid.occupy(location.x, location.y, drawable);
		}
	}

	public boolean update(Grid grid) {
		if (intendedLength < segments.size()) {
			Location tail = segments.get(intendedLength);
			grid.vacate(tail.x, tail.y);
			segments.remove(intendedLength);
		}
		Direction nextDirection = pendingTurns.poll();
		if (   this.direction.nextLeft().equals(nextDirection)
			|| this.direction.nextRight().equals(nextDirection)) 
		{
			this.direction = nextDirection;
		}

		Location head = this.direction.moveFrom(segments.get(0));
		head = grid.normalise(head);
		segments.add(0, head);
		return grid.occupy(head.x, head.y, drawable);

	}

	private class CompassListener implements MessageListener {
		@Override
		public void recieve(String... data) {
			Direction newDirection = Direction.valueOf(data[0]);
			turn(newDirection);
		}

		@Override
		public String id() {
			return "CompassListener";
		}
	}
	
	@SuppressWarnings("unused")
	private class TurnListener implements MessageListener {
		@Override
		public void recieve(String... data) {
			
			Direction newDirection = Direction.valueOf(data[0]);
			switch (newDirection) {
			case WEST:
				turnLeft();
				break;
			case EAST:
				turnRight();
				break;
			default:
			}
		}

		@Override
		public String id() {
			return "TurnListener";
		}
	}

	public void turnLeft() {
		turn(this.direction.nextLeft());
	}
	
	public void turnRight() {
		turn(this.direction.nextRight());
	}
	
	public void turn(Direction direction) {
		if (GameState.RUNNING.equals(GameEngine.state())) {
			pendingTurns.offer(direction);
		}
	}
	
	@Override
	public void join(MessageBus bus) {
		bus.subscribe(this.snakeName, MSG_LENGTHEN, new MessageListener() {
			@Override
			public void recieve(String... data) {
				lengthen();
			}

			@Override
			public String id() {
				return "SnakeLengthen";
			}
		});
		bus.subscribe(GameEngine.TOUCHSCREEN_NAME, NESWTouchListener.MSG_SWIPE, new CompassListener());
	}
	
	public void lengthen() {
		intendedLength += 5;
	}
}

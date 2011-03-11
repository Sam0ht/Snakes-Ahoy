package com.westmacott.tom.snakes;

import android.view.MotionEvent;
import android.view.View;

import com.westmacott.tom.snakes.Location.Distance;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class NESWTouchListener implements View.OnTouchListener, BusModule {
	
	public static final String MSG_SWIPE = "swipe";
	public static final String MSG_LIFT = "lift";
	
	private final int threshold = 5;
	private Location start;
	private MessageBus bus;

	private Direction lastDirection;
	
	public boolean onTouch(View v, MotionEvent e) {
		Location location = new Location((int)e.getX(), (int)e.getY());
		int action = e.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			start = location;
			break;
		case MotionEvent.ACTION_MOVE:
			sendDirection(location);
			break;
		case MotionEvent.ACTION_UP:
			this.lastDirection = null;
			sendPosition(location);
		default:
		}
		return true;
	}


	private void sendDirection(Location location) {
		Distance dist = start.distanceTo(location);
		Direction direction = discernDirection(dist.getDx(), dist.getDy());
		if (direction != null && !direction.equals(this.lastDirection)) {
			bus.send(GameEngine.TOUCHSCREEN_NAME, MSG_SWIPE, direction.name());
			this.lastDirection = direction;
		}
	}
	
	// a bit of a hack to allow for menus...
	private void sendPosition(Location location) {
		bus.send(GameEngine.TOUCHSCREEN_NAME, MSG_LIFT, location.toString());
	}

	public Direction discernDirection(int dx, int dy) {
		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx < -threshold) {
				return Direction.WEST;
			} 
			if (dx > threshold) {
				return Direction.EAST;
			}
		} else {
			if (dy < -threshold) {
				return Direction.NORTH;
			} 
			if (dy > threshold) {
				return Direction.SOUTH;
			}
		}
		return null;
	}

	@Override
	public void join(MessageBus bus) {
		this.bus = bus;
	}
}
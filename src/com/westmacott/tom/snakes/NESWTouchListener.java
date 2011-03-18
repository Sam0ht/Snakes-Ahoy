package com.westmacott.tom.snakes;

import android.view.MotionEvent;
import android.view.View;

import com.westmacott.tom.snakes.Location.Distance;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.engine.GameEngine.GameState;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class NESWTouchListener implements View.OnTouchListener, BusModule {
	
	public static final String MSG_LIFT = "lift";
	public static final String MSG_HSCROLL = "horizontalScroll";
	public static final String MSG_SWIPE = "swipe";

	private static final int SELECT_THRESHOLD = 10;
	private static final int DRAG_THRESHOLD = 5;
	
	private Location start;
	private MessageBus bus;
	private int lastY = 0;

	private Direction lastDirection;
	private boolean dragging;
	
	public boolean onTouch(View v, MotionEvent e) {
		Location location = new Location((int)e.getX(), (int)e.getY());
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			start = location;
			lastY = location.y;
			dragging = false;
			break;
		case MotionEvent.ACTION_MOVE:
			sendDirection(location);
			sendHScroll(location);
			break;
		case MotionEvent.ACTION_UP:
			this.lastDirection = null;
			sendPosition(location);
		default:
		}
		return true;
	}

	private void sendHScroll(Location location) {
		if (GameState.MENU.equals(GameEngine.state())) {
			final int hDistance = location.y - lastY;
			if (Math.abs(hDistance) > SELECT_THRESHOLD) {
				dragging = true;
				bus.send(GameEngine.TOUCHSCREEN_NAME, MSG_HSCROLL, String.valueOf(hDistance));
				lastY = location.y;
			}
		}
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
		if (!dragging && GameState.MENU.equals(GameEngine.state())) {
			bus.send(GameEngine.TOUCHSCREEN_NAME, MSG_LIFT, location.toString());
		}
	}

	public Direction discernDirection(int dx, int dy) {
		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx < -DRAG_THRESHOLD) {
				return Direction.WEST;
			} 
			if (dx > DRAG_THRESHOLD) {
				return Direction.EAST;
			}
		} else {
			if (dy < -DRAG_THRESHOLD) {
				return Direction.NORTH;
			} 
			if (dy > DRAG_THRESHOLD) {
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
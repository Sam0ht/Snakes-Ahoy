package com.westmacott.tom.snakes;

import android.view.MotionEvent;
import android.view.View;

import com.westmacott.tom.snakes.Location.Distance;
import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class NESWTouchListener implements View.OnTouchListener, BusModule {
	
	private NESWTouchListener(){}
	public static final NESWTouchListener INSTANCE = new NESWTouchListener();
	
	public static final String MSG_LIFT = "lift";
	public static final String MSG_HSCROLL = "horizontalScroll";
	public static final String MSG_SWIPE = "swipe";

	private static final int SWIPE_THRESHOLD = 5;
	
	private Location start;
	private MessageBus bus;

	private Direction lastDirection;
	
	public boolean onTouch(View v, MotionEvent e) {
		Location location = new Location((int)e.getX(), (int)e.getY());
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			start = location;
			break;
		case MotionEvent.ACTION_MOVE:
			sendDirection(location);
			break;
		case MotionEvent.ACTION_UP:
			this.lastDirection = null;
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

	public Direction discernDirection(int dx, int dy) {
		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx < -SWIPE_THRESHOLD) {
				return Direction.WEST;
			} 
			if (dx > SWIPE_THRESHOLD) {
				return Direction.EAST;
			}
		} else {
			if (dy < -SWIPE_THRESHOLD) {
				return Direction.NORTH;
			} 
			if (dy > SWIPE_THRESHOLD) {
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
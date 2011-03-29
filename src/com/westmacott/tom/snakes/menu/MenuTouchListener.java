package com.westmacott.tom.snakes.menu;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.westmacott.tom.snakes.Location;

final class MenuTouchListener implements OnTouchListener {
	
	private static final int SELECT_THRESHOLD = 10;
	
	private int lastY = 0;

	private boolean dragging;

	private final GameMenu menu;

	public MenuTouchListener(GameMenu menu) {
		this.menu = menu;
	}

	public boolean onTouch(View v, MotionEvent e) {
		Location location = new Location((int)e.getX(), (int)e.getY());
		MenuView mv = (MenuView)v;
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = location.y;
			dragging = false;
			Log.i("MenuTouchListener", "Touched at " + location);
			mv.setScrolling(true);
			break;
		case MotionEvent.ACTION_MOVE:
			sendHScroll(location);
			break;
		case MotionEvent.ACTION_UP:
			sendPosition(location);
			mv.setScrolling(false);
		default:
		}
		return true;
	}

	private void sendHScroll(Location location) {
		final int hDistance = location.y - lastY;
		if (Math.abs(hDistance) > SELECT_THRESHOLD) {
			dragging = true;
			menu.drag(hDistance);
			lastY = location.y;
		}
	}

	private void sendPosition(Location location) {
		if (!dragging) {
			menu.positionSelected(location);
		}
	}
}
package com.westmacott.tom.snakes.entities;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Drawable;
import com.westmacott.tom.snakes.Grid;
import com.westmacott.tom.snakes.Location;
import com.westmacott.tom.snakes.engine.GameEngine;

public class Apple {

	public static final String MSG_EATEN = "Eaten";
	
	public static final Colour COLOUR = Colour.GREEN;

	private final int x;
	private final int y;

	public Apple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void takeUpPositionOn(Grid grid) {
		grid.occupy(x, y, new Drawable() {
			
			@Override
			public String getName() {
				return GameEngine.APPLE_NAME;
			}
			
			@Override
			public Colour getColour() {
				return COLOUR;
			}
		});
	}

	public static Apple randomlyPlaceOn(Grid grid) {
		
		Location appleLocation = grid.getRandomFreeLocation();
		
		Apple apple = new Apple(appleLocation.x, appleLocation.y);
		apple.takeUpPositionOn(grid);
		return apple;
	}
	
}
package com.westmacott.tom.snakes.entities;

import com.westmacott.tom.snakes.Colour;
import com.westmacott.tom.snakes.Drawable;


public class Wall {
	public static final Colour COLOUR = Colour.DARK_RED;
	
	private final String wallName;
	private final String layout;

	public Wall(String wallName, String layout) {
		this.wallName = wallName;
		this.layout = layout;
	}

	public void takeUpPositionOn(Grid grid) {
		
		Drawable gridDrawable = new Drawable() {
			@Override
			public String getName() {
				return wallName;
			}
			@Override
			public Colour getColour() {
				return COLOUR;
			}
		};
		String[] points = layout.split(",");
		for (String point : points) {
			String[] coords = point.split("/");
			grid.occupy(Integer.valueOf(coords[1]) - 1, Integer.valueOf(coords[0]) - 1, gridDrawable);
		}
		
	}

}
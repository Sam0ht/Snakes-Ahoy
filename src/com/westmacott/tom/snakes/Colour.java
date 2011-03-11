package com.westmacott.tom.snakes;

import android.graphics.Paint;

public enum Colour {
	RED (paintOfColour(255, 0, 0)),
	YELLOW (paintOfColour(255, 255, 0)),
	GREEN (paintOfColour(0, 200, 0)),
	BLUE (paintOfColour(0, 0, 255)),
	SKY_BLUE (paintOfColour(75, 175, 255)),
	LIGHT_GREY (paintOfColour(200, 200, 200)),
	WHITE (paintOfColour(255, 255, 255));
	
	public final Paint paint;

	private Colour(Paint paint) {
		this.paint = paint;
	}
	
	private static Paint paintOfColour(int r, int g, int b) {
		Paint newPaint = new Paint();
		newPaint.setARGB(255, r, g, b);
		newPaint.setTextSize(30);
		return newPaint;
	}
	
	public static void updateTextSize(int textSize) {
		for (Colour colour : values()) {
			colour.paint.setTextSize(textSize);
		}
	}
}
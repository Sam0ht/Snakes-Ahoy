package com.westmacott.tom.snakes;

public class Location {
	public final int x;
	public final int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return x + "/" + y;
	}
	public Location(String fromString) {
		String[] coordinates = fromString.split("/");
		if (coordinates.length != 2) {
			throw new IllegalArgumentException("Expected a position in the form x/y, got " + fromString);
		}
		this.x = Integer.valueOf(coordinates[0]);
		this.y = Integer.valueOf(coordinates[1]);
	}
	public Distance distanceTo(Location end) {
		return new Distance(this, end);
	}
	
	public static class Distance {
		public final Location start;
		public final Location end;
		public Distance(Location start, Location end) {
			this.start = start;
			this.end = end;
		}
		public int getDx() {
			return end.x - start.x;
		}
		public int getDy() {
			return end.y - start.y;
		}
	}
	
	
}
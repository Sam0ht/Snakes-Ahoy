package com.westmacott.tom.snakes;

public enum Direction {
	NORTH {
		@Override
		public Location moveFrom(Location old) {
			return new Location(old.x, old.y - 1);
		}
		@Override
		public Direction nextLeft() {
			return WEST;
		}
		@Override
		public Direction nextRight() {
			return EAST;
		}
	},
	WEST{
		@Override
		public Location moveFrom(Location old) {
			return new Location(old.x - 1, old.y);
		}
		@Override
		public Direction nextLeft() {
			return SOUTH;
		}
		@Override
		public Direction nextRight() {
			return NORTH;
		}
	}, SOUTH {
		@Override
		public Location moveFrom(Location old) {
			return new Location(old.x, old.y + 1);
		}
		@Override
		public Direction nextLeft() {
			return EAST;
		}
		@Override
		public Direction nextRight() {
			return WEST;
		}
	},
	EAST {
		@Override
		public Location moveFrom(Location old) {
			return new Location(old.x + 1, old.y);
		}
		@Override
		public Direction nextLeft() {
			return NORTH;
		}
		@Override
		public Direction nextRight() {
			return SOUTH;
		}
	};
	public abstract Location moveFrom(Location location);

	public abstract Direction nextLeft();

	public abstract Direction nextRight();
}
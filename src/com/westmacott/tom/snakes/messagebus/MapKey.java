package com.westmacott.tom.snakes.messagebus;

public class MapKey {
	public final String subscriber;
	public final String message;
	public MapKey(String subscriber, String message) {
		this.subscriber = subscriber;
		this.message = message;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((subscriber == null) ? 0 : subscriber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapKey other = (MapKey) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (subscriber == null) {
			if (other.subscriber != null)
				return false;
		} else if (!subscriber.equals(other.subscriber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MapKey [subscriber=" + subscriber + ", message=" + message
				+ "]";
	}
	
}
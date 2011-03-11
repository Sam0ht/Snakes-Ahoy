package com.westmacott.tom.snakes.messagebus;

public interface MessageListener {
	
	void recieve(String... data);
	
	String id();

}

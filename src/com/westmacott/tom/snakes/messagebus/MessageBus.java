package com.westmacott.tom.snakes.messagebus;

public interface MessageBus {

	void send(String target, String message, String... data);

	void subscribe(String name, String message, MessageListener recipient);

	void unSubscribe(String listenerId);
}

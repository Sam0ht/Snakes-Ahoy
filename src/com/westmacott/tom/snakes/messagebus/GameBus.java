package com.westmacott.tom.snakes.messagebus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameBus implements MessageBus {

	private MultiMap<MapKey, MessageListener> actions = new MultiMap<MapKey, MessageListener>();
	private Map<String, MapKey> subscribers = new HashMap<String, MapKey>();
	
	@Override
	public void subscribe(String name, String message, MessageListener recipient) {
		unSubscribe(recipient.id());
		MapKey key = new MapKey(name, message);
		this.actions.put(key, recipient);
		this.subscribers.put(recipient.id(), key);
	}
	
	public void unSubscribe(String id) {
		MapKey key = this.subscribers.get(id);
		if (key != null) {
			MessageListener findExisting = findExisting(id, this.actions.get(key));
			this.actions.removeElement(key, findExisting);
		}
	}

	private MessageListener findExisting(String id, List<MessageListener> allActions ) {
		for (MessageListener action : allActions) {
			if (action.id().equals(id)) {
				return action;
			}
		}
		return null;
	}
	
	@Override
	public void send(String target, String message, String... data) {
		MapKey key = new MapKey(target, message);
		List<MessageListener> recipients = this.actions.get(key);
		if (recipients != null) {
			for (MessageListener recipient : recipients) {
				recipient.recieve(data);
			}
		} else {
			throw new RuntimeException("No listeners were found for message " + key);
		}
	}
	
}
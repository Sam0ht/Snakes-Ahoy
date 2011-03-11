package com.westmacott.tom.snakes;

import com.westmacott.tom.snakes.engine.GameEngine;
import com.westmacott.tom.snakes.messagebus.BusModule;
import com.westmacott.tom.snakes.messagebus.MessageBus;

public class MenuButtonListener implements BusModule {

	public static final String MSG_OPEN_MENU = "OpenMenu";
	
	private MessageBus bus;

	@Override
	public void join(MessageBus bus) {
		this.bus = bus;
	}

	public void returnToMenu() {
		bus.send(GameEngine.MENU_NAME, MSG_OPEN_MENU);
	}
	
}
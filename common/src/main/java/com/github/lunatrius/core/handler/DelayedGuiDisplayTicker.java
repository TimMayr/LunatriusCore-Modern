package com.github.lunatrius.core.handler;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.gui.screens.Screen;

public class DelayedGuiDisplayTicker {
	private final Screen screen;
	private int ticks;
	private ClientTickEvent.Client client;

	private DelayedGuiDisplayTicker(Screen screen, int delay) {
		this.screen = screen;
		this.ticks = delay;
		this.client = (minecraft) -> {
			this.ticks--;

			if (this.ticks < 0) {
				minecraft.setScreen(this.screen);
				ClientTickEvent.CLIENT_PRE.unregister(client);
			}
		};
	}

	public static void create(Screen guiScreen, int delay) {
		final DelayedGuiDisplayTicker delayedGuiDisplayTicker = new DelayedGuiDisplayTicker(guiScreen, delay);
		ClientTickEvent.CLIENT_PRE.register(delayedGuiDisplayTicker.client);
	}
}
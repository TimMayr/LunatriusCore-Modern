package com.github.lunatrius.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public class DelayedGuiDisplayTicker {
	private final Screen screen;
	private int ticks;

	private DelayedGuiDisplayTicker(Screen screen, int delay) {
		this.screen = screen;
		this.ticks = delay;
	}

	public static void create(Screen screen, int delay) {
		NeoForge.EVENT_BUS.register(new DelayedGuiDisplayTicker(screen, delay));
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		this.ticks--;

		if (this.ticks < 0) {
			Minecraft.getInstance().setScreen(this.screen);
			NeoForge.EVENT_BUS.unregister(this);
		}
	}
}
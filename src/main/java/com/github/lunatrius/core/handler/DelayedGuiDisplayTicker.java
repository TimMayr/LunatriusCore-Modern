package com.github.lunatrius.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DelayedGuiDisplayTicker {
	private final Screen screen;
	private int ticks;

	private DelayedGuiDisplayTicker(Screen screen, int delay) {
		this.screen = screen;
		this.ticks = delay;
	}

	public static void create(Screen guiScreen, int delay) {
		MinecraftForge.EVENT_BUS.register(new DelayedGuiDisplayTicker(guiScreen, delay));
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		this.ticks--;

		if (this.ticks < 0) {
			Minecraft.getInstance().displayGuiScreen(this.screen);
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
}
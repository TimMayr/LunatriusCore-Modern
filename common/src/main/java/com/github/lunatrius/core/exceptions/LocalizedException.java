package com.github.lunatrius.core.exceptions;


import net.minecraft.network.chat.Component;

public class LocalizedException extends Exception {
	public LocalizedException(String format) {
		super(String.valueOf(Component.translatable(format)));
	}

	public LocalizedException(String format, Object... arguments) {
		super(String.valueOf(Component.translatable(format, arguments)));
	}
}
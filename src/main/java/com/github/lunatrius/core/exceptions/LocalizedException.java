package com.github.lunatrius.core.exceptions;

import net.minecraft.util.text.TranslationTextComponent;

public class LocalizedException extends Exception {
	public LocalizedException(String format) {
		super(String.valueOf(new TranslationTextComponent(format)));
	}

	public LocalizedException(String format, Object... arguments) {
		super(String.valueOf(new TranslationTextComponent(format, arguments)));
	}
}
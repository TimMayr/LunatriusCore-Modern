package com.github.lunatrius.core.exceptions;

import net.minecraft.util.text.TranslationTextComponent;

public class LocalizedException extends Exception {
	public LocalizedException(final String format) {
		super(String.valueOf(new TranslationTextComponent(format)));
	}

	public LocalizedException(final String format, final Object... arguments) {
		super(String.valueOf(new TranslationTextComponent(format, arguments)));
	}
}

package com.github.lunatrius.core.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;

public class NumericFieldWidget extends Button {
	private static final int DEFAULT_VALUE = 0;
	private static final int BUTTON_WIDTH = 12;

	private final EditBox guiEditBox;
	private final PlainTextButton guiButtonDec;
	private final PlainTextButton guiButtonInc;

	private String previous = String.valueOf(DEFAULT_VALUE);
	private int minimum = Integer.MIN_VALUE;
	private int maximum = Integer.MAX_VALUE;
	private boolean wasFocused = false;

	public NumericFieldWidget(int x, int y, Button.OnPress onPress) {
		this(x, y, 100, 20, onPress);
	}

	public NumericFieldWidget(int x, int y, int width, int height, Button.OnPress onPress) {
		super(0, 0, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
		this.guiEditBox =
				new EditBox(Minecraft.getInstance().font, x + 1, y + 1, width - BUTTON_WIDTH * 2 - 2, height - 2,
				            CommonComponents.EMPTY);

		this.guiButtonDec =
				new PlainTextButton(x + width - BUTTON_WIDTH * 2, y, BUTTON_WIDTH, height, Component.literal("-"),
				                    (event) -> {}, Minecraft.getInstance().font);

		this.guiButtonInc =
				new PlainTextButton(x + width - BUTTON_WIDTH, y, BUTTON_WIDTH, height, Component.literal("+"),
				                    (event) -> {}, Minecraft.getInstance().font);

		setValue(DEFAULT_VALUE);
	}

	public NumericFieldWidget(int x, int y, int width, Button.OnPress onPress) {
		this(x, y, width, 20, onPress);
	}

	@ParametersAreNonnullByDefault
	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
		if (this.visible) {
			this.guiEditBox.renderWidget(guiGraphics, x, y, partialTicks);
			this.guiButtonInc.renderWidget(guiGraphics, x, y, partialTicks);
			this.guiButtonDec.renderWidget(guiGraphics, x, y, partialTicks);
		}
	}

	@Override
	public void onClick(double x, double y) {
		this.guiButtonDec.onClick(x, y);
		this.guiButtonInc.onClick(x, y);
		this.guiEditBox.onClick(x, y);
		super.onClick(x, y);
	}

	@Override
	public boolean keyPressed(int character, int code, int modifiers) {
		this.guiEditBox.keyPressed(character, code, modifiers);
		this.guiButtonDec.keyPressed(character, code, modifiers);
		this.guiButtonInc.keyPressed(character, code, modifiers);
		return super.keyPressed(character, code, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.wasFocused && !this.guiEditBox.isFocused()) {
			this.wasFocused = false;
			return true;
		}

		this.wasFocused = this.guiEditBox.isFocused();

		if (this.guiButtonDec.mouseClicked(mouseX, mouseY, button)) {
			setValue(getValue() - 1);
			this.onPress();
			return true;
		}

		if (this.guiButtonInc.mouseClicked(mouseX, mouseY, button)) {
			setValue(getValue() + 1);
			this.onPress();
			return true;
		}

		if (this.guiEditBox.mouseClicked(mouseX, mouseY, button)) {
			setValue(Integer.parseInt(guiEditBox.getValue()));
			this.onPress();
			return true;
		}

		return false;
	}

	public boolean isFocused() {
		return this.guiEditBox.isFocused();
	}

	public int getValue() {
		String text = this.guiEditBox.getValue();

		if (text.isEmpty() || text.equals("-")) {
			return DEFAULT_VALUE;
		}

		return Integer.parseInt(text);
	}

	public void setValue(int value) {
		if (value > this.maximum) {
			value = this.maximum;
		} else if (value < this.minimum) {
			value = this.minimum;
		}
		this.guiEditBox.setValue(String.valueOf(value));
	}

	@Override
	public boolean charTyped(char character, int code) {
		if (!this.guiEditBox.isFocused()) {
			return false;
		}

		int cursorPositionOld = this.guiEditBox.getCursorPosition();

		this.guiEditBox.charTyped(character, code);

		String text = this.guiEditBox.getValue();
		int cursorPositionNew = this.guiEditBox.getCursorPosition();

		if (text.isEmpty() || text.equals("-")) {
			return true;
		}

		try {
			long value = Long.parseLong(text);
			boolean outOfRange = false;

			if (value > this.maximum) {
				value = this.maximum;
				outOfRange = true;
			} else if (value < this.minimum) {
				value = this.minimum;
				outOfRange = true;
			}

			text = String.valueOf(value);

			if (!text.equals(this.previous) || outOfRange) {
				this.guiEditBox.setValue(String.valueOf(value));
				this.guiEditBox.setCursorPosition(cursorPositionNew);
			}

			this.previous = text;
			this.onPress();
			return true;
		} catch (NumberFormatException nfe) {
			this.guiEditBox.setValue(this.previous);
			this.guiEditBox.setCursorPosition(cursorPositionOld);
		}

		return false;
	}

	public void setPosition(int x, int y) {
		this.guiEditBox.setPosition(x + 1, y + 1);
		this.guiButtonInc.setPosition(x + width - BUTTON_WIDTH * 2, y);
		this.guiButtonDec.setPosition(x + width - BUTTON_WIDTH, y);
	}

	public void setActive(boolean active) {
		this.active = active;
		this.guiEditBox.active = active;
		this.guiButtonInc.active = active;
		this.guiButtonDec.active = active;
	}

	public int getMinimum() {
		return this.minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return this.maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
}
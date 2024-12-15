package com.github.lunatrius.core.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;

public class NumericFieldWidget extends Button {
	private static final int DEFAULT_VALUE = 0;
	private static final int BUTTON_WIDTH = 12;

	private final TextFieldWidget guiTextField;
	private final Button guiButtonDec;
	private final Button guiButtonInc;

	private String previous = String.valueOf(DEFAULT_VALUE);
	private int minimum = Integer.MIN_VALUE;
	private int maximum = Integer.MAX_VALUE;
	private boolean wasFocused = false;

	public NumericFieldWidget(FontRenderer fontRenderer, int x, int y, Button.IPressable onPress) {
		this(fontRenderer, x, y, 100, 20, onPress);
	}

	public NumericFieldWidget(FontRenderer fontRenderer, int x, int y, int width, int height,
	                          Button.IPressable onPress) {
		super(0, 0, width, height, "", onPress);
		this.guiTextField =
				new TextFieldWidget(fontRenderer, x + 1, y + 1, width - BUTTON_WIDTH * 2 - 2, height - 2, "");

		this.guiButtonDec = new Button(x + width - BUTTON_WIDTH * 2, y, BUTTON_WIDTH, height, "-", (event) -> {});

		this.guiButtonInc = new Button(x + width - BUTTON_WIDTH, y, BUTTON_WIDTH, height, "+", (event) -> {});

		setValue(DEFAULT_VALUE);
	}

	public NumericFieldWidget(FontRenderer fontRenderer, int x, int y, int width, Button.IPressable onPress) {
		this(fontRenderer, x, y, width, 20, onPress);
	}

	@Override
	public void renderButton(int x, int y, float partialTicks) {
		if (this.visible) {
			this.guiTextField.render(x, y, partialTicks);
			this.guiButtonInc.renderButton(x, y, partialTicks);
			this.guiButtonDec.renderButton(x, y, partialTicks);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.wasFocused && !this.guiTextField.isFocused()) {
			this.wasFocused = false;
			return true;
		}

		this.wasFocused = this.guiTextField.isFocused();

		if (this.guiButtonDec.mouseClicked(mouseX, mouseY, button)) {
			setValue(getValue() - 1);
			return true;
		}

		if (this.guiButtonInc.mouseClicked(mouseX, mouseY, button)) {
			setValue(getValue() + 1);
			return true;
		}

		return false;
	}

	public void mouseClicked(int x, int y, int action) {
		this.guiTextField.mouseClicked(x, y, action);

		if (this.guiButtonInc.mouseClicked(x, y, action)) {
			setValue(getValue() + 1);
		}

		if (this.guiButtonDec.mouseClicked(x, y, action)) {
			setValue(getValue() - 1);
		}
	}

	public void setValue(int value) {
		if (value > this.maximum) {
			value = this.maximum;
		} else if (value < this.minimum) {
			value = this.minimum;
		}
		this.guiTextField.setText(String.valueOf(value));
	}

	public boolean isFocused() {
		return this.guiTextField.isFocused();
	}

	public int getValue() {
		String text = this.guiTextField.getText();

		if (text.isEmpty() || text.equals("-")) {
			return DEFAULT_VALUE;
		}

		return Integer.parseInt(text);
	}

	@Override
	public boolean charTyped(char character, int code) {
		if (!this.guiTextField.isFocused()) {
			return false;
		}

		int cursorPositionOld = this.guiTextField.getCursorPosition();

		this.guiTextField.charTyped(character, code);

		String text = this.guiTextField.getText();
		int cursorPositionNew = this.guiTextField.getCursorPosition();

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
				this.guiTextField.setText(String.valueOf(value));
				this.guiTextField.setCursorPosition(cursorPositionNew);
			}

			this.previous = text;

			return true;
		} catch (NumberFormatException nfe) {
			this.guiTextField.setText(this.previous);
			this.guiTextField.setCursorPosition(cursorPositionOld);
		}

		return false;

	}

	public void tick() {
		this.guiTextField.tick();
	}

	public void setPosition(int x, int y) {
		this.guiTextField.x = x + 1;
		this.guiTextField.y = y + 1;
		this.guiButtonInc.x = x + width - BUTTON_WIDTH * 2;
		this.guiButtonInc.y = y;
		this.guiButtonDec.x = x + width - BUTTON_WIDTH;
		this.guiButtonDec.y = y;
	}

	public void setActive(boolean enabled) {
		this.active = enabled;
		this.guiTextField.setEnabled(enabled);
		this.guiButtonInc.active = enabled;
		this.guiButtonDec.active = enabled;
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
package com.github.lunatrius.core.client.gui;

import net.minecraft.client.Minecraft;
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

	public NumericFieldWidget(final FontRenderer fontRenderer, final int id, final int x, final int y) {
		this(fontRenderer, id, x, y, 100, 20);
	}

	public NumericFieldWidget(final FontRenderer fontRenderer, final int id, final int x, final int y, final int width,
	                          final int height) {
		super(0, 0, width, height, "", (event) -> {});
		this.guiTextField =
				new TextFieldWidget(fontRenderer, x + 1, y + 1, width - BUTTON_WIDTH * 2 - 2, height - 2, "");

		this.guiButtonDec = new Button(x + width - BUTTON_WIDTH * 2, y, BUTTON_WIDTH, height, "-", (event) -> {});

		this.guiButtonInc = new Button(x + width - BUTTON_WIDTH, y, BUTTON_WIDTH, height, "+", (event) -> {});

		setValue(DEFAULT_VALUE);
	}

	public NumericFieldWidget(final FontRenderer fontRenderer, final int id, final int x, final int y,
	                          final int width) {
		this(fontRenderer, id, x, y, width, 20);
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

	public int getValue() {
		final String text = this.guiTextField.getText();

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
		this.guiTextField.setText(String.valueOf(value));
	}

	public boolean isFocused() {
		return this.guiTextField.isFocused();
	}

	public void mouseClicked(final int x, final int y, final int action) {
		final Minecraft minecraft = Minecraft.getInstance();

		this.guiTextField.mouseClicked(x, y, action);

		if (this.guiButtonInc.mouseClicked(x, y, action)) {
			setValue(getValue() + 1);
		}

		if (this.guiButtonDec.mouseClicked(x, y, action)) {
			setValue(getValue() - 1);
		}
	}

	@Override
	public boolean charTyped(final char character, final int code) {
		if (!this.guiTextField.isFocused()) {
			return false;
		}

		final int cursorPositionOld = this.guiTextField.getCursorPosition();

		this.guiTextField.charTyped(character, code);

		String text = this.guiTextField.getText();
		final int cursorPositionNew = this.guiTextField.getCursorPosition();

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
		} catch (final NumberFormatException nfe) {
			this.guiTextField.setText(this.previous);
			this.guiTextField.setCursorPosition(cursorPositionOld);
		}

		return false;

	}

	public void tick() {
		this.guiTextField.tick();
	}

	public void setPosition(final int x, final int y) {
		this.guiTextField.x = x + 1;
		this.guiTextField.y = y + 1;
		this.guiButtonInc.x = x + width - BUTTON_WIDTH * 2;
		this.guiButtonInc.y = y;
		this.guiButtonDec.x = x + width - BUTTON_WIDTH;
		this.guiButtonDec.y = y;
	}

	public void setActive(final boolean enabled) {
		this.active = enabled;
		this.guiTextField.setEnabled(enabled);
		this.guiButtonInc.active = enabled;
		this.guiButtonDec.active = enabled;
	}

	public int getMinimum() {
		return this.minimum;
	}

	public void setMinimum(final int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return this.maximum;
	}

	public void setMaximum(final int maximum) {
		this.maximum = maximum;
	}
}

package com.github.lunatrius.core.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ScreenBase extends Screen {
	protected final Screen parentScreen;

	protected final List<TextFieldWidget> textFields = new ArrayList<>();

	public ScreenBase() {
		this(null);
	}

	public ScreenBase(Screen parentScreen) {
		super(new StringTextComponent(""));
		this.parentScreen = parentScreen;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseEvent) {
		for (Widget button : this.buttons) {
			if (button instanceof NumericFieldWidget) {
				NumericFieldWidget numericField = (NumericFieldWidget) button;
				if (numericField.mouseClicked(mouseX, mouseY, mouseEvent)) {
					return true;
				}
			} else {
				if (button.mouseClicked(mouseX, mouseY, mouseEvent)) {
					return true;
				}
			}
		}

		for (TextFieldWidget textField : this.textFields) {
			if (textField.mouseClicked(mouseX, mouseY, mouseEvent)) {
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseEvent);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);

		for (TextFieldWidget textField : this.textFields) {
			textField.render(mouseX, mouseY, partialTicks);
		}
	}

	public boolean charTyped(char character, int code) {
		for (Widget button : this.buttons) {
			if (button instanceof NumericFieldWidget) {
				NumericFieldWidget numericField = (NumericFieldWidget) button;
				if (numericField.charTyped(character, code)) {
					return true;
				}
			}
		}

		for (TextFieldWidget textField : this.textFields) {
			if (textField.charTyped(character,code)) {
				return true;
			}
		}

		return super.charTyped(character, code);
	}

	@Override
	public boolean keyPressed(int character, int code, int modifiers) {
		if (code == GLFW.GLFW_KEY_ESCAPE) {
			if (this.minecraft == null) {
				return false;
			}

			this.minecraft.displayGuiScreen(this.parentScreen);
			return true;
		}

		for (Widget button : this.buttons) {
			if (button instanceof NumericFieldWidget) {
				NumericFieldWidget numericField = (NumericFieldWidget) button;
				if (numericField.keyPressed(character, code, modifiers)) {
					numericField.onPress();
					return true;
				}
			}
		}

		for (TextFieldWidget textField : this.textFields) {
			if (textField.keyPressed(character, code, modifiers)) {
				return true;
			}
		}

		return super.keyPressed(character, code, modifiers);
	}

	@Override
	public void init() {
		this.buttons.clear();
		this.textFields.clear();
	}

	@Override
	public void tick() {
		super.tick();

		for (Widget button : this.buttons) {
			if (button instanceof NumericFieldWidget) {
				NumericFieldWidget numericField = (NumericFieldWidget) button;
				numericField.tick();
			}
		}

		for (TextFieldWidget textField : this.textFields) {
			textField.tick();
		}
	}
}
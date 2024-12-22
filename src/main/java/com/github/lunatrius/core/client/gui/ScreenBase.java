package com.github.lunatrius.core.client.gui;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNonnullByDefault;


public class ScreenBase extends Screen {
	protected final Screen parentScreen;


	public ScreenBase() {
		this(null);
	}

	public ScreenBase(Screen parentScreen) {
		super(CommonComponents.EMPTY);
		this.parentScreen = parentScreen;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseEvent) {
		for (GuiEventListener child : this.children()) {
			if (child instanceof NumericFieldWidget numericField) {
				if (numericField.mouseClicked(mouseX, mouseY, mouseEvent)) {
					return true;
				}
			} else if (child instanceof EditBox textArea) {
				if (textArea.mouseClicked(mouseX, mouseY, mouseEvent)) {
					return true;
				}
			} else {
				if (child.mouseClicked(mouseX, mouseY, mouseEvent)) {
					return true;
				}
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseEvent);
	}

	public boolean charTyped(char character, int code) {
		for (GuiEventListener child : this.children()) {
			if (child instanceof NumericFieldWidget numericField) {
				if (numericField.charTyped(character, code)) {
					return true;
				}
			} else if (child instanceof EditBox textArea) {
				if (textArea.charTyped(character, code)) {
					return true;
				}
			}
		}

		return super.charTyped(character, code);
	}

	@ParametersAreNonnullByDefault
	@Override
	public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
		super.render(guiGraphics, x, y, partialTicks);

		for (GuiEventListener child : this.children()) {
			if (child instanceof EditBox textArea) {
				textArea.render(guiGraphics, x, y, partialTicks);
			}
		}
	}

	@Override
	public boolean keyPressed(int character, int code, int modifiers) {
		if (code == GLFW.GLFW_KEY_ESCAPE) {
			if (this.minecraft == null) {
				return false;
			}

			this.minecraft.setScreen(this.parentScreen);
			return true;
		}

		for (GuiEventListener child : this.children()) {
			if (child instanceof NumericFieldWidget numericField) {
				if (numericField.keyPressed(character, code, modifiers)) {
					numericField.onPress();
					return true;
				}
			} else if (child instanceof EditBox textArea) {
				textArea.keyPressed(character, code, modifiers);
			}
		}

		return super.keyPressed(character, code, modifiers);
	}

	@Override
	public void init() {
		this.children().clear();
	}
}
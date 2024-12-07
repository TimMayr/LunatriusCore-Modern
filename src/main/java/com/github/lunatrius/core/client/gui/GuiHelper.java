package com.github.lunatrius.core.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
	private static final ItemRenderer renderItem = Minecraft.getInstance().getItemRenderer();
	private static final ResourceLocation STAT_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

	public static void drawItemStackWithSlot( TextureManager textureManager,  ItemStack itemStack,
	                                          int x,  int y) {
		drawItemStackSlot(textureManager, x, y);

		if (itemStack != null) {
			drawItemStack(itemStack, x + 2, y + 2);
		}
	}

	public static void drawItemStackSlot( TextureManager textureManager,  int x,  int y) {
		textureManager.bindTexture(STAT_ICONS);

		 Tessellator tessellator = Tessellator.getInstance();
		 BufferBuilder buffer = tessellator.getBuffer();
		 float uScale = (float) (1.0 / 128.0);
		 float vScale = (float) (1.0 / 128.0);

		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		drawTexturedRectangle(buffer, x + 1, y + 1, x + 1 + 18, y + 1 + 18, 0, uScale * 0, vScale * 0, uScale * 18,
		                      vScale * 18);
		tessellator.draw();
	}

	public static void drawTexturedRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                          double x1,  double y1,  double z,  float u0,
	                                          float v0,  float u1,  float v1) {
		buffer.pos(x0, y0, z).tex(u0, v0).endVertex();
		buffer.pos(x0, y1, z).tex(u0, v1).endVertex();
		buffer.pos(x1, y1, z).tex(u1, v1).endVertex();
		buffer.pos(x1, y0, z).tex(u1, v0).endVertex();
	}

	public static void drawItemStack( ItemStack itemStack,  int x,  int y) {
		RenderSystem.enableRescaleNormal();
		RenderHelper.setupGui3DDiffuseLighting();
		renderItem.renderItemIntoGUI(itemStack, x, y);
		RenderHelper.disableStandardItemLighting();
		RenderSystem.disableRescaleNormal();
	}

	public static void drawTexturedRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                          double x1,  double y1,  double z,
	                                          double textureWidth,  double textureHeight,  int argb) {
		 float u0 = (float) (x0 / textureWidth);
		 float v0 = (float) (y0 / textureHeight);
		 float u1 = (float) (x1 / textureWidth);
		 float v1 = (float) (y1 / textureHeight);

		drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, argb);
	}

	public static void drawTexturedRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                          double x1,  double y1,  double z,  float u0,
	                                          float v0,  float u1,  float v1,  int argb) {
		 int a = (argb >> 24) & 0xFF;
		 int r = (argb >> 16) & 0xFF;
		 int g = (argb >> 8) & 0xFF;
		 int b = argb & 0xFF;

		drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, r, g, b, a);
	}

	public static void drawTexturedRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                          double x1,  double y1,  double z,  float u0,
	                                          float v0,  float u1,  float v1,  int r,  int g,
	                                          int b,  int a) {
		buffer.pos(x0, y0, z).tex(u0, v0).color(r, g, b, a).endVertex();
		buffer.pos(x0, y1, z).tex(u0, v1).color(r, g, b, a).endVertex();
		buffer.pos(x1, y1, z).tex(u1, v1).color(r, g, b, a).endVertex();
		buffer.pos(x1, y0, z).tex(u1, v0).color(r, g, b, a).endVertex();
	}

	public static void drawColoredRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                         double x1,  double y1,  double z,  int argb) {
		 int a = (argb >> 24) & 0xFF;
		 int r = (argb >> 16) & 0xFF;
		 int g = (argb >> 8) & 0xFF;
		 int b = argb & 0xFF;

		drawColoredRectangle(buffer, x0, y0, x1, y1, z, r, g, b, a);
	}

	public static void drawColoredRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                         double x1,  double y1,  double z,  int r,  int g,
	                                         int b,  int a) {
		drawVerticalGradientRectangle(buffer, x0, y0, x1, y1, z, r, g, b, a, r, g, b, a);
	}

	public static void drawVerticalGradientRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                                  double x1,  double y1,  double z,  int sr,
	                                                  int sg,  int sb,  int sa,  int er,
	                                                  int eg,  int eb,  int ea) {
		buffer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
		buffer.pos(x0, y1, z).color(er, eg, eb, ea).endVertex();
		buffer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
		buffer.pos(x1, y0, z).color(sr, sg, sb, sa).endVertex();
	}

	public static void drawVerticalGradientRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                                  double x1,  double y1,  double z,
	                                                  int startColor,  int endColor) {
		 int sa = (startColor >> 24) & 255;
		 int sr = (startColor >> 16) & 255;
		 int sg = (startColor >> 8) & 255;
		 int sb = startColor & 255;
		 int ea = (endColor >> 24) & 255;
		 int er = (endColor >> 16) & 255;
		 int eg = (endColor >> 8) & 255;
		 int eb = endColor & 255;

		drawVerticalGradientRectangle(buffer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
	}

	public static void drawHorizontalGradientRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                                    double x1,  double y1,  double z,
	                                                    int startColor,  int endColor) {
		 int sa = (startColor >> 24) & 255;
		 int sr = (startColor >> 16) & 255;
		 int sg = (startColor >> 8) & 255;
		 int sb = startColor & 255;
		 int ea = (endColor >> 24) & 255;
		 int er = (endColor >> 16) & 255;
		 int eg = (endColor >> 8) & 255;
		 int eb = endColor & 255;

		drawHorizontalGradientRectangle(buffer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
	}

	public static void drawHorizontalGradientRectangle( BufferBuilder buffer,  double x0,  double y0,
	                                                    double x1,  double y1,  double z,  int sr,
	                                                    int sg,  int sb,  int sa,  int er,
	                                                    int eg,  int eb,  int ea) {
		buffer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
		buffer.pos(x0, y1, z).color(sr, sg, sb, sa).endVertex();
		buffer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
		buffer.pos(x1, y0, z).color(er, eg, eb, ea).endVertex();
	}
}

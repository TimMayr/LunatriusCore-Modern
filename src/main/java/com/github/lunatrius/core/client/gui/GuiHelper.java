package com.github.lunatrius.core.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GuiHelper {
	private static final ResourceLocation STAT_ICONS =
			ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/stats_icons.png");

	public static void drawItemStackWithSlot(TextureManager textureManager, ItemStack itemStack, int x, int y) {
		drawItemStackSlot(x, y);

		if (itemStack != null) {
			drawItemStack(itemStack, x + 2, y + 2);
		}
	}

	public static void drawItemStackSlot(int x, int y) {
		RenderSystem.setShaderTexture(0, STAT_ICONS);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		float uScale = (float) (1.0 / 128.0);
		float vScale = (float) (1.0 / 128.0);

		drawTexturedRectangle(buffer, x + 1, y + 1, x + 1 + 18, y + 1 + 18, 0, uScale * 0, vScale * 0, uScale * 18,
		                      vScale * 18);
	}

	public static void drawTexturedRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                         float u0, float v0, float u1, float v1) {
		buffer.addVertex(x0, y0, z).setUv(u0, v0);
		buffer.addVertex(x0, y1, z).setUv(u0, v1);
		buffer.addVertex(x1, y1, z).setUv(u1, v1);
		buffer.addVertex(x1, y0, z).setUv(u1, v0);
	}

	public static void drawItemStack(ItemStack itemStack, int x, int y) {
		Minecraft mc = Minecraft.getInstance();
		ItemRenderer itemRenderer = mc.getItemRenderer();
		PoseStack poseStack = new PoseStack();
		MultiBufferSource bufferSource = MultiBufferSource.immediate(new ByteBufferBuilder(1536));

		// Prepare rendering
		RenderSystem.enableDepthTest();
		poseStack.translate(x, y, 100); // Render in front of GUI elements if needed

		// Render the item
		itemRenderer.renderStatic(itemStack, ItemDisplayContext.GUI, 15, 0, poseStack, bufferSource, mc.level,
		                          (int) (Math.random() * 1000));

		// Clean up
		RenderSystem.disableDepthTest();
	}

	public static void drawTexturedRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                         double textureWidth, double textureHeight, int argb) {
		float u0 = (float) (x0 / textureWidth);
		float v0 = (float) (y0 / textureHeight);
		float u1 = (float) (x1 / textureWidth);
		float v1 = (float) (y1 / textureHeight);

		drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, argb);
	}

	public static void drawTexturedRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                         float u0, float v0, float u1, float v1, int argb) {
		int a = (argb >> 24) & 0xFF;
		int r = (argb >> 16) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = argb & 0xFF;

		drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, r, g, b, a);
	}

	public static void drawTexturedRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                         float u0, float v0, float u1, float v1, int r, int g, int b, int a) {
		buffer.addVertex(x0, y0, z).setUv(u0, v0).setColor(r, g, b, a);
		buffer.addVertex(x0, y1, z).setUv(u0, v1).setColor(r, g, b, a);
		buffer.addVertex(x1, y1, z).setUv(u1, v1).setColor(r, g, b, a);
		buffer.addVertex(x1, y0, z).setUv(u1, v0).setColor(r, g, b, a);
	}

	public static void drawColoredRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                        int argb) {
		int a = (argb >> 24) & 0xFF;
		int r = (argb >> 16) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = argb & 0xFF;

		drawColoredRectangle(buffer, x0, y0, x1, y1, z, r, g, b, a);
	}

	public static void drawColoredRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1, float z,
	                                        int r, int g, int b, int a) {
		drawVerticalGradientRectangle(buffer, x0, y0, x1, y1, z, r, g, b, a, r, g, b, a);
	}

	public static void drawVerticalGradientRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1,
	                                                 float z, int sr, int sg, int sb, int sa, int er, int eg, int eb,
	                                                 int ea) {
		buffer.addVertex(x0, y0, z).setColor(sr, sg, sb, sa);
		buffer.addVertex(x0, y1, z).setColor(er, eg, eb, ea);
		buffer.addVertex(x1, y1, z).setColor(er, eg, eb, ea);
		buffer.addVertex(x1, y0, z).setColor(sr, sg, sb, sa);
	}

	public static void drawVerticalGradientRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1,
	                                                 float z, int startColor, int endColor) {
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

	public static void drawHorizontalGradientRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1,
	                                                   float z, int startColor, int endColor) {
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

	public static void drawHorizontalGradientRectangle(BufferBuilder buffer, float x0, float y0, float x1, float y1,
	                                                   float z, int sr, int sg, int sb, int sa, int er, int eg, int eb,
	                                                   int ea) {
		buffer.addVertex(x0, y0, z).setColor(sr, sg, sb, sa);
		buffer.addVertex(x0, y1, z).setColor(sr, sg, sb, sa);
		buffer.addVertex(x1, y1, z).setColor(er, eg, eb, ea);
		buffer.addVertex(x1, y0, z).setColor(er, eg, eb, ea);
	}
}
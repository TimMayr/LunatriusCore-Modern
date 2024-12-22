package com.github.lunatrius.core.client.gui;


import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;

public class FontRendererHelper {
	public static void drawLeftAlignedString(Font font, String str, int x, int y, int color,
	                                         MultiBufferSource bufferSource, Matrix4f poseMatrix,
	                                         int packedLightCoords) {
		font.drawInBatch(str, x, y, color, true, poseMatrix, bufferSource, Font.DisplayMode.NORMAL, 0,
		                 packedLightCoords);
	}

	public static void drawCenteredString(Font font, String str, int x, int y, int color,
	                                      MultiBufferSource bufferSource, Matrix4f poseMatrix, int packedLightCoords) {
		font.drawInBatch(str, x - (float) font.width(str) / 2, y, color, true, poseMatrix, bufferSource,
		                 Font.DisplayMode.NORMAL, 0, packedLightCoords);
	}

	public static void drawRightAlignedString(Font font, String str, int x, int y, int color,
	                                          MultiBufferSource bufferSource, Matrix4f poseMatrix,
	                                          int packedLightCoords) {
		font.drawInBatch(str, x - (float) font.width(str), y, color, true, poseMatrix, bufferSource,
		                 Font.DisplayMode.NORMAL, 0, packedLightCoords);
	}
}
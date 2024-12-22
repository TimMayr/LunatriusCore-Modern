package com.github.lunatrius.core.client.renderer;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class GeometryTesselator extends Tesselator {
	private static GeometryTesselator instance = null;

	private static double deltaS = 0;

	public GeometryTesselator() {
		this(0x200000);
	}

	public GeometryTesselator(int size) {
		super(size);
	}

	public static @NotNull GeometryTesselator getInstance() {
		if (instance == null) {
			instance = new GeometryTesselator();
		}

		return instance;
	}

	public static void setStaticDelta(double delta) {
		deltaS = delta;
	}

	public static void drawCuboid(BufferBuilder buffer, BlockPos pos, int sides, int argb,
	                              VertexFormat.Mode drawmode) {
		drawCuboid(buffer, pos, pos, sides, argb, drawmode);
	}

	public static void drawCuboid(BufferBuilder buffer, BlockPos begin, BlockPos end, int sides, int argb,
	                              VertexFormat.Mode drawmode) {
		drawCuboid(buffer, begin, end, sides, argb, GeometryTesselator.deltaS, drawmode);
	}

	private static void drawCuboid(BufferBuilder buffer, BlockPos begin, BlockPos end, int sides, int argb,
	                               double delta, VertexFormat.Mode drawmode) {
		if (sides == 0) {
			return;
		}


		float x0 = (float) (begin.getX() - delta);
		float y0 = (float) (begin.getY() - delta);
		float z0 = (float) (begin.getZ() - delta);
		float x1 = (float) (end.getX() + 1 + delta);
		float y1 = (float) (end.getY() + 1 + delta);
		float z1 = (float) (end.getZ() + 1 + delta);

		switch (drawmode) {
			case VertexFormat.Mode.QUADS:
				drawQuads(buffer, x0, y0, z0, x1, y1, z1, sides, argb);
				break;

			case VertexFormat.Mode.LINES:
				drawLines(buffer, x0, y0, z0, x1, y1, z1, sides, argb);
				break;

			default:
				throw new IllegalStateException("Unsupported mode!");
		}
	}

	public static void drawQuads(BufferBuilder buffer, float x0, float y0, float z0, float x1, float y1, float z1,
	                             int sides, int argb) {
		int a = (argb >>> 24) & 0xFF;
		int r = (argb >>> 16) & 0xFF;
		int g = (argb >>> 8) & 0xFF;
		int b = argb & 0xFF;

		drawQuads(buffer, x0, y0, z0, x1, y1, z1, sides, a, r, g, b);
	}

	public static void drawQuads(BufferBuilder buffer, float x0, float y0, float z0, float x1, float y1, float z1,
	                             int sides, int a, int r, int g, int b) {
		if ((sides & GeometryMasks.Quad.DOWN) != 0) {
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Quad.UP) != 0) {
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Quad.NORTH) != 0) {
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Quad.SOUTH) != 0) {
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Quad.WEST) != 0) {
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Quad.EAST) != 0) {
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
		}
	}

	public static void drawLines(BufferBuilder buffer, float x0, float y0, float z0, float x1, float y1, float z1,
	                             int sides, int argb) {
		int a = (argb >>> 24) & 0xFF;
		int r = (argb >>> 16) & 0xFF;
		int g = (argb >>> 8) & 0xFF;
		int b = argb & 0xFF;

		drawLines(buffer, x0, y0, z0, x1, y1, z1, sides, a, r, g, b);
	}

	public static void drawLines(BufferBuilder buffer, float x0, float y0, float z0, float x1, float y1, float z1,
	                             int sides, int a, int r, int g, int b) {
		if ((sides & GeometryMasks.Line.DOWN_WEST) != 0) {
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.UP_WEST) != 0) {
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.DOWN_EAST) != 0) {
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.UP_EAST) != 0) {
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.DOWN_NORTH) != 0) {
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.UP_NORTH) != 0) {
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.DOWN_SOUTH) != 0) {
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.UP_SOUTH) != 0) {
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.NORTH_WEST) != 0) {
			buffer.addVertex(x0, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.NORTH_EAST) != 0) {
			buffer.addVertex(x1, y0, z0).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z0).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.SOUTH_WEST) != 0) {
			buffer.addVertex(x0, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x0, y1, z1).setColor(r, g, b, a);
		}

		if ((sides & GeometryMasks.Line.SOUTH_EAST) != 0) {
			buffer.addVertex(x1, y0, z1).setColor(r, g, b, a);
			buffer.addVertex(x1, y1, z1).setColor(r, g, b, a);
		}
	}

	public void setTranslation(PoseStack poseStack, double x, double y, double z) {
		poseStack.translate(x, y, z);
	}

	public BufferBuilder beginQuads() {
		return begin(VertexFormat.Mode.QUADS);
	}

	public BufferBuilder begin(VertexFormat.Mode mode) {
		return begin(mode, DefaultVertexFormat.POSITION_COLOR);
	}

	public BufferBuilder beginLines() {
		return begin(VertexFormat.Mode.LINES);
	}

	public void setDelta(double delta) {
	}
}
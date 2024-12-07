package com.github.lunatrius.core.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class GeometryTessellator extends Tessellator {
	private static GeometryTessellator instance = null;

	private static double deltaS = 0;
	private double delta = 0;

	public GeometryTessellator() {
		this(0x200000);
	}

	public GeometryTessellator( int size) {
		super(size);
	}

	public static GeometryTessellator getInstance() {
		if (instance == null) {
			instance = new GeometryTessellator();
		}

		return instance;
	}

	public static void setStaticDelta( double delta) {
		deltaS = delta;
	}

	public static void drawCuboid( BufferBuilder buffer,  BlockPos pos,  int sides,  int argb) {
		drawCuboid(buffer, pos, pos, sides, argb);
	}

	public static void drawCuboid( BufferBuilder buffer,  BlockPos begin,  BlockPos end,
	                               int sides,
	                               int argb) {
		drawCuboid(buffer, begin, end, sides, argb, GeometryTessellator.deltaS);
	}

	private static void drawCuboid( BufferBuilder buffer,  BlockPos begin,  BlockPos end,
	                                int sides,  int argb,  double delta) {
		if (buffer.getCurrentElement().getType().getGlConstant() == -1 || sides == 0) {
			return;
		}


		 double x0 = begin.getX() - delta;
		 double y0 = begin.getY() - delta;
		 double z0 = begin.getZ() - delta;
		 double x1 = end.getX() + 1 + delta;
		 double y1 = end.getY() + 1 + delta;
		 double z1 = end.getZ() + 1 + delta;

		switch (buffer.getCurrentElement().getType().getGlConstant()) {
			case GL11.GL_QUADS:
				drawQuads(buffer, x0, y0, z0, x1, y1, z1, sides, argb);
				break;

			case GL11.GL_LINES:
				drawLines(buffer, x0, y0, z0, x1, y1, z1, sides, argb);
				break;

			default:
				throw new IllegalStateException("Unsupported mode!");
		}
	}

	public static void drawQuads( BufferBuilder buffer,  double x0,  double y0,  double z0,
	                              double x1,  double y1,  double z1,  int sides,  int argb) {
		 int a = (argb >>> 24) & 0xFF;
		 int r = (argb >>> 16) & 0xFF;
		 int g = (argb >>> 8) & 0xFF;
		 int b = argb & 0xFF;

		drawQuads(buffer, x0, y0, z0, x1, y1, z1, sides, a, r, g, b);
	}

	public static void drawQuads( BufferBuilder buffer,  double x0,  double y0,  double z0,
	                              double x1,  double y1,  double z1,  int sides,  int a,
	                              int r,  int g,  int b) {
		if ((sides & GeometryMasks.Quad.DOWN) != 0) {
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Quad.UP) != 0) {
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Quad.NORTH) != 0) {
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Quad.SOUTH) != 0) {
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Quad.WEST) != 0) {
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Quad.EAST) != 0) {
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		}
	}

	public static void drawLines( BufferBuilder buffer,  double x0,  double y0,  double z0,
	                              double x1,  double y1,  double z1,  int sides,  int argb) {
		 int a = (argb >>> 24) & 0xFF;
		 int r = (argb >>> 16) & 0xFF;
		 int g = (argb >>> 8) & 0xFF;
		 int b = argb & 0xFF;

		drawLines(buffer, x0, y0, z0, x1, y1, z1, sides, a, r, g, b);
	}

	public static void drawLines( BufferBuilder buffer,  double x0,  double y0,  double z0,
	                              double x1,  double y1,  double z1,  int sides,  int a,
	                              int r,  int g,  int b) {
		if ((sides & GeometryMasks.Line.DOWN_WEST) != 0) {
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.UP_WEST) != 0) {
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.DOWN_EAST) != 0) {
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.UP_EAST) != 0) {
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.DOWN_NORTH) != 0) {
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.UP_NORTH) != 0) {
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.DOWN_SOUTH) != 0) {
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.UP_SOUTH) != 0) {
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.NORTH_WEST) != 0) {
			buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.NORTH_EAST) != 0) {
			buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.SOUTH_WEST) != 0) {
			buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
		}

		if ((sides & GeometryMasks.Line.SOUTH_EAST) != 0) {
			buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
			buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		}
	}

	public void setTranslation(MatrixStack matrixStack,  double x,  double y,  double z) {
		matrixStack.translate(x, y, z);
	}

	public void beginQuads() {
		begin(GL11.GL_QUADS);
	}

	public void begin( int mode) {
		getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
	}

	public void beginLines() {
		begin(GL11.GL_LINES);
	}

	public void setDelta( double delta) {
		this.delta = delta;
	}

	public void drawCuboid( BlockPos pos,  int sides,  int argb) {
		drawCuboid(pos, pos, sides, argb);
	}

	public void drawCuboid( BlockPos begin,  BlockPos end,  int sides,  int argb) {
		drawCuboid(getBuffer(), begin, end, sides, argb, this.delta);
	}
}

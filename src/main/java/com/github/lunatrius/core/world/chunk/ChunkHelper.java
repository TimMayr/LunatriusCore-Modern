package com.github.lunatrius.core.world.chunk;

import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class ChunkHelper {
	private static final Random RANDOM = new Random();

	public static boolean isSlimeChunk( long seed,  BlockPos pos) {
		 int x = pos.getX() >> 4;
		 int z = pos.getZ() >> 4;
		RANDOM.setSeed(seed + ((long) x * x * 4987142) + (x * 5947611L) + (long) z * z * 4392871L + (z * 389711L)
		               ^ 987234911L);
		return RANDOM.nextInt(10) == 0;
	}
}

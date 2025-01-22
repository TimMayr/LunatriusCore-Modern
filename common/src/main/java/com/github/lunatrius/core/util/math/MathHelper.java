package com.github.lunatrius.core.util.math;

public class MathHelper {
	/**
	 * Clamps the given value between the min and max values.
	 *
	 * @param value
	 * 		the value to be clamped
	 * @param min
	 * 		the minimum value
	 * @param max
	 * 		the maximum value
	 *
	 * @return the clamped value
	 */
	public static int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
}
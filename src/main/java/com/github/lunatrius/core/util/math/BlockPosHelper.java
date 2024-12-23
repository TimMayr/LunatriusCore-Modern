package com.github.lunatrius.core.util.math;

import com.google.common.collect.AbstractIterator;
import net.minecraft.core.BlockPos;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class BlockPosHelper {
	public static Iterable<MBlockPos> getAllInBox(BlockPos from, BlockPos to) {
		return getAllInBox(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}

	public static Iterable<MBlockPos> getAllInBox(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		BlockPos posMin = new BlockPos(Math.min(fromX, toX), Math.min(fromY, toY), Math.min(fromZ, toZ));
		BlockPos posMax = new BlockPos(Math.max(fromX, toX), Math.max(fromY, toY), Math.max(fromZ, toZ));
		return new Iterable<>() {
			@Override
			@Nonnull
			public Iterator<MBlockPos> iterator() {
				return new AbstractIterator<>() {
					private MBlockPos pos = null;
					private int x;
					private int y;
					private int z;

					@Override
					protected MBlockPos computeNext() {
						if (this.pos == null) {
							this.x = posMin.getX();
							this.y = posMin.getY();
							this.z = posMin.getZ();
							this.pos = new MBlockPos(this.x, this.y, this.z);
							return this.pos;
						}

						if (this.pos.equals(posMax)) {
							return this.endOfData();
						}

						if (this.x < posMax.getX()) {
							this.x++;
						} else if (this.y < posMax.getY()) {
							this.x = posMin.getX();
							this.y++;
						} else if (this.z < posMax.getZ()) {
							this.x = posMin.getX();
							this.y = posMin.getY();
							this.z++;
						}

						this.pos.x = this.x;
						this.pos.y = this.y;
						this.pos.z = this.z;
						return this.pos;
					}
				};
			}
		};
	}

	public static Iterable<MBlockPos> getAllInBoxXZY(BlockPos from, BlockPos to) {
		return getAllInBoxXZY(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}

	public static Iterable<MBlockPos> getAllInBoxXZY(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		BlockPos posMin = new BlockPos(Math.min(fromX, toX), Math.min(fromY, toY), Math.min(fromZ, toZ));
		BlockPos posMax = new BlockPos(Math.max(fromX, toX), Math.max(fromY, toY), Math.max(fromZ, toZ));
		return new Iterable<>() {
			@Override
			@Nonnull
			public Iterator<MBlockPos> iterator() {
				return new AbstractIterator<>() {
					private MBlockPos pos = null;
					private int x;
					private int y;
					private int z;

					@Override
					protected MBlockPos computeNext() {
						if (this.pos == null) {
							this.x = posMin.getX();
							this.y = posMin.getY();
							this.z = posMin.getZ();
							this.pos = new MBlockPos(this.x, this.y, this.z);
							return this.pos;
						}

						if (this.pos.equals(posMax)) {
							return this.endOfData();
						}

						if (this.x < posMax.getX()) {
							this.x++;
						} else if (this.z < posMax.getZ()) {
							this.x = posMin.getX();
							this.z++;
						} else if (this.y < posMax.getY()) {
							this.x = posMin.getX();
							this.z = posMin.getZ();
							this.y++;
						}

						this.pos.x = this.x;
						this.pos.y = this.y;
						this.pos.z = this.z;
						return this.pos;
					}
				};
			}
		};
	}

	public static Iterable<MBlockPos> getAllInBoxYXZ(BlockPos from, BlockPos to) {
		return getAllInBoxYXZ(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}

	public static Iterable<MBlockPos> getAllInBoxYXZ(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		BlockPos posMin = new BlockPos(Math.min(fromX, toX), Math.min(fromY, toY), Math.min(fromZ, toZ));
		BlockPos posMax = new BlockPos(Math.max(fromX, toX), Math.max(fromY, toY), Math.max(fromZ, toZ));
		return new Iterable<>() {
			@Override
			@Nonnull
			public Iterator<MBlockPos> iterator() {
				return new AbstractIterator<>() {
					private MBlockPos pos = null;
					private int x;
					private int y;
					private int z;

					@Override
					protected MBlockPos computeNext() {
						if (this.pos == null) {
							this.x = posMin.getX();
							this.y = posMin.getY();
							this.z = posMin.getZ();
							this.pos = new MBlockPos(this.x, this.y, this.z);
							return this.pos;
						}

						if (this.pos.equals(posMax)) {
							return this.endOfData();
						}

						if (this.y < posMax.getY()) {
							this.y++;
						} else if (this.x < posMax.getX()) {
							this.y = posMin.getY();
							this.x++;
						} else if (this.z < posMax.getZ()) {
							this.y = posMin.getY();
							this.x = posMin.getX();
							this.z++;
						}

						this.pos.x = this.x;
						this.pos.y = this.y;
						this.pos.z = this.z;
						return this.pos;
					}
				};
			}
		};
	}
}
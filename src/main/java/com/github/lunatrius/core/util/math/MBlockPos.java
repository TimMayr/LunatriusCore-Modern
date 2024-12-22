package com.github.lunatrius.core.util.math;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MBlockPos extends BlockPos {
	public int x;
	public int y;
	public int z;

	public MBlockPos() {
		this(0, 0, 0);
	}

	public MBlockPos(int x, int y, int z) {
		super(0, 0, 0);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MBlockPos(Entity source) {
		this(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos(double x, double y, double z) {
		this((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
	}

	public MBlockPos(Vec3 source) {
		this(source.x, source.y, source.z);
	}

	public MBlockPos(Vec3i source) {
		this(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos set(Entity source) {
		return set(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos set(double x, double y, double z) {
		return set((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
	}

	public MBlockPos set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public MBlockPos set(Vec3 source) {
		return set(source.x, source.y, source.z);
	}

	public MBlockPos set(Vec3i source) {
		return set(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos subtract(double x, double y, double z) {
		return subtract((int) x, (int) y, (int) z);
	}

	public MBlockPos subtract(int x, int y, int z) {
		return new MBlockPos(this.x - x, this.y - y, this.z - z);
	}

	public MBlockPos offset(double x, double y, double z) {
		return offset((int) x, (int) y, (int) z);
	}

	@Override
	public MBlockPos offset(int x, int y, int z) {
		return new MBlockPos(this.x + x, this.y + y, this.z + z);
	}

	@Override
	public MBlockPos offset(Vec3i vec) {
		return offset(vec.getX(), vec.getY(), vec.getZ());
	}

	public MBlockPos subtract(Vec3i vec) {
		return subtract(vec.getX(), vec.getY(), vec.getZ());
	}

	public MBlockPos multiply(int factor) {
		return new MBlockPos(this.x * factor, this.y * factor, this.z * factor);
	}

	@Override
	public MBlockPos above() {
		return above(1);
	}

	@Override
	public MBlockPos above(int n) {
		return relative(Direction.UP, n);
	}

	@Override
	public MBlockPos below() {
		return below(1);
	}

	@Override
	public MBlockPos below(int n) {
		return relative(Direction.DOWN, n);
	}

	@Override
	public MBlockPos north() {
		return north(1);
	}

	@Override
	public MBlockPos north(int n) {
		return relative(Direction.NORTH, n);
	}

	@Override
	public MBlockPos south() {
		return south(1);
	}

	@Override
	public MBlockPos south(int n) {
		return relative(Direction.SOUTH, n);
	}

	@Override
	public MBlockPos west() {
		return west(1);
	}

	@Override
	public MBlockPos west(int n) {
		return relative(Direction.WEST, n);
	}

	@Override
	public MBlockPos east() {
		return east(1);
	}

	@Override
	public MBlockPos east(int n) {
		return relative(Direction.EAST, n);
	}

	@Override
	public MBlockPos relative(Direction facing) {
		return relative(facing, 1);
	}

	@Override
	public MBlockPos relative(Direction facing, int n) {
		return new MBlockPos(this.x + facing.getStepX() * n, this.y + facing.getStepY() * n,
		                     this.z + facing.getStepZ() * n);
	}

	@Override
	public MBlockPos cross(Vec3i vec) {
		return new MBlockPos(this.y * vec.getZ() - this.z * vec.getY(), this.z * vec.getX() - this.x * vec.getZ(),
		                     this.x * vec.getY() - this.y * vec.getX());
	}

	@Override
	public BlockPos immutable() {
		return new BlockPos(this);
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getZ() {
		return this.z;
	}
}
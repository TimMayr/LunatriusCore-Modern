package com.github.lunatrius.core.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class MBlockPos extends BlockPos {
	public int x;
	public int y;
	public int z;

	public MBlockPos() {
		this(0, 0, 0);
	}

	public MBlockPos(Entity source) {
		this(source.getPosX(), source.getPosY(), source.getPosZ());
	}

	public MBlockPos(double x, double y, double z) {
		this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	public MBlockPos(int x, int y, int z) {
		super(0, 0, 0);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MBlockPos(Vec3d source) {
		this(source.x, source.y, source.z);
	}

	public MBlockPos(Vec3i source) {
		this(source.getX(), source.getY(), source.getZ());
	}

	public MBlockPos set(Entity source) {
		return set(source.getPosX(), source.getPosY(), source.getPosZ());
	}

	public MBlockPos set(double x, double y, double z) {
		return set(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	public MBlockPos set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public MBlockPos set(Vec3d source) {
		return set(source.x, source.y, source.z);
	}

	public MBlockPos set(Vec3i source) {
		return set(source.getX(), source.getY(), source.getZ());
	}

	@Override
	public MBlockPos add(double x, double y, double z) {
		return add(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	@Override
	public MBlockPos add(int x, int y, int z) {
		return new MBlockPos(this.x + x, this.y + y, this.z + z);
	}

	@Override
	public MBlockPos add(Vec3i vec) {
		return add(vec.getX(), vec.getY(), vec.getZ());
	}

	@SuppressWarnings("override")
	public MBlockPos subtract(Vec3i vec) {
		return subtract(vec.getX(), vec.getY(), vec.getZ());
	}

	public MBlockPos subtract(int x, int y, int z) {
		return new MBlockPos(this.x - x, this.y - y, this.z - z);
	}

	@Override
	public MBlockPos up() {
		return up(1);
	}

	@Override
	public MBlockPos up(int n) {
		return offset(Direction.UP, n);
	}

	@Override
	public MBlockPos down() {
		return down(1);
	}

	@Override
	public MBlockPos down(int n) {
		return offset(Direction.DOWN, n);
	}

	@Override
	public MBlockPos north() {
		return north(1);
	}

	@Override
	public MBlockPos north(int n) {
		return offset(Direction.NORTH, n);
	}

	@Override
	public MBlockPos south() {
		return south(1);
	}

	@Override
	public MBlockPos south(int n) {
		return offset(Direction.SOUTH, n);
	}

	@Override
	public MBlockPos west() {
		return west(1);
	}

	@Override
	public MBlockPos west(int n) {
		return offset(Direction.WEST, n);
	}

	@Override
	public MBlockPos east() {
		return east(1);
	}

	@Override
	public MBlockPos east(int n) {
		return offset(Direction.EAST, n);
	}

	@Override
	public MBlockPos offset(Direction facing) {
		return offset(facing, 1);
	}

	@Override
	public MBlockPos offset(Direction facing, int n) {
		return new MBlockPos(this.x + facing.getXOffset() * n, this.y + facing.getYOffset() * n,
		                     this.z + facing.getZOffset() * n);
	}

	@Override
	public MBlockPos crossProduct(Vec3i vec) {
		return new MBlockPos(this.y * vec.getZ() - this.z * vec.getY(), this.z * vec.getX() - this.x * vec.getZ(),
		                     this.x * vec.getY() - this.y * vec.getX());
	}

	@Override
	public BlockPos toImmutable() {
		return new BlockPos(this);
	}

	public MBlockPos multiply(int factor) {
		return new MBlockPos(this.x * factor, this.y * factor, this.z * factor);
	}

	public MBlockPos subtract(double x, double y, double z) {
		return subtract(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
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
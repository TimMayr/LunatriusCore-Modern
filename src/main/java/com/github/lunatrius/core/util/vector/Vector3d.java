package com.github.lunatrius.core.util.vector;

import net.minecraft.core.Position;

public class Vector3d extends Vector2d {
	public double z;

	public Vector3d() {
		this(0, 0, 0);
	}

	public Vector3d(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public Vector3d(Vector3d vec) {
		this(vec.x, vec.y, vec.z);
	}

	public Vector3d(double num) {
		this(num, num, num);
	}

	public Vector3d(Position pos) {
		this(pos.x(), pos.y(), pos.z());
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3d set(Vector3d vec) {
		return set(vec.x, vec.y, vec.z);
	}

	public Vector3d set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	@Override
	public Vector3d scale(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}

	@Override
	public Vector3d negate() {
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vector3d && equals((Vector3d) obj);
	}

	@Override
	public Vector3d clone() {
		return new Vector3d(this);
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s]", this.x, this.y, this.z);
	}

	public boolean equals(Vector3d vec) {
		return equals(vec, DOUBLE_EPSILON);
	}

	public boolean equals(Vector3d vec, double epsilon) {
		return Math.abs(this.x - vec.x) < epsilon
				&& Math.abs(this.y - vec.y) < epsilon
				&& Math.abs(this.z - vec.z) < epsilon;
	}

	public double lengthTo(Vector3d vec) {
		return Math.sqrt(lengthSquaredTo(vec));
	}

	public double lengthSquaredTo(Vector3d vec) {
		return pow2(this.x - vec.x) + pow2(this.y - vec.y) + pow2(this.z - vec.z);
	}

	public double dot(Vector3d vec) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}

	public Vector3d add(Vector3d vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}

	public Vector3d add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3d sub(Vector3d vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}

	public Vector3d sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vector3i toVector3i() {
		return new Vector3i((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
	}

	public Vector3i toVector3i(Vector3i vec) {
		return vec.set((int) Math.floor(this.x), (int) Math.floor(this.y), (int) Math.floor(this.z));
	}

	public Vector3f toVector3f() {
		return new Vector3f((float) Math.floor(this.x), (float) Math.floor(this.y), (float) Math.floor(this.z));
	}

	public Vector3f toVector3f(Vector3f vec) {
		return vec.set((float) Math.floor(this.x), (float) Math.floor(this.y), (float) Math.floor(this.z));
	}
}
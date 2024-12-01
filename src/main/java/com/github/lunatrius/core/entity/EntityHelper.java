package com.github.lunatrius.core.entity;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.core.util.vector.Vector3i;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EntityHelper {
	public final static int WILDMARK = -1;

	public static int getItemCountInInventory(final IInventory inventory, final Item item) {
		return getItemCountInInventory(inventory, item, WILDMARK);
	}

	public static int getItemCountInInventory(final IInventory inventory, final Item item, final int itemDamage) {
		final int inventorySize = inventory.getSizeInventory();
		int count = 0;

		for (int slot = 0; slot < inventorySize; slot++) {
			final ItemStack itemStack = inventory.getStackInSlot(slot);

			if (itemStack.getItem() == item && (itemDamage == WILDMARK || itemDamage == itemStack.getDamage())) {
				count += itemStack.getCount();
			}
		}

		return count;
	}

	public static Vector3f getVector3fFromEntity(final Entity entity) {
		return new Vector3f((float) entity.getPosX(), (float) entity.getPosY(), (float) entity.getPosZ());
	}

	public static Vector3f getVector3fFromEntity(final Entity entity, final Vector3f vec) {
		return vec.set((float) entity.getPosX(), (float) entity.getPosY(), (float) entity.getPosZ());
	}

	public static Vector3i getVector3iFromEntity(final Entity entity) {
		return new Vector3i((int) Math.floor(entity.getPosX()), (int) Math.floor(entity.getPosY()),
		                    (int) Math.floor(entity.getPosZ()));
	}

	public static Vector3i getVector3iFromEntity(final Entity entity, final Vector3i vec) {
		return vec.set((int) Math.floor(entity.getPosX()), (int) Math.floor(entity.getPosY()),
		               (int) Math.floor(entity.getPosZ()));
	}
}

package com.github.lunatrius.core.entity;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.core.util.vector.Vector3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EntityHelper {
	public static final int WILDCARD = -1;

	public static int getItemCountInInventory(Inventory inventory, Item item) {
		return getItemCountInInventory(inventory, item, WILDCARD);
	}

	public static int getItemCountInInventory(Inventory inventory, Item item, int itemDamage) {
		int inventorySize = inventory.getContainerSize();
		int count = 0;

		for (int slot = 0; slot < inventorySize; slot++) {
			ItemStack itemStack = inventory.getItem(slot);

			if (itemStack.getItem() == item && (itemDamage == WILDCARD || itemDamage == itemStack.getDamageValue())) {
				count += itemStack.getCount();
			}
		}

		return count;
	}

	public static Vector3f getVector3fFromEntity(Entity entity) {
		return new Vector3f((float) entity.getX(), (float) entity.getY(), (float) entity.getZ());
	}

	public static Vector3f getVector3fFromEntity(Entity entity, Vector3f vec) {
		return vec.set((float) entity.getX(), (float) entity.getY(), (float) entity.getZ());
	}

	public static Vector3i getVector3iFromEntity(Entity entity) {
		return new Vector3i((int) Math.floor(entity.getX()), (int) Math.floor(entity.getY()),
		                    (int) Math.floor(entity.getZ()));
	}

	public static Vector3i getVector3iFromEntity(Entity entity, Vector3i vec) {
		return vec.set((int) Math.floor(entity.getX()), (int) Math.floor(entity.getY()),
		               (int) Math.floor(entity.getZ()));
	}
}
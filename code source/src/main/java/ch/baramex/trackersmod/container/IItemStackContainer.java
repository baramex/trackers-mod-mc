package ch.baramex.trackersmod.container;

import net.minecraft.item.ItemStack;

public interface IItemStackContainer {

	void syncPlayerItem(ItemStack item, String name);
}

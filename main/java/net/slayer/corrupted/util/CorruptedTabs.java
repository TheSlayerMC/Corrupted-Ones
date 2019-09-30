package net.slayer.corrupted.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.slayer.corrupted.CorruptedItems;

public class CorruptedTabs extends CreativeTabs {

	public static CorruptedTabs ctab = new CorruptedTabs();
	
	public CorruptedTabs() {
		super("corruptedones");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(CorruptedItems.corrupted_rod);
	}
	
}
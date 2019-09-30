package net.slayer.corrupted.util;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.corrupted.CorruptedItems;
import net.slayer.corrupted.CorruptedOnes;

public class ItemModFood extends ItemFood {

	protected String name;

	public ItemModFood(String name, String finalName){
		this(name, finalName, CorruptedTabs.ctab);
		this.name = name;
	}

	public ItemModFood(String name, String finalName, CreativeTabs tab){
		super(5, true);
		this.name = name;
		LangRegistry.addItem(name.toLowerCase(), finalName);
		setUnlocalizedName(name.toLowerCase());
		setCreativeTab(tab);
		CorruptedItems.itemNames.add(SlayerAPI.PREFIX + name.toLowerCase());
		CorruptedItems.items.add(this);
		setRegistryName(SlayerAPI.MOD_ID, name.toLowerCase());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flagIn) {
		
	}

	public void registerItemModel() {
		CorruptedOnes.proxy.registerItemRenderer(this, 0, name);
	}
}
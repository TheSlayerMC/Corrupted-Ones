package net.slayer.corrupted.util;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.corrupted.CorruptedItems;
import net.slayer.corrupted.CorruptedOnes;

public class ItemMod extends Item {

	protected int healAmount = 0;
	protected String name;

	public ItemMod(String name, String finalName){
		this(name, finalName, CorruptedTabs.ctab);
		this.name = name;
	}

	public ItemMod(String name, String finalName, CreativeTabs tab){
		this.name = name;
		LangRegistry.addItem(name.toLowerCase(), finalName);
		setUnlocalizedName(name.toLowerCase());
		setCreativeTab(tab);
		CorruptedItems.itemNames.add(SlayerAPI.PREFIX + name.toLowerCase());
		CorruptedItems.items.add(this);
		setRegistryName(SlayerAPI.MOD_ID, name.toLowerCase());
	}

	public ItemMod setHealAmount(int healAmount){
		this.healAmount = healAmount;
		return this;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		if(player.getHeldItemMainhand().getItem() instanceof ItemMod) {
			if(healAmount != 0){
				if(player.getHealth() < player.getMaxHealth()) {
					player.heal(healAmount);
					player.getHeldItemMainhand().shrink(1);
				}
			}
		}
		return super.onItemRightClick(worldIn, player, handIn);
	}

	public void spawnEntityIntoWorld(World w, EntityPlayer p, Entity entity, boolean magic, SoundEvent sound, boolean damage, ItemStack item, int dam) {
		if(!w.isRemote) {
			if(magic) w.spawnEntity(entity);
		}
		if(magic) {
			//JourneySounds.playSound(sound, w, p);
			if(damage) item.damageItem(dam, p);
		}
	}

	public void spawnEntityIntoWorld(World w, EntityPlayer p, Entity entity, SoundEvent sound, boolean damage, ItemStack item, int dam) {
		if(!w.isRemote) {
			w.spawnEntity(entity);
			//JourneySounds.playSound(sound, w, p);
			if(damage) item.damageItem(dam, p);
		}
	}

	public void spawnEntityIntoWorld(World w, EntityPlayer p, Entity entity, boolean magic, SoundEvent sound) {
		spawnEntityIntoWorld(w, p, entity, magic, sound, false, new ItemStack(Items.APPLE), 0);
	}


	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World player, List list){ }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flagIn) {
		//ItemDescription.addInformation(item, list);
		addInformation(item, world, list);
		addInformation(item, list);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack i, List l) { }

	public void registerItemModel() {
		CorruptedOnes.proxy.registerItemRenderer(this, 0, name);
	}
}
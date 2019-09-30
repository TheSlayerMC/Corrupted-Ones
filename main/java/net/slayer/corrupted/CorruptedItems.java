package net.slayer.corrupted;

import java.util.ArrayList;

import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.slayer.corrupted.util.ItemCorruptedPearl;
import net.slayer.corrupted.util.ItemMod;
import net.slayer.corrupted.util.ItemModFood;

public class CorruptedItems {

	public static Item corrupted_rod;
	public static Item corrupted_bone;
	public static Item corrupted_pearl;
	public static Item corrupted_eye;
	public static Item corrupted_flesh;
	public static Item corrupted_star;
	public static Item corrupted_powder;

	public static ArrayList<String> itemNames = new ArrayList<String>();
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static void init() {
		corrupted_rod = new ItemMod("corruptedblazerod", "Corrupted Blaze Rod");
		corrupted_bone = new ItemMod("corruptedbone", "Corrupted Bone");
		corrupted_star = new ItemMod("corruptednetherstar", "Corrupted Nether Star");
		corrupted_powder = new ItemMod("corruptedgunpowder", "Corrupted Gun Powder");
		corrupted_pearl = new ItemCorruptedPearl("corruptedenderpearl", "Corrupted Enderpearl");
		corrupted_eye = new ItemModFood("corruptedeye", "Corrupted Spider Eye").setPotionEffect(new PotionEffect(MobEffects.POISON, 600, 0), 0.8F);
		corrupted_flesh = new ItemModFood("corruptedflesh", "Corrupted Zombie Flesh").setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.8F);
	}	
}
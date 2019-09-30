package net.slayer.corrupted.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {

	public static Configuration cfg;

	public static void init(FMLPreInitializationEvent event) {
		cfg = new Configuration(new File(event.getModConfigurationDirectory() + "/CorruptedOnes.cfg"));
		cfg.load();
		dimensionInit();
		miscInit();
		cfg.save();
	}

	public static boolean keepLoadingDim;
	
	public static boolean JSON = false;
	
	public static int dimID;
	public static String dimBiome;

	public static int baseMobID, baseProjectileID;

	public static void dimensionInit() {
		dimBiome = cfg.get("Biome", "Withering Sands biome ID", 190).getString();
		keepLoadingDim = cfg.get("Dimension", "Keep loading Frozen Lands", false).getBoolean(false);
	
		baseMobID = cfg.get("Entity", "The starting ID for the mobs (only gets greater the more mobs this mod has registered)", 350).getInt();
		baseProjectileID = cfg.get("Entity", "The starting ID for the projectiles (only gets greater the more projectiles this mod has registered)", 500).getInt();
		
		JSON = cfg.getBoolean("Generate JSON", "Misc", JSON, "Enable recipe JSON generator");
		
	}
	

	public static void miscInit() {}
}
package net.slayer.corrupted.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.slayer.corrupted.CorruptedBlocks;
import net.slayer.corrupted.CorruptedItems;
import net.slayer.corrupted.util.Config;
import net.slayer.corrupted.util.EntityRegistry;
import net.slayer.corrupted.util.LangRegistry;
import net.slayer.corrupted.util.SlayerAPI;

public class CommonProxy {

	public void registerClient() { }
	public void clientInit(FMLInitializationEvent event) { }
	public void clientPreInit() { }

	public void preInit(FMLPreInitializationEvent event) {
		Config.init(event);
		CorruptedItems.init();
		CorruptedBlocks.init();
		EntityRegistry.init();
		
	}
	
	public void init(FMLInitializationEvent event) {

	}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) { }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) { }
    
	public void registerItemRenderer(Item itemBlock, int i, String name) { }
	
	public void registerEntityRenderer(Entity entity, int i, String name) { }
	
	public void registerVariantRenderer(Item item, int meta, String name, String id) {}
	
	public void postInit(FMLPostInitializationEvent event) { }
	
	public void serverStarting(FMLServerStartingEvent event) { }
	
	private void addOreDictionary() { }
	
}
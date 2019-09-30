package net.slayer.corrupted;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.slayer.corrupted.client.IHasModel;
import net.slayer.corrupted.util.SlayerAPI;

@EventBusSubscriber(modid=SlayerAPI.MOD_ID)
public class Registrys {

	public static final List<SoundEvent> SOUNDS = new ArrayList<>();

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for(int i = 0; i < CorruptedItems.items.size(); i++)
			event.getRegistry().registerAll(CorruptedItems.items.get(i));
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for(int i = 0; i < CorruptedBlocks.blocks.size(); i++)
			event.getRegistry().registerAll(CorruptedBlocks.blocks.get(i));
		//TileEntityHandler.register();
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for(Block block : CorruptedBlocks.blocks)
			if(block instanceof IHasModel)
				((IHasModel)block).registerModels(event);
		for(Item item : CorruptedItems.items)
			if(item instanceof IHasModel)
				((IHasModel)item).registerModels(event);
	}
}
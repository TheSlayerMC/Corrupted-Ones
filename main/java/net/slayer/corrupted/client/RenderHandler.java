package net.slayer.corrupted.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.corrupted.CorruptedBlocks;
import net.slayer.corrupted.CorruptedItems;
import net.slayer.corrupted.util.SlayerAPI;

@EventBusSubscriber
public class RenderHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRender(ModelRegistryEvent event) {
		for(Item i : CorruptedItems.items) 
			ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
		
		for(Block b : CorruptedBlocks.blocks) 
			ModelLoader.setCustomModelResourceLocation(SlayerAPI.toItem(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
	}
}
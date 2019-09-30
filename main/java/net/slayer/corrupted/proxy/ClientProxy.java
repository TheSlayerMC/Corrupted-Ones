package net.slayer.corrupted.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.slayer.corrupted.client.EntityRendering;
import net.slayer.corrupted.client.RenderHandler;
import net.slayer.corrupted.util.SlayerAPI;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerClient() {
		//NetworkRegistry.INSTANCE.registerGuiHandler(JITL.instance, new GuiHandler());
		//if(!SlayerAPI.DEVMODE) SlayerAPI.registerEvent(new UpdateCheckerEvent());
	}

	@Override
	public void clientPreInit() {
		SlayerAPI.registerEvent(new RenderHandler());
	}

	@Override
	public void clientInit(FMLInitializationEvent event) {
		EntityRendering.init();
	}
}
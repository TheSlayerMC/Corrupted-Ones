package net.slayer.corrupted.client;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.slayer.corrupted.entity.mob.EntityEnderSteve;

public class RenderEnderSteve extends RenderModMob {

	private final Random rnd = new Random();	

	public RenderEnderSteve(ModelBase model, ResourceLocation tex) {
		super(model, tex);
		//this.addLayer(new LayerEnderSteveEyes(this));
		this.addLayer(new LayerEnderSteveHeldBlock(this));
	}

	public void doRender(EntityEnderSteve entity, double x, double y, double z, float entityYaw, float partialTicks) {
		IBlockState iblockstate = entity.getHeldBlockState();
		ModelBase modelenderman = this.getMainModel();

		if (entity.isScreaming()) {
			double d0 = 0.02D;
			x += this.rnd.nextGaussian() * 0.02D;
			z += this.rnd.nextGaussian() * 0.02D;
		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

}
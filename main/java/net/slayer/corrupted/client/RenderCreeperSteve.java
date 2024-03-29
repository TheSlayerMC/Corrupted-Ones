package net.slayer.corrupted.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.corrupted.entity.mob.EntityCreeperSteve;
import net.slayer.corrupted.entity.mob.EntityModMob;

@SideOnly(Side.CLIENT)
public class RenderCreeperSteve extends RenderModMob {

	public RenderCreeperSteve(ModelBiped b, ResourceLocation r) {
		super(b, r);
		this.addLayer(new LayerCreeperSteve(this));
	}

	@Override
	protected void preRenderCallback(EntityModMob entitylivingbaseIn, float partialTickTime) {
		preRenderCallback((EntityCreeperSteve)entitylivingbaseIn, partialTickTime);
	}
	
	@Override
	protected int getColorMultiplier(EntityModMob entitylivingbaseIn, float lightBrightness, float partialTickTime) {
		return getColorMultiplier((EntityCreeperSteve)entitylivingbaseIn, lightBrightness, partialTickTime);
	}
	
	protected void preRenderCallback(EntityCreeperSteve entitylivingbaseIn, float partialTickTime) {
		float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
		float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		f = f * f;
		f = f * f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		GlStateManager.scale(f2, f3, f2);
	}

	protected int getColorMultiplier(EntityCreeperSteve entitylivingbaseIn, float lightBrightness, float partialTickTime) {
		float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

		if ((int)(f * 10.0F) % 2 == 0) {
			return 0;
		} else {
			int i = (int)(f * 0.2F * 255.0F);
			i = MathHelper.clamp(i, 0, 255);
			return i << 24 | 822083583;
		}
	}
}
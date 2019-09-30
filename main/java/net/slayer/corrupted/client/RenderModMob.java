package net.slayer.corrupted.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.slayer.corrupted.entity.mob.EntityModMob;

public class RenderModMob extends RenderLiving<EntityModMob> {

	private ResourceLocation texture;

	public RenderModMob(ModelBase model, float shadow, ResourceLocation tex) {
		super(Minecraft.getMinecraft().getRenderManager(), model, shadow);
        this.addLayer(new LayerHeldItem(this));
		texture = tex;
	}

	public RenderModMob(ModelBase model, ResourceLocation tex) {
		this(model, 0.5F, tex);
	}
	
	@Override
	public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityModMob entity) {
		return texture;
	}
}
package net.slayer.corrupted.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.slayer.corrupted.entity.EntityCorruptedEnderPearl;
import net.slayer.corrupted.entity.mob.EntityBlazeSteve;
import net.slayer.corrupted.entity.mob.EntityCreeperSteve;
import net.slayer.corrupted.entity.mob.EntityEnderSteve;
import net.slayer.corrupted.entity.mob.EntitySkeletonSteve;
import net.slayer.corrupted.entity.mob.EntitySpiderSteve;
import net.slayer.corrupted.entity.mob.EntityWitherSteve;
import net.slayer.corrupted.entity.mob.EntityZombiePigSteve;
import net.slayer.corrupted.entity.mob.EntityZombieSteve;
import net.slayer.corrupted.util.SlayerAPI;

public class EntityRendering {

	public static void init() {

		RenderingRegistry.registerEntityRenderingHandler(EntityCorruptedEnderPearl.class, new RenderProjectile(new ResourceLocation(SlayerAPI.PREFIX + "textures/projectile/pearl.png")));

		RenderingRegistry.registerEntityRenderingHandler(EntityZombieSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/zombiesteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperSteve.class, new RenderCreeperSteve(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/creepersteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderSteve.class, new RenderEnderSteve(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/endersteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/spidersteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityZombiePigSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/zombiepigsteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/blazesteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/withersteve.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonSteve.class, new RenderModMob(new ModelBiped(), new ResourceLocation(SlayerAPI.PREFIX + "textures/models/mobs/skeletonsteve.png")));
	}
}
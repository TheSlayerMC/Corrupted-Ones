package net.slayer.corrupted.util;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.slayer.corrupted.entity.EntityCorruptedEnderPearl;
import net.slayer.corrupted.entity.mob.EntityBlazeSteve;
import net.slayer.corrupted.entity.mob.EntityCreeperSteve;
import net.slayer.corrupted.entity.mob.EntityEnderSteve;
import net.slayer.corrupted.entity.mob.EntitySkeletonSteve;
import net.slayer.corrupted.entity.mob.EntitySpiderSteve;
import net.slayer.corrupted.entity.mob.EntityWitherSteve;
import net.slayer.corrupted.entity.mob.EntityZombiePigSteve;
import net.slayer.corrupted.entity.mob.EntityZombieSteve;

public class EntityRegistry {

	public static void init() {
		registerProjectiles();
		registerEntitys();
		registerSpawns();
	}

	public static void registerEntitys() {
		SlayerAPI.registerOverworldMob(EntityZombieSteve.class, "zombiesteve", "Zombie Steve");
		SlayerAPI.registerOverworldMob(EntityBlazeSteve.class, "blazesteve", "Blaze Steve");
		SlayerAPI.registerOverworldMob(EntityCreeperSteve.class, "creepersteve", "Creeper Steve");
		SlayerAPI.registerOverworldMob(EntityEnderSteve.class, "endersteve", "Ender Steve");
		SlayerAPI.registerOverworldMob(EntitySkeletonSteve.class, "skeletonsteve", "Skeleton Steve");
		SlayerAPI.registerOverworldMob(EntitySpiderSteve.class, "spidersteve", "Spider Steve");
		SlayerAPI.registerOverworldMob(EntityWitherSteve.class, "withersteve", "Wither Steve");
		SlayerAPI.registerOverworldMob(EntityZombiePigSteve.class, "zombiepigsteve", "Zombie Pigman Steve");
	}

	public static void registerProjectiles() {
		SlayerAPI.registerProjectile(EntityCorruptedEnderPearl.class, "corruptedones:entitycorruptedpearl");
	}
	
	public static void registerSpawns() {
		for(Biome b : Biome.REGISTRY) {
			int amount = 3;
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityZombieSteve.class, amount + 1, 1, 4, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityBlazeSteve.class, amount - 1, 1, 2, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityCreeperSteve.class, amount, 1, 1, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityEnderSteve.class, amount, 1, 1, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntitySkeletonSteve.class, amount + 1, 1, 3, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntitySpiderSteve.class, amount + 2, 1, 3, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityWitherSteve.class, amount - 2, 1, 1, EnumCreatureType.MONSTER, b);
			net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(EntityZombiePigSteve.class, amount, 1, 2, EnumCreatureType.MONSTER, b);
		}
	}
}
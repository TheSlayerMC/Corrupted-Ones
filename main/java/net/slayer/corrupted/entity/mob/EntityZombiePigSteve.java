package net.slayer.corrupted.entity.mob;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntityZombiePigSteve extends EntityModMob {

	public EntityZombiePigSteve(World par1World) {
		super(par1World);
		addAttackingAI();
		addBasicAI();
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return s.baseDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.overworldHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_flesh;
	}

}
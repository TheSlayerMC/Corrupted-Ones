package net.slayer.corrupted.entity.mob;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntityZombieSteve extends EntityModMob {

	public EntityZombieSteve(World par1World) {
		super(par1World);
		addAttackingAI();
		addBasicAI();
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return s.zombieDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.zombieHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_flesh;
	}

}
package net.slayer.corrupted.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntityWitherSteve extends EntityModMob {

	public EntityWitherSteve(World par1World) {
		super(par1World);
		addAttackingAI();
		addBasicAI();
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if(super.attackEntityAsMob(entityIn)) {
			if (entityIn instanceof EntityLivingBase) {
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2, 0));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return s.witherDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.witherHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_WITHER_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_WITHER_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_WITHER_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_star;
	}

}
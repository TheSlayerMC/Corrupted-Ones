package net.slayer.corrupted.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntityBlazeSteve extends EntityModMob {

	public EntityBlazeSteve(World par1World) {
		super(par1World);
		addAttackingAI();
		addBasicAI();
		isImmuneToFire = true;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if(super.attackEntityAsMob(entityIn)) {
			if(entityIn instanceof EntityLivingBase) {
				((EntityLivingBase)entityIn).setFire(2);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return s.blazeDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.blazeHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_rod;
	}

}
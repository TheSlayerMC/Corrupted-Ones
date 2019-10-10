package net.slayer.corrupted.entity.mob;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntitySpiderSteve extends EntityModMob {

    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySpider.class, DataSerializers.BYTE);
	
	public EntitySpiderSteve(World par1World) {
		super(par1World);
		addAttackingAI();
		addBasicAI();
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }

	@Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }
    
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    public void setInWeb() { }

    @Override
    public void fall(float distance, float damageMultiplier) { }
    
    @Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
    }

    public boolean isBesideClimbableBlock() {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

        if(climbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }

	@Override
	public double setAttackDamage(MobStats s) {
		return s.spiderDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.spiderHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_SPIDER_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_SPIDER_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_SPIDER_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_eye;
	}

}
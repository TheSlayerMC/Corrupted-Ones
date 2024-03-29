package net.slayer.corrupted.entity.mob;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.slayer.corrupted.CorruptedItems;

public class EntityEnderSteve extends EntityModMob {

	private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
	private static final AttributeModifier ATTACKING_SPEED_BOOST = (new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
	private static final Set<Block> CARRIABLE_BLOCKS = Sets.<Block>newIdentityHashSet();
	private static final DataParameter<Optional<IBlockState>> CARRIED_BLOCK = EntityDataManager.<Optional<IBlockState>>createKey(EntityEnderSteve.class, DataSerializers.OPTIONAL_BLOCK_STATE);
	private static final DataParameter<Boolean> SCREAMING = EntityDataManager.<Boolean>createKey(EntityEnderSteve.class, DataSerializers.BOOLEAN);
	private int lastCreepySound;
	private int targetChangeTime;

	public EntityEnderSteve(World par1World) {
		super(par1World);
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(10, new EntityEnderSteve.AIPlaceBlock(this));
		this.tasks.addTask(11, new EntityEnderSteve.AITakeBlock(this));
		this.targetTasks.addTask(1, new EntityEnderSteve.AIFindPlayer(this));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>() {
			public boolean apply(@Nullable EntityEndermite p_apply_1_) {
				return p_apply_1_.isSpawnedByPlayer();
			}
		}));
	}
	
	@Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (entitylivingbaseIn == null) {
            this.targetChangeTime = 0;
            this.dataManager.set(SCREAMING, Boolean.valueOf(false));
            iattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            this.targetChangeTime = this.ticksExisted;
            this.dataManager.set(SCREAMING, Boolean.valueOf(true));

            if (!iattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                iattributeinstance.applyModifier(ATTACKING_SPEED_BOOST);
            }
        }
    }

	@Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CARRIED_BLOCK, Optional.absent());
        this.dataManager.register(SCREAMING, Boolean.valueOf(false));
    }

    public void playEndermanSound() {
        if (this.ticksExisted >= this.lastCreepySound + 400) {
            this.lastCreepySound = this.ticksExisted;

            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, this.getSoundCategory(), 2.5F, 1.0F, false);
            }
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SCREAMING.equals(key) && this.isScreaming() && this.world.isRemote) {
            this.playEndermanSound();
        }

        super.notifyDataManagerChange(key);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        IBlockState iblockstate = this.getHeldBlockState();

        if (iblockstate != null) {
            compound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
            compound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        IBlockState iblockstate;

        if (compound.hasKey("carried", 8)) {
            iblockstate = Block.getBlockFromName(compound.getString("carried")).getStateFromMeta(compound.getShort("carriedData") & 65535);
        } else {
            iblockstate = Block.getBlockById(compound.getShort("carried")).getStateFromMeta(compound.getShort("carriedData") & 65535);
        }

        if (iblockstate == null || iblockstate.getBlock() == null || iblockstate.getMaterial() == Material.AIR) {
            iblockstate = null;
        }

        this.setHeldBlockState(iblockstate);
    }

    private boolean shouldAttackPlayer(EntityPlayer player) {
        ItemStack itemstack = player.inventory.armorInventory.get(3);

        if (itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {
            return false;
        } else {
            Vec3d vec3d = player.getLook(1.0F).normalize();
            Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + (double)this.getEyeHeight() - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
            double d0 = vec3d1.lengthVector();
            vec3d1 = vec3d1.normalize();
            double d1 = vec3d.dotProduct(vec3d1);
            return d1 > 1.0D - 0.025D / d0 ? player.canEntityBeSeen(this) : false;
        }
    }

    @Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        this.isJumping = false;
        super.onLivingUpdate();
    }

    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        if (this.world.isDaytime() && this.ticksExisted >= this.targetChangeTime + 600) {
            float f = this.getBrightness();

            if (f > 0.5F && this.world.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setAttackTarget((EntityLivingBase)null);
                this.teleportRandomly();
            }
        }

        super.updateAITasks();
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(d1, d2, d3);
    }

    private boolean teleportTo(double x, double y, double z) {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag) {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }

        return flag;
    }

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isScreaming() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_ENDERMEN_AMBIENT;
	}

	@Override
	public double setAttackDamage(MobStats s) {
		return s.enderDamage;
	}

	@Override
	public double setMaxHealth(MobStats s) {
		return s.enderHealth;
	}

	@Override
	public SoundEvent setLivingSound() {
		return SoundEvents.ENTITY_ENDERMEN_AMBIENT;
	}

	@Override
	public SoundEvent setHurtSound() {
		return SoundEvents.ENTITY_ENDERMEN_HURT;
	}

	@Override
	public SoundEvent setDeathSound() {
		return SoundEvents.ENTITY_ENDERMEN_DEATH;
	}

	@Override
	public Item getItemDropped() {
		return CorruptedItems.corrupted_pearl;
	}

	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
		super.dropEquipment(wasRecentlyHit, lootingModifier);
		IBlockState iblockstate = this.getHeldBlockState();

		if (iblockstate != null) {
			Item item = Item.getItemFromBlock(iblockstate.getBlock());
			int i = item.getHasSubtypes() ? iblockstate.getBlock().getMetaFromState(iblockstate) : 0;
			this.entityDropItem(new ItemStack(item, 1, i), 0.0F);
		}
	}

	public void setHeldBlockState(@Nullable IBlockState state) {
		this.dataManager.set(CARRIED_BLOCK, Optional.fromNullable(state));
	}

	@Nullable
	public IBlockState getHeldBlockState() {
		return (IBlockState)((Optional)this.dataManager.get(CARRIED_BLOCK)).orNull();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		else if (source instanceof EntityDamageSourceIndirect) {
			for (int i = 0; i < 64; ++i) {
				if (this.teleportRandomly()) {
					return true;
				}
			}

			return false;
		} else {
			boolean flag = super.attackEntityFrom(source, amount);

			if (source.isUnblockable() && this.rand.nextInt(10) != 0) {
				this.teleportRandomly();
			}

			return flag;
		}
	}

	public static void setCarriable(Block block, boolean canCarry) {
		if (canCarry) CARRIABLE_BLOCKS.add(block);
		else          CARRIABLE_BLOCKS.remove(block);
	}

	public static boolean getCarriable(Block block) {
		return CARRIABLE_BLOCKS.contains(block);
	}

	public boolean isScreaming() {
		return ((Boolean)this.dataManager.get(SCREAMING)).booleanValue();
	}

	static {
		CARRIABLE_BLOCKS.add(Blocks.GRASS);
		CARRIABLE_BLOCKS.add(Blocks.DIRT);
		CARRIABLE_BLOCKS.add(Blocks.SAND);
		CARRIABLE_BLOCKS.add(Blocks.GRAVEL);
		CARRIABLE_BLOCKS.add(Blocks.YELLOW_FLOWER);
		CARRIABLE_BLOCKS.add(Blocks.RED_FLOWER);
		CARRIABLE_BLOCKS.add(Blocks.BROWN_MUSHROOM);
		CARRIABLE_BLOCKS.add(Blocks.RED_MUSHROOM);
		CARRIABLE_BLOCKS.add(Blocks.TNT);
		CARRIABLE_BLOCKS.add(Blocks.CACTUS);
		CARRIABLE_BLOCKS.add(Blocks.CLAY);
		CARRIABLE_BLOCKS.add(Blocks.PUMPKIN);
		CARRIABLE_BLOCKS.add(Blocks.MELON_BLOCK);
		CARRIABLE_BLOCKS.add(Blocks.MYCELIUM);
		CARRIABLE_BLOCKS.add(Blocks.NETHERRACK);
	}

	static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
		private final EntityEnderSteve enderman;
		private EntityPlayer player;
		private int aggroTime;
		private int teleportTime;

		public AIFindPlayer(EntityEnderSteve p_i45842_1_) {
			super(p_i45842_1_, EntityPlayer.class, false);
			this.enderman = p_i45842_1_;
		}

		@Override
		public boolean shouldExecute() {
			double d0 = this.getTargetDistance();
			this.player = this.enderman.world.getNearestAttackablePlayer(this.enderman.posX, this.enderman.posY, this.enderman.posZ, d0, d0, (Function)null, new Predicate<EntityPlayer>() {
				public boolean apply(@Nullable EntityPlayer p_apply_1_) {
					return p_apply_1_ != null && AIFindPlayer.this.enderman.shouldAttackPlayer(p_apply_1_);
				}
			});
			return this.player != null;
		}

		@Override
		public void startExecuting() {
			this.aggroTime = 5;
			this.teleportTime = 0;
		}

		@Override
		public void resetTask() {
			this.player = null;
			super.resetTask();
		}

		@Override
		public boolean shouldContinueExecuting() {
			if (this.player != null) {
				if (!this.enderman.shouldAttackPlayer(this.player)) {
					return false;
				} else {
					this.enderman.faceEntity(this.player, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.targetEntity != null && ((EntityPlayer)this.targetEntity).isEntityAlive() ? true : super.shouldContinueExecuting();
			}
		}

		@Override
		public void updateTask() {
			if (this.player != null) {
				if (--this.aggroTime <= 0) {
					this.targetEntity = this.player;
					this.player = null;
					super.startExecuting();
				}
			} else {
				if (this.targetEntity != null) {
					if (this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
						if (((EntityPlayer)this.targetEntity).getDistanceSq(this.enderman) < 16.0D) {
							this.enderman.teleportRandomly();
						}

						this.teleportTime = 0;
					}
					else if (((EntityPlayer)this.targetEntity).getDistanceSq(this.enderman) > 256.0D && this.teleportTime++ >= 30 && this.enderman.teleportToEntity(this.targetEntity)) {
						this.teleportTime = 0;
					}
				}

				super.updateTask();
			}
		}
	}

	static class AIPlaceBlock extends EntityAIBase {
		private final EntityEnderSteve enderman;

		public AIPlaceBlock(EntityEnderSteve p_i45843_1_) {
			this.enderman = p_i45843_1_;
		}

		@Override
		public boolean shouldExecute() {
			if (this.enderman.getHeldBlockState() == null) {
				return false;
			}
			else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.enderman.world, this.enderman)) {
				return false;
			} else {
				return this.enderman.getRNG().nextInt(2000) == 0;
			}
		}

		@Override
		public void updateTask() {
			Random random = this.enderman.getRNG();
			World world = this.enderman.world;
			int i = MathHelper.floor(this.enderman.posX - 1.0D + random.nextDouble() * 2.0D);
			int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 2.0D);
			int k = MathHelper.floor(this.enderman.posZ - 1.0D + random.nextDouble() * 2.0D);
			BlockPos blockpos = new BlockPos(i, j, k);
			IBlockState iblockstate = world.getBlockState(blockpos);
			IBlockState iblockstate1 = world.getBlockState(blockpos.down());
			IBlockState iblockstate2 = this.enderman.getHeldBlockState();

			if (iblockstate2 != null && this.canPlaceBlock(world, blockpos, iblockstate2.getBlock(), iblockstate, iblockstate1)) {
				world.setBlockState(blockpos, iblockstate2, 3);
				this.enderman.setHeldBlockState((IBlockState)null);
			}
		}

		private boolean canPlaceBlock(World w, BlockPos p, Block b, IBlockState i, IBlockState ii) {
			if (!b.canPlaceBlockAt(w, p)) {
				return false;
			}
			else if (i.getMaterial() != Material.AIR) {
				return false;
			}
			else if (ii.getMaterial() == Material.AIR) {
				return false;
			} else {
				return ii.isFullCube();
			}
		}
	}

	static class AITakeBlock extends EntityAIBase {
		private final EntityEnderSteve enderman;

		public AITakeBlock(EntityEnderSteve p_i45841_1_) {
			this.enderman = p_i45841_1_;
		}

		@Override
		public boolean shouldExecute() {
			if (this.enderman.getHeldBlockState() != null) {
				return false;
			}
			else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.enderman.world, this.enderman)) {
				return false;
			} else {
				return this.enderman.getRNG().nextInt(20) == 0;
			}
		}

		@Override
		public void updateTask() {
			Random random = this.enderman.getRNG();
			World world = this.enderman.world;
			int i = MathHelper.floor(this.enderman.posX - 2.0D + random.nextDouble() * 4.0D);
			int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 3.0D);
			int k = MathHelper.floor(this.enderman.posZ - 2.0D + random.nextDouble() * 4.0D);
			BlockPos blockpos = new BlockPos(i, j, k);
			IBlockState iblockstate = world.getBlockState(blockpos);
			Block block = iblockstate.getBlock();
			RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((double)((float)MathHelper.floor(this.enderman.posX) + 0.5F), (double)((float)j + 0.5F), (double)((float)MathHelper.floor(this.enderman.posZ) + 0.5F)), new Vec3d((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F)), false, true, false);
			boolean flag = raytraceresult != null && raytraceresult.getBlockPos().equals(blockpos);

			if (EntityEnderSteve.CARRIABLE_BLOCKS.contains(block) && flag) {
				this.enderman.setHeldBlockState(iblockstate);
				world.setBlockToAir(blockpos);
			}
		}
	}

}
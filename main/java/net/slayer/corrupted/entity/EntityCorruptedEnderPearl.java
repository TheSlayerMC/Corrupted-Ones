package net.slayer.corrupted.entity;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCorruptedEnderPearl extends EntityThrowable {

	private EntityLivingBase perlThrower;

	public EntityCorruptedEnderPearl(World worldIn) {
		super(worldIn);
	}

	public EntityCorruptedEnderPearl(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		this.perlThrower = throwerIn;
	}

	@SideOnly(Side.CLIENT)
	public EntityCorruptedEnderPearl(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void registerFixesEnderPearl(DataFixer fixer) {
		EntityThrowable.registerFixesThrowable(fixer, "ThrownEnderpearl");
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		EntityLivingBase entitylivingbase = this.getThrower();

		if (result.entityHit != null) {
			if (result.entityHit == this.perlThrower) {
				return;
			}

		}

		if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();
			TileEntity tileentity = this.world.getTileEntity(blockpos);

			if (tileentity instanceof TileEntityEndGateway) {
				TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;

				if (entitylivingbase != null) {
					if (entitylivingbase instanceof EntityPlayerMP) {
						CriteriaTriggers.ENTER_BLOCK.trigger((EntityPlayerMP)entitylivingbase, this.world.getBlockState(blockpos));
					}

					tileentityendgateway.teleportEntity(entitylivingbase);
					this.setDead();
					return;
				}

				tileentityendgateway.teleportEntity(this);
				return;
			}
		}

		for (int i = 0; i < 32; ++i) {
			this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
		}

		if (!this.world.isRemote) {
			if (entitylivingbase instanceof EntityPlayerMP) {
				EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;

				if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping()) {
					net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
					if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { 
						if (this.rand.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
							EntityEndermite entityendermite = new EntityEndermite(this.world);
							entityendermite.setSpawnedByPlayer(true);
							entityendermite.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
							this.world.spawnEntity(entityendermite);
						}

						if(entitylivingbase.isRiding())
							entitylivingbase.dismountRidingEntity();

						entitylivingbase.setPositionAndUpdate(event.getTargetX(), event.getTargetY(), event.getTargetZ());
						entitylivingbase.fallDistance = 0.0F;
					}
				}
			}
			else if(entitylivingbase != null) {
				entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
				entitylivingbase.fallDistance = 0.0F;
			}
			this.setDead();
		}
	}

	@Override
	public void onUpdate() {
		EntityLivingBase entitylivingbase = this.getThrower();
		if(entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) {
			this.setDead();
		} else {
			super.onUpdate();
		}
	}

	@Override
	@Nullable
	public Entity changeDimension(int dimensionIn, net.minecraftforge.common.util.ITeleporter teleporter) {
		if(this.thrower.dimension != dimensionIn) 
			this.thrower = null;
		return super.changeDimension(dimensionIn, teleporter);
	}
}
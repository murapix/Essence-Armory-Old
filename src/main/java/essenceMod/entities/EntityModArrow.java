package essenceMod.entities;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.compatibility.ThaumcraftHandler;
import essenceMod.items.ItemModBow;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class EntityModArrow extends EntityArrow
{
	protected int xTile = -1;
	protected int yTile = -1;
	protected int zTile = -1;
	protected Block inTile;
	protected int inData;
	protected boolean inGround;
	protected int ticksInGround;
	protected int ticksInAir;
	protected double damage = 2.0D;
	protected int knockbackStrength;

	protected ItemStack bow;

	public EntityModArrow(World world, EntityLivingBase entity, float charge, ItemStack bow)
	{
		super(world, entity, charge);

		this.bow = bow;

		setDamage(getDamage() + (ItemModBow.getLevel(bow) / 5));
		float physDamage = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponPhysicalDamage);
		physDamage *= ConfigHandler.isNormalDamagePercent ? getDamage() * ConfigHandler.normalDamageMulti : ConfigHandler.normalDamageAmount;
		physDamage *= ConfigHandler.normalBowMulti;
		setDamage(getDamage() + physDamage);

		int punch = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponKnockback);
		if (punch > 0) setKnockbackStrength(punch);

		if (Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDoT) > 0) setFire(100);
	}

	@Override
	public void onUpdate()
	{
		// Mostly just copying the vanilla code, from here...
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);

			if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}

		if (this.arrowShake > 0)
		{
			--this.arrowShake;
		}

		if (this.inGround)
		{
			int j = block.getMetaFromState(iblockstate);

			if (block == this.inTile && j == this.inData)
			{
				++this.ticksInGround;

				if (this.ticksInGround >= 1200)
				{
					this.setDead();
				}
			}
			else
			{
				this.inGround = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			++this.ticksInAir;
			Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
			Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3(this.posX, this.posY, this.posZ);
			vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null)
			{
				vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity1 = (Entity) list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
				{
					float f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null)
					{
						double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer))
				{
					movingobjectposition = null;
				}
			}
			// ... to here.
			float f2, f9;
			// This part is different, to allow the various bow upgrades to take effect. I have removed the randomness from the arrow damage, and added in extra code to use the upgrades.
			if (movingobjectposition != null)
			{
				Entity target = movingobjectposition.entityHit;
				if (target != null)
				{
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int k = MathHelper.ceiling_double_int((double) f2 * getDamage());

					if (this.getIsCritical())
					{
						k += k / 4 + 1;
					}

					DamageSource source = null;

					if (this.shootingEntity == null) source = DamageSource.causeArrowDamage(this, this);
					else source = DamageSource.causeArrowDamage(this, this.shootingEntity);

					if (this.isBurning() && !(target instanceof EntityEnderman))
					{
						int fire = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDoT);
						target.setFire(fire);
					}

					if (target.attackEntityFrom(source, (float) k))
					{
						if (target instanceof EntityLivingBase)
						{
							EntityLivingBase enemy = (EntityLivingBase) target;

							if (!this.worldObj.isRemote) enemy.setArrowCountInEntity(enemy.getArrowCountInEntity() + 1);

							if (this.knockbackStrength > 0)
							{
								f9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f9 > 0.0F) enemy.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6D / (double) f9, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6D / (double) f9);
							}

							if (this.shootingEntity instanceof EntityPlayer)
							{
								EntityPlayer player = (EntityPlayer) this.shootingEntity;
								float weaponDamage = k;
								DamageSource fireDamage = new EntityDamageSource("onFire", player);
								DamageSource magicDamage = new EntityDamageSource("magic", player);
								DamageSource witherDamage = new EntityDamageSource("wither", player);

								int pierce = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponArmorPiercing);
								int enemyArmor = enemy.getTotalArmorValue();
								if (enemyArmor >= 25) source = DamageSource.causeArrowDamage(this, player).setDamageBypassesArmor();

								float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
								if (pierce != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(source, weaponDamage * pierceMultiplier);
								}

								float fire = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDamage);
								fire *= ConfigHandler.isFireDamagePercent ? ConfigHandler.fireDamageMulti * weaponDamage : ConfigHandler.fireDamageAmount;
								fire *= ConfigHandler.fireBowMulti;
								if (fire != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(fireDamage, Math.round(fire * 4) / 4F);
								}

								float magic = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponMagicDamage);
								magic *= ConfigHandler.isMagicDamagePercent ? ConfigHandler.magicDamageMulti * weaponDamage : ConfigHandler.magicDamageAmount;
								magic *= ConfigHandler.magicBowMulti;
								if (magic != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(magicDamage, Math.round(magic * 4) / 4F);
								}

								float wither = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponWitherDamage);
								wither *= ConfigHandler.isWitherDamagePercent ? ConfigHandler.witherDamageMulti * weaponDamage : ConfigHandler.witherDamageAmount;
								wither *= ConfigHandler.witherBowMulti;
								if (wither != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(witherDamage, Math.round(wither * 4) / 4F);
								}

								int poison = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponMagicDoT);
								if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));

								int decay = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponWitherDoT);
								if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));

								int slow = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponSlow);
								if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));

								int blind = Upgrade.getUpgradeLevel(bow, UpgradeRegistry.WeaponBlind);
								if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));

								if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
								{
									try
									{
										// DraconicEvolutionHandler.doChaosDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
								{
									try
									{
										// ExUHandler.doDivineDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("Thaumcraft") && ConfigHandler.thaumcraftIntegration)
								{
									try
									{
										ThaumcraftHandler.doTaintDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
										ThaumcraftHandler.doTaintDoT(bow, enemy);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("arsmagica2") && ConfigHandler.arsMagicaIntegration)
								{
									try
									{
										// ArsMagicaHandler.doFrostDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
										// ArsMagicaHandler.doHolyDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
										// ArsMagicaHandler.doLightningDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
										// ArsMagicaHandler.doWindDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
										// ArsMagicaHandler.doEntangle(bow, enemy);
										// ArsMagicaHandler.doFrostSlow(bow, enemy);
									}
									catch (Exception e)
									{}
								}
							}

							if (this.shootingEntity != null && target != this.shootingEntity && target instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
						}

						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

						if (!(target instanceof EntityEnderman)) this.setDead();
					}
					else
					{
						BlockPos blockpos1 = movingobjectposition.getBlockPos();
						this.xTile = blockpos1.getX();
						this.yTile = blockpos1.getY();
						this.zTile = blockpos1.getZ();
						IBlockState iblockstate1 = this.worldObj.getBlockState(blockpos1);
						this.inTile = iblockstate1.getBlock();
						this.inData = this.inTile.getMetaFromState(iblockstate1);
						this.motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
						this.motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
						this.motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
						float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						this.posX -= this.motionX / (double) f5 * 0.05000000074505806D;
						this.posY -= this.motionY / (double) f5 * 0.05000000074505806D;
						this.posZ -= this.motionZ / (double) f5 * 0.05000000074505806D;
						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						this.inGround = true;
						this.arrowShake = 7;
						this.setIsCritical(false);

						if (this.inTile.getMaterial() != Material.air)
						{
							this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this);
						}
					}
				}

				if (this.getIsCritical())
				{
					for (int k = 0; k < 4; ++k)
					{
						this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) k / 4.0D, this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
					}
				}

				this.posX += this.motionX;
				this.posY += this.motionY;
				this.posZ += this.motionZ;
				float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

				for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f3) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
				{
					;
				}

				while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
				{
					this.prevRotationPitch += 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw < -180.0F)
				{
					this.prevRotationYaw -= 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
				{
					this.prevRotationYaw += 360.0F;
				}

				this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
				this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
				float f4 = 0.99F;
				float f6 = 0.05F;

				if (this.isInWater())
				{
					for (int i1 = 0; i1 < 4; ++i1)
					{
						float f8 = 0.25F;
						this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f8, this.posY - this.motionY * (double) f8, this.posZ - this.motionZ * (double) f8, this.motionX, this.motionY, this.motionZ, new int[0]);
					}

					f4 = 0.6F;
				}

				if (this.isWet())
				{
					this.extinguish();
				}

				this.motionX *= (double) f4;
				this.motionY *= (double) f4;
				this.motionZ *= (double) f4;
				this.motionY -= (double) f6;
				this.setPosition(this.posX, this.posY, this.posZ);
				this.doBlockCollisions();
			}
		}
	}
}

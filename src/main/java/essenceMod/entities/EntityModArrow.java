package essenceMod.entities;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import essenceMod.items.ItemModBow;
import essenceMod.registry.crafting.UpgradeRegistry;
import essenceMod.utility.UtilityHelper;

public class EntityModArrow extends EntityArrow
{
	protected int xLoc = -1;
	protected int yLoc = -1;
	protected int zLoc = -1;
	protected Block currentBlock;
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
		float physDamage = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponPhysicalDamage);
		physDamage *= ConfigHandler.isNormalDamagePercent ? getDamage() * ConfigHandler.normalDamageMulti : ConfigHandler.normalDamageAmount;
		physDamage *= ConfigHandler.normalBowMulti;
		setDamage(getDamage() + physDamage);

		int punch = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponKnockback);
		if (punch > 0) setKnockbackStrength(punch);

		if (UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDoT) > 0) setFire(100);
	}

	@Override
	public void onUpdate()
	{
		// Mostly just copying the vanilla code, from here...
		super.onEntityUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ + this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		Block block = this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)).getBlock();

		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(this.worldObj, new BlockPos(this.xLoc, this.yLoc, this.zLoc));
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, new BlockPos(this.xLoc, this.yLoc, this.zLoc), this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)));

			if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) this.inGround = true;
		}

		if (this.arrowShake > 0) --this.arrowShake;

		if (this.inGround)
		{
			int j = block.getMetaFromState(this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)));

			if (block == this.currentBlock && j == this.inData)
			{
				++this.ticksInGround;

				if (this.ticksInGround == 1200) this.setDead();
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

			if (movingobjectposition != null) vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);

			Entity entity = null;
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getCollisionBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			float f1;

			for (int i = 0; i < list.size(); ++i)
			{
				Entity temp = list.get(i);

				if (temp.canBeCollidedWith() && (temp != this.shootingEntity || this.ticksInAir >= 5))
				{
					f1 = 0.3F;
					AxisAlignedBB area = temp.getCollisionBoundingBox().expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition pos = area.calculateIntercept(vec31, vec3);

					if (pos != null)
					{
						double d1 = vec31.distanceTo(pos.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = temp;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) movingobjectposition = new MovingObjectPosition(entity);

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) movingobjectposition.entityHit;

				if (player.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(player)) movingobjectposition = null;
			}
			// ... to here.
			float f2, f4;
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
						int fire = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDoT);
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
								f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f4 > 0.0F) enemy.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6D / (double) f4, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6D / (double) f4);
							}

							if (this.shootingEntity instanceof EntityPlayer)
							{
								EntityPlayer player = (EntityPlayer) this.shootingEntity;
								float weaponDamage = k;
								DamageSource fireDamage = new EntityDamageSource("onFire", player);
								DamageSource magicDamage = new EntityDamageSource("magic", player);
								DamageSource witherDamage = new EntityDamageSource("wither", player);

								int pierce = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponArmorPiercing);
								int enemyArmor = enemy.getTotalArmorValue();
								if (enemyArmor >= 25) source = DamageSource.causeArrowDamage(this, player).setDamageBypassesArmor();
								
								float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
								if (pierce != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(source, weaponDamage * pierceMultiplier);
								}
								
								float fire = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponFireDamage);
								fire *= ConfigHandler.isFireDamagePercent ? ConfigHandler.fireDamageMulti * weaponDamage : ConfigHandler.fireDamageAmount;
								fire *= ConfigHandler.fireBowMulti;
								if (fire != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(fireDamage, Math.round(fire * 4) / 4F);
								}
								
								float magic = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponMagicDamage);
								magic *= ConfigHandler.isMagicDamagePercent ? ConfigHandler.magicDamageMulti * weaponDamage : ConfigHandler.magicDamageAmount;
								magic *= ConfigHandler.magicBowMulti;
								if (magic != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(magicDamage, Math.round(magic * 4) / 4F);
								}
								
								float wither = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponWitherDamage);
								wither *= ConfigHandler.isWitherDamagePercent ? ConfigHandler.witherDamageMulti * weaponDamage : ConfigHandler.witherDamageAmount;
								wither *= ConfigHandler.witherBowMulti;
								if (wither != 0)
								{
									enemy.hurtResistantTime = 0;
									enemy.attackEntityFrom(witherDamage, Math.round(wither * 4) / 4F);
								}
								
								int poison = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponMagicDoT);
								if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));
								
								int decay = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponWitherDoT);
								if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));
								
								int slow = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponSlow);
								if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));

								int blind = UtilityHelper.getUpgradeLevel(bow, UpgradeRegistry.WeaponBlind);
								if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));
								
								if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
								{
									try
									{
//										DraconicEvolutionHandler.doChaosDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
								{
									try
									{
//										ExUHandler.doDivineDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("Thaumcraft") && ConfigHandler.thaumcraftIntegration)
								{
									try
									{
//										ThaumcraftHandler.doTaintDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
//										ThaumcraftHandler.doTaintDoT(bow, enemy);
									}
									catch (Exception e)
									{}
								}

								if (Loader.isModLoaded("arsmagica2") && ConfigHandler.arsMagicaIntegration)
								{
									try
									{
//										ArsMagicaHandler.doFrostDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
//										ArsMagicaHandler.doHolyDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
//										ArsMagicaHandler.doLightningDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
//										ArsMagicaHandler.doWindDamage(bow, (EntityPlayer) player, enemy, weaponDamage, true);
//										ArsMagicaHandler.doEntangle(bow, enemy);
//										ArsMagicaHandler.doFrostSlow(bow, enemy);
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
						this.xLoc = movingobjectposition.getBlockPos().getX();
						this.yLoc = movingobjectposition.getBlockPos().getY();
						this.zLoc = movingobjectposition.getBlockPos().getZ();
						this.currentBlock = this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)).getBlock();
						this.inData = currentBlock.getMetaFromState(this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)));
						this.motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
						this.motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
						this.motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
						f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						this.posX -= this.motionX / (double) f2 * 0.05;
						this.posY -= this.motionY / (double) f2 * 0.05;
						this.posZ -= this.motionZ / (double) f2 * 0.05;
						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						this.inGround = true;
						this.arrowShake = 7;
						this.setIsCritical(false);

						if (this.currentBlock.getMaterial() != Material.air) this.currentBlock.onEntityCollidedWithBlock(this.worldObj, new BlockPos(this.xLoc, this.yLoc, this.zLoc), this.worldObj.getBlockState(new BlockPos(this.xLoc, this.yLoc, this.zLoc)), this);
					}
				}
			}

			if (this.getIsCritical())
			{
				for (int i = 0; i < 4; ++i)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) i / 4.0D, this.posY + this.motionY * (double) i / 4.0D, this.posZ + this.motionZ * (double) i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
			float f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater())
			{
				for (int l = 0; l < 4; ++l)
				{
					f4 = 0.25F;
					this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
				}

				f3 = 0.8F;
			}

			if (this.isWet())
			{
				this.extinguish();
			}

			this.motionX *= (double) f3;
			this.motionY *= (double) f3;
			this.motionZ *= (double) f3;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();

		}
	}
}

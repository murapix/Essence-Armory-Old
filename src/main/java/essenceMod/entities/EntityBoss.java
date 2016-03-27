//package essenceMod.entities;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLiving;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.EnumCreatureAttribute;
//import net.minecraft.entity.IRangedAttackMob;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.ai.EntityAIHurtByTarget;
//import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
//import net.minecraft.entity.boss.IBossDisplayData;
//import net.minecraft.entity.monster.EntityMob;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.entity.projectile.EntityWitherSkull;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.DamageSource;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import essenceMod.registry.ModItems;
//
//public class EntityBoss extends EntityMob implements IBossDisplayData, IRangedAttackMob // Not implemented yet, please ignore
//{
//	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
//	{
//		public boolean isEntityApplicable(Entity entity)
//		{
//			return entity instanceof EntityPlayer;
//		}
//	};
//	
//	public EntityBoss(World world)
//	{
//		super(world);
//		setHealth(getMaxHealth());
//		setSize(3.0F, 3.0F);
//		getNavigator().setSpeed(0);
//		getNavigator().setBreakDoors(false);
//		getNavigator().setCanSwim(false);
//		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
//		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
//	}
//	
//	@Override
//	protected void entityInit()
//	{
//		super.entityInit();
//		dataWatcher.addObject(17, new Integer(0));
//		dataWatcher.addObject(18, new Integer(0));
//		dataWatcher.addObject(19, new Integer(0));
//		dataWatcher.addObject(20, new Integer(0));
//	}
//	
//	@Override
//	public void writeEntityToNBT(NBTTagCompound tag)
//	{
//		super.writeEntityToNBT(tag);
//		tag.setInteger("Invul", 0);
//	}
//	
//	@Override
//	public void readEntityFromNBT(NBTTagCompound tag)
//	{
//		super.readEntityFromNBT(tag);
//	}
//	
//	@Override
//	@SideOnly(Side.CLIENT)
//	public float getShadowSize()
//	{
//		return height / 8.0F;
//	}
//	
//	@Override
//	public String getLivingSound()
//	{
//		return "mob.enderman.idle";
//	}
//	
//	@Override
//	public String getHurtSound()
//	{
//		return "mob.enderman.hurt";
//	}
//	
//	@Override
//	public String getDeathSound()
//	{
//		return "mob.enderman.death";
//	}
//	
//	@Override
//	public void onLivingUpdate()
//	{
//		motionX = 0;
//		motionY = 0;
//		motionZ = 0;
//		double d1, d2, d3;
//		
//		if (!worldObj.isRemote)
//		{
//			if (getWatchedTargetId(0) > 0)
//			{
//				Entity entity = worldObj.getEntityByID(getWatchedTargetId(0));
//				
//				if (entity == null)
//				{
//					setHealth(getMaxHealth());
//				}
//			}
//		}
//	}
//
//	@Override
//	protected void updateAITasks()
//	{
//		int i;
//		
//		super.updateAITasks();
//	}
//
//	@Override
//	public void setInWeb()
//	{}
//	
//	@Override
//	public int getTotalArmorValue()
//	{
//		return 0;
//	}
//	
//	@Override
//	public void attackEntityWithRangedAttack(EntityLivingBase target, float damage)
//	{
//		EntityWitherSkull entityWitherSkull = new EntityWitherSkull(worldObj, this, posX, posY, posZ);
//		
//		entityWitherSkull.setInvulnerable(true);
//		double dx = target.posX - posX;
//		double dy = target.posY - posY;
//		double dz = target.posZ - posZ;
//		double rt = Math.sqrt(dx*dx + dy*dy + dz*dz);
//		entityWitherSkull.posX = posX + (dx / rt);
//		entityWitherSkull.posY = posY + (dy / rt);
//		entityWitherSkull.posZ = posZ + (dz / rt);
//		
//		worldObj.spawnEntityInWorld(entityWitherSkull);
//	}
//	
//	@Override
//	public boolean attackEntityFrom(DamageSource source, float amount)
//	{
//		if (isEntityInvulnerable()) return false;
//		else if (source == DamageSource.drown) return false;
//		else
//		{
//			Entity entity;
//			
//			entity = source.getSourceOfDamage();
//			if (entity instanceof EntityArrow) return false;
////			else if (entity instanceof tconstruct.weaponry.entity.ArrowEntity) return false;
//			
//			entity = source.getEntity();
//			if (entity != null && !(entity instanceof EntityPlayer)) return false;
//			else return super.attackEntityFrom(source, amount);
//		}
//	}
//	
//	@Override
//	protected void dropFewItems(boolean recentlyHit, int lootingLevel)
//	{
//		dropItem(ModItems.infusedIngot, 8 + lootingLevel);
//	}
//	
//	@Override
//	protected void despawnEntity()
//	{
//		entityAge = 0;
//	}
//	
//	@Override
//	protected void fall(float distance)
//	{}
//	
//	@Override
//	public void addPotionEffect(PotionEffect potion)
//	{}
//	
//	@Override
//	protected boolean isAIEnabled()
//	{
//		return true;
//	}
//	
//	@Override
//	protected void applyEntityAttributes()
//	{
//		super.applyEntityAttributes();
//		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
//		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
//		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(60.0D);
//	}
//	
//	public int getInt20()
//	{
//		return dataWatcher.getWatchableObjectInt(20);
//	}
//	
//	public void setInt20(int newValue)
//	{
//		dataWatcher.updateObject(20, Integer.valueOf(newValue));
//	}
//	
//	public int getWatchedTargetId(int offset)
//	{
//		return dataWatcher.getWatchableObjectInt(17 + offset);
//	}
//	
//	public void setWatchedTargetId(int offset, int newValue)
//	{
//		dataWatcher.updateObject(17 + offset, Integer.valueOf(newValue));
//	}
//	
//	@Override
//	public EnumCreatureAttribute getCreatureAttribute()
//	{
//		return EnumCreatureAttribute.UNDEFINED;
//	}
//	
//	@Override
//	public void mountEntity(Entity mount)
//	{
//		ridingEntity = null;
//	}
//}

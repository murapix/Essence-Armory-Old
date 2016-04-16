package essenceMod.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.codechicken.lib.vec.Vector3;

@SuppressWarnings("unused")
public class EntityBoss extends EntityMob implements IBossDisplayData // Not implemented yet, please ignore
{
	public static final int SPAWN_TICKS = 160;
	private static final float MAX_HP = 500F;
	private static final float RANGE = 11;

	public static final int MOB_SPAWN_COUNTDOWN = 80;
	public static final int FLAMEBLAST_COUNTDOWN = 300;
	public static final int FLAMEBLAST_DELAY = 60;
	public static final int WITHER_CLOUD_COUNTDOWN = 60;
	public static final int WITHER_CLOUD_DURATION = 300;
	public static final int IMMOBILIZE_COUNTDOWN = 300;
	public static final int IMMOBILIZE_DURATION = 30;
	public static final int CRYSTAL_COUNTDOWN = 60;
	public static final int CRYSTAL_DELAY = 20;

	private static final String TAG_SPAWN_INVUL_TIME = "invulTime";
	private static final String TAG_AGGRO = "aggro";
	private static final String TAG_SOURCE_X = "sourceX";
	private static final String TAG_SOURCE_Y = "sourceY";
	private static final String TAG_SOURCE_Z = "sourcesZ";
	private static final String TAG_MOB_SPAWN_COUNTDOWN = "mobSpawnCountdown";
	private static final String TAG_FLAMEBLAST_COUNTDOWN = "flameblastCountdown";
	private static final String TAG_WITHER_CLOUD_COUNTDOWN = "witherCloudCountdown";
	private static final String TAG_IMMOBILIZE_COUNTDOWN = "immobilizeCountdown";
	private static final String TAG_CRYSTAL_COUNTDOWN = "crystalCountdown";
	private static final String TAG_LEVEL = "level";
	private static final String TAG_PLAYER_COUNT = "playerCount";
	private static final String TAG_ATTACK_SELECTOR = "attackSelector";

	private ArrayList<UUID> playersWhoAttacked = new ArrayList<UUID>();
	private byte attackSelector = 0;

	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(20, 0); // Invul Time
		dataWatcher.addObject(21, (byte) 0); // Aggro
		dataWatcher.addObject(22, 0); // X Loc
		dataWatcher.addObject(23, 0); // Y Loc
		dataWatcher.addObject(24, 0); // Z Loc
		dataWatcher.addObject(25, 0); // Ticks between mob spawns
		dataWatcher.addObject(26, 0); // Level
		dataWatcher.addObject(27, 0); // Player count
		dataWatcher.addObject(28, 0); // Flameblast Cooldown
		dataWatcher.addObject(29, 0); // Wither Cloud Cooldown
		dataWatcher.addObject(30, 0); // Immobilize Cooldown
		dataWatcher.addObject(31, 0); // Crystal Cooldown
		dataWatcher.addObject(32, (byte) 0); // Attack Selector
	}
	
	public EntityBoss(World world, int level)
	{
		super(world);
		setSize(3F, 5F);
		isImmuneToFire = true;
		experienceValue = 500 * (level + 1);
	}

	@Override
	public void addPotionEffect(PotionEffect effect)
	{
		return;
	}

	public static boolean spawn(EntityPlayer player, ItemStack item, World world, BlockPos pos, int level)
	{
		if (isTruePlayer(player))
		{
			if (world.getDifficulty() == EnumDifficulty.PEACEFUL) { return false; }

			if (!hasProperArena(world, pos)) { return false; }

			item.stackSize--;
			if (world.isRemote) return true;

			EntityBoss boss = new EntityBoss(world, level);
			boss.setPosition(pos.getX() + 0.5, pos.getY() + 5, pos.getZ() + 0.5);
			boss.setHealth(1F);
			boss.setSource(pos);
			boss.setInvulTime(SPAWN_TICKS);
			boss.setMobSpawnTicks(MOB_SPAWN_COUNTDOWN);
			boss.setLevel(level);

			List<EntityPlayer> players = boss.getPlayersAround();
			int playerCount = 0;
			for (EntityPlayer p : players)
				if (isTruePlayer(p)) playerCount++;

			boss.setPlayerCount(playerCount);
			boss.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HP * playerCount * level);

			world.playSoundAtEntity(boss, "mob.enderdragon.growl", 10F, 0.1F);
			world.spawnEntityInWorld(boss);
			return true;
		}
		return false;
	}

	private static boolean hasProperArena(World world, BlockPos startPos)
	{
		int heightCheck = 6;
		int heightMin = 5;
		int range = (int) Math.ceil(RANGE);
		for (int i = -range; i <= range; i++)
		{
			for (int j = -range; j <= range; j++)
			{
				int air = 0;

				yCheck:
				{
					for (int k = heightCheck + heightMin + 1; k >= -heightCheck; k--)
					{
						BlockPos pos = startPos.add(i, k, j);
						boolean isAir = world.getBlockState(pos).getBlock().getCollisionBoundingBox(world, pos, world.getBlockState(pos)) == null;
						if (isAir) air++;
						else
						{
							if (k > heightCheck) continue;
							else if (air > 2) break yCheck;
							air = 0;
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public int getInvulTime()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}

	public boolean isAggroed()
	{
		return dataWatcher.getWatchableObjectByte(21) == 1;
	}

	public BlockPos getSource()
	{
		int x = dataWatcher.getWatchableObjectInt(22);
		int y = dataWatcher.getWatchableObjectInt(23);
		int z = dataWatcher.getWatchableObjectInt(24);
		return new BlockPos(x, y, z);
	}

	public int getMobSpawnTicks()
	{
		return dataWatcher.getWatchableObjectInt(25);
	}

	public int getLevel()
	{
		return dataWatcher.getWatchableObjectInt(26);
	}

	public int getPlayerCount()
	{
		return dataWatcher.getWatchableObjectInt(27);
	}
	
	public byte getAttackSelector()
	{
		return dataWatcher.getWatchableObjectByte(32);
	}

	public void setInvulTime(int time)
	{
		dataWatcher.updateObject(20, time);
	}

	public void setAggroed(boolean aggroed)
	{
		dataWatcher.updateObject(21, (byte) (aggroed ? 1 : 0));
	}

	public void setSource(BlockPos pos)
	{
		dataWatcher.updateObject(22, pos.getX());
		dataWatcher.updateObject(23, pos.getY());
		dataWatcher.updateObject(24, pos.getZ());
	}

	public void setMobSpawnTicks(int ticks)
	{
		dataWatcher.updateObject(25, ticks);
	}

	public void setLevel(int level)
	{
		dataWatcher.updateObject(26, level);
	}

	public void setPlayerCount(int count)
	{
		dataWatcher.updateObject(27, count);
	}
	
	public void setAttackSelector(byte selector)
	{
		dataWatcher.updateObject(32, selector);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger(TAG_SPAWN_INVUL_TIME, getInvulTime());
		compound.setBoolean(TAG_AGGRO, isAggroed());
		compound.setInteger(TAG_MOB_SPAWN_COUNTDOWN, getMobSpawnTicks());

		BlockPos source = getSource();
		compound.setInteger(TAG_SOURCE_X, source.getX());
		compound.setInteger(TAG_SOURCE_Y, source.getY());
		compound.setInteger(TAG_SOURCE_Z, source.getZ());

		compound.setInteger(TAG_LEVEL, getLevel());
		compound.setInteger(TAG_PLAYER_COUNT, getPlayerCount());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		setInvulTime(compound.getInteger(TAG_SPAWN_INVUL_TIME));
		setAggroed(compound.getBoolean(TAG_AGGRO));
		setMobSpawnTicks(compound.getInteger(TAG_MOB_SPAWN_COUNTDOWN));

		int x = compound.getInteger(TAG_SOURCE_X);
		int y = compound.getInteger(TAG_SOURCE_Y);
		int z = compound.getInteger(TAG_SOURCE_Z);
		setSource(new BlockPos(x, y, z));

		setLevel(compound.getInteger(TAG_LEVEL));
		if (compound.hasKey(TAG_PLAYER_COUNT)) setPlayerCount(compound.getInteger(TAG_PLAYER_COUNT));
		else setPlayerCount(1);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity entity = source.getEntity();
		if (entity == null) return false;
		if (!isTruePlayer(entity)) return false;
		if (source.isProjectile()) return false;
		if (source.getDamageType().equals("player")) return false;

		EntityPlayer player = (EntityPlayer) entity;
		if (!playersWhoAttacked.contains(player.getUniqueID())) playersWhoAttacked.add(player.getUniqueID());

		return super.attackEntityFrom(source, amount);
	}

	private static boolean isTruePlayer(Entity entity)
	{
		if (!(entity instanceof EntityPlayer)) return false;
		EntityPlayer player = (EntityPlayer) entity;
		String name = player.getName();
		return !(player instanceof FakePlayer || Pattern.compile("^(?:\\[.*\\])|(?:ComputerCraft)$").matcher(name).matches());
	}

	@Override
	public void damageEntity(DamageSource source, float amount)
	{
		super.damageEntity(source, amount);

		Entity attacker = source.getEntity();
		if (attacker != null)
		{
			Vector3 thisVector = Vector3.fromEntityCenter(this);
			Vector3 playerVector = Vector3.fromEntityCenter(attacker);
			Vector3 motionVector = thisVector.copy().sub(playerVector).copy().normalize().multiply(0.75);

			if (getHealth() > 0)
			{
				motionX = -motionVector.x;
				motionY = 0;
				motionZ = -motionVector.y;
			}
			setAggroed(true);
		}
	}

	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);

		worldObj.playSoundAtEntity(this, "random.explode", 20F, (1F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY, posZ, 1D, 0D, 0D);
	}

	@Override
	public void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HP);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100.0);
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

	@Override
	public void dropFewItems(boolean b, int i)
	{

	}

	public List<EntityPlayer> getPlayersAround()
	{
		BlockPos source = getSource();
		float range = 15;
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range, source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
		return players;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (ridingEntity != null)
		{
			if (ridingEntity.riddenByEntity != null) ridingEntity.riddenByEntity = null;
			ridingEntity = null;
		}

		if (!worldObj.isRemote && worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) setDead();

		BlockPos source = getSource();
		int level = getLevel();
		float range = RANGE;
		List<EntityPlayer> players = getPlayersAround();
		int playerCount = getPlayerCount();

		if (players.isEmpty() && !worldObj.playerEntities.isEmpty()) setDead();
		else
		{
			for (EntityPlayer player : players)
			{
				player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
			}
		}

		if (isDead) return;

		posX = source.getX() + 0.5;
		posY = source.getY();
		posZ = source.getZ() + 0.5;

		int invul = getInvulTime();
		int mobTicks = getMobSpawnTicks();
		byte attackSelector = getAttackSelector();
		boolean physicalAttack = attackSelector % 2 == 1;
		boolean fireAttack = (attackSelector / 2) % 2 == 1;
		boolean magicAttack = (attackSelector / 4) % 2 == 1;
		boolean witherAttack = (attackSelector / 8) % 2 == 1;
		if (physicalAttack)
		{
			
		}
		if (fireAttack)
		{
			worldObj.spawnParticle(EnumParticleTypes.FLAME, source.getX() + 0.5, source.getY() - 2, source.getZ() + 0.5, 0, 0, 0, 0);
		}
		if (magicAttack)
		{
			
		}
		if (witherAttack)
		{
			
		}
	}
}
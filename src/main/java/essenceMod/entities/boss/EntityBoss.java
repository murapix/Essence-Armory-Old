package essenceMod.entities.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class EntityBoss extends EntityMob implements IBossDisplayData // Not implemented yet, please ignore
{
	private ArrayList<BossAttack> attacks;

	private static final int SPAWN_TICKS = 160;
	private static final float BASE_HP = 500F;
	private static final int PLAYER_RANGE = 15;
	private static final int ARENA_RANGE = 11;
	private static final float BOSS_HEIGHT = 5;
	private static final float BOSS_WIDTH = 3;
	
	private static final String TAG_SPAWN_INVUL_TIME = "SpawnInvulTime";
	private static final String TAG_SOURCE_X = "SourceX";
	private static final String TAG_SOURCE_Y = "SourceY";
	private static final String TAG_SOURCE_Z = "SourceZ";
	private static final String TAG_LEVEL = "Level";

	private ArrayList<UUID> playersWhoAttacked = new ArrayList<UUID>();

	/**
	 * Default number of ticks between Flameblasts
	 */
	public static final int FB_DEF_TIMER = 200;
	/**
	 * Default number of ticks between the start of a Flameblast and the pop
	 */
	public static final int FB_DEF_DELAY = 60;
	/**
	 * Default damage (in half hearts) of a Flameblast
	 */
	public static final int FB_DEF_DMG = 5;

	/**
	 * Default number of ticks between Wither Cloud spawns
	 */
	public static final int WC_DEF_TIMER = 20;
	/**
	 * Default Wither Cloud duration in ticks
	 */
	public static final int WC_DEF_DUR = 200;
	/**
	 * Default Wither Cloud Wither effect strength
	 */
	public static final int WC_DEF_WITHER = 0;
	/**
	 * Default Wither Cloud Slow effect strength
	 */
	public static final int WC_DEF_SLOW = 1;

	/**
	 * Default number of ticks between Roots
	 */
	public static final int ROOT_DEF_TIMER = 300;
	/**
	 * Default Root duration in ticks
	 */
	public static final int ROOT_DEF_DUR = 20;

	/**
	 * Default number of ticks between Shards falling
	 */
	public static final int SHARD_DEF_TIMER = 120;
	/**
	 * Default number of ticks before Shards begin to fall
	 */
	public static final int SHARD_DEF_WIND = 20;
	/**
	 * Default number of ticks for the Shard to fall. Landing point is calculated at the here
	 */
	public static final int SHARD_DEF_DELAY = 10;
	/**
	 * Default damage per Shard
	 */
	public static final int SHARD_DEF_DMG = 5;

	/**
	 * Number of ticks before Selection Crystals are strike-able
	 */
	public static final int SELECTOR_INVINCIBILITY_TIMER = 60;

	@Override
	public void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(20, 0); // Invul Time
		dataWatcher.addObject(21, 0); // Y Loc
		dataWatcher.addObject(23, 0); // X Loc
		dataWatcher.addObject(22, 0); // Z Loc
		dataWatcher.addObject(24, 0); // Attack Selector
		dataWatcher.addObject(25, 0); // Boss Level
	}

	public EntityBoss(World world)
	{
		super(world);
		setSize(BOSS_WIDTH, BOSS_HEIGHT);
		isImmuneToFire = true;
	}

	public void setAttacks(BossAttack... attacks)
	{
		this.attacks = new ArrayList<BossAttack>();
		for (BossAttack attack : attacks)
			this.attacks.add(attack);
	}

	@Override
	public void addPotionEffect(PotionEffect effect)
	{
		return;
	}

	public boolean spawn(EntityPlayer player, World world, BlockPos pos)
	{
		if (isTruePlayer(player))
		{
			if (world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;

			if (!hasProperArena(world, pos, player)) return false;
			if (world.isRemote)
			{
				player.addChatComponentMessage(new ChatComponentText("Arena Complete"));
				return true;
			}

			this.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

			this.setHealth(1);
			this.setSource(pos);
			this.setInvulTime(SPAWN_TICKS);
			this.experienceValue = 500 * (getLevel() + 1);

			List<EntityPlayer> players = this.getPlayersAround();
			int playerCount = 0;
			for (EntityPlayer p : players)
				if (isTruePlayer(p)) playerCount++;
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(BASE_HP * playerCount * getLevel());

			world.playSoundAtEntity(this, "mob.enderdragon.growl", 10F, 0.1F);
			world.spawnEntityInWorld(this);
			setHealth(getMaxHealth());
			player.addChatComponentMessage(new ChatComponentText("World Shard Spawned.\nMax Health: " + this.getMaxHealth() + "\nCurrent Health: " + this.getHealth()));
			return true;
		}
		return false;
	}

	public static boolean hasProperArena(World world, BlockPos startPos, EntityPlayer player)
	{
		int vertDist = (int) BOSS_HEIGHT + 1;
		int horizDist = ARENA_RANGE;
		for (int i = -horizDist; i <= horizDist; i++)
		{
			for (int j = -horizDist; j <= horizDist; j++)
			{
				for (int k = 0; k <= vertDist; k++)
				{
					if (k == 0 && j == 0 && i == 0) continue;
					BlockPos pos = startPos.add(i, k, j);
					boolean isAir = world.getBlockState(pos).getBlock().getCollisionBoundingBox(world, pos, world.getBlockState(pos)) == null;
					if (!isAir)
					{
						player.addChatComponentMessage(new ChatComponentText("Found non-air block at " + pos));
						return false;
					}
				}
				BlockPos pos = startPos.add(i, -1, j);
				boolean isAir = world.getBlockState(pos).getBlock().getCollisionBoundingBox(world, pos, world.getBlockState(pos)) == null;
				if (isAir)
				{
					player.addChatComponentMessage(new ChatComponentText("Found air block at " + pos));
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity entity = source.getEntity();
		if (entity == null) return false;
		if (!isTruePlayer(entity)) return false;
		if (source.isProjectile()) return false;
		if (source.getDamageType().equals("player")) amount = 0;

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
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(BASE_HP);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100);
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
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
		float range = PLAYER_RANGE;
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
		List<EntityPlayer> players = getPlayersAround();

		// if (players.isEmpty() && !worldObj.playerEntities.isEmpty()) setDead();
		// else
		// {
		for (EntityPlayer player : players)
		{
			player.capabilities.isFlying = player.capabilities.isFlying && player.capabilities.isCreativeMode;
		}
		// }

		if (isDead) return;

		posX = source.getX() + 0.5;
		posY = source.getY() + 0.5;
		posZ = source.getZ() + 0.5;
		motionX = 0;
		motionY = 0;
		motionZ = 0;

		if (attacks != null && attacks.size() != 0)
		{
			attacks.get(0).update(players);
			attacks.get(0).drawParticles();
			int attackSelector = getAttackSelector();
			for (int i = 1; i < attacks.size(); i++)
			{
				if ((attackSelector / (int) Math.pow(2, i - 1)) % 2 == 1)
				{
					attacks.get(i).update(players);
					if (worldObj.isRemote) attacks.get(i).drawParticles();
				}
			}
		}
	}

	public int getInvulTime()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}

	public BlockPos getSource()
	{
		int x = dataWatcher.getWatchableObjectInt(21);
		int y = dataWatcher.getWatchableObjectInt(22);
		int z = dataWatcher.getWatchableObjectInt(23);
		return new BlockPos(x, y, z);
	}

	public int getAttackSelector()
	{
		return dataWatcher.getWatchableObjectInt(24);
	}

	public int getLevel()
	{
		return dataWatcher.getWatchableObjectInt(25);
	}

	public void setInvulTime(int time)
	{
		dataWatcher.updateObject(20, time);
	}

	public void setSource(BlockPos pos)
	{
		dataWatcher.updateObject(21, pos.getX());
		dataWatcher.updateObject(22, pos.getY());
		dataWatcher.updateObject(23, pos.getZ());
	}

	public void setAttackSelector(int attackSelector)
	{
		dataWatcher.updateObject(24, attackSelector);
	}

	public void setLevel(int level)
	{
		dataWatcher.updateObject(25, level);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger(TAG_SPAWN_INVUL_TIME, getInvulTime());

		BlockPos source = getSource();
		compound.setInteger(TAG_SOURCE_X, source.getX());
		compound.setInteger(TAG_SOURCE_Y, source.getY());
		compound.setInteger(TAG_SOURCE_Z, source.getZ());

		compound.setInteger(TAG_LEVEL, getLevel());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		setInvulTime(compound.getInteger(TAG_SPAWN_INVUL_TIME));

		int x = compound.getInteger(TAG_SOURCE_X);
		int y = compound.getInteger(TAG_SOURCE_Y);
		int z = compound.getInteger(TAG_SOURCE_Z);
		setSource(new BlockPos(x, y, z));

		setLevel(compound.getInteger(TAG_LEVEL));
	}
}
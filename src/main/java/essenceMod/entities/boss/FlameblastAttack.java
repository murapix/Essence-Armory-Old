package essenceMod.entities.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.utility.UtilityHelper;

public class FlameblastAttack extends BossAttack
{
	protected int delayTimer;
	protected int diameter;
	protected ArrayList<BlockPos> sources = new ArrayList<BlockPos>();
	
	/**
	 * Generates the flameblast attack pattern with the default cooldown and delay. Default cooldown is 200 ticks. Default delay is 60 ticks. Suggested diameter of 9
	 * 
	 * @param boss
	 *            The boss entity using this attack pattern
	 * @param damageType
	 *            The damage type dealt by this attack pattern
	 * @param damage
	 *            The amount of damage done by a hit from this pattern
	 */
	public FlameblastAttack(EntityBoss boss, DamageSource damageType, float damage, int diameter)
	{
		this(boss, damageType, damage, diameter, 200, 60);
	}

	public FlameblastAttack(EntityBoss boss, DamageSource damageType, float damage, int diameter, int attackTimer, int duration)
	{
		super(boss, damageType, damage, attackTimer, duration);
		this.diameter = diameter;
	}

	@Override
	public void update(List<EntityPlayer> players)
	{
		if (cooldown <= 0)
		{
			if (delayTimer <= 0)
			{
				delayTimer = 1;
				for (EntityPlayer player : players)
				{
					sources.add(new BlockPos(player.getPosition()));
				}
			}
			else
			{
				delayTimer++;
				if (delayTimer > duration)
				{
					delayTimer = 0;
					cooldown = attackTimer;
					for (EntityPlayer player : players)
					{
						for (BlockPos pos : sources)
						{
							double playerX = player.posX;
							double playerZ = player.posZ;
							double sourceX = pos.getX() + 0.5;
							double sourceZ = pos.getZ() + 0.5;
							double dist = Math.pow(playerX - sourceX, 2) + Math.pow(playerZ - sourceZ, 2);
							if (dist < Math.pow(diameter / 2.0, 2)) player.attackEntityFrom(damageType, damage);
						}
					}
				}
			}
		}
		else cooldown--;
		
		drawParticles();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawParticles()
	{
		World world = boss.worldObj;
		Random rand = new Random();
		if (delayTimer > 0)
		{
			for (BlockPos source : sources)
			{
				double x = source.getX() + 0.5;
				double y = source.getY();
				double z = source.getZ() + 0.5;
				double maxRadius = diameter / 2D;
				double radiusPerTick = maxRadius / (duration - 20);
				double currentRadius = Math.min(radiusPerTick * delayTimer, maxRadius);
				double anglePerFlame = 1 / currentRadius;
				double offset = rand.nextDouble() * 2 * Math.PI;
				for (double angle = offset; angle < 2 * Math.PI + offset; angle += anglePerFlame)
				{
					double xPos = x + Math.cos(angle) * currentRadius;
					double zPos = z + Math.sin(angle) * currentRadius;
					world.spawnParticle(EnumParticleTypes.FLAME, xPos, y, zPos, 0, 0, 0);
				}
			}
		}
		if (delayTimer == duration)
		{
			for (BlockPos source : sources)
			{
				double x = source.getX() + 0.5;
				double y = source.getY();
				double z = source.getZ() + 0.5;
				double anglePerFlame = 2D / diameter;
				for (double angle = 0; angle < 2 * Math.PI; angle += anglePerFlame)
				{
					for (double yPos = y; yPos < y + boss.height; yPos += Math.abs(rand.nextGaussian()) / 4)
					{
						double offset = rand.nextDouble() * 2 * Math.PI;
						double xPos = x + Math.cos(angle + offset) * diameter / 2;
						double zPos = z + Math.sin(angle + offset) * diameter / 2;
						world.spawnParticle(EnumParticleTypes.FLAME, true, xPos, yPos, zPos, 0, 0, 0);
					}
				}
			}
		}
	}

	public int getCrystalColor()
	{
		return UtilityHelper.rgbToHex(255, 0, 0);
	}
}

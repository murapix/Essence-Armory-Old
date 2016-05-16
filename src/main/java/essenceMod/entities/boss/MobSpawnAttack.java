package essenceMod.entities.boss;

import java.util.List;
import java.util.Random;
import essenceMod.utility.UtilityHelper;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MobSpawnAttack extends BossAttack
{
	protected int number;
	protected int strength;
	
	public MobSpawnAttack(EntityBoss boss, int number, int strength)
	{
		this(boss, number, strength, 100);
	}
	
	public MobSpawnAttack(EntityBoss boss, int number, int strength, int attackTimer)
	{
		super(boss, null, 0, attackTimer, 0);
		this.number = number;
		this.strength = strength;
	}

	@Override
	public void update(List<EntityPlayer> players)
	{
		if (cooldown <= 0)
		{
			cooldown = attackTimer;
			EntityMob mob;
			World world = boss.worldObj;
			for (int i = 0; i < players.size(); i++)
			{
				EntityPlayer player = players.get(i);
				for (int j = 0; j < number; j++)
				{
					Random rand = new Random();
					float offsetX = (rand.nextFloat() * 2 + 3);
					float offsetZ = (rand.nextFloat() * 2 + 3);
					offsetX *= rand.nextInt(2) == 1 ? 1 : -1;
					offsetZ *= rand.nextInt(2) == 1 ? 1 : -1;
					switch(rand.nextInt(17))
					{
						case 0: // Zombie
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
							mob = new EntityZombie(world);
							mob.setPosition(player.posX + offsetX, player.posY + 2, player.posZ + offsetZ);
							world.spawnEntityInWorld(mob);
							mob.addPotionEffect(new PotionEffect(Potion.healthBoost.id, Integer.MAX_VALUE, strength * 5));
							mob.addPotionEffect(new PotionEffect(Potion.damageBoost.id, Integer.MAX_VALUE, strength));
							break;
						case 8: // Skeleton, 90% normal, 10% wither
						case 9:
						case 10:
						case 11:
							mob = new EntitySkeleton(world);
							EntitySkeleton skele = (EntitySkeleton) mob;
							skele.setSkeletonType(rand.nextInt(10) == 0 ? 1 : 0);
							if (skele.getSkeletonType() == 0) skele.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
							if (skele.getSkeletonType() == 1) skele.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
							skele.setPosition(player.posX + offsetX, player.posY + 2, player.posZ + offsetZ);
							world.spawnEntityInWorld(skele);
							skele.addPotionEffect(new PotionEffect(Potion.healthBoost.id, Integer.MAX_VALUE, strength * 5));
							skele.addPotionEffect(new PotionEffect(Potion.damageBoost.id, Integer.MAX_VALUE, strength));
							break;
						case 12: // Spider, 80% normal, 20% cave
						case 13:
						case 14:
						case 15:
							mob = rand.nextInt(5) == 0 ? new EntityCaveSpider(world) : new EntitySpider(world);
							mob.setPosition(player.posX + offsetX, player.posY + 2, player.posZ + offsetZ);
							world.spawnEntityInWorld(mob);
							mob.addPotionEffect(new PotionEffect(Potion.healthBoost.id, Integer.MAX_VALUE, strength * 5));
							mob.addPotionEffect(new PotionEffect(Potion.damageBoost.id, Integer.MAX_VALUE, strength));
							break;
						case 16: // Witch
							mob = new EntityWitch(world);
							mob.setPosition(player.posX + offsetX, player.posY + 2, player.posZ + offsetZ);
							world.spawnEntityInWorld(mob);
							mob.addPotionEffect(new PotionEffect(Potion.healthBoost.id, Integer.MAX_VALUE, strength * 5));
							mob.addPotionEffect(new PotionEffect(Potion.damageBoost.id, Integer.MAX_VALUE, strength));
							break;
					}
				}
			}
		}
		else cooldown--;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawParticles()
	{}

	@Override
	public int getCrystalColor()
	{
		return UtilityHelper.rgbToHex(55, 94, 50);
	}
}

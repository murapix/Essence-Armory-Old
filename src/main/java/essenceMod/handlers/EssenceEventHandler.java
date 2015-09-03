package essenceMod.handlers;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import essenceMod.init.ModItems;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemLootAmulet;

public class EssenceEventHandler
{
	Random rand = new Random();

	public static void postinit()
	{
		MinecraftForge.EVENT_BUS.register(new EssenceEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new EssenceEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new EssenceEventHandler());
		FMLCommonHandler.instance().bus().register(new EssenceEventHandler());
		Random rand = new Random();
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		if (event.entityLiving instanceof EntityMob)
		{
			if (rand.nextInt(25) < 5 + event.lootingLevel)
			{
				int amount;
				if (event.lootingLevel == 0) amount = 1; 
				else amount = 1 + rand.nextInt(Math.max(0, event.lootingLevel));
				if (event.source.getEntity() instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) event.source.getEntity();
					if (PlayerHandler.getPlayerBaubles(player).getStackInSlot(0).getItem() instanceof ItemLootAmulet)
					{
						ItemStack item = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
						if (ItemLootAmulet.getLevel(item) != 0)
						{
							amount += rand.nextInt(ItemLootAmulet.getLevel(item));
							amount *= ItemLootAmulet.getLevel(item);
						}
					}
				}
				event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, amount)));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerHit(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			DamageSource source = event.source;
			int protValue = 0;
			int resValue = 0;
			for (int i = 0; i < 4; i++)
			{
				ItemStack armor = player.getEquipmentInSlot(i+1);
				if (armor != null && armor.getItem() instanceof ItemModArmor)
				{
					protValue += armor.stackTagCompound.getInteger("Protection") * 2;
					if (source.isFireDamage()) protValue += armor.stackTagCompound.getInteger("Fire Protection") * 3;
					if (source.isExplosion()) protValue += armor.stackTagCompound.getInteger("Blast Protection") * 3;
					if (source.isMagicDamage()) protValue += armor.stackTagCompound.getInteger("Magic Protection") * 3;
					if (source.isProjectile()) protValue += armor.stackTagCompound.getInteger("Projectile Protection") * 3;
					if (source == source.wither) protValue += armor.stackTagCompound.getInteger("Wither Protection") * 3;
					resValue += armor.stackTagCompound.getInteger("Resistance");
				}
			}
			double protReduction = protValue * 0.01;
			double resReduction = resValue * 0.04;
			event.ammount *= (1 - protReduction) * (1 - resReduction);
		}
	}
}
package essenceMod.handlers;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import baubles.common.lib.PlayerHandler;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemAmulet;
import essenceMod.items.baubles.ItemBelt;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.UpgradeRegistry;
import essenceMod.utility.UtilityHelper;

public class EssenceEventHandler
{
	Random rand = new Random();

	public static void preinit()
	{
		MinecraftForge.EVENT_BUS.register(new EssenceEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new EssenceEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new EssenceEventHandler());
		FMLCommonHandler.instance().bus().register(new EssenceEventHandler());

//		if (ConfigHandler.ticoIntegration) MinecraftForge.EVENT_BUS.register(new TConstructHandler());
		
		Random rand = new Random();
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		if (event.entityLiving instanceof EntityMob)
		{
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer)
			{
				if (ConfigHandler.useWhiteList)
				{
					ArrayList<String> whiteListedMobs = new ArrayList<String>();
					for (String string : ConfigHandler.whiteList) whiteListedMobs.add(string);
					if (!(whiteListedMobs.contains(event.entityLiving.toString()))) return;
				}
				if (ConfigHandler.useBlackList)
				{
					ArrayList<String> blackListedMobs = new ArrayList<String>();
					for (String string : ConfigHandler.blackList) blackListedMobs.add(string);
					if (blackListedMobs.contains(event.entityLiving.toString())) return;
				}
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack weapon = player.getCurrentEquippedItem();
				if (weapon != null && EnchantmentHelper.getEnchantmentLevel(ModArmory.shardLooter.effectId, weapon) == 1)
				{
					int amuletLevel = 0;
					ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
					if (amulet != null && amulet.getItem() instanceof ItemAmulet)
					{
						amuletLevel = UtilityHelper.getUpgradeLevel(amulet, "AmuletLooting");
					}
					if (rand.nextInt(30) < (5 * (1 + amuletLevel)))
					{
						int amount;
						if (event.lootingLevel == 0) amount = 1;
						else amount = 1 + rand.nextInt(Math.max(0, event.lootingLevel));
						amount *= (1 + amuletLevel);
						event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, amount)));
					}
				}
			}
		}
		
		if (event.entityLiving instanceof EntityDragon && rand.nextInt(100) < ConfigHandler.dragonShardChance * 100)
		{
			int shardCount = ConfigHandler.dragonShardCount;
			
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack weapon = player.getCurrentEquippedItem();
				if (weapon != null && EnchantmentHelper.getEnchantmentLevel(ModArmory.shardLooter.effectId, weapon) == 1)
				{
					int amuletLevel = 0;
					ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
					if (amulet != null && amulet.getItem() instanceof ItemAmulet)
					{
						amuletLevel = UtilityHelper.getUpgradeLevel(amulet, "AmuletLooting");
					}
					shardCount *= (1 + amuletLevel);
				}
			}
			
			while (shardCount > 64)
			{
				event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, 64)));
				shardCount -= 64;
			}
			if (shardCount != 0) event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, shardCount)));
		}
		
		if (event.entityLiving instanceof EntityWither && rand.nextInt(100) < ConfigHandler.witherShardChance * 100)
		{
			int shardCount = ConfigHandler.witherShardCount;
			
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack weapon = player.getCurrentEquippedItem();
				if (weapon != null && EnchantmentHelper.getEnchantmentLevel(ModArmory.shardLooter.effectId, weapon) == 1)
				{
					int amuletLevel = 0;
					ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
					if (amulet != null && amulet.getItem() instanceof ItemAmulet)
					{
						amuletLevel = UtilityHelper.getUpgradeLevel(amulet, "AmuletLooting");
					}
					shardCount *= (1 + amuletLevel);
				}
			}
			
			while (shardCount > 64)
			{
				event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, 64)));
				shardCount -= 64;
			}
			if (shardCount != 0) event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.infusedShard, shardCount)));
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
			int poisonThorns = 0;
			int poisonCount = 0;
			int blindThorns = 0;
			int blindCount = 0;
			for (int i = 0; i < 4; i++)
			{
				ItemStack armor = player.getEquipmentInSlot(i + 1);
				if (armor != null && armor.getItem() instanceof ItemModArmor)
				{
					NBTTagCompound compound = armor.getTagCompound();
					compound.setInteger("Absorption Delay", ConfigHandler.absorptionDelay);
					armor.setTagCompound(compound);
					protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorPhysicalProtection") * 2;
					if (source.isFireDamage()) protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorFireProtection") * 3;
					if (source.isExplosion()) protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorBlastProtection") * 3;
					if (source.isMagicDamage()) protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorMagicProtection") * 3;
					if (source.isProjectile()) protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorProjectileProtection") * 3;
					if (source == source.wither) protValue += UtilityHelper.getUpgradeLevel(armor, "ArmorWitherProtection") * 3;
//					if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration) protValue += DraconicEvolutionHandler.getChaosDamageProtection(armor, source);
					resValue += UtilityHelper.getUpgradeLevel(armor, "ArmorResistance");
					int poisonTemp = UtilityHelper.getUpgradeLevel(armor, "ArmorMagicThorns");
					if (poisonTemp != 0)
					{
						poisonThorns += poisonTemp;
						poisonCount++;
					}
					int blindTemp = UtilityHelper.getUpgradeLevel(armor, "ArmorBlindThorns");
					if (blindTemp != 0)
					{
						blindThorns += blindTemp;
						blindCount++;
					}
				}
			}
			double protReduction = protValue * ConfigHandler.maxProtectionValue / 80;
			double resReduction = resValue * 0.04;
			event.ammount *= (1 - protReduction) * (1 - resReduction);
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase)
			{
				EntityLivingBase enemy = (EntityLivingBase) event.source.getEntity();
				if (rand.nextInt(100) < (ConfigHandler.blindThornsChance * blindCount))
				{
					enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, blindThorns * ConfigHandler.blindThornsDuration, 0));
				}
				if (poisonCount != 0)
				{
					enemy.addPotionEffect(new PotionEffect(Potion.poison.id, poisonThorns * ConfigHandler.poisonThornsDuration, poisonCount - 1));
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void infusedTooltip(ItemTooltipEvent event)
	{
		if (event.itemStack == null || event.entityPlayer == null || event.entityPlayer.worldObj == null) return;
		if (event.itemStack.getItem() instanceof ItemModSword)
		{
			ListIterator<String> iterator = event.toolTip.listIterator();
			while (iterator.hasNext())
			{
				String next = iterator.next();
				if (next.contains("Attack Damage"))
				{
					iterator.previous();
					float weaponDamage = event.itemStack.getTagCompound().getFloat("weaponDamage");
					float fireDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponFireDamage);
					float witherDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponWitherDamage);
					float magicDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponMagicDamage);
					float chaosDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponChaosDamage);
					float divineDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponDivineDamage);
					float taintDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponTaintDamage);
					float frostDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponFrostDamage);
					float holyDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponHolyDamage);
					float lightningDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponLightningDamage);
					float windDamage = UtilityHelper.getUpgradeLevel(event.itemStack, UpgradeRegistry.WeaponWindDamage);
					
					fireDamage *= ConfigHandler.isFireDamagePercent ? weaponDamage * ConfigHandler.fireDamageMulti : ConfigHandler.fireDamageAmount;
					witherDamage *= ConfigHandler.isWitherDamagePercent ? weaponDamage * ConfigHandler.witherDamageMulti : ConfigHandler.witherDamageAmount;
					magicDamage *= ConfigHandler.isMagicDamagePercent ? weaponDamage * ConfigHandler.magicDamageMulti : ConfigHandler.magicDamageAmount;
					chaosDamage *= ConfigHandler.isChaosDamagePercent ? weaponDamage * ConfigHandler.chaosDamageMulti : ConfigHandler.chaosDamageAmount;
					divineDamage *= ConfigHandler.isDivineDamagePercent ? weaponDamage * ConfigHandler.divineDamageMulti : ConfigHandler.divineDamageAmount;
					taintDamage *= ConfigHandler.isTaintDamagePercent ? weaponDamage * ConfigHandler.taintDamageMulti : ConfigHandler.taintDamageAmount;
					frostDamage *= ConfigHandler.isFrostDamagePercent ? weaponDamage * ConfigHandler.frostDamageMulti : ConfigHandler.frostDamageAmount;
					holyDamage *= ConfigHandler.isHolyDamagePercent ? weaponDamage * ConfigHandler.holyDamageMulti : ConfigHandler.holyDamageAmount;
					lightningDamage *= ConfigHandler.isLightningDamagePercent ? weaponDamage * ConfigHandler.lightningDamageMulti : ConfigHandler.lightningDamageAmount;
					windDamage *= ConfigHandler.isWindDamagePercent ? weaponDamage * ConfigHandler.windDamageMulti : ConfigHandler.windDamageAmount;
					
					double fireText = Math.round(fireDamage * 4) / 4D;
					double witherText = Math.round(witherDamage * 4) / 4D;
					double magicText = Math.round(magicDamage * 4) / 4D;
					double chaosText = Math.round(chaosDamage * 4) / 4D;
					double divineText = Math.round(divineDamage * 4) / 4D;
					double taintText = Math.round(taintDamage * 4) / 4D;
					double frostText = Math.round(frostDamage * 4) / 4D;
					double holyText = Math.round(holyDamage * 4) / 4D;
					double lightningText = Math.round(lightningDamage * 4) / 4D;
					double windText = Math.round(windDamage * 4) / 4D;

					if (fireText != 0)
					{
						if (fireText == (int) fireText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) fireText) + " Fire Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + fireText + " Fire Damage");
					}
					if (witherText != 0)
					{
						if (witherText == (int) witherText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) witherText) + " Wither Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + witherText + " Wither Damage");
					}
					if (magicText != 0)
					{
						if (magicText == (int) magicText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) magicText) + " Magic Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + magicText + " Magic Damage");
					}
					if (chaosText != 0)
					{
						if (chaosText == (int) chaosText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) chaosText) + " Chaos Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + chaosText + " Chaos Damage");
					}
					if (divineText != 0)
					{
						if (divineText == (int) divineText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) divineText) + " Divine Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + divineText + " Divine Damage");
					}
					if (taintText != 0)
					{
						if (taintText == (int) taintText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) taintText) + " Flux Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + taintText + " Flux Damage");
					}
					if (frostText != 0)
					{
						if (frostText == (int) frostText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) frostText) + " Frost Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + frostText + " Frost Damage");
					}
					if (holyText != 0)
					{
						if (holyText == (int) holyText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) holyText) + " Holy Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + holyText + " Holy Damage");
					}
					if (lightningText != 0)
					{
						if (lightningText == (int) lightningText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) lightningText) + " Lightning Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + lightningText + " Lightning Damage");
					}
					if (windText != 0)
					{
						if (windText == (int) windText) iterator.add(EnumChatFormatting.BLUE + "+" + ((int) windText) + " Wind Damage"); 
						else iterator.add(EnumChatFormatting.BLUE + "+" + windText + " Wind Damage");
					}
					break;
				}
			}
		}
	}
	
//	@SubscribeEvent
//	public void spawnOPZombie()
//	{
//		
//	}
}
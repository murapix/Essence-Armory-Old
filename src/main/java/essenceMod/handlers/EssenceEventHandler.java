package essenceMod.handlers;

import java.util.ListIterator;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.handlers.compatibility.TConstructHandler;
import essenceMod.init.ModArmory;
import essenceMod.init.ModItems;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemKnockbackBelt;
import essenceMod.items.baubles.ItemLootAmulet;
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

		MinecraftForge.EVENT_BUS.register(new TConstructHandler());
		
		Random rand = new Random();
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		if (Loader.isModLoaded("TConstruct"))
		{
			System.out.println("Tinkers Construct Loaded");
		}
		if (event.entityLiving instanceof EntityMob)
		{
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				ItemStack weapon = player.getCurrentEquippedItem();
				if (weapon != null && EnchantmentHelper.getEnchantmentLevel(ModArmory.shardLooter.effectId, weapon) == 1)
				{
					int amuletLevel = 0;
					ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
					if (amulet != null && amulet.getItem() instanceof ItemLootAmulet)
					{
						amuletLevel = ItemLootAmulet.getLevel(amulet);
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
					protValue += UtilityHelper.getUpgradeLevel(armor, "Protection") * 2;
					if (source.isFireDamage()) protValue += UtilityHelper.getUpgradeLevel(armor, "Fire Protection") * 3;
					if (source.isExplosion()) protValue += UtilityHelper.getUpgradeLevel(armor, "Blast Protection") * 3;
					if (source.isMagicDamage()) protValue += UtilityHelper.getUpgradeLevel(armor, "Magic Protection") * 3;
					if (source.isProjectile()) protValue += UtilityHelper.getUpgradeLevel(armor, "Projectile Protection") * 3;
					if (source == source.wither) protValue += UtilityHelper.getUpgradeLevel(armor, "Wither Protection") * 3;
					resValue += UtilityHelper.getUpgradeLevel(armor, "Resistance");
					int poisonTemp = UtilityHelper.getUpgradeLevel(armor, "PoisonThorns");
					if (poisonTemp != 0)
					{
						poisonThorns += poisonTemp;
						poisonCount++;
					}
					int blindTemp = UtilityHelper.getUpgradeLevel(armor, "BlindThorns");
					if (blindTemp != 0)
					{
						blindThorns += blindTemp;
						blindCount++;
					}
				}
			}
			double protReduction = protValue * 0.01;
			double resReduction = resValue * 0.04;
			event.ammount *= (1 - protReduction) * (1 - resReduction);
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase)
			{
				EntityLivingBase enemy = (EntityLivingBase) event.source.getEntity();
				if (rand.nextInt(100) < (25 * blindCount))
				{
					enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, blindThorns * 10, 0));
				}
				if (poisonCount != 0)
				{
					enemy.addPotionEffect(new PotionEffect(Potion.poison.id, poisonThorns * 10, poisonCount - 1));
				}
			}
		}
	}

	@SubscribeEvent
	public void onFlyableFall(PlayerFlyableFallEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack belt = PlayerHandler.getPlayerBaubles(player).getStackInSlot(3);
		if (belt != null && belt.getItem() instanceof ItemKnockbackBelt) ItemKnockbackBelt.knockback(belt, player, event.distance);
	}

	@SubscribeEvent
	public void onPlayerFall(LivingFallEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack belt = PlayerHandler.getPlayerBaubles(player).getStackInSlot(3);
			if (belt != null && belt.getItem() instanceof ItemKnockbackBelt) ItemKnockbackBelt.knockback(belt, player, event.distance);
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
					float weaponDamage = event.itemStack.stackTagCompound.getFloat("weaponDamage");
					float fireDamage = UtilityHelper.getUpgradeLevel(event.itemStack, "Fire") * weaponDamage * 0.05F;
					float witherDamage = UtilityHelper.getUpgradeLevel(event.itemStack, "Wither") * weaponDamage * 0.05F;
					float magicDamage = UtilityHelper.getUpgradeLevel(event.itemStack, "Magic") * weaponDamage * 0.05F;
					float chaosDamage = UtilityHelper.getUpgradeLevel(event.itemStack, "Chaos") * weaponDamage * 0.05F;
					float divineDamage = UtilityHelper.getUpgradeLevel(event.itemStack, "Divine") * weaponDamage * 0.05F;
					
					double fireText = Math.round(fireDamage * 4) / 4D;
					double witherText = Math.round(witherDamage * 4) / 4D;
					double magicText = Math.round(magicDamage * 4) / 4D;
					double chaosText = Math.round(chaosDamage * 4) / 4D;
					double divineText = Math.round(divineDamage * 4) / 4D;

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
					break;
				}
			}
		}
	}
}
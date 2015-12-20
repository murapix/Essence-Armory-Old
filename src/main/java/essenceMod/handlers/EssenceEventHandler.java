package essenceMod.handlers;

import java.util.ListIterator;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
import essenceMod.init.ModArmory;
import essenceMod.init.ModItems;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemKnockbackBelt;
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
					float fireDamage = ItemModSword.getUpgradeLevel(event.itemStack, "Fire") * weaponDamage * 0.05F;
					float witherDamage = ItemModSword.getUpgradeLevel(event.itemStack, "Wither") * weaponDamage * 0.05F;
					float magicDamage = ItemModSword.getUpgradeLevel(event.itemStack, "Magic") * weaponDamage * 0.05F;
					float chaosDamage = ItemModSword.getUpgradeLevel(event.itemStack, "Chaos") * weaponDamage * 0.05F;
					float divineDamage = ItemModSword.getUpgradeLevel(event.itemStack, "Divine") * weaponDamage * 0.05F;
					
					if (fireDamage != 0) iterator.add(EnumChatFormatting.BLUE + "+" + fireDamage + " Fire Damage");
					if (witherDamage != 0) iterator.add(EnumChatFormatting.BLUE + "+" + witherDamage + " Wither Damage");
					if (magicDamage != 0) iterator.add(EnumChatFormatting.BLUE + "+" + magicDamage + " Magic Damage");
					if (chaosDamage != 0) iterator.add(EnumChatFormatting.BLUE + "+" + chaosDamage + " Chaos Damage");
					if (divineDamage != 0) iterator.add(EnumChatFormatting.BLUE + "+" + divineDamage + " Divine Damage");
					break;
				}
			}
		}
	}
}
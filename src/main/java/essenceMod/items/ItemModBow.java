package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.entities.EntityModArrow;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModArmory;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.UtilityHelper;

public class ItemModBow extends ItemBow implements IUpgradeable
{
	int level;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (!item.hasTagCompound()) onCreated(item, world, (EntityPlayer) entity);
	}

	public ItemModBow()
	{
		super();
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		level = 0;
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		NBTTagCompound compound = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
		compound.setInteger("Level", level);
		compound.setBoolean("ItemInUse", false);
		item.setTagCompound(compound);
		item.addEnchantment(ModArmory.shardLooter, 1);
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		for (int i = 0; i < 4; i++)
		{
			ModelBakery.registerItemVariants(this, new ModelResourceLocation(getRegistryName() + "_" + i, "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(getRegistryName() + "_" + i, "inventory"));
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item)
	{
		return EnumAction.BOW;
	}

	public static int getLevel(ItemStack item)
	{
		return item.getTagCompound().getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (!item.hasTagCompound()) onCreated(item, entityPlayer.worldObj, entityPlayer);
		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));

		int phys = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponPhysicalDamage);
		int fire = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFireDamage);
		int magic = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponMagicDamage);
		int wither = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWitherDamage);
		int divine = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponDivineDamage);
		int chaos = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponChaosDamage);
		int taint = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDamage);
		int frost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostDamage);
		int holy = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponHolyDamage);
		int lightning = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponLightningDamage);
		int wind = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWindDamage);

		list.add("Arrows deal up to:");

		float weaponDamage = 2.0F + (getLevel(item) / 5);
		weaponDamage *= 4.125F * (1 + Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed) * 0.05F);
		phys *= ConfigHandler.isNormalDamagePercent ? weaponDamage * ConfigHandler.normalDamageMulti : ConfigHandler.normalDamageAmount * ConfigHandler.normalBowMulti;
		fire *= ConfigHandler.isFireDamagePercent ? weaponDamage * ConfigHandler.fireDamageMulti : ConfigHandler.fireDamageAmount;
		wither *= ConfigHandler.isWitherDamagePercent ? weaponDamage * ConfigHandler.witherDamageMulti : ConfigHandler.witherDamageAmount;
		magic *= ConfigHandler.isMagicDamagePercent ? weaponDamage * ConfigHandler.magicDamageMulti : ConfigHandler.magicDamageAmount;
		chaos *= ConfigHandler.isChaosDamagePercent ? weaponDamage * ConfigHandler.chaosDamageMulti : ConfigHandler.chaosDamageAmount;
		divine *= ConfigHandler.isDivineDamagePercent ? weaponDamage * ConfigHandler.divineDamageMulti : ConfigHandler.divineDamageAmount;
		taint *= ConfigHandler.isTaintDamagePercent ? weaponDamage * ConfigHandler.taintDamageMulti : ConfigHandler.taintDamageAmount;
		frost *= ConfigHandler.isFrostDamagePercent ? weaponDamage * ConfigHandler.frostDamageMulti : ConfigHandler.frostDamageAmount;
		holy *= ConfigHandler.isHolyDamagePercent ? weaponDamage * ConfigHandler.holyDamageMulti : ConfigHandler.holyDamageAmount;
		lightning *= ConfigHandler.isLightningDamagePercent ? weaponDamage * ConfigHandler.lightningDamageMulti : ConfigHandler.lightningDamageAmount;
		wind *= ConfigHandler.isWindDamagePercent ? weaponDamage * ConfigHandler.windDamageMulti : ConfigHandler.windDamageAmount;

		phys *= ConfigHandler.normalBowMulti;
		fire *= ConfigHandler.fireBowMulti;
		magic *= ConfigHandler.magicBowMulti;
		wither *= ConfigHandler.witherBowMulti;
		divine *= ConfigHandler.divineBowMulti;
		chaos *= ConfigHandler.chaosBowMulti;
		taint *= ConfigHandler.taintBowMulti;
		frost *= ConfigHandler.frostBowMulti;
		holy *= ConfigHandler.holyBowMulti;
		lightning *= ConfigHandler.lightningBowMulti;
		wind *= ConfigHandler.windBowMulti;

		phys += weaponDamage;

		double physText = Math.round(phys * 4) / 4D;
		double fireText = Math.round(fire * 4) / 4D;
		double witherText = Math.round(wither * 4) / 4D;
		double magicText = Math.round(magic * 4) / 4D;
		double chaosText = Math.round(chaos * 4) / 4D;
		double divineText = Math.round(divine * 4) / 4D;
		double taintText = Math.round(taint * 4) / 4D;
		double frostText = Math.round(frost * 4) / 4D;
		double holyText = Math.round(holy * 4) / 4D;
		double lightningText = Math.round(lightning * 4) / 4D;
		double windText = Math.round(wind * 4) / 4D;

		if (physText != 0)
		{
			if (physText == (int) physText) list.add(EnumChatFormatting.BLUE + "+" + ((int) physText) + " Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + physText + " Damage");
		}
		if (fireText != 0)
		{
			if (fireText == (int) fireText) list.add(EnumChatFormatting.BLUE + "+" + ((int) fireText) + " Fire Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + fireText + " Fire Damage");
		}
		if (witherText != 0)
		{
			if (witherText == (int) witherText) list.add(EnumChatFormatting.BLUE + "+" + ((int) witherText) + " Wither Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + witherText + " Wither Damage");
		}
		if (magicText != 0)
		{
			if (magicText == (int) magicText) list.add(EnumChatFormatting.BLUE + "+" + ((int) magicText) + " Magic Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + magicText + " Magic Damage");
		}
		if (chaosText != 0)
		{
			if (chaosText == (int) chaosText) list.add(EnumChatFormatting.BLUE + "+" + ((int) chaosText) + " Chaos Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + chaosText + " Chaos Damage");
		}
		if (divineText != 0)
		{
			if (divineText == (int) divineText) list.add(EnumChatFormatting.BLUE + "+" + ((int) divineText) + " Divine Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + divineText + " Divine Damage");
		}
		if (taintText != 0)
		{
			if (taintText == (int) taintText) list.add(EnumChatFormatting.BLUE + "+" + ((int) taintText) + " Flux Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + taintText + " Flux Damage");
		}
		if (frostText != 0)
		{
			if (frostText == (int) frostText) list.add(EnumChatFormatting.BLUE + "+" + ((int) frostText) + " Frost Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + frostText + " Frost Damage");
		}
		if (holyText != 0)
		{
			if (holyText == (int) holyText) list.add(EnumChatFormatting.BLUE + "+" + ((int) holyText) + " Holy Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + holyText + " Holy Damage");
		}
		if (lightningText != 0)
		{
			if (lightningText == (int) lightningText) list.add(EnumChatFormatting.BLUE + "+" + ((int) lightningText) + " Lightning Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + lightningText + " Lightning Damage");
		}
		if (windText != 0)
		{
			if (windText == (int) windText) list.add(EnumChatFormatting.BLUE + "+" + ((int) windText) + " Wind Damage");
			else list.add(EnumChatFormatting.BLUE + "+" + windText + " Wind Damage");
		}
	}

	private List addNormalInfo(ItemStack item)
	{
		List list = new ArrayList();

		int fireDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFireDoT);
		int magicDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponMagicDoT);
		int witherDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWitherDoT);
		int taintDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDoT);
		int armorPierce = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponArmorPiercing);
		int arrowSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed);
		int drawSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed);
		int knockback = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponKnockback);
		int blind = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponBlind);
		int slow = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponSlow);
		int entangled = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponEntangled);
		int frostSlow = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostSlow);
		int physDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponPhysicalDamage);
		int fireDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFireDamage);
		int magicDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponMagicDamage);
		int witherDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWitherDamage);
		int divineDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponDivineDamage);
		int chaosDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponChaosDamage);
		int taintDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDamage);
		int frostDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostDamage);
		int holyDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponHolyDamage);
		int lightningDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponLightningDamage);
		int windDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWindDamage);

		int level = getLevel(item);
		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));

		if (fireDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFireDoT.name) + " " + UtilityHelper.toRoman(fireDot));
		if (magicDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponMagicDoT.name) + " " + UtilityHelper.toRoman(magicDot));
		if (witherDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWitherDoT.name) + " " + UtilityHelper.toRoman(witherDot));
		if (taintDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponTaintDoT.name) + " " + UtilityHelper.toRoman(taintDot));
		if (armorPierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponArmorPiercing.name) + " " + UtilityHelper.toRoman(armorPierce));
		if (arrowSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArrowSpeed.name) + " " + UtilityHelper.toRoman(arrowSpeed));
		if (drawSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDrawSpeed.name) + " " + UtilityHelper.toRoman(drawSpeed));
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponKnockback.name) + " " + UtilityHelper.toRoman(knockback));
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponBlind.name) + " " + UtilityHelper.toRoman(blind));
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponSlow.name) + " " + UtilityHelper.toRoman(slow));
		if (entangled != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponEntangled.name) + " " + UtilityHelper.toRoman(entangled));
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFrostSlow.name) + " " + UtilityHelper.toRoman(frostSlow));
		if (physDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponPhysicalDamage.name) + " " + UtilityHelper.toRoman(physDamage));
		if (fireDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFireDamage.name) + " " + UtilityHelper.toRoman(fireDamage));
		if (magicDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponMagicDamage.name) + " " + UtilityHelper.toRoman(magicDamage));
		if (witherDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWitherDamage.name) + " " + UtilityHelper.toRoman(witherDamage));
		if (divineDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponDivineDamage.name) + " " + UtilityHelper.toRoman(divineDamage));
		if (chaosDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponChaosDamage.name) + " " + UtilityHelper.toRoman(chaosDamage));
		if (taintDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponTaintDamage.name) + " " + UtilityHelper.toRoman(taintDamage));
		if (frostDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFrostDamage.name) + " " + UtilityHelper.toRoman(frostDamage));
		if (holyDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponHolyDamage.name) + " " + UtilityHelper.toRoman(holyDamage));
		if (lightningDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponLightningDamage.name) + " " + UtilityHelper.toRoman(lightningDamage));
		if (windDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWindDamage.name) + " " + UtilityHelper.toRoman(windDamage));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();

		int fireDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFireDoT);
		int magicDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponMagicDoT);
		int witherDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWitherDoT);
		int taintDot = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDoT);
		int armorPierce = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponArmorPiercing);
		int arrowSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed);
		int drawSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed);
		int knockback = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponKnockback);
		int blind = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponBlind);
		int slow = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponSlow);
		int entangled = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponEntangled);
		int frostSlow = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostSlow);
		int physDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponPhysicalDamage);
		int fireDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFireDamage);
		int magicDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponMagicDamage);
		int witherDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWitherDamage);
		int divineDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponDivineDamage);
		int chaosDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponChaosDamage);
		int taintDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDamage);
		int frostDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostDamage);
		int holyDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponHolyDamage);
		int lightningDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponLightningDamage);
		int windDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponWindDamage);

		int level = getLevel(item);
		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));

		if (fireDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFireDoT.name) + ": Shots light enemies on fire for " + fireDot + " seconds.");
		if (magicDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponMagicDoT.name) + ": Shots give Poison " + UtilityHelper.toRoman(magicDot) + " for 5 seconds.");
		if (witherDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWitherDoT.name) + ": Shots give Wither " + UtilityHelper.toRoman(witherDot) + " for 5 seconds.");
		if (taintDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponTaintDoT.name) + ": Shots taint enemies for " + taintDot + " seconds.");
		if (armorPierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponArmorPiercing.name) + ": Shots ignore " + armorPierce * 20 + "% of armor.");
		if (arrowSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArrowSpeed.name) + ": Draw time and arrow speed increased by " + arrowSpeed * 5 + "%.");
		if (drawSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDrawSpeed.name) + ": Draw time decreased by " + drawSpeed * 5 + "%.");
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponKnockback.name) + ": Knock enemies away on hit.");
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponBlind.name) + ": Shots blind enemies for " + blind + " seconds.");
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponSlow.name) + ": Shots slow enemies for " + slow + " seconds.");
		if (entangled != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponEntangled.name) + ": Shots entangle enemies for " + entangled * 2 + " ticks.");
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFrostSlow.name) + ": Shots heavily slow enemies for " + frostSlow + " seconds.");
		if (physDamage != 0)
		{
			if (ConfigHandler.isNormalDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponPhysicalDamage.name) + ": Shots deal " + physDamage * ConfigHandler.normalDamageMulti * ConfigHandler.normalBowMulti * 100 + "% increased damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponPhysicalDamage.name) + ": Shots deal " + physDamage * ConfigHandler.normalDamageAmount * ConfigHandler.normalBowMulti + " extra damage.");
		}
		if (fireDamage != 0)
		{
			if (ConfigHandler.isFireDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFireDamage.name) + ": Shots deal " + fireDamage * ConfigHandler.fireDamageMulti * ConfigHandler.fireBowMulti * 100 + "% more damage as fire damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFireDamage.name) + ": Shots deal " + fireDamage * ConfigHandler.fireDamageAmount * ConfigHandler.fireBowMulti + " extra damage as fire damage.");
		}
		if (magicDamage != 0)
		{
			if (ConfigHandler.isMagicDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponMagicDamage.name) + ": Shots deal " + magicDamage * ConfigHandler.magicDamageMulti * ConfigHandler.magicBowMulti * 100 + "% more damage as magic damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponMagicDamage.name) + ": Shots deal " + magicDamage * ConfigHandler.magicDamageAmount * ConfigHandler.magicBowMulti + " extra damage as magic damage.");
		}
		if (witherDamage != 0)
		{
			if (ConfigHandler.isWitherDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWitherDamage.name) + ": Shots deal " + witherDamage * ConfigHandler.witherDamageMulti * ConfigHandler.witherBowMulti * 100 + "% more damage as wither damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWitherDamage.name) + ": Shots deal " + witherDamage * ConfigHandler.witherDamageAmount * ConfigHandler.witherBowMulti + " extra damage as wither damage.");
		}
		if (divineDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponDivineDamage.name) + ": Shots deal " + divineDamage * ConfigHandler.divineDamageMulti * ConfigHandler.divineBowMulti * 100 + "% more damage as divine damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponDivineDamage.name) + ": Shots deal " + divineDamage * ConfigHandler.divineDamageAmount * ConfigHandler.divineBowMulti + " extra damage as divine damage.");
		}
		if (chaosDamage != 0)
		{
			if (ConfigHandler.isChaosDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponChaosDamage.name) + ": Shots deal " + chaosDamage * ConfigHandler.chaosDamageMulti * ConfigHandler.chaosBowMulti * 100 + "% more damage as chaos damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponChaosDamage.name) + ": Shots deal " + chaosDamage * ConfigHandler.chaosDamageAmount * ConfigHandler.chaosBowMulti + " extra damage as chaos damage.");
		}
		if (taintDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponTaintDamage.name) + ": Shots deal " + taintDamage * ConfigHandler.taintDamageMulti * ConfigHandler.taintBowMulti * 100 + "% more damage as taint damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponTaintDamage.name) + ": Shots deal " + taintDamage * ConfigHandler.taintDamageAmount * ConfigHandler.taintBowMulti + " extra damage as taint damage.");
		}
		if (frostDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFrostDamage.name) + ": Shots deal " + frostDamage * ConfigHandler.frostDamageMulti * ConfigHandler.frostBowMulti * 100 + "% more damage as frost damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponFrostDamage.name) + ": Shots deal " + frostDamage * ConfigHandler.frostDamageAmount * ConfigHandler.frostBowMulti + " extra damage as frost damage.");
		}
		if (holyDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponHolyDamage.name) + ": Shots deal " + holyDamage * ConfigHandler.holyDamageMulti * ConfigHandler.holyBowMulti * 100 + "% more damage as holy damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponHolyDamage.name) + ": Shots deal " + holyDamage * ConfigHandler.holyDamageAmount * ConfigHandler.holyBowMulti + " extra damage as holy damage.");
		}
		if (lightningDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponLightningDamage.name) + ": Shots deal " + lightningDamage * ConfigHandler.lightningDamageMulti * ConfigHandler.lightningBowMulti * 100 + "% more damage as lightning damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponLightningDamage.name) + ": Shots deal " + lightningDamage * ConfigHandler.lightningDamageAmount * ConfigHandler.lightningBowMulti + " extra damage as lightning damage.");
		}
		if (windDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWindDamage.name) + ": Shots deal " + windDamage * ConfigHandler.windDamageMulti * ConfigHandler.windBowMulti * 100 + "% more damage as wind damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.WeaponWindDamage.name) + ": Shots deal " + windDamage * ConfigHandler.windDamageAmount * ConfigHandler.windBowMulti + " extra damage as wind damage.");
		}

		return list;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, item);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) return event.result;

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow))
		{
			player.setItemInUse(item, this.getMaxItemUseDuration(item));
			NBTTagCompound compound = item.getTagCompound();
			compound.setBoolean("ItemInUse", true);
			item.setTagCompound(compound);
		}

		return item;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack old, ItemStack newStack, boolean slotChanged)
	{
		return slotChanged;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack item, EntityPlayer player, int count)
	{
		int variant = 0;

		if (item.getTagCompound().getBoolean("ItemInUse"))
		{
			count = this.getMaxItemUseDuration(item) - count;

			int arrowSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed);
			int drawSpeed = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed);
			double maxCount = (Math.sqrt(1 + 3 * (1 + arrowSpeed * 0.05)) - 1) * 20 / (1 + drawSpeed * 0.05);
			int countPercent = (int) (((double) count) * 100 / maxCount);
			if (countPercent >= 90) variant = 3;
			else if (countPercent > 65) variant = 2;
			else if (countPercent > 0) variant = 1;
			else variant = 0;
		}
		else variant = 0;

		return new ModelResourceLocation(item.getItem().getRegistryName() + "_" + variant, "inventory");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int itemInUseCount)
	{
		int bowCharge = getMaxItemUseDuration(item) - itemInUseCount;

		NBTTagCompound compound = item.getTagCompound();
		compound.setBoolean("ItemInUse", false);
		item.setTagCompound(compound);
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, item, bowCharge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) { return; }
		bowCharge = event.charge;

		boolean freeArrow = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;

		if (freeArrow || player.inventory.hasItem(Items.arrow))
		{
			float adjustedCharge = (float) bowCharge / 20.0F;
			adjustedCharge = (adjustedCharge * adjustedCharge + adjustedCharge * 2.0F) / 3.0F * (1 + Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed) * 0.5F);
			if ((double) adjustedCharge < 0.1D) return;
			float chargeCutoff = (float) (1 + Upgrade.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed) * 0.05F);
			if (adjustedCharge > chargeCutoff) adjustedCharge = chargeCutoff;

			EntityModArrow entityArrow = new EntityModArrow(world, player, adjustedCharge * 2.0F, item);
			if (adjustedCharge == chargeCutoff) entityArrow.setIsCritical(true);

			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + adjustedCharge * 0.5F);

			if (freeArrow) entityArrow.canBePickedUp = 2;
			else player.inventory.consumeInventoryItem(Items.arrow);

			if (!world.isRemote) world.spawnEntityInWorld(entityArrow);
		}
	}
}
package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.crafting.upgrades.UpgradeRegistry;
import essenceMod.entities.EntityModArrow;
import essenceMod.handlers.ConfigHandler;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModBow extends ItemBow implements IUpgradeable
{
	private IIcon[] icons;
	
	int level;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (item.stackTagCompound == null) onCreated(item, world, (EntityPlayer) entity);
		int level = item.stackTagCompound.getInteger("Level");
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
		if (item.stackTagCompound == null) item.setTagCompound(new NBTTagCompound());
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setBoolean("ItemInUse", false);
		item.addEnchantment(ModArmory.shardLooter, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_standby");
		icons = new IIcon[bowPullIconNameArray.length];
		
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_" + bowPullIconNameArray[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int duration)
	{
		return icons[duration];
	}
	
	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);
		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));
		
		int phys = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowPhysicalDamage);
		int fire = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFireDamage);
		int magic = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowMagicDamage);
		int wither = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWitherDamage);
		int divine = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDivineDamage);
		int chaos = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowChaosDamage);
		int taint = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowTaintDamage);
		int frost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFrostDamage);
		int holy = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowHolyDamage);
		int lightning = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowLightningDamage);
		int wind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWindDamage);
		
		list.add("Arrows deal up to:");
		
		float weaponDamage = 2.0F + (getLevel(item) / 5);
		weaponDamage *= 4.125F * (1 + UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed) * 0.05F);
		phys *= ConfigHandler.isNormalDamagePercent ? weaponDamage * ConfigHandler.normalDamageMulti : ConfigHandler.normalDamageAmount * ConfigHandler.normalBowMulti;
		fire *= ConfigHandler.isFireDamagePercent ? weaponDamage * ConfigHandler.fireDamageMulti : ConfigHandler.fireDamageAmount;
		wither*= ConfigHandler.isWitherDamagePercent ? weaponDamage * ConfigHandler.witherDamageMulti : ConfigHandler.witherDamageAmount;
		magic*= ConfigHandler.isMagicDamagePercent ? weaponDamage * ConfigHandler.magicDamageMulti : ConfigHandler.magicDamageAmount;
		chaos*= ConfigHandler.isChaosDamagePercent ? weaponDamage * ConfigHandler.chaosDamageMulti : ConfigHandler.chaosDamageAmount;
		divine*= ConfigHandler.isDivineDamagePercent ? weaponDamage * ConfigHandler.divineDamageMulti : ConfigHandler.divineDamageAmount;
		taint*= ConfigHandler.isTaintDamagePercent ? weaponDamage * ConfigHandler.taintDamageMulti : ConfigHandler.taintDamageAmount;
		frost*= ConfigHandler.isFrostDamagePercent ? weaponDamage * ConfigHandler.frostDamageMulti : ConfigHandler.frostDamageAmount;
		holy*= ConfigHandler.isHolyDamagePercent ? weaponDamage * ConfigHandler.holyDamageMulti : ConfigHandler.holyDamageAmount;
		lightning*= ConfigHandler.isLightningDamagePercent ? weaponDamage * ConfigHandler.lightningDamageMulti : ConfigHandler.lightningDamageAmount;
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
		
		int fireDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFireDoT);
		int magicDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowMagicDoT);
		int witherDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWitherDoT);
		int taintDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowTaintDoT);
		int armorPierce = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArmorPiercing);
		int arrowSpeed = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed);
		int drawSpeed = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed);
		int knockback = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowKnockback);
		int blind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowBlind);
		int slow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowSlow);
		int entangled = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowEntangled);
		int frostSlow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFrostSlow);
		int physDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowPhysicalDamage);
		int fireDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFireDamage);
		int magicDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowMagicDamage);
		int witherDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWitherDamage);
		int divineDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDivineDamage);
		int chaosDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowChaosDamage);
		int taintDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowTaintDamage);
		int frostDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFrostDamage);
		int holyDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowHolyDamage);
		int lightningDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowLightningDamage);
		int windDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWindDamage);
		
		int level = getLevel(item);
		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));
		
		if (fireDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFireDoT.name) + " " + UtilityHelper.toRoman(fireDot));
		if (magicDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowMagicDoT.name) + " " + UtilityHelper.toRoman(magicDot));
		if (witherDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWitherDoT.name) + " " + UtilityHelper.toRoman(witherDot));
		if (taintDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowTaintDoT.name) + " " + UtilityHelper.toRoman(taintDot));
		if (armorPierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArmorPiercing.name) + " " + UtilityHelper.toRoman(armorPierce));
		if (arrowSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArrowSpeed.name) + " " + UtilityHelper.toRoman(arrowSpeed));
		if (drawSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDrawSpeed.name) + " " + UtilityHelper.toRoman(drawSpeed));
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowKnockback.name) + " " + UtilityHelper.toRoman(knockback));
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowBlind.name) + " " + UtilityHelper.toRoman(blind));
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowSlow.name) + " " + UtilityHelper.toRoman(slow));
		if (entangled != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowEntangled.name) + " " + UtilityHelper.toRoman(entangled));
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFrostSlow.name) + " " + UtilityHelper.toRoman(frostSlow));
		if (physDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowPhysicalDamage.name) + " " + UtilityHelper.toRoman(physDamage));
		if (fireDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFireDamage.name) + " " + UtilityHelper.toRoman(fireDamage));
		if (magicDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowMagicDamage.name) + " " + UtilityHelper.toRoman(magicDamage));
		if (witherDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWitherDamage.name) + " " + UtilityHelper.toRoman(witherDamage));
		if (divineDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDivineDamage.name) + " " + UtilityHelper.toRoman(divineDamage));
		if (chaosDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowChaosDamage.name) + " " + UtilityHelper.toRoman(chaosDamage));
		if (taintDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowTaintDamage.name) + " " + UtilityHelper.toRoman(taintDamage));
		if (frostDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFrostDamage.name) + " " + UtilityHelper.toRoman(frostDamage));
		if (holyDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowHolyDamage.name) + " " + UtilityHelper.toRoman(holyDamage));
		if (lightningDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowLightningDamage.name) + " " + UtilityHelper.toRoman(lightningDamage));
		if (windDamage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWindDamage.name) + " " + UtilityHelper.toRoman(windDamage));
		
		return list;
	}
	
	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();
		
		int fireDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFireDoT);
		int magicDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowMagicDoT);
		int witherDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWitherDoT);
		int taintDot = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowTaintDoT);
		int armorPierce = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArmorPiercing);
		int arrowSpeed = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed);
		int drawSpeed = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed);
		int knockback = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowKnockback);
		int blind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowBlind);
		int slow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowSlow);
		int entangled = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowEntangled);
		int frostSlow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFrostSlow);
		int physDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowPhysicalDamage);
		int fireDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFireDamage);
		int magicDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowMagicDamage);
		int witherDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWitherDamage);
		int divineDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDivineDamage);
		int chaosDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowChaosDamage);
		int taintDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowTaintDamage);
		int frostDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowFrostDamage);
		int holyDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowHolyDamage);
		int lightningDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowLightningDamage);
		int windDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowWindDamage);

		int level = getLevel(item);
		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));
		
		if (fireDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFireDoT.name) + ": Shots light enemies on fire for " + fireDot + " seconds.");
		if (magicDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowMagicDoT.name) + ": Shots give Poison " + UtilityHelper.toRoman(magicDot) + " for 5 seconds.");
		if (witherDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWitherDoT.name) + ": Shots give Wither " + UtilityHelper.toRoman(witherDot) + " for 5 seconds.");
		if (taintDot != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowTaintDoT.name) + ": Shots taint enemies for " + taintDot + " seconds.");
		if (armorPierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArmorPiercing.name) + ": Shots ignore " + armorPierce * 20 + "% of armor.");
		if (arrowSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowArrowSpeed.name) + ": Draw time and arrow speed increased by " + arrowSpeed * 5 + "%.");
		if (drawSpeed != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDrawSpeed.name) + ": Draw time decreased by " + drawSpeed * 5 + "%.");
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowKnockback.name) + ": Knock enemies away on hit.");
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowBlind.name) + ": Shots blind enemies for " + blind + " seconds.");
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowSlow.name) + ": Shots slow enemies for " + slow + " seconds.");
		if (entangled != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowEntangled.name) + ": Shots entangle enemies for " + entangled * 2 + " ticks.");
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFrostSlow.name) + ": Shots heavily slow enemies for " + frostSlow + " seconds.");
		if (physDamage != 0)
		{
			if (ConfigHandler.isNormalDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowPhysicalDamage.name) + ": Shots deal " + physDamage * ConfigHandler.normalDamageMulti * ConfigHandler.normalBowMulti * 100 + "% increased damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowPhysicalDamage.name) + ": Shots deal " + physDamage * ConfigHandler.normalDamageAmount * ConfigHandler.normalBowMulti + " extra damage.");
		}
		if (fireDamage != 0)
		{
			if (ConfigHandler.isFireDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFireDamage.name) + ": Shots deal " + fireDamage * ConfigHandler.fireDamageMulti * ConfigHandler.fireBowMulti * 100 + "% more damage as fire damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFireDamage.name) + ": Shots deal " + fireDamage * ConfigHandler.fireDamageAmount * ConfigHandler.fireBowMulti + " extra damage as fire damage.");
		}
		if (magicDamage != 0)
		{
			if (ConfigHandler.isMagicDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowMagicDamage.name) + ": Shots deal " + magicDamage * ConfigHandler.magicDamageMulti * ConfigHandler.magicBowMulti * 100 + "% more damage as magic damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowMagicDamage.name) + ": Shots deal " + magicDamage * ConfigHandler.magicDamageAmount * ConfigHandler.magicBowMulti + " extra damage as magic damage.");
		}
		if (witherDamage != 0)
		{
			if (ConfigHandler.isWitherDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWitherDamage.name) + ": Shots deal " + witherDamage * ConfigHandler.witherDamageMulti * ConfigHandler.witherBowMulti * 100 + "% more damage as wither damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWitherDamage.name) + ": Shots deal " + witherDamage * ConfigHandler.witherDamageAmount * ConfigHandler.witherBowMulti + " extra damage as wither damage.");
		}
		if (divineDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDivineDamage.name) + ": Shots deal " + divineDamage * ConfigHandler.divineDamageMulti * ConfigHandler.divineBowMulti * 100 + "% more damage as divine damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowDivineDamage.name) + ": Shots deal " + divineDamage * ConfigHandler.divineDamageAmount * ConfigHandler.divineBowMulti + " extra damage as divine damage.");
		}
		if (chaosDamage != 0)
		{
			if (ConfigHandler.isChaosDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowChaosDamage.name) + ": Shots deal " + chaosDamage * ConfigHandler.chaosDamageMulti * ConfigHandler.chaosBowMulti * 100 + "% more damage as chaos damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowChaosDamage.name) + ": Shots deal " + chaosDamage * ConfigHandler.chaosDamageAmount * ConfigHandler.chaosBowMulti + " extra damage as chaos damage.");
		}
		if (taintDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowTaintDamage.name) + ": Shots deal " + taintDamage * ConfigHandler.taintDamageMulti * ConfigHandler.taintBowMulti * 100 + "% more damage as taint damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowTaintDamage.name) + ": Shots deal " + taintDamage * ConfigHandler.taintDamageAmount * ConfigHandler.taintBowMulti + " extra damage as taint damage.");
		}
		if (frostDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFrostDamage.name) + ": Shots deal " + frostDamage * ConfigHandler.frostDamageMulti * ConfigHandler.frostBowMulti * 100 + "% more damage as frost damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowFrostDamage.name) + ": Shots deal " + frostDamage * ConfigHandler.frostDamageAmount * ConfigHandler.frostBowMulti + " extra damage as frost damage.");
		}
		if (holyDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowHolyDamage.name) + ": Shots deal " + holyDamage * ConfigHandler.holyDamageMulti * ConfigHandler.holyBowMulti * 100 + "% more damage as holy damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowHolyDamage.name) + ": Shots deal " + holyDamage * ConfigHandler.holyDamageAmount * ConfigHandler.holyBowMulti + " extra damage as holy damage.");
		}
		if (lightningDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowLightningDamage.name) + ": Shots deal " + lightningDamage * ConfigHandler.lightningDamageMulti * ConfigHandler.lightningBowMulti * 100 + "% more damage as lightning damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowLightningDamage.name) + ": Shots deal " + lightningDamage * ConfigHandler.lightningDamageAmount * ConfigHandler.lightningBowMulti + " extra damage as lightning damage.");
		}
		if (windDamage != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWindDamage.name) + ": Shots deal " + windDamage * ConfigHandler.windDamageMulti * ConfigHandler.windBowMulti * 100 + "% more damage as wind damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.BowWindDamage.name) + ": Shots deal " + windDamage * ConfigHandler.windDamageAmount * ConfigHandler.windBowMulti + " extra damage as wind damage.");
		}
		
		return list;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return event.result;
        
        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow)) player.setItemInUse(item, this.getMaxItemUseDuration(item));

        return item;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int itemInUseCount)
	{
		int bowCharge = getMaxItemUseDuration(item) - itemInUseCount;
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, item, bowCharge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		bowCharge = event.charge;
		
		boolean freeArrow = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		
		if (freeArrow || player.inventory.hasItem(Items.arrow))
		{
			float adjustedCharge = (float) bowCharge / 20.0F;
			adjustedCharge = (adjustedCharge * adjustedCharge + adjustedCharge * 2.0F) / 3.0F * (1 + UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowDrawSpeed) * 0.5F);
			if ((double) adjustedCharge < 0.1D) return;
			float chargeCutoff = (float) (1 + UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BowArrowSpeed) * 0.05F);
			if (adjustedCharge > chargeCutoff) adjustedCharge = chargeCutoff;
			
			EntityModArrow entityArrow = new EntityModArrow(world, player, adjustedCharge * 2.0F, item);
			if (adjustedCharge == chargeCutoff) entityArrow.setIsCritical(true);
			
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + adjustedCharge * 0.5F);
			
			if (freeArrow) entityArrow.canBePickedUp = 2;
			else player.inventory.consumeInventoryItem(Items.arrow);
			
			if (!world.isRemote) world.spawnEntityInWorld(entityArrow);
		}
		item.stackTagCompound.setBoolean("ItemInUse", false);
	}
}
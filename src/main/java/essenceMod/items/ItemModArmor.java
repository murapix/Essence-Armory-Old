package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IRevealer;
import thaumcraft.api.items.IRunicArmor;
import thaumcraft.api.items.IVisDiscountGear;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModArmory;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

@Optional.InterfaceList(value = { @Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IVisDiscountGear"), @Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IGoggles"), @Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IRevealer"), @Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IRunicArmor")/*, @Interface(modid = "Botania", iface = "vaskii.botania.api.mana.IManaDiscountArmor")*/ })
public class ItemModArmor extends ItemArmor implements IUpgradeable, IVisDiscountGear, IGoggles, IRevealer, IRunicArmor//, IManaDiscountArmor
{
	private static AttributeModifier health = new AttributeModifier(UUID.fromString("EE15F16D-AA48-45CE-8B72-BF5A1A1D5CFD"), "EssenceArmoryArmorHealth", ConfigHandler.healthBoostCount, 0);

	private int level;

	public ItemModArmor(ArmorMaterial material, int ArmorType)
	{
		super(material, 0, ArmorType);
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		MinecraftForge.EVENT_BUS.register(this);

		level = 0;
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		NBTTagCompound compound = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
		compound.setInteger("Level", level);
		compound.setInteger("Absorption Delay", 0);
		item.setTagCompound(compound);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack item)
	{
		if (!item.hasTagCompound()) onCreated(item, world, player);

		if (item.getItem() instanceof ItemModArmor)
		{
			NBTTagCompound compound = item.getTagCompound();
			int absorptionDelay = compound.getInteger("Absorption Delay");
			if (absorptionDelay <= 0) absorptionDelay = 0;
			else absorptionDelay--;
			compound.setInteger("Absorption Delay", absorptionDelay);
			item.setTagCompound(compound);
		}
	}

	@Override
	public String getArmorTexture(ItemStack item, Entity entity, int slot, String type)
	{
		if (item.getItem() == ModArmory.infusedHelm || item.getItem() == ModArmory.infusedPlate || item.getItem() == ModArmory.infusedBoots)
		{
			return Reference.MODID + ":models/armor/infused_layer1.png";
		}
		else if (item.getItem() == ModArmory.infusedPants)
		{
			return Reference.MODID + ":models/armor/infused_layer2.png";
		}
		else return null;
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean regenAbsorption = true;
			int absorption = 0;
			int healthBoost = 0;

			for (int i = 1; i <= 4; i++)
			{
				ItemStack item = player.getEquipmentInSlot(i);
				if (item == null) continue;
				if (item.getItem() instanceof ItemModArmor)
				{
					if (item.getTagCompound().getInteger("Absorption Delay") != 0) regenAbsorption = false;
					absorption += Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorAbsorption);
					healthBoost += Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorHealthBoost);
				}
			}
			absorption *= ConfigHandler.absorptionCount;
			PotionEffect potion = player.getActivePotionEffect(Potion.absorption);
			float potionAbsorption;
			if (potion == null) potionAbsorption = 0;
			else potionAbsorption = (potion.getAmplifier() + 1) * 4;
			float currentAbsorption = player.getAbsorptionAmount();
			float maxAbsorption = potionAbsorption + absorption;
			if (currentAbsorption < maxAbsorption)
			{
				if (currentAbsorption > potionAbsorption && regenAbsorption) player.setAbsorptionAmount(currentAbsorption + 1);
				else if (currentAbsorption <= potionAbsorption && regenAbsorption) player.setAbsorptionAmount(currentAbsorption + 1);
			}
			else player.setAbsorptionAmount(maxAbsorption);
			if (regenAbsorption)
			{
				for (int i = 1; i <= 4; i++)
				{
					ItemStack item = player.getEquipmentInSlot(i);
					if (item == null) continue;
					if (item.getItem() instanceof ItemModArmor)
					{
						NBTTagCompound compound = item.getTagCompound();
						compound.setInteger("Absorption Delay", ConfigHandler.absorptionRecharge);
						item.setTagCompound(compound);
					}
				}
			}

			IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
			if (attribute != null)
			{
				attribute.removeModifier(health);
				attribute.applyModifier(new AttributeModifier(health.getID(), health.getName() + healthBoost, health.getAmount() * healthBoost, health.getOperation()));
			}
		}
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool)
	{
		if (!item.hasTagCompound()) onCreated(item, player.worldObj, player);

		int visDiscount = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorVisDiscount);
		if (visDiscount != 0) list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + visDiscount + "%");

		int revealing = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorRevealing);
		if (revealing != 0) list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal(UpgradeRegistry.ArmorRevealing.name));

		int manaDiscount = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorManaDiscount);
		if (manaDiscount != 0) list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal(UpgradeRegistry.ArmorManaDiscount.name) + ": " + manaDiscount + "%");
		
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorStepAssist) != 0) list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal(UpgradeRegistry.ArmorStepAssist.name));

		int invisible = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorInvisible);
		if (invisible != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorInvisible.name));

		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));
	}

	private List addNormalInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModArmor.getLevel(item);
		int protection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorPhysicalProtection);
		int fireProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorFireProtection);
		int magicProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorMagicProtection);
		int witherProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorWitherProtection);
		int blastProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorBlastProtection);
		int projectileProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorProjectileProtection);
		int chaosProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorChaosProtection);
		int resistance = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorResistance);
		int absorption = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorAbsorption);
		int healthBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorHealthBoost);
		int thorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorPhysicalThorns);
		int poisonThorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorMagicThorns);
		int blindThorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorBlindThorns);
		int speedBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorRunSpeed);

		if (level != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(level));
		}
		if (protection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorPhysicalProtection.name) + " " + UtilityHelper.toRoman(protection));
		if (fireProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorFireProtection.name) + " " + UtilityHelper.toRoman(fireProtection));
		if (magicProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorMagicProtection.name) + " " + UtilityHelper.toRoman(magicProtection));
		if (witherProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorWitherProtection.name) + " " + UtilityHelper.toRoman(witherProtection));
		if (blastProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorBlastProtection.name) + " " + UtilityHelper.toRoman(blastProtection));
		if (projectileProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorProjectileProtection.name) + " " + UtilityHelper.toRoman(projectileProtection));
		if (chaosProtection != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorChaosProtection.name) + " " + UtilityHelper.toRoman(chaosProtection));
		if (resistance != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorResistance.name) + " " + UtilityHelper.toRoman(resistance));
		if (absorption != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorAbsorption.name) + " " + UtilityHelper.toRoman(absorption));
		if (healthBoost != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorHealthBoost.name) + " " + UtilityHelper.toRoman(healthBoost));
		if (thorns != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorPhysicalThorns.name) + " " + UtilityHelper.toRoman(thorns));
		if (poisonThorns != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorMagicThorns.name) + " " + UtilityHelper.toRoman(poisonThorns));
		if (blindThorns != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorBlindThorns.name) + " " + UtilityHelper.toRoman(blindThorns));
		if (speedBoost != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.ArmorRunSpeed.name) + " " + UtilityHelper.toRoman(speedBoost));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModArmor.getLevel(item);
		int protection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorPhysicalProtection);
		int fireProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorFireProtection);
		int magicProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorMagicProtection);
		int witherProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorWitherProtection);
		int blastProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorBlastProtection);
		int projectileProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorProjectileProtection);
		int chaosProtection = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorChaosProtection);
		int resistance = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorResistance);
		int absorption = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorAbsorption);
		int healthBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorHealthBoost);
		int thorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorPhysicalThorns);
		int poisonThorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorMagicThorns);
		int blindThorns = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorBlindThorns);
		int speedBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.ArmorRunSpeed);

		if (level != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(level));
		}

		int baseRes = protection * 2;
		int fireRes = fireProtection * 3;
		int magicRes = magicProtection * 3;
		int witherRes = witherProtection * 3;
		int blastRes = blastProtection * 3;
		int projRes = projectileProtection * 3;
		int chaosRes = chaosProtection * 3;
		int res = resistance * 5;

		list.add("All damage reduced by " + baseRes + "%");
		if (fireRes != 0) list.add("Fire damage reduced by an additional " + fireRes + "%");
		if (magicRes != 0) list.add("Magic damage reduced by an additional " + magicRes + "%");
		if (witherRes != 0) list.add("Wither damage reduced by an additional " + witherRes + "%");
		if (blastRes != 0) list.add("Blast damage reduced by an additional " + blastRes + "%");
		if (projRes != 0) list.add("Projectile damage reduced by an additional " + projRes + "%");
		if (chaosRes != 0) list.add("Chaos damage reduced by an additional " + chaosRes + "%");
		if (res != 0) list.add("All damage reduced further by " + res + "%");
		if (absorption != 0) list.add("Gain a temporary shield of " + absorption + " health");
		if (healthBoost != 0) list.add("Gain " + healthBoost + " health");
		if (thorns != 0) list.add("Deals " + (thorns * 0.25F) + " damage to any who attack you");
		if (poisonThorns != 0) list.add("Poisons any who attack you for " + poisonThorns * ConfigHandler.poisonThornsDuration / 20 + " seconds");
		if (blindThorns != 0) list.add("Gives a chance to blind any who attack you for " + blindThorns * ConfigHandler.blindThornsDuration / 20 + " seconds");
		if (speedBoost != 0) list.add("Run " + (speedBoost * 20) + "% faster");

		return list;
	}

	public static int getLevel(ItemStack item)
	{
		return item.getTagCompound().getInteger("Level");
	}

	@Override
	@Optional.Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack item, EntityPlayer player, Aspect aspect)
	{
		return Upgrade.getUpgradeLevel(item, "ArmorVisDiscount");
	}

	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack item, EntityLivingBase player)
	{
		if (item.getItem() instanceof ItemModArmor && ((ItemModArmor) item.getItem()).armorType == 0) return Upgrade.getUpgradeLevel(item, "ArmorRevealing") > 0;
		return false;
	}

	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showIngamePopups(ItemStack item, EntityLivingBase player)
	{
		if (item.getItem() instanceof ItemModArmor && ((ItemModArmor) item.getItem()).armorType == 0) return Upgrade.getUpgradeLevel(item, "ArmorRevealing") > 0;
		return false;
	}

	@Override
	@Optional.Method(modid = "Thaumcraft")
	public int getRunicCharge(ItemStack item)
	{
		return Upgrade.getUpgradeLevel(item, "ArmorRunicShielding");
	}

//	@Override
//	@Optional.Method(modid = "Botania")
//	public float getDiscount(ItemStack item, int slot, EntityPlayer player)
//	{
//		return Upgrade.getUpgradeLevel(item, "ArmorManaDiscount") * 0.01F;
//	}
}
package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.crafting.Upgrade;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModArmor extends ItemArmor implements IModItem
{
	private final AttributeModifier health = new AttributeModifier(UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC"), "EssenceArmoryArmorHealth", 2D, 0);

	int level;
	int armorType;
	int absorptionDelay;
	float absorptionRemaining;

	public ItemModArmor(ArmorMaterial material, int ArmorType)
	{
		super(material, 0, ArmorType);
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		MinecraftForge.EVENT_BUS.register(this);

		level = 0;
		armorType = ArmorType;
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setInteger("Absorption Delay", 0);

	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack item)
	{
		if (!item.hasTagCompound()) onCreated(item, world, player);

		if (item.getItem() instanceof ItemModArmor)
		{
			int absorptionDelay = item.stackTagCompound.getInteger("Absorption Delay");
			if (absorptionDelay <= 0) absorptionDelay = 0;
			else absorptionDelay--;
			item.stackTagCompound.setInteger("Absorption Delay", absorptionDelay);
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean resetAbsorption = true;
			int absorption = 0;
			int healthBoost = 0;

			for (int i = 1; i <= 4; i++)
			{
				ItemStack item = player.getEquipmentInSlot(i);
				if (item == null) ;
				else if (item.getItem() instanceof ItemModArmor)
				{
					if (item.stackTagCompound.getInteger("Absorption Delay") != 0) resetAbsorption = false;
					absorption += UtilityHelper.getUpgradeLevel(item, "Absorption");
					healthBoost += UtilityHelper.getUpgradeLevel(item, "HealthBoost");
				}
			}
			PotionEffect potion = player.getActivePotionEffect(Potion.field_76444_x);
			float maxAbsorption;
			if (potion == null) maxAbsorption = 0;
			else maxAbsorption = (potion.getAmplifier() + 1) * 4;
			float currentAbsorption = player.getAbsorptionAmount();
			if (currentAbsorption > maxAbsorption && resetAbsorption) player.setAbsorptionAmount(maxAbsorption + absorption);
			else if (currentAbsorption <= maxAbsorption && resetAbsorption) player.setAbsorptionAmount(currentAbsorption + absorption);

			UUID playerID = player.getGameProfile().getId();
			IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
			if (attribute != null)
			{
				attribute.removeModifier(health);
				attribute.applyModifier(new AttributeModifier(health.getID(), health.getName() + healthBoost, health.getAmount() * healthBoost, health.getOperation()));
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			DamageSource damage = event.source;
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack[] equipment = new ItemStack[4];
			int resistance = 0;
			int protection = 0;
			int thorns = 0;
			for (int i = 0; i < 4; i++)
			{
				equipment[i] = player.getEquipmentInSlot(i + 1);
				if (equipment[i] != null && equipment[i].getItem() instanceof ItemModArmor)
				{
					equipment[i].stackTagCompound.setInteger("Absorption Delay", 200);
					resistance += UtilityHelper.getUpgradeLevel(equipment[i], "Resistance");
					protection += UtilityHelper.getUpgradeLevel(equipment[i], "Protection") * 2;
					thorns += UtilityHelper.getUpgradeLevel(equipment[i], "Thorns");

					if (damage.isFireDamage()) protection += UtilityHelper.getUpgradeLevel(equipment[i], "FireProtection") * 3;
					else if (damage.isMagicDamage()) protection += UtilityHelper.getUpgradeLevel(equipment[i], "MagicProtection") * 3;
					else if (damage.isProjectile()) protection += UtilityHelper.getUpgradeLevel(equipment[i], "ProjectileProtection") * 3;
					else if (damage.isExplosion()) protection += UtilityHelper.getUpgradeLevel(equipment[i], "BlastProtection") * 3;
					else if (damage.damageType.equals(DamageSource.wither.damageType)) protection += UtilityHelper.getUpgradeLevel(equipment[i], "WitherProtection") * 3;
				}
			}
			event.ammount *= (1 - protection / 100);
			event.ammount *= (1 - resistance / 20);

			Entity entity = event.source.getEntity();
			if (entity != null)
			{
				if (thorns != 0) entity.attackEntityFrom(DamageSource.causeThornsDamage(player), thorns * 0.25F);
			}
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool)
	{
		if (!item.hasTagCompound()) onCreated(item, player.worldObj, player);
		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));
	}

	private List addNormalInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModArmor.getLevel(item);
		int protection = UtilityHelper.getUpgradeLevel(item, "Protection");
		int fireProtection = UtilityHelper.getUpgradeLevel(item, "FireProtection");
		int magicProtection = UtilityHelper.getUpgradeLevel(item, "MagicProtection");
		int witherProtection = UtilityHelper.getUpgradeLevel(item, "WitherProtection");
		int blastProtection = UtilityHelper.getUpgradeLevel(item, "BlastProtection");
		int projectileProtection = UtilityHelper.getUpgradeLevel(item, "ProjectileProtection");
		int resistance = UtilityHelper.getUpgradeLevel(item, "Resistance");
		int absorption = UtilityHelper.getUpgradeLevel(item, "Absorption");
		int healthBoost = UtilityHelper.getUpgradeLevel(item, "HealthBoost");
		int thorns = UtilityHelper.getUpgradeLevel(item, "Thorns");
		int poisonThorns = UtilityHelper.getUpgradeLevel(item, "PoisonThorns");
		int blindThorns = UtilityHelper.getUpgradeLevel(item, "BlindThorns");

		if (level != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(level));
		}
		if (protection != 0) list.add("Protection " + UtilityHelper.toRoman(protection));
		if (fireProtection != 0) list.add("Fire Protection " + UtilityHelper.toRoman(fireProtection));
		if (magicProtection != 0) list.add("Magic Protection " + UtilityHelper.toRoman(magicProtection));
		if (witherProtection != 0) list.add("Wither Protection " + UtilityHelper.toRoman(witherProtection));
		if (blastProtection != 0) list.add("Blast Protection " + UtilityHelper.toRoman(blastProtection));
		if (projectileProtection != 0) list.add("Projectile Protection " + UtilityHelper.toRoman(projectileProtection));
		if (resistance != 0) list.add("Resistance " + UtilityHelper.toRoman(resistance));
		if (absorption != 0) list.add("Absorption " + UtilityHelper.toRoman(absorption));
		if (healthBoost != 0) list.add("Health Boost " + UtilityHelper.toRoman(healthBoost));
		if (thorns != 0) list.add("Thorns " + UtilityHelper.toRoman(thorns));
		if (poisonThorns != 0) list.add("Poisonous " + UtilityHelper.toRoman(poisonThorns));
		if (blindThorns != 0) list.add("Blinding " + UtilityHelper.toRoman(blindThorns));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModArmor.getLevel(item);
		int protection = UtilityHelper.getUpgradeLevel(item, "Protection");
		int fireProtection = UtilityHelper.getUpgradeLevel(item, "FireProtection");
		int magicProtection = UtilityHelper.getUpgradeLevel(item, "MagicProtection");
		int witherProtection = UtilityHelper.getUpgradeLevel(item, "WitherProtection");
		int blastProtection = UtilityHelper.getUpgradeLevel(item, "BlastProtection");
		int projectileProtection = UtilityHelper.getUpgradeLevel(item, "ProjectileProtection");
		int resistance = UtilityHelper.getUpgradeLevel(item, "Resistance");
		int absorption = UtilityHelper.getUpgradeLevel(item, "Absorption");
		int healthBoost = UtilityHelper.getUpgradeLevel(item, "HealthBoost");
		int thorns = UtilityHelper.getUpgradeLevel(item, "Thorns");
		int poisonThorns = UtilityHelper.getUpgradeLevel(item, "PoisonThorns");
		int blindThorns = UtilityHelper.getUpgradeLevel(item, "BlindThorns");

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
		int res = resistance * 5;

		list.add("All damage reduced by " + baseRes + "%");
		if (fireRes != 0) list.add("Fire damage reduced by an additional " + fireRes + "%");
		if (magicRes != 0) list.add("Magic damage reduced by an additional " + magicRes + "%");
		if (witherRes != 0) list.add("Wither damage reduced by an additional " + witherRes + "%");
		if (blastRes != 0) list.add("Blast damage reduced by an additional " + blastRes + "%");
		if (projRes != 0) list.add("Projectile damage reduced by an additional " + projRes + "%");
		if (res != 0) list.add("All damage reduced further by " + res + "%");
		if (absorption != 0) list.add("Gain a temporary shield of " + (absorption / 2) + " hearts");
		if (healthBoost != 0) list.add("Gain " + (healthBoost / 2) + " hearts");
		if (thorns != 0) list.add("Deals " + (thorns * 0.25F) + " damage to any who attack you");
		if (poisonThorns != 0) list.add("Poisons any who attack you for " + poisonThorns + " seconds");
		if (blindThorns != 0) list.add("Blinds any who attack you for " + (blindThorns * 0.5) + " seconds");

		return list;
	}

	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
}
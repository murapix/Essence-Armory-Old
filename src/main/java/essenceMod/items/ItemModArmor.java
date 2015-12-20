package essenceMod.items;

import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
					if (item.hasTagCompound())
					{
						if (item.stackTagCompound.getInteger("Absorption Delay") != 0) resetAbsorption = false;
						absorption += ItemModArmor.getUpgradeLevel(item, "Absorption");
						healthBoost += ItemModArmor.getUpgradeLevel(item, "Health Boost");
					}
					else onCreated(item, player.worldObj, player);
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
					resistance += ItemModArmor.getUpgradeLevel(equipment[i], "Resistance");
					protection += ItemModArmor.getUpgradeLevel(equipment[i], "Protection") * 2;
					thorns += ItemModArmor.getUpgradeLevel(equipment[i], "Thorns");

					if (damage.isFireDamage()) protection += ItemModArmor.getUpgradeLevel(equipment[i], "Fire Protection") * 3;
					else if (damage.isMagicDamage()) protection += ItemModArmor.getUpgradeLevel(equipment[i], "Magic Protection") * 3;
					else if (damage.isProjectile()) protection += ItemModArmor.getUpgradeLevel(equipment[i], "Projectile Protection") * 3;
					else if (damage.isExplosion()) protection += ItemModArmor.getUpgradeLevel(equipment[i], "Blast Protection") * 3;
					else if (damage.damageType.equals(DamageSource.wither.damageType)) protection += ItemModArmor.getUpgradeLevel(equipment[i], "Wither Protection") * 3;
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
			if (item.stackTagCompound.getBoolean("Invisible")) return Reference.MODID + ":models/armor/invis_layer1.png";
			return Reference.MODID + ":models/armor/infused_layer1.png";
		}
		else if (item.getItem() == ModArmory.infusedPants)
		{
			if (item.stackTagCompound.getBoolean("Invisible")) return Reference.MODID + ":models/armor/invis_layer2.png";
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

		if (item.stackTagCompound.getInteger("Absorption") != 0)
		{
			list.add("Absorption " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Absorption")));
		}
		if (item.stackTagCompound.getInteger("Health Boost") != 0)
		{
			list.add("Health Boost " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Health Boost")));
		}
	}

	/**
	 * Returns the level of the upgrade with the given name found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param name
	 *            The name of the upgrade.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, String name)
	{
		NBTTagList upgradeList = item.stackTagCompound.getTagList("Upgrades", NBT.TAG_COMPOUND);
		for (int i = 0; i < upgradeList.tagCount(); i++)
		{
			NBTTagCompound tag = upgradeList.getCompoundTagAt(i);
			if (tag.getString("Name").equals(name)) return tag.getInteger("Level");
		}
		return 0;
	}

	/**
	 * Returns the level of the given upgrade found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param upgrade
	 *            The upgrade of which the level is being checked.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, Upgrade upgrade)
	{
		if (upgrade == null || upgrade.name == null) return 0;
		return ItemModArmor.getUpgradeLevel(item, upgrade.name);
	}
}
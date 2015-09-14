package essenceMod.items;

import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.help.Reference;
import essenceMod.help.UtilityHelper;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;

public class ItemModArmor extends ItemArmor implements IModItem
{
	private final AttributeModifier health = new AttributeModifier(UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC"), "EssenceArmoryArmorHealth", 2D, 0);
	
	int level, resist, thorns, absorption, protection, fireProt, blastProt, projProt, witherProt, magicProt, healthBoost;
	public final int maxLevel = 15, maxResist = 5, maxThorns = 3, maxAbsorption = 5, maxProtection = 4, maxFireProt = 4, maxBlastProt = 4, maxProjProt = 4, maxWitherProt = 4, maxMagicProt = 4, maxHealthBoost = 5;
	boolean invisible;
	
	int armorType;

	int absorptionDelay;
	float absorptionRemaining;

	public ItemModArmor(ArmorMaterial material, int ArmorType)
	{
		super(material, 0, ArmorType);
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		MinecraftForge.EVENT_BUS.register(this);

		level = resist = thorns = absorption = protection = fireProt = blastProt = projProt = witherProt = magicProt = healthBoost = 0;
		invisible = false;
		armorType = ArmorType;
	}

	public ItemModArmor(ArmorMaterial material, int ArmorType, String[] upgrades)
	{
		this(material, ArmorType);

		level = upgrades.length;
		for (String str : upgrades)
		{
			if (str.equals("Resistance")) resist++;
			else if (str.equals("Thorns")) thorns++;
			else if (str.equals("Absorption")) absorption++;
			else if (str.equals("Protection")) protection++;
			else if (str.equals("Fire Protection")) fireProt++;
			else if (str.equals("BlastProtection")) blastProt++;
			else if (str.equals("Projectile Protection")) projProt++;
			else if (str.equals("Wither Protection")) witherProt++;
			else if (str.equals("Magic Protection")) magicProt++;
			else if (str.equals("Health Boost")) healthBoost++;
			else if (str.equals("Invisible")) invisible = true;
		}
		absorption = 5;
		healthBoost = 5;
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setInteger("Resistance", resist);
		item.stackTagCompound.setInteger("Thorns", thorns);
		item.stackTagCompound.setInteger("Absorption", absorption);
		item.stackTagCompound.setInteger("Protection", protection);
		item.stackTagCompound.setInteger("Blast Protection", blastProt);
		item.stackTagCompound.setInteger("Fire Protection", fireProt);
		item.stackTagCompound.setInteger("Projectile Protection", projProt);
		item.stackTagCompound.setInteger("Wither Protection", witherProt);
		item.stackTagCompound.setInteger("Magic Protection", magicProt);
		item.stackTagCompound.setInteger("Health Boost", healthBoost);
		item.stackTagCompound.setInteger("Absorption Delay", 0);
		
		if (thorns != 0) item.addEnchantment(Enchantment.thorns, thorns);
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
				if (item == null);
				else if (item.getItem() instanceof ItemModArmor)
				{
					if (item.hasTagCompound())
					{
						if (item.stackTagCompound.getInteger("Absorption Delay") != 0) resetAbsorption = false;
						absorption += item.stackTagCompound.getInteger("Absorption");
						healthBoost += item.stackTagCompound.getInteger("Health Boost");
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
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack[] equipment = new ItemStack[4];
			int resistance = 0;
			for (int i = 0; i < 4; i++)
			{
				equipment[i] = player.getEquipmentInSlot(i + 1);
				if (equipment[i] != null && equipment[i].getItem() instanceof ItemModArmor)
				{
					equipment[i].stackTagCompound.setInteger("Absorption Delay", 200);
					resistance += equipment[i].stackTagCompound.getInteger("Resistance");
				}
			}
			event.ammount *= (1 - resistance / 20);
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
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
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
}
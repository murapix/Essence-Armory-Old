package essenceMod.items.baubles;

import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Upgrade;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemPotionRing extends ItemBauble
{
	public int level, cooldown;
	public IIcon[] icons = new IIcon[19];
	
	private final AttributeModifier speed = new AttributeModifier(UUID.fromString("BCA6DE48-7202-4AA5-B5E0-628D346179C7"), "EssenceArmoryRingSpeed", 0.2D, 2);
	private final AttributeModifier strength = new AttributeModifier(UUID.fromString("F8924A96-C647-4C2C-A68E-2543CA6B6306"), "EssenceArmoryRingStrength", 0.5D, 2);

	public ItemPotionRing()
	{
		this(0);
	}

	public ItemPotionRing(int level)
	{
		this.level = level;
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 1; i < 19; i++)
			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "-" + i);
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta > 19) meta = 0;
		return icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 1; i < 19; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack item)
	{
		return this.getUnlocalizedName() + ":" + item.getItemDamage();
	}

	@Override
	public BaubleType getBaubleType(ItemStack item)
	{
		return BaubleType.RING;
	}

	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);

		if (item.hasTagCompound())
		{
			if (UtilityHelper.getUpgradeLevel(item, "NightVision") != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
		}

		if (player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			UUID playerID = p.getGameProfile().getId();
			IAttributeInstance attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (UtilityHelper.getUpgradeLevel(item, "Swiftness") != 0)
				{
					attribute.removeModifier(speed);
				}
			}
			attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
			if (attribute != null)
			{
				if (UtilityHelper.getUpgradeLevel(item, "Strength") != 0)
				{
					attribute.removeModifier(strength);
				}
			}
		}
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		if (getLevel(item) == 0)
		{
			level = (item.getItemDamage() + 2) % 3 + 1;
			switch (item.getItemDamage())
			{
				case 1:
				case 2:
				case 3:
					InfuserRecipes.addLevel(item, new Upgrade("Swiftness", level));
					break;
				case 4:
				case 5:
				case 6:
					InfuserRecipes.addLevel(item, new Upgrade("Haste", level));
					break;
				case 7:
				case 8:
				case 9:
					InfuserRecipes.addLevel(item, new Upgrade("Strength", level));
					break;
				case 10:
				case 11:
				case 12:
					InfuserRecipes.addLevel(item, new Upgrade("JumpBoost", level));
					break;
				case 13:
				case 14:
				case 15:
					InfuserRecipes.addLevel(item, new Upgrade("Regeneration", level));
					break;
				case 16:
					InfuserRecipes.addLevel(item, new Upgrade("NightVision", 1));
					level = 1;
					break;
				case 17:
					InfuserRecipes.addLevel(item, new Upgrade("WaterBreathing", 1));
					level = 1;
					break;
				case 18:
					InfuserRecipes.addLevel(item, new Upgrade("FireResistance", 1));
					level = 1;
					break;
			}
		}
		item.stackTagCompound.setInteger("Level", level);
	}

	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);

		int level = item.stackTagCompound.getInteger("Level");
		int type = item.stackTagCompound.getInteger("Type");
		if (UtilityHelper.getUpgradeLevel(item, "Regenration") != 0)
		{
			if (cooldown != 0) cooldown--;
			else if (cooldown == 0)
			{
				player.heal(1);
				cooldown = 60 / ((ItemPotionRing) item.getItem()).getLevel(item);
			}
		}
		if (UtilityHelper.getUpgradeLevel(item, "NightVision") != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0));
		if (UtilityHelper.getUpgradeLevel(item, "WaterBreathing") != 0) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
		if (UtilityHelper.getUpgradeLevel(item, "FireResistance") != 0) player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0));
	}

	public int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		String info = "";
		int level = 0;
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);

		level = item.stackTagCompound.getInteger("Level");
		info = UtilityHelper.toRoman(level);

		String potionName;
		if (UtilityHelper.getUpgradeLevel(item, "Swiftness") != 0) potionName = "Swiftness";
		else if (UtilityHelper.getUpgradeLevel(item, "Haste") != 0) potionName = "Haste";
		else if (UtilityHelper.getUpgradeLevel(item, "Strength") != 0) potionName = "Strength";
		else if (UtilityHelper.getUpgradeLevel(item, "JumpBoost") != 0) potionName = "Jump Boost";
		else if (UtilityHelper.getUpgradeLevel(item, "Regeneration") != 0) potionName = "Regeneration";
		else if (UtilityHelper.getUpgradeLevel(item, "NightVision") != 0) potionName = "Night Vision";
		else if (UtilityHelper.getUpgradeLevel(item, "WaterBreathing") != 0) potionName = "Water Breathing";
		else if (UtilityHelper.getUpgradeLevel(item, "FireResistance") != 0) potionName = "Fire Resistance";
		else potionName = "No Effect";
		
		info = potionName + " " + info;
		list.add(info);
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType() == event.source.fall.getDamageType())
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int jumpLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing) jumpLevel = UtilityHelper.getUpgradeLevel(ring1, "JumpBoost");
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing) jumpLevel = Math.max(jumpLevel, UtilityHelper.getUpgradeLevel(ring2, "JumpBoost"));
			event.ammount -= jumpLevel / 20;
		}
	}

	@SubscribeEvent
	public void updatePlayerStats(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int swiftnessLevel = 0;
			int strengthLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
			{
				swiftnessLevel = UtilityHelper.getUpgradeLevel(ring1, "Swiftness");
				strengthLevel = UtilityHelper.getUpgradeLevel(ring1, "Strength");
			}
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
			{
				swiftnessLevel = Math.max(swiftnessLevel, UtilityHelper.getUpgradeLevel(ring2, "Swiftness"));
				strengthLevel = Math.max(strengthLevel, UtilityHelper.getUpgradeLevel(ring2, "Strength"));
			}
			UUID playerID = player.getGameProfile().getId();
			IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (swiftnessLevel != 0)
				{
					attribute.removeModifier(speed);
					attribute.applyModifier(new AttributeModifier(speed.getID(), speed.getName() + swiftnessLevel, speed.getAmount() * swiftnessLevel, speed.getOperation()));
				}
			}
			attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
			if (attribute != null)
			{
				if (strengthLevel != 0)
				{
					attribute.removeModifier(strength);
					attribute.applyModifier(new AttributeModifier(strength.getID(), strength.getName() + strengthLevel, strength.getAmount() * strengthLevel, strength.getOperation()));
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerMine(BreakSpeed event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
		ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
		int hasteLevel = 0;
		if (ring1 != null && ring1.getItem() instanceof ItemPotionRing) hasteLevel = UtilityHelper.getUpgradeLevel(ring1, "Haste");
		if (ring2 != null && ring2.getItem() instanceof ItemPotionRing) hasteLevel = Math.max(hasteLevel, UtilityHelper.getUpgradeLevel(ring2, "Haste"));
		event.newSpeed *= (1 + 0.01F * hasteLevel);
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int jumpLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing) jumpLevel = UtilityHelper.getUpgradeLevel(ring1, "JumpBoost");
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing) jumpLevel = Math.max(jumpLevel, UtilityHelper.getUpgradeLevel(ring2, "JumpBoost"));
			switch (jumpLevel)
			{
				case 1:
					player.motionY += 0.01F;
					break;
				case 2:
					player.motionY += 0.016F;
					break;
				case 3:
					player.motionY += 0.023F;
					break;
			}
		}
	}
}
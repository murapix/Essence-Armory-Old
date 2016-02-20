package essenceMod.items.baubles;

import java.util.List;
import java.util.UUID;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import essenceMod.items.Upgrade;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.UpgradeRegistry;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;

public class ItemRing extends ItemBauble
{
	public int level, cooldown;
	public IIcon[] icons = new IIcon[19];
	
	private final AttributeModifier speed = new AttributeModifier(UUID.fromString("BCA6DE48-7202-4AA5-B5E0-628D346179C7"), "EssenceArmoryRingSpeed", 0.2D, 2);
	private final AttributeModifier strength = new AttributeModifier(UUID.fromString("F8924A96-C647-4C2C-A68E-2543CA6B6306"), "EssenceArmoryRingStrength", 0.5D, 2);

	public ItemRing()
	{
		this(0);
	}

	public ItemRing(int level)
	{
		this.level = level;
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		icons[0] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
		for (int i = 1; i < icons.length; i++)
			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "-" + i);
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta >= icons.length) meta = 0;
		return icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < icons.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
//	@Override
//	public String getUnlocalizedName(ItemStack item)
//	{
//		return this.getUnlocalizedName() + ":" + item.getItemDamage();
//	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.RING;
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		int swiftness = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness);
		int haste = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionHaste);
		int strength = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength);
		int jumpBoost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionJumpBoost);
		int regeneration = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration);
		int nightVision = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision);
		int waterBreathing = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing);
		int fireResistance = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance);
		
		if (swiftness != 0) item.setItemDamage(swiftness);
		else if (haste != 0) item.setItemDamage(haste + 3);
		else if (strength != 0) item.setItemDamage(strength + 6);
		else if (jumpBoost != 0) item.setItemDamage(jumpBoost + 9);
		else if (regeneration != 0) item.setItemDamage(regeneration + 12);
		else if (nightVision != 0) item.setItemDamage(nightVision + 15);
		else if (waterBreathing != 0) item.setItemDamage(waterBreathing + 16);
		else if (fireResistance != 0) item.setItemDamage(fireResistance + 17);
		else item.setItemDamage(0);
	}

	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);

		if (item.hasTagCompound())
		{
			if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision) != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
		}

		if (player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			UUID playerID = p.getGameProfile().getId();
			IAttributeInstance attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness) != 0)
				{
					attribute.removeModifier(speed);
				}
			}
			attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
			if (attribute != null)
			{
				if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength) != 0)
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
		item.stackTagCompound.setInteger("Level", 0);
		int meta = item.getItemDamage();
		if (meta == 0) return;
		else if (meta <= 3) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionSwiftness.setLevel(meta));
		else if (meta <= 6) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionHaste.setLevel(meta - 3));
		else if (meta <= 9) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionStrength.setLevel(meta - 6));
		else if (meta <= 12) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionJumpBoost.setLevel(meta - 9));
		else if (meta <= 15) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionRegeneration.setLevel(meta - 12));
		else if (meta == 16) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionNightVision.setLevel(1));
		else if (meta == 17) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionWaterBreathing.setLevel(1));
		else if (meta == 18) InfuserRecipes.addUpgrade(item, UpgradeRegistry.RingPotionFireResistance.setLevel(1));
	}

	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);

		int level = item.stackTagCompound.getInteger("Level");
		int type = item.stackTagCompound.getInteger("Type");
		if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration) != 0)
		{
			if (cooldown != 0) cooldown--;
			else if (cooldown == 0)
			{
				player.heal(1);
				cooldown = 60 / ((ItemRing) item.getItem()).getLevel(item);
			}
		}
		if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision) != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0));
		if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing) != 0) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
		if (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance) != 0) player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0));
	}

	public int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);
		
		int swiftness = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness);
		int haste = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionHaste);
		int strength = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength);
		int jumpBoost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionJumpBoost);
		int regeneration = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration);
		int nightVision = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision);
		int waterBreathing = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing);
		int fireResistance = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance);
		
		if (swiftness != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionSwiftness.name + swiftness));
		if (haste != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionSwiftness.name + haste));
		if (strength != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionStrength.name + strength));
		if (jumpBoost != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionJumpBoost.name + jumpBoost));
		if (regeneration != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionRegeneration.name + regeneration));
		if (nightVision != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionNightVision.name + nightVision));
		if (waterBreathing != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionWaterBreathing.name + waterBreathing));
		if (fireResistance != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.RingPotionFireResistance.name + fireResistance));
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
			if (ring1 != null && ring1.getItem() instanceof ItemRing) jumpLevel = UtilityHelper.getUpgradeLevel(ring1, "JumpBoost");
			if (ring2 != null && ring2.getItem() instanceof ItemRing) jumpLevel = Math.max(jumpLevel, UtilityHelper.getUpgradeLevel(ring2, "JumpBoost"));
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
			if (ring1 != null && ring1.getItem() instanceof ItemRing)
			{
				swiftnessLevel = UtilityHelper.getUpgradeLevel(ring1, "Swiftness");
				strengthLevel = UtilityHelper.getUpgradeLevel(ring1, "Strength");
			}
			if (ring2 != null && ring2.getItem() instanceof ItemRing)
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
		if (ring1 != null && ring1.getItem() instanceof ItemRing) hasteLevel = UtilityHelper.getUpgradeLevel(ring1, "Haste");
		if (ring2 != null && ring2.getItem() instanceof ItemRing) hasteLevel = Math.max(hasteLevel, UtilityHelper.getUpgradeLevel(ring2, "Haste"));
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
			if (ring1 != null && ring1.getItem() instanceof ItemRing) jumpLevel = UtilityHelper.getUpgradeLevel(ring1, "JumpBoost");
			if (ring2 != null && ring2.getItem() instanceof ItemRing) jumpLevel = Math.max(jumpLevel, UtilityHelper.getUpgradeLevel(ring2, "JumpBoost"));
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
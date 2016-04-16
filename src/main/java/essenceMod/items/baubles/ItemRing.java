package essenceMod.items.baubles;

import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class ItemRing extends ItemBauble
{
	public int level, cooldown;
	public static final int numSubTypes = 19;
	
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
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < numSubTypes; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelBakery.registerItemVariants(this, new ModelResourceLocation(getRegistryName(), "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		for (int i = 1; i < numSubTypes; i++)
		{
			ModelBakery.registerItemVariants(this, new ModelResourceLocation(getRegistryName() + "-" + i, "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(getRegistryName() + "-" + i, "inventory"));
		}
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.RING;
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		int swiftness = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness);
		int haste = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionHaste);
		int strength = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength);
		int jumpBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionJumpBoost);
		int regeneration = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration);
		int nightVision = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision);
		int waterBreathing = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing);
		int fireResistance = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance);
		
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
			if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision) != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
		}

		if (player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			IAttributeInstance attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness) != 0)
				{
					attribute.removeModifier(speed);
				}
			}
			attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
			if (attribute != null)
			{
				if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength) != 0)
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
		NBTTagCompound compound = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
		compound.setInteger("Level", 0);
		item.setTagCompound(compound);
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

		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration) != 0)
		{
			if (cooldown != 0) cooldown--;
			else if (cooldown == 0)
			{
				player.heal(player.getMaxHealth() * 0.05F);
				cooldown = 60 / ((ItemRing) item.getItem()).getLevel(item);
			}
		}
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision) != 0) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0));
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing) != 0) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance) != 0) player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0));
	}

	public int getLevel(ItemStack item)
	{
		return item.getTagCompound().getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (!item.hasTagCompound()) onCreated(item, entityPlayer.worldObj, entityPlayer);
		
		int swiftness = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionSwiftness);
		int haste = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionHaste);
		int strength = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionStrength);
		int jumpBoost = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionJumpBoost);
		int regeneration = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionRegeneration);
		int nightVision = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionNightVision);
		int waterBreathing = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionWaterBreathing);
		int fireResistance = Upgrade.getUpgradeLevel(item, UpgradeRegistry.RingPotionFireResistance);
		
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
			if (ring1 != null && ring1.getItem() instanceof ItemRing) jumpLevel = Upgrade.getUpgradeLevel(ring1, UpgradeRegistry.RingPotionJumpBoost);
			if (ring2 != null && ring2.getItem() instanceof ItemRing) jumpLevel = Math.max(jumpLevel, Upgrade.getUpgradeLevel(ring2, UpgradeRegistry.RingPotionJumpBoost));
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
				swiftnessLevel = Upgrade.getUpgradeLevel(ring1, UpgradeRegistry.RingPotionSwiftness);
				strengthLevel = Upgrade.getUpgradeLevel(ring1, UpgradeRegistry.RingPotionStrength);
			}
			if (ring2 != null && ring2.getItem() instanceof ItemRing)
			{
				swiftnessLevel = Math.max(swiftnessLevel, Upgrade.getUpgradeLevel(ring2, UpgradeRegistry.RingPotionSwiftness));
				strengthLevel = Math.max(strengthLevel, Upgrade.getUpgradeLevel(ring2, UpgradeRegistry.RingPotionStrength));
			}
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
		if (ring1 != null && ring1.getItem() instanceof ItemRing) hasteLevel = Upgrade.getUpgradeLevel(ring1, UpgradeRegistry.RingPotionHaste);
		if (ring2 != null && ring2.getItem() instanceof ItemRing) hasteLevel = Math.max(hasteLevel, Upgrade.getUpgradeLevel(ring2, UpgradeRegistry.RingPotionHaste));
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
			if (ring1 != null && ring1.getItem() instanceof ItemRing) jumpLevel = Upgrade.getUpgradeLevel(ring1, UpgradeRegistry.RingPotionJumpBoost);
			if (ring2 != null && ring2.getItem() instanceof ItemRing) jumpLevel = Math.max(jumpLevel, Upgrade.getUpgradeLevel(ring2, UpgradeRegistry.RingPotionJumpBoost));
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
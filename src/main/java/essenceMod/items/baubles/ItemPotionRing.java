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
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemPotionRing extends ItemBauble
{
	public int level, cooldown;
	public Effect type;
	public IIcon[] icons = new IIcon[19];
	
	private final AttributeModifier speed = new AttributeModifier(UUID.fromString("91AEAA56-376B-4498-935B-2F7F68070635"), "EssenceArmoryRingSpeed", 0.2D, 2);

	private enum Effect
	{
		NONE,
		SWIFTNESS,
		HASTE,
		STRENGTH,
		JUMP,
		REGENERATION,
		SPECIAL;
	}
	
	
	public ItemPotionRing()
	{
		this(0, Effect.NONE);
	}
	

	public ItemPotionRing(int level, Effect effect)
	{
		this.level = level;
		this.type = effect;
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < 19; i++)
			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
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
		for (int i = 0; i < 19; i++)
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
			if (item.stackTagCompound.getInteger("PotionID") == Potion.nightVision.id) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1, 1));
		}

		if (player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			UUID playerID = p.getGameProfile().getId();
			IAttributeInstance attribute = p.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (type == Effect.SWIFTNESS)
				{
					attribute.removeModifier(speed);
				}
			}
		}
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		if (type == Effect.NONE)
			switch (item.getItemDamage())
			{
				case 0:
					type = Effect.NONE;
					break;
				case 1:
				case 2:
				case 3:
					type = Effect.SWIFTNESS;
					break;
				case 4:
				case 5:
				case 6:
					type = Effect.HASTE;
					break;
				case 7:
				case 8:
				case 9:
					type = Effect.STRENGTH;
					break;
				case 10:
				case 11:
				case 12:
					type = Effect.JUMP;
					break;
				case 13:
				case 14:
				case 15:
					type = Effect.REGENERATION;
					break;
				case 16:
				case 17:
				case 18:
					type = Effect.SPECIAL;
					break;
			}
		if (level == 0) level = (item.getItemDamage() + 2) % 3 + 1;
		if (type == Effect.NONE) level = 0; 
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setInteger("Type", type.ordinal());
	}

	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);

		int level = item.stackTagCompound.getInteger("Level");
		int type = item.stackTagCompound.getInteger("Type");
		if (type == Effect.REGENERATION.ordinal())
		{
			if (cooldown != 0) cooldown--;
			else if (cooldown == 0)
			{
				player.heal(1);
				cooldown = 60 / ((ItemPotionRing) item.getItem()).getLevel(item);
			}
		}
		if (type == Effect.SPECIAL.ordinal())
		{
			if (level == 1) player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0));
			if (level == 2) player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 0));
			if (level == 3) player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0));
		}
	}

	public int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	public int getType(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Type");
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool)
	{
		String info = "";
		int level = 0;
		if (itemStack.stackTagCompound == null) onCreated(itemStack, entityPlayer.worldObj, entityPlayer);

		if (itemStack.stackTagCompound.hasKey("Level"))
		{
			level = itemStack.stackTagCompound.getInteger("Level");
			info = UtilityHelper.toRoman(level);
		}

		if (itemStack.stackTagCompound.hasKey("PotionID"))
		{
			int type = itemStack.stackTagCompound.getInteger("Type");
			String potionName;
			switch (type)
			{
				case 1:
					potionName = "Swiftness";
					break;
				case 2:
					potionName = "Haste";
					break;
				case 3:
					potionName = "Strength";
					break;
				case 4:
					potionName = "Jump Boost";
					break;
				case 5:
					potionName = "Regeneration";
					break;
				case 6:
					if (level == 1) potionName = "Fire Resistance";
					else if (level == 2) potionName = "Water Breathing";
					else if (level == 3) potionName = "Night Vision";
					else potionName = "No Effect";
					break;
				default:
					potionName = "No Effect";
			}
			info = potionName + " " + info;
		}
		list.add(info);
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int strengthLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
			{
				int potionID = ((ItemPotionRing) ring1.getItem()).getType(ring1);
				if (potionID == Potion.damageBoost.id) strengthLevel = ((ItemPotionRing) ring1.getItem()).getLevel(ring1);
			}
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
			{
				int potionID = ((ItemPotionRing) ring2.getItem()).getType(ring2);
				if (potionID == Potion.damageBoost.id) strengthLevel = Math.max(strengthLevel, ((ItemPotionRing) ring2.getItem()).getLevel(ring2));
			}
			event.ammount += event.ammount * strengthLevel / 40.0F;
		}
		if (event.entityLiving instanceof EntityPlayer && event.source.getDamageType() == event.source.fall.getDamageType())
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int jumpLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring1.getItem()).getType(ring1);
				if (type == Effect.JUMP.ordinal()) jumpLevel = ((ItemPotionRing) ring1.getItem()).getLevel(ring1);
			}
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring2.getItem()).getType(ring2);
				if (type == Effect.JUMP.ordinal()) jumpLevel = Math.max(jumpLevel, ((ItemPotionRing) ring2.getItem()).getLevel(ring2));
			}
			event.ammount -= jumpLevel / 20;
		}
	}

	@SubscribeEvent
	public void updatePlayerSwiftness(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack ring1 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(1);
			ItemStack ring2 = PlayerHandler.getPlayerBaubles(player).getStackInSlot(2);
			int swiftnessLevel = 0;
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring1.getItem()).getType(ring1);
				if (type == Effect.SWIFTNESS.ordinal()) swiftnessLevel = ((ItemPotionRing) ring1.getItem()).getLevel(ring1);
			}
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring2.getItem()).getType(ring2);
				if (type == Effect.SWIFTNESS.ordinal()) swiftnessLevel = Math.max(swiftnessLevel, ((ItemPotionRing) ring2.getItem()).getLevel(ring2));
			}
			UUID playerID = player.getGameProfile().getId();
			IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			if (attribute != null)
			{
				if (type == Effect.SWIFTNESS)
				{
					attribute.removeModifier(speed);
					attribute.applyModifier(new AttributeModifier(speed.getID(), speed.getName() + swiftnessLevel, speed.getAmount() * swiftnessLevel, speed.getOperation()));
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
		if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
		{
			int type = ((ItemPotionRing) ring1.getItem()).getType(ring1);
			if (type == Effect.HASTE.ordinal()) hasteLevel = ((ItemPotionRing) ring1.getItem()).getLevel(ring1);
		}
		if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
		{
			int type = ((ItemPotionRing) ring2.getItem()).getType(ring2);
			if (type == Effect.HASTE.ordinal()) hasteLevel = Math.max(hasteLevel, ((ItemPotionRing) ring2.getItem()).getLevel(ring2));
		}
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
			if (ring1 != null && ring1.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring1.getItem()).getType(ring1);
				if (type == Effect.JUMP.ordinal()) jumpLevel = ((ItemPotionRing) ring1.getItem()).getLevel(ring1);
			}
			if (ring2 != null && ring2.getItem() instanceof ItemPotionRing)
			{
				int type = ((ItemPotionRing) ring2.getItem()).getType(ring2);
				if (type == Effect.JUMP.ordinal()) jumpLevel = Math.max(jumpLevel, ((ItemPotionRing) ring2.getItem()).getLevel(ring2));
			}
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
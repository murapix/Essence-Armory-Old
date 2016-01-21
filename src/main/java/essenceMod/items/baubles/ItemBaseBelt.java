package essenceMod.items.baubles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import tconstruct.util.Reference;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Upgrade;
import essenceMod.utility.UtilityHelper;

public class ItemBaseBelt extends ItemBauble
{
	int level;
	public IIcon[] icons = new IIcon[17];
	
	private final AttributeModifier health = new AttributeModifier(UUID.fromString("BD4FE64C-9E37-4391-9D21-88F273020B0F"), "EssenceArmoryHealthBoost", 1D, 2);
	
	public ItemBaseBelt()
	{
		this(0);
	}
	
	public ItemBaseBelt(int level)
	{
		super();
		this.level = level;
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		icons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + getUnlocalizedName().substring(5));
		for (int i = 1; i < icons.length; i++)
			icons[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + getUnlocalizedName().substring(5) + "-" + i);
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
	
	@Override
	public String getUnlocalizedName(ItemStack item)
	{
		return this.getUnlocalizedName() + ":" + item.getItemDamage();
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.BELT;
	}
	
	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		int meta = item.getItemDamage();
		item.stackTagCompound.setInteger("Level", 0);
		if (meta == 0) return;
		else if (meta <= 5) InfuserRecipes.addLevel(item, new Upgrade("BeltCleave", meta));
		else if (meta <= 10) InfuserRecipes.addLevel(item, new Upgrade("BeltKnockback", meta - 5));
		else if (meta <= 15) InfuserRecipes.addLevel(item, new Upgrade("BeltHealth", meta - 10));
		else if (meta == 16) InfuserRecipes.addLevel(item, new Upgrade("BeltMiningLimit", 1));
	}
	
	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		if (item.stackTagCompound == null) onCreated(item, player.worldObj, (EntityPlayer) player);
		int cooldown = item.stackTagCompound.getInteger("Cooldown");
		cooldown = (cooldown > 0) ? cooldown - 1 : 0;
		item.stackTagCompound.setInteger("Cooldown", cooldown);
	}
	
	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);
		if (player instanceof EntityPlayer)
		{
			IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
			if (attribute != null)
				attribute.removeModifier(health);
		}
	}
	
	@SubscribeEvent
	public void updatePlayerHealth(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack belt = PlayerHandler.getPlayerBaubles(player).getStackInSlot(3);
			if (belt != null && belt.getItem() instanceof ItemBaseBelt)
			{
				IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
				if (attribute != null)
				{
					int level = UtilityHelper.getUpgradeLevel(belt, "BeltHealth");
					if (level == 0) return;
					double current = attribute.getAttributeValue();
					double next = health.getAmount() * level * current * 0.1;
					attribute.removeModifier(health);
					attribute.applyModifier(new AttributeModifier(health.getID(), health.getName() + level, next, health.getOperation()));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttack(AttackEntityEvent event)
	{
		EntityPlayer attacker = event.entityPlayer;
		ItemStack belt = PlayerHandler.getPlayerBaubles(attacker).getStackInSlot(3);
		if (belt != null && belt.getItem() instanceof ItemBaseBelt)
		{
			int level = UtilityHelper.getUpgradeLevel(belt, "BeltCleave");
			if (level != 0 && event.target instanceof EntityLivingBase)
			{
				EntityLivingBase target = (EntityLivingBase) event.target;
				World world = target.worldObj;
				AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(target.posX - level, target.posY - 1, target.posZ - level, target.posX + level, target.posY + 1, target.posZ + level);
				List list;
				if (target instanceof EntityMob)
					list = world.getEntitiesWithinAABB(EntityMob.class, axis);
				else if (target instanceof EntityAmbientCreature)
					list = world.getEntitiesWithinAABB(EntityAmbientCreature.class, axis);
				else if (target instanceof EntityPlayer)
					list = world.getEntitiesWithinAABB(EntityPlayer.class, axis);
				else
					list = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
				list.remove(attacker);
				list.remove(target);
				
				ItemStack weapon = attacker.getCurrentEquippedItem();
				double damage = 0;
				Iterator iterator = weapon.getItem().getAttributeModifiers(weapon).get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName()).iterator();
				while (iterator.hasNext())
					damage += ((AttributeModifier) iterator.next()).getAmount();
				for (Object obj : list)
				{
					EntityLivingBase entity = (EntityLivingBase) obj;
					double xDiff = Math.abs(target.posX - entity.posX);
					double zDiff = Math.abs(target.posZ - entity.posZ);
					int distance = MathHelper.floor_double(Math.max(xDiff, zDiff));
					entity.attackEntityFrom(DamageSource.causePlayerDamage(attacker), (float) damage * 0.2F * (level - distance));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakSpeed event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack belt = PlayerHandler.getPlayerBaubles(player).getStackInSlot(3);
		if (belt != null && belt.getItem() instanceof ItemBaseBelt && UtilityHelper.getUpgradeLevel(belt, "BeltMiningLimit") != 0)
		{
			float hardness = event.block.getBlockHardness(player.worldObj, event.x, event.y, event.z);
			event.newSpeed *= Math.min(event.newSpeed, hardness * 30 / 2);
		}
	}
	
	public static void knockback(ItemStack item, EntityPlayer player, float fallDistance)
	{
		int cooldown = item.stackTagCompound.getInteger("Cooldown");
		player.addChatMessage(new ChatComponentText("Cooldown: " + cooldown));
		if (cooldown == 0)
		{
			int strength = UtilityHelper.getUpgradeLevel(item, "BeltKnockback");
			float distance = (float) Math.pow(fallDistance, 0.625);
			AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(player.posX - strength, player.posY, player.posZ - strength, player.posX + strength, player.posY + 1, player.posZ + strength);
			List list = player.worldObj.getEntitiesWithinAABB(EntityMob.class, axis);
			ArrayList<EntityMob> mobs = new ArrayList<EntityMob>();
			mobs.addAll(list);
			for (EntityMob mob : mobs)
			{
				float angle = (float) Math.atan2(mob.posZ - player.posZ, mob.posX - player.posX);
				mob.moveEntity(mob.motionX, mob.motionY, mob.motionZ);
				mob.setVelocity(distance * MathHelper.cos(angle), 0, distance * MathHelper.sin(angle));
				mob.knockBack(player, 0, player.posX - mob.posX, player.posZ - mob.posZ);
				item.stackTagCompound.setInteger("Cooldown", 600);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		String info = "";
		int level = 0;
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);

		level = item.stackTagCompound.getInteger("Level");

		list.add(info);
	}
}

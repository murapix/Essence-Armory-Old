package essenceMod.items.baubles;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemCleaveBelt extends ItemBauble
{
	int level;

	public ItemCleaveBelt()
	{
		this(0);
	}

	public ItemCleaveBelt(int level)
	{
		super();
		this.level = level;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		item.stackTagCompound.setInteger("Level", level);
	}

	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.BELT;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool)
	{
		if (!item.hasTagCompound()) onCreated(item, player.worldObj, player);
		if (item.stackTagCompound.hasKey("Level"))
		{
			int level = item.stackTagCompound.getInteger("Level");
			list.add("Level: " + item.stackTagCompound.getInteger("Level"));
			if (level != 0) list.add("Attacks deal damage to all enemies within " + level + ((level == 1) ? " block" : " blocks") + ", decreasing based on distance");
		}
	}

	@SubscribeEvent
	public void onPlayerAttack(AttackEntityEvent event)
	{
		EntityLivingBase target = (EntityLivingBase) event.target;
		World world = target.worldObj;
		EntityPlayer attacker = event.entityPlayer;
		ItemStack belt = PlayerHandler.getPlayerBaubles(attacker).getStackInSlot(3);
		if (belt != null && belt.getItem() instanceof ItemCleaveBelt)
		{
			int level = ItemCleaveBelt.getLevel(belt);
			if (level == 0) return;
			AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(target.posX - level, target.posY - 1, target.posZ - level, target.posX + level, target.posY + 1, target.posZ + level);
			List list;
			if (target instanceof EntityMob)
			{
				list = world.getEntitiesWithinAABB(EntityMob.class, axis);
				list.remove(target);
				list.remove(attacker);
			}
			else if (target instanceof EntityAmbientCreature)
			{
				list = world.getEntitiesWithinAABB(EntityAmbientCreature.class, axis);
				list.remove(target);
				list.remove(attacker);
			}
			else if (target instanceof EntityPlayer)
			{
				list = world.getEntitiesWithinAABB(EntityPlayer.class, axis);
				list.remove(target);
				list.remove(attacker);
			}
			else
			{
				list = world.getEntitiesWithinAABB(EntityLivingBase.class, axis);
				list.remove(target);
				list.remove(attacker);
			}
			ItemStack weapon = attacker.getCurrentEquippedItem();
			double damage = 0;
			Iterator iterator = weapon.getItem().getAttributeModifiers(weapon).get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName()).iterator();
			while (iterator.hasNext())
			{
				damage += ((AttributeModifier) iterator.next()).getAmount();
			}
			for (Object obj : list)
			{
				EntityLivingBase entity = (EntityLivingBase) obj;
				double xDiff = Math.abs(target.posX - entity.posX);
				double zDiff = Math.abs(target.posZ - entity.posZ);
				int distance = MathHelper.floor_double(Math.max(xDiff, zDiff));
				entity.attackEntityFrom(DamageSource.causePlayerDamage(attacker), (float) damage * 0.2F * (5 - distance));
			}
		}
	}
}

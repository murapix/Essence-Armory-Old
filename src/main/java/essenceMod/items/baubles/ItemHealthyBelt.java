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
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import essenceMod.utility.Reference;

public class ItemHealthyBelt extends ItemBauble
{
	int level;
	public IIcon[] icons = new IIcon[5];
	
	private final AttributeModifier health = new AttributeModifier(UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC"), "EssenceArmoryVambraceHealth", 1D, 0);
	
	public ItemHealthyBelt()
	{
		this(0);
	}
	
	public ItemHealthyBelt(int level)
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
		for (int i = 0; i < 5; i++)
		{
			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta > 5) meta = 0;
		return icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < 5; i++)
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
		if (level == 0)
			level = item.getItemDamage();
		item.stackTagCompound.setInteger("Level", level);
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (itemStack.stackTagCompound == null) onCreated(itemStack, entityPlayer.worldObj, entityPlayer);
		list.add("Increases heart count by " + itemStack.stackTagCompound.getInteger("Level") * 10 + "%");
	}
	
	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);
		if (player instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) player;
			UUID playerID = p.getGameProfile().getId();
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
			if (belt != null && belt.getItem() instanceof ItemHealthyBelt)
			{
				UUID playerID = player.getGameProfile().getId();
				IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
				if (attribute != null)
				{
					attribute.removeModifier(health);
					double current = attribute.getAttributeValue();
					double next = health.getAmount() * level * current * 0.1;
					attribute.applyModifier(new AttributeModifier(health.getID(), health.getName() + level, next, health.getOperation()));
				}
			}
		}
	}
}
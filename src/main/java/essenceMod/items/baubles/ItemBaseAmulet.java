package essenceMod.items.baubles;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import tconstruct.util.Reference;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Upgrade;
import essenceMod.utility.UtilityHelper;

public class ItemBaseAmulet extends ItemBauble
{
	public int level;
	public IIcon[] icons = new IIcon[21];
	
	public ItemBaseAmulet()
	{
		this(0);
	}
	
	public ItemBaseAmulet(int level)
	{
		super();
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.level = level;
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
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		item.stackTagCompound.setInteger("Level", 0);
		int meta = item.getItemDamage();
		if (meta == 0) return;
		else if (meta <= 5) InfuserRecipes.addLevel(item, new Upgrade("AmuletPoisonImmunity", meta));
		else if (meta <= 10) InfuserRecipes.addLevel(item, new Upgrade("AmuletWitherImmunity", meta - 5));
		else if (meta <= 15) InfuserRecipes.addLevel(item, new Upgrade("AmuletFireImmunity", meta - 10));
		else if (meta <= 20) InfuserRecipes.addLevel(item, new Upgrade("AmuletLooting", meta - 15));
		else InfuserRecipes.addLevel(item, new Upgrade("AmuletFlight", meta - 20));
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}
	
	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
	
	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);
		EntityPlayer p = (EntityPlayer) player;
		if (UtilityHelper.getUpgradeLevel(item, "AmuletFlight") != 0) p.capabilities.allowFlying = true;
	}
	
	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);
		EntityPlayer p = (EntityPlayer) player;
		if (UtilityHelper.getUpgradeLevel(item, "AmuletFlight") != 0) p.capabilities.allowFlying = false;
	}
	
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
		if (amulet != null && amulet.getItem() instanceof ItemBaseAmulet && UtilityHelper.getUpgradeLevel(amulet, "AmuletFlight") != 0) player.capabilities.allowFlying = true;
	}
	
	@SubscribeEvent
	public void onPotionDamage(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
			if (amulet != null && amulet.getItem() instanceof ItemBaseAmulet)
			{
				int level = Math.min(this.getLevel(amulet), 5);
				int poison = UtilityHelper.getUpgradeLevel(amulet, "AmuletPoisonImmunity");
				int wither = UtilityHelper.getUpgradeLevel(amulet, "AmuletWitherImmunity");
				int fire = UtilityHelper.getUpgradeLevel(amulet, "AmuletFireImmunity");
				float damageReduction = Math.min(level * 0.25F, 1F);
				float healAmount = Math.max((level - 4) * 0.25F, 0F);
				if (poison != 0 && event.source.isMagicDamage())
				{
					event.ammount -= damageReduction;
					player.heal(healAmount);
				}
				else if (wither != 0 && event.source.equals(DamageSource.wither))
				{
					event.ammount -= damageReduction;
					player.heal(healAmount);
				}
				else if (fire != 0 && event.source.isFireDamage())
				{
					event.ammount -= damageReduction;
					player.heal(healAmount);
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		int level = 0;
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);

		level = item.stackTagCompound.getInteger("Level");
		list.add("Level " + UtilityHelper.toRoman(level));
		
		int flight = UtilityHelper.getUpgradeLevel(item, "AmuletFlight");
		int looting = UtilityHelper.getUpgradeLevel(item, "AmuletLooting");
		int poison = UtilityHelper.getUpgradeLevel(item, "AmuletPoisonImmunity");
		int wither = UtilityHelper.getUpgradeLevel(item, "AmuletWitherImmunity");
		int fire = UtilityHelper.getUpgradeLevel(item, "AmuletFireImmunity");
		
		if (flight != 0) list.add("Archangel's Wings");
		if (looting != 0) list.add("Essence Extractor " + UtilityHelper.toRoman(looting));
		if (poison != 0) list.add("Poison Infusion " + UtilityHelper.toRoman(poison));
		if (wither != 0) list.add("Wither Infusion " + UtilityHelper.toRoman(wither));
		if (fire != 0) list.add("Fire Infusion " + UtilityHelper.toRoman(fire));
	}
}

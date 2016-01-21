package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.handlers.ConfigHandler;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModBow extends ItemBow implements IModItem
{
	private IIcon[] iconArray;
	
	int level;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (item.stackTagCompound == null) onCreated(item, world, (EntityPlayer) entity);
		int level = item.stackTagCompound.getInteger("Level");
	}
	
	public ItemModBow()
	{
		super();
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		level = 0;
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		if (item.stackTagCompound == null) item.setTagCompound(new NBTTagCompound());
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setBoolean("ItemInUse", false);
		item.addEnchantment(ModArmory.shardLooter, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_standby");
		iconArray = new IIcon[bowPullIconNameArray.length];
		
		for (int i = 0; i < iconArray.length; ++i)
		{
			iconArray[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_" + bowPullIconNameArray[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int duration)
	{
		return iconArray[duration];
	}
	
	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);
		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));
	}
	
	private List addNormalInfo(ItemStack item)
	{
		List list = new ArrayList();
		
		
		
		return list;
	}
	
	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();
		
		
		
		return list;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, item);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return event.result;
        
        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow)) player.setItemInUse(item, this.getMaxItemUseDuration(item));

        return item;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int itemInUseCount)
	{
		int bowCharge = getMaxItemUseDuration(item) - itemInUseCount;
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, item, bowCharge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		bowCharge = event.charge;
		
		boolean freeArrow = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		
		if (freeArrow || player.inventory.hasItem(Items.arrow))
		{
			float adjustedCharge = (float) bowCharge / 20.0F;
			adjustedCharge = (adjustedCharge * adjustedCharge + adjustedCharge * 2.0F) / 3.0F * (1 + UtilityHelper.getUpgradeLevel(item, "BowDrawSpeed") * 0.5F);
			if ((double) adjustedCharge < 0.1D) return;
			float chargeCutoff = (float) (1 + UtilityHelper.getUpgradeLevel(item, "BowArrowSpeed") * 0.05F);
			if (adjustedCharge > chargeCutoff) adjustedCharge = chargeCutoff;
			
			EntityArrow entityArrow = new EntityArrow(world, player, adjustedCharge * 2.0F);
			if (adjustedCharge == chargeCutoff) entityArrow.setIsCritical(true);
			
			entityArrow.setDamage(entityArrow.getDamage() * (1 + UtilityHelper.getUpgradeLevel(item, "BowPhysicalDamage") * ConfigHandler.normalDamageMulti * ConfigHandler.normalBowMulti));
			
			int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);
			if (power > 0) entityArrow.setDamage(entityArrow.getDamage() + (double) power * 0.5D + 0.5D);
			
			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);
			if (punch > 0) entityArrow.setKnockbackStrength(punch);
			
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0) entityArrow.setFire(100);
			
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + adjustedCharge * 0.5F);
			
			if (freeArrow) entityArrow.canBePickedUp = 2;
			else player.inventory.consumeInventoryItem(Items.arrow);
			
			if (!world.isRemote) world.spawnEntityInWorld(entityArrow);
		}
		
		item.stackTagCompound.setBoolean("ItemInUse", false);
	}
}
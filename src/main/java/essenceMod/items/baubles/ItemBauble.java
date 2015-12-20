package essenceMod.items.baubles;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.items.ItemMod;
import essenceMod.utility.Reference;

public abstract class ItemBauble extends ItemMod implements IBauble
{
	public ItemBauble()
	{
		super();
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}
	
	@Override
	public boolean canEquip(ItemStack item, EntityLivingBase player)
	{
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack item, EntityLivingBase player)
	{
		return true;
	}
	
	@Override
	public void onEquipped(ItemStack item, EntityLivingBase player)
	{}
	
	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{}
	
	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		if (!item.hasTagCompound()) onCreated(item, player.worldObj, (EntityPlayer) player);
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (!item.hasTagCompound()) onCreated(item, world, (EntityPlayer) entity);
	}
}

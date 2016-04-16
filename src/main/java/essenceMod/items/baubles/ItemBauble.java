package essenceMod.items.baubles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import baubles.api.IBauble;
import essenceMod.items.ItemMod;

public abstract class ItemBauble extends ItemMod implements IBauble
{
	public ItemBauble()
	{
		super();
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
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

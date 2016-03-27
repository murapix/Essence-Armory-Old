//package essenceMod.items.travellersGear;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.Optional;
//import essenceMod.items.ItemMod;
//
//@Optional.Interface(iface = "ITravellersGear", modid = "TravellersGear")
//public abstract class ItemTravellersGear extends ItemMod implements ITravellersGear
//{
//	public ItemTravellersGear()
//	{
//		super();
//		this.setMaxStackSize(1);
//	}
//	
//	@Override
//	public void onCreated(ItemStack item, World world, EntityPlayer player)
//	{
//		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
//	}
//	
//	@Override
//	@Optional.Method(modid = "TravellersGear")
//	public void onTravelGearTick(EntityPlayer player, ItemStack item)
//	{
//		if (!item.hasTagCompound()) onCreated(item, player.worldObj, player);
//	}
//	
//	@Override
//	@Optional.Method(modid = "TravellersGear")
//	public void onTravelGearEquip(EntityPlayer player, ItemStack item)
//	{}
//	
//	@Override
//	@Optional.Method(modid = "TravellersGear")
//	public void onTravelGearUnequip(EntityPlayer player, ItemStack item)
//	{}
//	
//	@Override
//	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
//	{
//		if (!item.hasTagCompound()) onCreated(item, world, (EntityPlayer) entity);
//	}
//}

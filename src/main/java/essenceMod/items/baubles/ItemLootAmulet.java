package essenceMod.items.baubles;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.help.Reference;
import essenceMod.items.ItemMod;

public class ItemLootAmulet extends ItemBauble
{
	int level;
	
	public ItemLootAmulet()
	{
		this(0);
	}
	
	public ItemLootAmulet(int level)
	{
		super();
		this.level = level;
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		item.stackTagCompound.setInteger("Level", level);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack item)
	{
		return BaubleType.AMULET;
	}
	
	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool)
	{
		if (item.stackTagCompound == null) onCreated(item, player.worldObj, player);
		if (item.stackTagCompound.hasKey("Level")) list.add("Level: " + item.stackTagCompound.getInteger("Level"));
	}
}
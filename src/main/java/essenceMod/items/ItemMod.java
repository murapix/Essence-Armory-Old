package essenceMod.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;

public class ItemMod extends Item implements IUpgradeable
{
	public ItemMod()
	{
		super();
		setCreativeTab(ModTabs.tabEssence);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister icon)
	{
		itemIcon = icon.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}
}
package essenceMod.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Optional;
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		itemIcon = icon.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}
}
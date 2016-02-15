package essenceMod.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.registry.ModItems;

public class ModTab extends CreativeTabs
{
	String name;
	
	public ModTab(int par1, String name)
	{
		super(par1, name);
		this.name = name;
	}
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		if (this.name == "tabEssence")
		{
			return ModItems.infusedIngot;
		}
		return null;
	}
}
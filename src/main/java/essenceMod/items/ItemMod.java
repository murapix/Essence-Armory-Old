package essenceMod.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.tabs.ModTabs;

public class ItemMod extends Item implements IUpgradeable
{
	public ItemMod()
	{
		super();
		setCreativeTab(ModTabs.tabEssence);
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
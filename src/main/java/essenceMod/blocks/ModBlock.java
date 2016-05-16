package essenceMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.tabs.ModTabs;

public class ModBlock extends Block
{
	public ModBlock()
	{
		super(Material.iron);
		setCreativeTab(ModTabs.tabEssence);
		setStepSound(soundTypeMetal);
		setHardness(5.0F);
		setResistance(10.0F);
		setLightLevel(1.0F);
		setHarvestLevel("pickaxe", 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state)
	{
		return getDefaultState();
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
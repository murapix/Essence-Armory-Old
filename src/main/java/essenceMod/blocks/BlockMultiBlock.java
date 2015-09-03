package essenceMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.tabs.ModTabs;

public class BlockMultiBlock extends BlockContainer
{
	public BlockMultiBlock(Material material)
	{
		super(material);
		setCreativeTab(ModTabs.tabEssence);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityEssenceInfuser)
		{
			TileEntityEssenceInfuser multiBlock = (TileEntityEssenceInfuser) tile;
			if (!multiBlock.checkMultiBlockForm()) multiBlock.resetStructure();
			else
			{
				multiBlock.reset();
			}
		}
		super.onNeighborBlockChange(world, x, y, z, block);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return null;
	}
}
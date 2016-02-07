package essenceMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import essenceMod.items.IUpgradeable;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;

public class ModBlock extends Block implements IUpgradeable
{
	public ModBlock(String blockName)
	{
		super(Material.iron);
		setBlockName(blockName);
		setBlockTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
		setCreativeTab(ModTabs.tabEssence);
		setStepSound(soundTypeMetal);
		setHardness(5.0F);
		setResistance(10.0F);
		setLightLevel(1.0F);
		setHarvestLevel("pickaxe", 3);
	}
	
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
}
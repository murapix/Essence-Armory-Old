package essenceMod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.help.Reference;
import essenceMod.tabs.ModTabs;

public class ModBlock extends Block
{
	public ModBlock()
	{
		super(Material.iron);
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
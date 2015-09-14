package essenceMod.init;

import net.minecraft.block.Block;
import essenceMod.blocks.EssenceInfuser;
import essenceMod.blocks.EssencePylon;
import essenceMod.blocks.ModBlock;
import essenceMod.help.RegisterHelper;

public class ModBlocks
{
	public static Block infusedBlock = new ModBlock().setBlockName("infusedBlock");
	public static Block infusedStarmetal = new ModBlock().setBlockName("infusedStarmetal");
	public static Block essenceInfuser = new EssenceInfuser().setBlockName("essenceInfuser");
	public static Block essencePylon = new EssencePylon().setBlockName("essencePylon");
	
	public static void init()
	{
		RegisterHelper.registerBlock(infusedBlock);
		RegisterHelper.registerBlock(essenceInfuser);
		RegisterHelper.registerBlock(essencePylon);
		RegisterHelper.registerBlock(infusedStarmetal);
	}
}

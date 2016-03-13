package essenceMod.registry;

import net.minecraft.block.Block;
import essenceMod.blocks.EssenceInfuser;
import essenceMod.blocks.EssencePylon;
import essenceMod.blocks.ModBlock;
import essenceMod.utility.RegisterHelper;

public class ModBlocks
{
	public static Block infusedBlock = new ModBlock("infusedBlock");
	public static Block infusedStarmetal = new ModBlock("infusedStarmetal");
	public static Block shardBlock = new ModBlock("shardBlock");
	public static Block essenceInfuser = new EssenceInfuser().setBlockName("essenceInfuser");
	public static Block essencePylon = new EssencePylon().setBlockName("essencePylon");
	
	public static void init()
	{
		RegisterHelper.registerBlock(infusedBlock);
		RegisterHelper.registerBlock(essenceInfuser);
		RegisterHelper.registerBlock(shardBlock);
		RegisterHelper.registerBlock(essencePylon);
		RegisterHelper.registerBlock(infusedStarmetal);
	}
}

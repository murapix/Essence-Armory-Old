package essenceMod.init;

import net.minecraft.block.Block;
import essenceMod.blocks.EssenceInfuser;
import essenceMod.blocks.EssencePylon;
import essenceMod.blocks.ModBlock;
import essenceMod.blocks.ModBlock2;
import essenceMod.help.RegisterHelper;

public class ModBlocks
{
	public static Block infusedBlock = new ModBlock();
	public static Block infusedStarmetal = new ModBlock2();
	public static Block essenceInfuser = new EssenceInfuser();
	public static Block essencePylon = new EssencePylon();
	
	public static void init()
	{
		RegisterHelper.registerBlock(infusedBlock);
		RegisterHelper.registerBlock(essenceInfuser);
		RegisterHelper.registerBlock(essencePylon);
		RegisterHelper.registerBlock(infusedStarmetal);
	}
}

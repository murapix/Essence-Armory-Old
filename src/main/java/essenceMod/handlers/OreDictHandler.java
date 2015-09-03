package essenceMod.handlers;

import net.minecraftforge.oredict.OreDictionary;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModItems;

public class OreDictHandler
{
	public OreDictHandler()
	{
		OreDictionary.registerOre("nuggetEssenceInfused", ModItems.infusedNugget);
		OreDictionary.registerOre("ingotEssenceInfused", ModItems.infusedIngot);
		OreDictionary.registerOre("blockEssenceInfused", ModBlocks.infusedBlock);
		OreDictionary.registerOre("blockInfusedStarmetal", ModBlocks.infusedStarmetal);
		OreDictionary.registerOre("appleEssenceInfused", ModItems.infusedApple);
	}
}

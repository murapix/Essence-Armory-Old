package essenceMod.help;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.handlers.TileEntityEssenceInfuserRenderer;
import essenceMod.handlers.TileEntityEssencePylonRenderer;

public class RegisterHelper
{
	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, Reference.MODID + block.getUnlocalizedName().substring(5));
	}
	
	public static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, Reference.MODID + item.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenderThings()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssenceInfuser.class, new TileEntityEssenceInfuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssencePylon.class, new TileEntityEssencePylonRenderer());
	}
}
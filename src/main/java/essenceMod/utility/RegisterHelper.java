package essenceMod.utility;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.blocks.models.InfuserRenderer;
import essenceMod.blocks.models.PylonRenderer;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;

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
		InfuserRenderer infuser = new InfuserRenderer();
		PylonRenderer pylon = new PylonRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssenceInfuser.class, infuser);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssencePylon.class, pylon);
		
	}
}
package essenceMod.init;

import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.help.Reference;
import essenceMod.help.RegisterHelper;

public class ModTileEntity
{
	public static void init()
	{
		RegisterHelper.registerRenderThings();
		GameRegistry.registerTileEntity(TileEntityEssenceInfuser.class, Reference.MODID + ":TileEntityEssenceInfuser");
		GameRegistry.registerTileEntity(TileEntityEssencePylon.class, Reference.MODID + ":TileEntityEssencePylon");
	}
}

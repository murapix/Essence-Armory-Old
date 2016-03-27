package essenceMod.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.utility.Reference;

public class ModTileEntity
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityEssenceInfuser.class, Reference.MODID + ":TileEntityEssenceInfuser");
		GameRegistry.registerTileEntity(TileEntityEssencePylon.class, Reference.MODID + ":TileEntityEssencePylon");
	}
}

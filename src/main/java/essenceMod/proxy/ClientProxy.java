package essenceMod.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.blocks.models.BlockItemRenderer;
import essenceMod.blocks.models.InfuserRenderer;
import essenceMod.blocks.models.PylonRenderer;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.handlers.compatibility.nei.NEIHandler;
import essenceMod.registry.ModBlocks;

public class ClientProxy extends CommonProxy
{
	@Override
	@SideOnly(Side.CLIENT)
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		InfuserRenderer infuser = new InfuserRenderer();
		PylonRenderer pylon = new PylonRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssenceInfuser.class, infuser);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssencePylon.class, pylon);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.essenceInfuser), new BlockItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.essencePylon), new BlockItemRenderer());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
		
		registerNEIStuff();
	}
	
	@Optional.Method(modid = "NotEnoughItems")
	public void registerNEIStuff()
	{
		NEIHandler.init();
	}
}

package essenceMod.proxy;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.blocks.models.InfuserRenderer;
import essenceMod.blocks.models.PylonRenderer;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModItems;
import essenceMod.utility.Reference;

public class ClientProxy extends CommonProxy
{
	@Override
	@SideOnly(Side.CLIENT)
	public void Init(FMLInitializationEvent event)
	{
		super.Init(event);
		
		OBJLoader.instance.addDomain(Reference.MODID.toLowerCase());
		InfuserRenderer infuser = new InfuserRenderer();
		PylonRenderer pylon = new PylonRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssenceInfuser.class, infuser);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssencePylon.class, pylon);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.essenceInfuser), 0, new ModelResourceLocation(ModBlocks.essenceInfuser.getRegistryName()));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.essencePylon), 0, new ModelResourceLocation(ModBlocks.essencePylon.getRegistryName()));
		
		ModItems.initItemRenderers();
		ModBlocks.initItemRenderers();
		ModArmory.initItemRenderers();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);

		if (Loader.isModLoaded("NotEnoughItems"))
		{
			try
			{
				registerNEIStuff();
			}
			catch (Exception e)
			{}
		}
	}

	@Optional.Method(modid = "NotEnoughItems")
	public void registerNEIStuff()
	{
//		NEIHandler.init();
	}
}

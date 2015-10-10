package essenceMod;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Recipes;
import essenceMod.handlers.EssenceEventHandler;
import essenceMod.handlers.TConstructHandler;
import essenceMod.help.Reference;
import essenceMod.init.ModArmory;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModEntities;
import essenceMod.init.ModItems;
import essenceMod.init.ModTileEntity;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
dependencies = "required-after:Forge@[1.7.10-10.13.2.1291,);" +
		"required-after:Baubles@[1.7.10-1.0.1.10,);" +
		"after:TConstruct@[1.7.10-1.8.7,);")
public class EssenceMod
{
	public static EssenceMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModItems.init();
		ModBlocks.init();
		ModArmory.init();
		ModEntities.init();
		ModTileEntity.init();
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Recipes.init();
		InfuserRecipes.init();
		if (Loader.isModLoaded("TConstruct"))
		{
			try
			{
				TConstructHandler.init();
			}
			catch (Exception e){}
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		EssenceEventHandler.postinit();
	}
}
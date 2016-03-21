package essenceMod.proxy;

import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import essenceMod.handlers.CommandUpgrade;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.EssenceEventHandler;
import essenceMod.handlers.OreDictHandler;
import essenceMod.handlers.compatibility.TConstructHandler;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModEntities;
import essenceMod.registry.ModItems;
import essenceMod.registry.ModTileEntity;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.Recipes;
import essenceMod.registry.crafting.upgrades.UpgradeCraftingRecipe;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.initProps(event.getSuggestedConfigurationFile());
		ModItems.init();
		ModBlocks.init();
		ModArmory.init();
		ModEntities.init();
		ModTileEntity.init();
		EssenceEventHandler.preinit();
		OreDictHandler.preInit();
	}
	
	public void Init(FMLInitializationEvent event)
	{
		InfuserRecipes.init();
		Recipes.init();
		if (Loader.isModLoaded("TConstruct") && ConfigHandler.ticoIntegration)
		{
			try
			{
				TConstructHandler.init();
			}
			catch (Exception e){}
		}	
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		// Do Nothing
	}
	
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandUpgrade());
	}
}

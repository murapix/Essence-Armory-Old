package essenceMod.handlers.compatibility.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import essenceMod.utility.Reference;

public class NEIHandler implements IConfigureNEI
{
	public static void init()
	{
		API.registerGuiOverlay(GuiNEI.class, "crafting");
		API.registerGuiOverlayHandler(GuiNEI.class, new DefaultOverlayHandler(), "crafting");
		API.registerRecipeHandler(new RecipeHandlerInfuser());
		API.registerUsageHandler(new RecipeHandlerInfuser());
	}
	
	@Override
	public String getName()
	{
		return Reference.NAME;
	}

	@Override
	public String getVersion()
	{
		return Reference.VERSION;
	}

	@Override
	public void loadConfig()
	{
		API.registerRecipeHandler(new RecipeHandlerInfuser());
		API.registerUsageHandler(new RecipeHandlerInfuser());
	}
}

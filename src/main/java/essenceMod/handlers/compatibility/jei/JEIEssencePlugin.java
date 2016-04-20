package essenceMod.handlers.compatibility.jei;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.UpgradeRecipe;

@JEIPlugin
public class JEIEssencePlugin implements IModPlugin
{
	@Override
	public void register(@Nonnull IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		registry.addRecipeCategories(new InfuserUpgradeRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeHandlers(new InfuserUpgradeRecipeHandler());
		
		ArrayList<UpgradeRecipe> upgradeRecipes = new ArrayList<UpgradeRecipe>();
		for (Class c : InfuserRecipes.upgradeRecipes.keySet())
			upgradeRecipes.addAll(InfuserRecipes.upgradeRecipes.get(c));
		registry.addRecipes(upgradeRecipes);
	}
	
	@Override
	public void onItemRegistryAvailable(IItemRegistry arg0)
	{}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers arg0)
	{}

	@Override
	public void onRecipeRegistryAvailable(IRecipeRegistry arg0)
	{}

	@Override
	public void onRuntimeAvailable(IJeiRuntime arg0)
	{}
}
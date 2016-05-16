package essenceMod.handlers.compatibility.jei.upgrade;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import essenceMod.registry.crafting.upgrades.UpgradeRecipe;

public class InfuserUpgradeRecipeHandler implements IRecipeHandler<UpgradeRecipe>
{
	@Override
	public Class<UpgradeRecipe> getRecipeClass()
	{
		return UpgradeRecipe.class;
	}
	
	@Override
	public String getRecipeCategoryUid()
	{
		return "essenceArmory.infuserUpgrade";
	}
	
	@Override
	public IRecipeWrapper getRecipeWrapper(UpgradeRecipe recipe)
	{
		return new InfuserUpgradeRecipeWrapper(recipe);
	}
	
	@Override
	public boolean isRecipeValid(UpgradeRecipe recipe)
	{
		return true;
	}
}
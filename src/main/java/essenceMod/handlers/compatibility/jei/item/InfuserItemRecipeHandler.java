package essenceMod.handlers.compatibility.jei.item;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import essenceMod.registry.crafting.ItemRecipe;

public class InfuserItemRecipeHandler implements IRecipeHandler<ItemRecipe>
{
	@Override
	public Class<ItemRecipe> getRecipeClass()
	{
		return ItemRecipe.class;
	}
	
	@Override
	public String getRecipeCategoryUid()
	{
		return "essenceArmory.infuserItem";
	}
	
	@Override
	public IRecipeWrapper getRecipeWrapper(ItemRecipe recipe)
	{
		return new InfuserItemRecipeWrapper(recipe);
	}
	
	@Override
	public boolean isRecipeValid(ItemRecipe recipe)
	{
		return true;
	}
}

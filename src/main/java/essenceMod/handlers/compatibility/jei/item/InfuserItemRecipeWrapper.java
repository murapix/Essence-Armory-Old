package essenceMod.handlers.compatibility.jei.item;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import com.google.common.collect.ImmutableList;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.ItemRecipe;

public class InfuserItemRecipeWrapper implements IRecipeWrapper
{
	private ItemStack item;
	private ArrayList input;
	private ItemStack output;

	public InfuserItemRecipeWrapper(ItemRecipe recipe)
	{
		input = new ArrayList();
		for (Object o : recipe.getItems())
		{
			if (o instanceof ItemStack) input.add(o);
			if (o instanceof String) input.add(OreDictionary.getOres((String) o));
		}
		input.add(new ItemStack(ModItems.infusedShard, recipe.getShardCount()));
		item = recipe.getCenter();
		output = recipe.getOutput();
	}
	
	@Override
	public List getInputs()
	{
		input.add(0, item);
		return input;
	}

	@Override
	public List<ItemStack> getOutputs()
	{
		return ImmutableList.of(output);
	}

	@Override
	public List<FluidStack> getFluidInputs()
	{
		return ImmutableList.of();
	}

	@Override
	public List<FluidStack> getFluidOutputs()
	{
		return ImmutableList.of();
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{}
	
	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight)
	{}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return ImmutableList.of();
	}
	
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton)
	{
		return false;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight)
	{}
}

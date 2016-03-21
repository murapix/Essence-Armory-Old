package essenceMod.handlers.compatibility.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRecipe;
import essenceMod.utility.Reference;

public class RecipeHandlerInfuser extends TemplateRecipeHandler
{
	public class CachedInfuserRecipe extends CachedRecipe
	{
		public ArrayList<PositionedStack> inputs = new ArrayList<PositionedStack>();
		public PositionedStack upgradeReqs;
		public PositionedStack output;

		public CachedInfuserRecipe(UpgradeRecipe recipe)
		{
			ItemStack out = recipe.getItem().copy();
			ItemStack in = recipe.getItem().copy();
			InfuserRecipes.addUpgrade(out, recipe.getUpgrade());
			for (Upgrade upgrade : recipe.getRequirements())
				InfuserRecipes.addUpgrade(in, upgrade);

			setIngredients(recipe.getRecipeItems());

			upgradeReqs = new PositionedStack(in, 55, 55);
			inputs.add(upgradeReqs);
			output = new PositionedStack(out, 118, 55);
		}

		public List<PositionedStack> setIngredients(ArrayList<ItemStack> inputs)
		{
			float degreePerInput = 360F / inputs.size();
			float currentDegree = -90F;

			for (ItemStack item : inputs)
			{
				int posX = (int) Math.round(55 + Math.cos(currentDegree * Math.PI / 180D) * 36);
				int posY = (int) Math.round(55 + Math.sin(currentDegree * Math.PI / 180D) * 36);

				this.inputs.add(new PositionedStack(item, posX, posY));
				currentDegree += degreePerInput;
			}
			return this.inputs;
		}

		@Override
		public List<PositionedStack> getIngredients()
		{
			return this.getCycledIngredients(cycleticks / 20, inputs);
		}

		@Override
		public PositionedStack getResult()
		{
			return output;
		}
	}

	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal(Reference.MODID + ".nei.infuser");
	}

	@Override
	public String getGuiTexture()
	{
		return Reference.MODID + ":textures/gui/infuser.png";
	}
	
	@Override
	public void drawBackground(int recipe)
	{
		super.drawBackground(recipe);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(new ResourceLocation(Reference.MODID + ":textures/gui/infuserOverlay.png"));
		GuiDraw.drawTexturedModalRect(2, 2, 0, 0, 156, 146);
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	public List<? extends UpgradeRecipe> getRecipes()
	{
		ArrayList<UpgradeRecipe> recipes = new ArrayList<UpgradeRecipe>();
		for (Class cls : InfuserRecipes.upgradeRecipes.keySet())
		{
			recipes.addAll(InfuserRecipes.upgradeRecipes.get(cls));
		}
		return recipes;
	}

	public CachedInfuserRecipe getCachedRecipe(UpgradeRecipe recipe)
	{
		return new CachedInfuserRecipe(recipe);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if ((outputId.equals("item") || outputId.equals(Reference.MODID + ".nei.infuser")) && (results.length > 0)) loadCraftingRecipes((ItemStack) results[0]);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		for (UpgradeRecipe recipe : getRecipes())
		{
			if (recipe == null) continue;
			if (InfuserRecipes.getCurrentUpgrades(result).contains(recipe.getUpgrade())) arecipes.add(getCachedRecipe(recipe));
		}
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new RecipeTransferRect(new Rectangle(46, 46, 36, 9), Reference.MODID + ".infuser"));
		transferRects.add(new RecipeTransferRect(new Rectangle(46, 55, 9, 18), Reference.MODID + ".infuser"));
		transferRects.add(new RecipeTransferRect(new Rectangle(46, 73, 36, 9), Reference.MODID + ".infuser"));
		transferRects.add(new RecipeTransferRect(new Rectangle(73, 55, 9, 18), Reference.MODID + ".infuser"));
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients)
	{
		if (ingredients.length > 0 && ingredients[0] instanceof ItemStack) loadUsageRecipes((ItemStack) ingredients[0]);
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		for (UpgradeRecipe recipe : getRecipes())
		{
			if (recipe == null) continue;
			CachedInfuserRecipe crecipe = getCachedRecipe(recipe);
			if (crecipe.contains(crecipe.inputs, ingredient)) arecipes.add(crecipe);
			else if (InfuserRecipes.getCurrentUpgrades(ingredient).containsAll(recipe.getRequirements())) arecipes.add(getCachedRecipe(recipe));
		}
	}
}
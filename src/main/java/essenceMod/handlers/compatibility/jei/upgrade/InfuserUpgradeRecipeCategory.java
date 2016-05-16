package essenceMod.handlers.compatibility.jei.upgrade;

import java.util.Collection;
import java.util.List;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import essenceMod.utility.Reference;

public class InfuserUpgradeRecipeCategory implements IRecipeCategory
{
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable overlay;
	
	public InfuserUpgradeRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.createBlankDrawable(150, 110);
		localizedName = StatCollector.translateToLocal("EssenceArmory.nei.infuser.upgrade");
		overlay = guiHelper.createDrawable(new ResourceLocation(Reference.MODID + ":textures/gui/infuserOverlay.png"), 0, 0, 156, 122);
	}
	
	@Override
	public String getUid()
	{
		return "essenceArmory.infuserUpgrade";
	}
	
	@Override
	public String getTitle()
	{
		return localizedName;
	}
	
	@Override
	public IDrawable getBackground()
	{
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft)
	{
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		overlay.draw(minecraft);
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
	}
	
	@Override
	public void drawAnimations(Minecraft minecraft){}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper)
	{
		if (!(recipeWrapper instanceof InfuserUpgradeRecipeWrapper)) return;
		InfuserUpgradeRecipeWrapper wrapper = (InfuserUpgradeRecipeWrapper) recipeWrapper;
		
		List inputs = wrapper.getInputs();
		recipeLayout.getItemStacks().init(0, true, 52, 52);
		recipeLayout.getItemStacks().set(0, (ItemStack) inputs.get(0));
		
		float degreePerInput = 360F / (inputs.size() - 1);
		float currentDegree = -90F;
		
		int i;
		for (i = 1; i < inputs.size(); i++)
		{
			int posX = (int) Math.round(52 + Math.cos(currentDegree * Math.PI / 180D) * 36);
			int posY = (int) Math.round(52 + Math.sin(currentDegree * Math.PI / 180D) * 36);
			recipeLayout.getItemStacks().init(i, true, posX, posY);
			Object o = inputs.get(i);
			if (o instanceof Collection) recipeLayout.getItemStacks().set(i, (Collection<ItemStack>) o);
			if (o instanceof ItemStack) recipeLayout.getItemStacks().set(i, (ItemStack) o);
			currentDegree += degreePerInput;
		}
		recipeLayout.getItemStacks().init(i, false, 115, 52);
		recipeLayout.getItemStacks().set(i, wrapper.getOutputs().get(0));
	}
}
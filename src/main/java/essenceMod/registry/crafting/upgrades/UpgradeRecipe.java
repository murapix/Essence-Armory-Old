package essenceMod.registry.crafting.upgrades;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UpgradeRecipe
{
	private ItemStack item;
	private int slot;
	private Upgrade upgrade;
	private ArrayList<Upgrade> requirements = new ArrayList<Upgrade>();
	private ArrayList<ItemStack> recipeItems = new ArrayList<ItemStack>();
	
	public UpgradeRecipe(ItemStack item, Upgrade upgrade, Upgrade[] requirements, Object... recipeItems)
	{
		this(item, -1, upgrade, requirements, recipeItems);
	}
	
	public UpgradeRecipe(ItemStack item, int itemSlot, Upgrade upgrade, Upgrade[] requirements, Object... recipeItems)
	{
		this.item = item.copy();
		this.slot = itemSlot;
		this.upgrade = upgrade;
		for (Upgrade requirement : requirements)
			this.requirements.add(requirement);
		for (Object stack : recipeItems)
		{
			if (stack == null) continue;
			else if (stack instanceof ItemStack) this.recipeItems.add(((ItemStack) stack).copy());
			else if (stack instanceof Item) this.recipeItems.add(new ItemStack((Item) stack));
			else if (stack instanceof Block) this.recipeItems.add(new ItemStack((Block) stack));
		}
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public int getSlot()
	{
		return slot;
	}
	
	public Upgrade getUpgrade()
	{
		return upgrade;
	}
	
	public ArrayList<Upgrade> getRequirements()
	{
		return requirements;
	}
	
	public ArrayList<ItemStack> getRecipeItems()
	{
		return recipeItems;
	}
}

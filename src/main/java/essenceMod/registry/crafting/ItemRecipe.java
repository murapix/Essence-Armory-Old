package essenceMod.registry.crafting;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRecipe
{
	private ItemStack center;
	private int shardCount;
	private ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	private ItemStack output;
	
	public ItemRecipe(ItemStack output, int shardCount, ItemStack center, Object... items)
	{
		this.center = center;
		this.shardCount = shardCount;
		this.output = output;
		for (Object stack : items)
		{
			if (stack == null) continue;
			else if (stack instanceof ItemStack) this.items.add((ItemStack) stack);
			else if (stack instanceof Item) this.items.add(new ItemStack((Item) stack));
			else if (stack instanceof Block) this.items.add(new ItemStack((Block) stack));
		}
	}
	
	public ItemStack getCenter()
	{
		return center;
	}
	
	public int getShardCount()
	{
		return shardCount;
	}
	
	public ItemStack getOutput()
	{
		return output;
	}
	
	public ArrayList<ItemStack> getItems()
	{
		return items;
	}
}

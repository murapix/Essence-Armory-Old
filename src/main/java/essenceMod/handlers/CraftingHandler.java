package essenceMod.handlers;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import essenceMod.crafting.InfusionRecipe;
import essenceMod.init.ModArmory;
import essenceMod.init.ModItems;
import essenceMod.items.baubles.ItemPotionRing;

public class CraftingHandler
{
	private static final CraftingHandler instance = new CraftingHandler();
	
	private List recipes = new ArrayList();
	
	private ItemStack item;
	
	public static final CraftingHandler getInstance()
	{
		return instance;
	}
	
	private CraftingHandler()
	{
		int[] potionID = new int[] {0};
		item = new ItemStack(ModArmory.infusedPotionRing);
		addRecipe(new ItemStack(new ItemPotionRing(1, Potion.damageBoost.id)), new ItemStack(new ItemPotionRing()), new Object[] {"AAAA", "BCBCBCBC", 'A', ModItems.infusedDiamond, 'B', new ItemStack(Items.potionitem, 1, Potion.damageBoost.id), 'C', ModItems.infusedIngot});
	}
	
	public void addRecipe(ItemStack output, ItemStack input, Object ... resources)
	{
		ArrayList<ItemStack> fuelList = new ArrayList<ItemStack>();
		ArrayList<ItemStack> resourceList = new ArrayList<ItemStack>();
		Object[] objArray = resources;
		
		for (int i = 0; i < resources.length; i++)
		{
			Object object = objArray[i];
			
			if (object instanceof ItemStack)
			{
				fuelList.add(((ItemStack) object).copy());
			}
			else if (object instanceof Item)
			{
				fuelList.add(new ItemStack((Item) object));
			}
			else
			{
				if (!(object instanceof Block))
				{
					throw new RuntimeException("Invalid infusion recipy!");
				}
				fuelList.add(new ItemStack((Block) object));
			}
		}
		objArray = new Object[fuelList.size()];
		objArray = fuelList.toArray();
		fuelList.clear();
		for (int i = 0; i < 4; i++) fuelList.add((ItemStack) objArray[i]);
		for (int i = 4; i < 12; i++) resourceList.add((ItemStack) objArray[i]);
		recipes.add(new InfusionRecipe(output, input, fuelList, resourceList));
	}
}
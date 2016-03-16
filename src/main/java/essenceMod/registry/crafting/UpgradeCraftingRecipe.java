package essenceMod.registry.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import essenceMod.items.upgrades.Upgrade;

public class UpgradeCraftingRecipe implements IRecipe
{
	private ItemStack output;
	private Object[] input = null;
	private int width = 0;
	private int height = 0;

	public UpgradeCraftingRecipe(ItemStack output, Object... recipe)
	{
		this.output = output;
		
		String shape = "";
		int idx = 0;
		
		if (recipe[idx] instanceof String[])
		{
			String[] parts = ((String[]) recipe[idx++]);
			
			for (String s : parts)
			{
				width = s.length();
				shape += s;
			}
			
			height = parts.length;
		}
		else
		{
			while (recipe[idx] instanceof String)
			{
				String s = (String) recipe[idx++];
				shape += s;
				width = s.length();
				height++;
			}
		}
		
		if (width * height != shape.length())
		{
			String ret = "Invalid shaped upgrade recipe: ";
			for (Object tmp : recipe) ret += tmp + ", ";
			ret += output;
			throw new RuntimeException(ret);
		}
		
		HashMap<Character, Object> itemMap = new HashMap<Character, Object>();
		
		for (; idx < recipe.length; idx += 2)
		{
			Character chr = (Character) recipe[idx];
			Object in = recipe[idx + 1];
			
			if (in instanceof ItemStack) itemMap.put(chr, ((ItemStack) in).copy());
			else if (in instanceof Item) itemMap.put(chr, new ItemStack((Item) in));
			else if (in instanceof Block) itemMap.put(chr, new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE));
			else if (in instanceof String) itemMap.put(chr, OreDictionary.getOres((String) in));
			else
			{
				String ret = "Invalid shaped upgrade recipe: ";
				for (Object tmp : recipe)
				{
					ret += tmp + ", ";
				}
				ret += output;
				throw new RuntimeException(ret);
			}
		}
		
		input = new Object[width * height];
		int x = 0;
		for (char chr : shape.toCharArray())
		{
			input[x++] = itemMap.get(chr);
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		for (int i = 0; i <= 3 - width; i++)
		{
			for (int j = 0; j <= 3 - height; j++)
			{
				if (checkMatch(inv, i, j, true)) return true;
				if (checkMatch(inv, i, j, false)) return true;
			}
		}
		return false;
	}

	private boolean checkMatch(InventoryCrafting inv, int initX, int initY, boolean mirror)
	{
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				int subX = x - initX;
				int subY = y - initY;
				Object target = null;

				if (subX >= 0 && subY >= 0 && subX < width && subY < height)
				{
					if (mirror) target = input[width - subX - 1 + subY * width];
					else target = input[subX + subY * width];
				}

				ItemStack item = inv.getStackInRowAndColumn(x, y);

				if (target instanceof ItemStack)
				{
					if (!OreDictionary.itemMatches((ItemStack) target, item, false)) return false;
					ItemStack temp = (ItemStack) target;
					ArrayList<Upgrade> itemUpgrades = InfuserRecipes.getCurrentUpgrades(item);
					ArrayList<Upgrade> tempUpgrades = InfuserRecipes.getCurrentUpgrades(temp);
					if (!itemUpgrades.containsAll(tempUpgrades)) return false;
				}
				else if (target instanceof ArrayList)
				{
					boolean matched = false;

					Iterator<ItemStack> iterator = ((ArrayList<ItemStack>) target).iterator();
					while (iterator.hasNext() && !matched)
						matched = OreDictionary.itemMatches(iterator.next(), item, false);
					if (!matched) return false;
				}
				else if (target == null && item != null) return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack item = getRecipeOutput().copy();
		
		NBTTagCompound compound = new NBTTagCompound();
		if (getRecipeOutput().hasTagCompound()) compound = getRecipeOutput().getTagCompound();

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack temp = inv.getStackInSlot(i);

			if (temp != null && temp.hasTagCompound())
			{
				compound.setTag("ench", temp.getEnchantmentTagList());
			}
		}

		item.setTagCompound(compound);
		return item;
	}

	@Override
	public int getRecipeSize()
	{
		return width * height;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}
}

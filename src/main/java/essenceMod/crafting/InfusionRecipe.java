package essenceMod.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;

public class InfusionRecipe implements IRecipe
{
	private final ItemStack output;
	public final ItemStack input;
	public final List fuel;
	public final List resources;
	
	public InfusionRecipe(ItemStack output, ItemStack input, List fuel, List resources)
	{
		this.output = output;
		this.input = input;
		this.fuel = fuel;
		this.resources = resources;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}
	
	@Override
	public boolean matches(InventoryCrafting table, World world)
	{
		return false;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting table)
	{
		return output.copy();
	}
	
	@Override
	public int getRecipeSize()
	{
		return fuel.size() + resources.size() + input.stackSize;
	}
	
	public boolean matches(TileEntityEssenceInfuser infuser, World world)
	{
		ArrayList<ItemStack> fuelArray = new ArrayList<ItemStack>(fuel);
		ArrayList<ItemStack> resourceArray = new ArrayList<ItemStack>(resources);
		
		ArrayList<ItemStack> infuserFuel = new ArrayList<ItemStack>(infuser.getInnerPylonItems());
		ArrayList<ItemStack> infuserResources = new ArrayList<ItemStack>(infuser.getOuterPylonItems());
		
		while (fuelArray.size() > 0 && infuserFuel.size() > 0)
		{
			if (!infuserFuel.remove(fuelArray.remove(0))) return false;
		}
		if (fuelArray.size() != infuserFuel.size()) return false;
		
		while (resourceArray.size() > 0 && infuserResources.size() > 0)
		{
			if (!resourceArray.remove(infuserResources.remove(0))) return false;
		}
		if (resourceArray.size() != infuserResources.size()) return false;
		
		if (!input.isItemEqual(infuser.inv));
		
		return true;
	}
}
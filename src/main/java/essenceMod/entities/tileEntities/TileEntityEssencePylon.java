package essenceMod.entities.tileEntities;

import java.util.ArrayList;
import essenceMod.utility.Reference;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TileEntityEssencePylon extends TileEntityEssenceInfuser implements IInventory
{	
	public TileEntityEssencePylon()
	{
		super();
		slots = new ItemStack[1];
	}
	
	@Override
	public void updateEntity()
	{}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return true;
	}
	
	@Override
	public String getInventoryName()
	{
		return Reference.MODID + ".TileEntityEssencePylon";
	}
	
	public void infuse()
	{
		slots[0] = null;
	}
}
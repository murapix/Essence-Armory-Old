package essenceMod.entities.tileEntities;

import java.util.ArrayList;
import essenceMod.utility.Reference;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TileEntityEssencePylon extends TileEntityEssenceInfuser implements IInventory
{	
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
}
package essenceMod.entities.tileEntities;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import essenceMod.utility.Reference;

public class TileEntityEssencePylon extends TileEntityEssenceInfuser implements IInventory, ITickable
{	
	public TileEntityEssencePylon()
	{
		super();
		slots = new ItemStack[1];
	}
	
	@Override
	public void update()
	{
		worldObj.markBlockForUpdate(pos);
		markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return true;
	}
	
	@Override
	public String getName()
	{
		return Reference.MODID + ".TileEntityEssencePylon";
	}
	
	public void infuse()
	{
		slots[0] = null;
		worldObj.markBlockForUpdate(pos);
		markDirty();
	}
}
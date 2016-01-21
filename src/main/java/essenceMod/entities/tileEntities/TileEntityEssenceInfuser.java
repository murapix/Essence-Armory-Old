package essenceMod.entities.tileEntities;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;
import scala.actors.threadpool.Arrays;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Upgrade;
import essenceMod.items.IModItem;
import essenceMod.utility.Reference;

public class TileEntityEssenceInfuser extends TileEntity implements IInventory
{
	public static final int INNER_SLOT_COUNT = 4;
	public static final int OUTER_SLOT_COUNT = 8;
	public static final int TOTAL_SLOT_COUNT = INNER_SLOT_COUNT + OUTER_SLOT_COUNT + 1;

	public static final int INFUSER_SLOT = 0;
	public static final int FIRST_INNER_SLOT = INFUSER_SLOT + 1;
	public static final int FIRST_OUTER_SLOT = FIRST_INNER_SLOT + INNER_SLOT_COUNT;

	protected ItemStack[] slots = new ItemStack[TOTAL_SLOT_COUNT];

	private ArrayList<TileEntity> pylons;

	public int infuseTime;
	public static final short TOTAL_INFUSE_TIME = 200;

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (checkPylons())
		{
			if (canInfuse())
			{
				infuseTime++;

				if (infuseTime >= TOTAL_INFUSE_TIME)
				{
					infuseItem();
					infuseTime = 0;
				}
			}
			else infuseTime = 0;
		}
	}

	private boolean checkPylons()
	{
		pylons = new ArrayList<TileEntity>();

		pylons.add(worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord));
		pylons.add(worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord));
		pylons.add(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1));
		pylons.add(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1));

		pylons.add(worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord));
		pylons.add(worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord));
		pylons.add(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3));
		pylons.add(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3));

		pylons.add(worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord + 2));
		pylons.add(worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord - 2));
		pylons.add(worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord + 2));
		pylons.add(worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord - 2));

		if (pylons.size() != 12) return false;
		for (TileEntity pylon : pylons)
		{
			if (pylon == null) return false;
			if (!(pylon instanceof TileEntityEssencePylon)) return false;
		}

		return true;
	}

	private void grabPylons()
	{
		if (pylons.size() != 12) return;
		for (TileEntity pylon : pylons)
		{
			if (pylon == null) return;
			if (!(pylon instanceof TileEntityEssencePylon)) return;
		}

		for (int i = 0; i < pylons.size(); i++)
		{
			slots[i + 1] = ((TileEntityEssencePylon) pylons.get(i)).getStackInSlot(0);
		}
	}

	private void updatePylons()
	{
		if (pylons.size() != 12) return;
		for (TileEntity pylon : pylons)
		{
			if (pylon == null) return;
			if (!(pylon instanceof TileEntityEssencePylon)) return;
		}

		for (int i = 0; i < pylons.size(); i++)
		{
			((TileEntityEssencePylon) pylons.get(i)).slots[0] = slots[i + 1];
		}
	}

	private boolean canInfuse()
	{
		grabPylons();
		return infuseItem(false);
	}

	private void infuseItem()
	{
		infuseItem(true);
	}

	private boolean infuseItem(boolean performInfusion)
	{
		for (int slot = INFUSER_SLOT; slot < INFUSER_SLOT + TOTAL_SLOT_COUNT; slot++)
		{
			if (slots[slot] == null) return false;
		}
		if (performInfusion)
		{
			Upgrade result = InfuserRecipes.checkRecipe(slots[INFUSER_SLOT], getInnerItems(), getOuterItems());
			for (int slot = FIRST_INNER_SLOT; slot < INFUSER_SLOT + TOTAL_SLOT_COUNT; slot++)
			{
				slots[slot].stackSize--;
				if (slots[slot].stackSize <= 0) slots[slot] = null;
			}
			InfuserRecipes.addLevel(slots[INFUSER_SLOT], result);
			updatePylons();
		}
		return true;
	}

	private ArrayList<ItemStack> getInnerItems()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = FIRST_INNER_SLOT; i < FIRST_INNER_SLOT + INNER_SLOT_COUNT; i++)
			items.add(getStackInSlot(i));
		return items;
	}

	private ArrayList<ItemStack> getOuterItems()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = FIRST_OUTER_SLOT; i < FIRST_OUTER_SLOT + OUTER_SLOT_COUNT; i++)
			items.add(getStackInSlot(i));
		return items;
	}

	@Override
	public int getSizeInventory()
	{
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack item = getStackInSlot(index);
		if (item == null) return null;

		ItemStack temp;
		if (item.stackSize <= count)
		{
			temp = item;
			setInventorySlotContents(index, null);
		}
		else
		{
			temp = item.splitStack(count);
			if (temp.stackSize == 0) setInventorySlotContents(index, null);
		}
		return temp;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack item)
	{
		slots[index] = item;
		if (item != null && item.stackSize > getInventoryStackLimit()) item.stackSize = getInventoryStackLimit();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) return false;
		double xOffset = 0.5;
		double yOffset = 0.5;
		double zOffset = 0.5;
		double maxDistance = 8.0 * 8.0;
		return player.getDistanceSq(xCoord - xOffset, yCoord - yOffset, zCoord - zOffset) < maxDistance;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		NBTTagList inventory = new NBTTagList();
		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i] != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				slots[i].writeToNBT(tag);
				inventory.appendTag(tag);
			}
		}

		tagCompound.setTag("Items", inventory);

		tagCompound.setInteger("InfuseTime", infuseTime);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		NBTTagList inventory = tagCompound.getTagList("Items", NBT.TAG_COMPOUND);

		Arrays.fill(slots, null);
		for (int i = 0; i < inventory.tagCount(); i++)
		{
			NBTTagCompound item = inventory.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < slots.length) slots[slot] = ItemStack.loadItemStackFromNBT(item);
		}

		infuseTime = tagCompound.getInteger("InfuseTime");
	}

	@Override
	public String getInventoryName()
	{
		return Reference.MODID + ".TileEntityEssenceInfuser";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack item)
	{
		if (index == INFUSER_SLOT) return item != null && item.getItem() instanceof IModItem;
		return false;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack item = getStackInSlot(index);
		if (item != null) setInventorySlotContents(index, null);
		return item;
	}

	@Override
	public void openInventory()
	{}

	@Override
	public void closeInventory()
	{}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}
}
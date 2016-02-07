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
import essenceMod.crafting.upgrades.Upgrade;
import essenceMod.items.IUpgradeable;
import essenceMod.utility.Reference;

public class TileEntityEssenceInfuser extends TileEntity implements IInventory
{
	public static final int InfuserSlotCount = 1;
	public static final int PylonSlotCount = 120;
	public static final int TotalSlotCount = InfuserSlotCount + PylonSlotCount;

	public static final int InfuserSlot = 0;
	public static final int FirstInnerSlot = InfuserSlot + InfuserSlotCount;

	protected ItemStack[] slots = new ItemStack[TotalSlotCount];

	private ArrayList<TileEntity> pylons;

	public int infuseTime;
	public static final short TotalInfuseTime = 200;

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		checkPylons();
		grabPylons();
		
		Upgrade upgrade = InfuserRecipes.checkRecipe(slots[InfuserSlot], getPylonItems());
		if (upgrade != null)
		{
			infuseTime++;
			if (infuseTime >= TotalInfuseTime)
			{
				updatePylons();
				InfuserRecipes.addUpgrade(slots[InfuserSlot], upgrade);
				infuseTime = 0;
			}
		}
		else infuseTime = 0;
	}

	private boolean checkPylons()
	{
		pylons = new ArrayList<TileEntity>();
		
		for (int xDiff = -5; xDiff <= 5; xDiff++)
		{
			for (int zDiff = -5; zDiff <= 5; zDiff++)
			{
				TileEntity entity = worldObj.getTileEntity(xCoord + xDiff, yCoord - 1, zCoord + zDiff);
				if (entity != null && entity instanceof TileEntityEssencePylon) pylons.add(entity);
			}
		}
		for (int i = 0; i < pylons.size(); i++)
		{
			if (((TileEntityEssencePylon)pylons.get(i)).slots[0] == null)
			{
				pylons.remove(i);
				i--;
			}
		}
		return pylons.size() >= 1;
	}

	private void grabPylons()
	{
		for (int i = 0; i < pylons.size(); i++)
		{
			if (pylons.get(i) == null || !(pylons.get(i) instanceof TileEntityEssencePylon))
			{
				pylons.remove(i);
				i--;
			}
			else slots[i + 1] = ((TileEntityEssencePylon) pylons.get(i)).getStackInSlot(0);
		}
	}

	private void updatePylons()
	{
		for (int i = 0; i < pylons.size(); i++)
		{
			if (pylons.get(i) == null || !(pylons.get(i) instanceof TileEntityEssencePylon))
			{
				pylons.remove(i);
				i--;
			}
			else ((TileEntityEssencePylon) pylons.get(i)).infuse();
		}
	}
	
	private ArrayList<ItemStack> getPylonItems()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = FirstInnerSlot; i < TotalSlotCount; i++)
		{
			ItemStack item = getStackInSlot(i);
			if (item != null) items.add(item);
		}
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
		if (index == InfuserSlot) return item != null && item.getItem() instanceof IUpgradeable;
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
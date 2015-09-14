package essenceMod.entities.tileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import essenceMod.help.Reference;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModItems;
import essenceMod.items.IModItem;
import essenceMod.items.ItemModSword;

public class TileEntityEssenceInfuser extends TileEntity implements IInventory
{
	public boolean multiblock = false;
	private boolean active = false;
	private int ticksRemaining;
	private static final int infuseDuration = 200;

	public ItemStack inv;

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			if (multiblock)
			{
				if (ticksRemaining > 0)
				{
					multiblock = checkMultiBlockForm();
					if (!multiblock || !active)
					{
						ticksRemaining = 0;
						active = false;
						return;
					}
					else ticksRemaining--;
				}
				else if (ticksRemaining == 0 && active)
				{
					if (inv.getItem() instanceof ItemModSword)
					{
						active = false;
					}
				}
			}
		}
	}

	public void activate()
	{
		ticksRemaining = infuseDuration;
		active = true;
	}

	public boolean isReady(EntityPlayer player)
	{
		return false;
		// return hasFuel(getInnerPylonItems(), player) &&
		// hasResource(getOuterPylonItems(), player) && hasUpgradeableItem(inv);
	}

	protected boolean isFuel(ItemStack item)
	{
		if (item == null || item.stackSize == 0) return false;
		if (item.isItemEqual(new ItemStack(Items.coal))) return true;
		if (item.isItemEqual(new ItemStack(Blocks.coal_block))) return true;
		if (item.isItemEqual(new ItemStack(Items.blaze_powder))) return true;
		if (item.isItemEqual(new ItemStack(Items.blaze_rod))) return true;
		if (item.isItemEqual(new ItemStack(Items.diamond))) return true;
		if (item.isItemEqual(new ItemStack(ModItems.infusedCrystal))) return true;
		if (item.isItemEqual(new ItemStack(Items.nether_star))) return true;
		if (item.isItemEqual(new ItemStack(Blocks.diamond_block))) return true;
		if (item.isItemEqual(new ItemStack(ModItems.infusedIngot))) return true;
		if (item.isItemEqual(new ItemStack(ModBlocks.infusedBlock))) return true;
		return false;
	}

	private boolean isUpgradeableItem(ItemStack infuserItem)
	{
		if (infuserItem == null) return false;
		Item item = infuserItem.getItem();
		return item instanceof IModItem;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return isUpgradeableItem(itemStack);
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inv;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inv = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) stack.stackSize = getInventoryStackLimit();
	}
	

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize <= amount) setInventorySlotContents(slot, null);
			else
			{
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}
	

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) setInventorySlotContents(slot, null);
		return stack;
	}
	

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
	

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}
	

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();
		ItemStack stack = inv;
		if (inv != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setByte("Slot", (byte) 0);
			stack.writeToNBT(tag);
			itemList.appendTag(tag);
		}
		tagCompound.setTag("Inventory", itemList);
	}
	

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(0);
			byte slot = tag.getByte("Slot");
			if (slot == 0) inv = ItemStack.loadItemStackFromNBT(tag);
		}
	}
	

	@Override
	public String getInventoryName()
	{
		return Reference.MODID + ".TileEntityEssenceInfuser";
	}
	

	public boolean checkMultiBlockForm()
	{
		if (worldObj.getBlock(xCoord, yCoord - 2, zCoord) != Blocks.stonebrick) return false;
		if (worldObj.getBlockMetadata(xCoord, yCoord - 2, zCoord) != 0) return false;
		for (int i = -3; i <= 3; i++)
		{
			if (!isBlockSame(xCoord + i, yCoord - 2, zCoord)) return false;
			if (!isBlockSame(xCoord, yCoord - 2, zCoord + i)) return false;
		}
		for (int i = -2; i <= 2; i++)
		{
			if (!isBlockSame(xCoord + i, yCoord - 2, zCoord + i)) return false;
			if (!isBlockSame(xCoord - i, yCoord - 2, zCoord + i)) return false;
		}
		if (!isBlockSame(xCoord, yCoord - 1, zCoord)) return false;

		if (worldObj.isAirBlock(xCoord, yCoord, zCoord)) return false;
		if (!(worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileEntityEssenceInfuser)) return false;
		if (!(worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord + 2) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord - 2) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord + 2) instanceof TileEntityEssencePylon)) return false;
		if (!(worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord - 2) instanceof TileEntityEssencePylon)) return false;
		return true;
	}
	

	private boolean isBlockSame(int x, int y, int z)
	{
		return Block.isEqualTo(worldObj.getBlock(xCoord, yCoord - 2, zCoord), worldObj.getBlock(x, y, z)) && worldObj.getBlockMetadata(xCoord, yCoord - 2, zCoord) == worldObj.getBlockMetadata(x, y, z);
	}
	


	public void reset()
	{
		multiblock = false;
	}
	

	public void resetStructure()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null && tile instanceof TileEntityEssenceInfuser)
		{
			((TileEntityEssenceInfuser) tile).reset();
		}
	}
	

	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}
	

	@Override
	public void openInventory()
	{}
	

	@Override
	public void closeInventory()
	{}
	

	public boolean isActive()
	{
		return active;
	}
	

	public int ticksRemaining()
	{
		return ticksRemaining;
	}
}
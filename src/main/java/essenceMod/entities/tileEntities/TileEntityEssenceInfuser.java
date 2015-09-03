package essenceMod.entities.tileEntities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.Constants;
import essenceMod.help.Reference;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModItems;
import essenceMod.items.ItemModBow;
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
//						((ItemModSword) inv.getItem()).levelUp(getOuterPylonItems().get(0), inv);
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
		return hasFuel(getInnerPylonItems(), player) && hasResource(getOuterPylonItems(), player) && hasUpgradeableItem(inv);
	}

	public ArrayList<ItemStack> getInnerPylonItems()
	{
		ArrayList<ItemStack> pylonItems = new ArrayList<ItemStack>();
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 1, yCoord - 1, zCoord)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 1, yCoord - 1, zCoord)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 1)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 1)).inv);
		return pylonItems;
	}

	public ArrayList<ItemStack> getOuterPylonItems()
	{
		ArrayList<ItemStack> pylonItems = new ArrayList<ItemStack>();
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 3, yCoord - 1, zCoord)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 3, yCoord - 1, zCoord)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord - 3)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord + 3)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord - 2) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord - 2)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord - 2)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord + 2) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord + 2)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord - 2, yCoord - 1, zCoord + 2)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord - 2) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord - 2)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord - 2)).inv);
		if ((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord + 2) != null) if (((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord + 2)).inv != null) pylonItems.add(((TileEntityEssencePylon) worldObj.getTileEntity(xCoord + 2, yCoord - 1, zCoord + 2)).inv);
		return pylonItems;
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

	protected boolean isResource(ItemStack item)
	{
		if (item == null || item.stackSize == 0) return false;
		if (item.isItemEqual(new ItemStack(Items.blaze_powder))) return true;
		if (item.isItemEqual(new ItemStack(Items.spider_eye))) return true;
		if (item.isItemEqual(new ItemStack(Items.ender_pearl))) return true;
		if (item.isItemEqual(new ItemStack(Items.gold_nugget))) return true;
		if (item.isItemEqual(new ItemStack(Items.bone))) return true;
		if (item.isItemEqual(new ItemStack(Items.slime_ball))) return true;
		if (item.isItemEqual(new ItemStack(Items.string))) return true;
		if (item.isItemEqual(new ItemStack(Items.glowstone_dust))) return true;
		if (item.isItemEqual(new ItemStack(Items.rotten_flesh))) return true;
		if (item.isItemEqual(new ItemStack(Items.skull, 1, 1))) return true;
		return false;
	}

	private boolean hasFuel(ArrayList<ItemStack> innerPylonItems, EntityPlayer player)
	{
		ItemStack fuel = null;
		int level = upgradeLevel(inv);
		if (level != 0)
		{
			switch (level)
			{
				case 1:
					fuel = new ItemStack(Items.coal);
					break;
				case 2:
					fuel = new ItemStack(Blocks.coal_block);
					break;
				case 3:
					fuel = new ItemStack(Items.blaze_powder);
					break;
				case 4:
					fuel = new ItemStack(Items.blaze_rod);
					break;
				case 5:
					fuel = new ItemStack(Items.diamond);
					break;
				case 6:
					fuel = new ItemStack(ModItems.infusedCrystal);
					break;
				case 7:
					fuel = new ItemStack(Items.nether_star);
					break;
				case 8:
					fuel = new ItemStack(Blocks.diamond_block);
					break;
				case 9:
					fuel = new ItemStack(ModItems.infusedIngot);
					break;
				case 10:
					fuel = new ItemStack(ModBlocks.infusedBlock);
					break;
			}
		}
		if (innerPylonItems == null || innerPylonItems.size() == 0)
		{
			player.addChatComponentMessage(new ChatComponentText("! Ritual requires " + fuel.getDisplayName() + " x4"));
			return false;
		}
		int num = 0;
		for (ItemStack pylonItem : innerPylonItems)
		{
			if (pylonItem != null && pylonItem.stackSize != 0 && pylonItem.isItemEqual(fuel)) num++;
		}
		if (num != 4)
		{
			player.addChatComponentMessage(new ChatComponentText("! Ritual requires an additional " + fuel.getDisplayName() + " x" + (4 - num)));
			return false;
		}
		player.addChatComponentMessage(new ChatComponentText("- Ritual has required fuel source"));
		return true;
	}

	private ArrayList<ItemStack> correctResource()
	{
		ArrayList<ItemStack> resource = new ArrayList<ItemStack>();
//		ItemModSword.updateUpgrades(inv);
//		boolean[] levels = ItemModSword.getAvailableLevels(inv);
//		if (levels[0]) resource.add(new ItemStack(Items.blaze_powder));
//		if (levels[1]) resource.add(new ItemStack(Items.spider_eye));
//		if (levels[2]) resource.add(new ItemStack(Items.ender_pearl));
//		if (levels[3]) resource.add(new ItemStack(Items.gold_nugget));
//		if (levels[4]) resource.add(new ItemStack(Items.bone));
//		if (levels[5]) resource.add(new ItemStack(Items.slime_ball));
//		if (levels[6]) resource.add(new ItemStack(Items.string));
//		if (levels[7]) resource.add(new ItemStack(Items.glowstone_dust));
//		if (levels[8]) resource.add(new ItemStack(Items.rotten_flesh));
//		if (levels[9]) resource.add(new ItemStack(Items.skull, 1, 1));
		return resource;
	}

	private boolean hasResource(ArrayList<ItemStack> outerPylonItems, EntityPlayer player)
	{
		if (inv == null) return false;
		if (!(inv.getItem() instanceof ItemModSword)) return false;
		ArrayList<ItemStack> resource = correctResource();
		if (resource == null || resource.size() == 0)
		{
			player.addChatMessage(new ChatComponentText("! No available infusions"));
			return false;
		}
		if (outerPylonItems == null || outerPylonItems.size() == 0)
		{
			player.addChatMessage(new ChatComponentText("! Ritual requires valid mob drops"));
			return false;
		}
		int num = 0;
		Set<ItemStack> types = new HashSet<ItemStack>();
		for (ItemStack item : outerPylonItems)
		{
			for (ItemStack test : resource)
			{
				if (item != null && item.stackSize != 0)
				{
					if (item.isItemEqual(test))
					{
						types.add(test);
						num++;
					}
				}
			}
		}
		if (num < 8)
		{
			player.addChatMessage(new ChatComponentText("! Ritual requires an additional " + (8 - num) + " mob drops"));
			return false;
		}
		if (types.size() > 1)
		{
			player.addChatMessage(new ChatComponentText("! Ritual contains " + types.size() + " different mob drops, requires only 1 type"));
			return false;
		}
		player.addChatComponentMessage(new ChatComponentText("- Ritual has required resources"));
		return true;
	}

	private boolean hasUpgradeableItem(ItemStack infuserItem)
	{
		if (infuserItem == null) return false;
		Item item = infuserItem.getItem();
		return item instanceof ItemModBow || item instanceof ItemModSword;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return hasUpgradeableItem(itemStack);
	}

	private int upgradeLevel(ItemStack infuserItem)
	{
		if (infuserItem != null && infuserItem.stackSize != 0) if (hasUpgradeableItem(infuserItem)) if (infuserItem.getItem() instanceof ItemModSword) if (infuserItem.stackTagCompound.hasKey("Level")) return infuserItem.stackTagCompound.getInteger("Level") + 1;
		return 0;
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

	public void setupStructure()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		if (tile != null && tile instanceof TileEntityEssenceInfuser)
		{

		}
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
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	public boolean isActive()
	{
		return active;
	}

	public int ticksRemaining()
	{
		return ticksRemaining;
	}
}
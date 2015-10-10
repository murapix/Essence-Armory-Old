package essenceMod.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.gui.GuiEssenceInfuser;
import essenceMod.help.Reference;
import essenceMod.tabs.ModTabs;

public class EssenceInfuser extends BlockContainer
{
	public EssenceInfuser()
	{
		super(Material.rock);
		setBlockName("essenceInfuser");
		setCreativeTab(ModTabs.tabEssence);
		setBlockTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
		setHardness(5.0F);
		setResistance(10.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityEssenceInfuser();
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			TileEntityEssenceInfuser tileEntity = (TileEntityEssenceInfuser) world.getTileEntity(x, y, z);
			if (tileEntity.isActive())
			{
				player.addChatMessage(new ChatComponentText("Infusion Progress: " + ((200 - tileEntity.ticksRemaining()) / 2) + "%"));
			}
			else
			{
				if (tileEntity.inv != null)
				{
					if (player.isSneaking() && player.getCurrentEquippedItem() == null)
					{
						player.setCurrentItemOrArmor(0, tileEntity.inv.copy());
						if (tileEntity.inv.hasTagCompound()) player.getEquipmentInSlot(0).setTagCompound((NBTTagCompound) tileEntity.inv.getTagCompound().copy());
						tileEntity.setInventorySlotContents(0, null);
					}
					else if (player.isSneaking() && player.getCurrentEquippedItem() != null)
					{
						dropItems(world, x, y, z);
						tileEntity.setInventorySlotContents(0, null);
					}
					else
					{
						boolean bool = tileEntity.checkMultiBlockForm();
						player.addChatMessage(new ChatComponentText(bool ? "- Essence Infuser is correctly set up." : "! Essence Infuser is not correctly set up."));
						Minecraft.getMinecraft().displayGuiScreen(new GuiEssenceInfuser(player.inventory, tileEntity));
						tileEntity.multiblock = bool;
						if (tileEntity.isReady())
						{
							tileEntity.activate();
							player.addChatMessage(new ChatComponentText("Beginng infusion"));
						}
						return true;
					}
				}
				else if (tileEntity.isItemValidForSlot(0, player.getCurrentEquippedItem()))
				{
					tileEntity.setInventorySlotContents(0, player.getCurrentEquippedItem().splitStack(1).copy());
				}
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
	{
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	private void dropItems(World world, int x, int y, int z)
	{
		Random rand = new Random();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) return;
		IInventory inventory = (IInventory) tileEntity;

		ItemStack item = inventory.getStackInSlot(0);
		if (item != null && item.stackSize > 0)
		{
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem()));
			if (item.hasTagCompound()) entityItem.getEntityItem().setTagCompound(item.getTagCompound());
			
			float factor = 0.05F;
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			world.spawnEntityInWorld(entityItem);
			item = null;
		}
	}
}
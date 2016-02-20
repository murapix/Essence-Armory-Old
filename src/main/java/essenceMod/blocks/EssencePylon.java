package essenceMod.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.items.IUpgradeable;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;

public class EssencePylon extends BlockContainer implements IUpgradeable
{
	public EssencePylon()
	{
		super(Material.rock);
		setBlockName("essencePylon");
		setCreativeTab(ModTabs.tabEssence);
		setBlockTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
		setHardness(5.0F);
		setResistance(10.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityEssencePylon();
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
		if (world.isRemote) return true;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || !(tileEntity instanceof TileEntityEssencePylon)) return true;
		TileEntityEssencePylon pylonEntity = (TileEntityEssencePylon) tileEntity;
		
		world.markBlockForUpdate(x, y, z);
		pylonEntity.markDirty();
		
		ItemStack item = pylonEntity.getStackInSlot(0);
		ItemStack playerItem = player.getCurrentEquippedItem();
		if (item != null && item.stackSize > 0)
		{
			Random rand = new Random();
			EntityItem itemEntity = new EntityItem(world, player.posX, player.posY + player.getDefaultEyeHeight() / 2.0F, player.posZ, item.copy());
			world.spawnEntityInWorld(itemEntity);
			pylonEntity.setInventorySlotContents(0, null);
			
			world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
			
			return true;
		}
		else if (playerItem != null && playerItem.stackSize > 0 && pylonEntity.isItemValidForSlot(0, playerItem))
		{
			ItemStack tempItem = playerItem.splitStack(1);
			pylonEntity.setInventorySlotContents(0, tempItem);
			
			if (playerItem.stackSize == 0) player.setCurrentItemOrArmor(0, null);
			else player.setCurrentItemOrArmor(0, playerItem);
			
			return true;
		}
//		player.openGui(EssenceMod.instance, GuiHandler.EssencePylonGui, world, x, y, z);
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

		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack item = inventory.getStackInSlot(i);
			if (item != null && item.stackSize > 0)
			{
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem()));
				if (item.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionY = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}
}
package essenceMod.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.items.IUpgradeable;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;

public class EssenceInfuser extends BlockContainer implements IUpgradeable
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
		if (world.isRemote) return true;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || !(tileEntity instanceof TileEntityEssenceInfuser)) return true;
		TileEntityEssenceInfuser infuserEntity = (TileEntityEssenceInfuser) tileEntity;
		
		world.markBlockForUpdate(x, y, z);
		infuserEntity.markDirty();
		
		if (infuserEntity.isActive())
		{
			int percent = infuserEntity.infuseTime / infuserEntity.TotalInfuseTime * 100;
			player.addChatComponentMessage(new ChatComponentText("Infuser Progress: " + percent + "%"));
			return true;
		}
		else
		{
			ItemStack item = infuserEntity.getStackInSlot(0);
			ItemStack playerItem = player.getCurrentEquippedItem();
			if (item != null && item.stackSize > 0)
			{
				if (playerItem != null && playerItem.stackSize > 0 && new ItemStack(ModItems.infusedWand).isItemEqual(playerItem))
				{
					player.addChatComponentMessage(new ChatComponentText("Infuser Activeted. Upgrade: " + InfuserRecipes.checkRecipe(item, infuserEntity.getPylonItems())));
					infuserEntity.activate();
				}
				else
				{
					Random rand = new Random();
					EntityItem itemEntity = new EntityItem(world, player.posX, player.posY + player.getDefaultEyeHeight() / 2.0F, player.posZ, item.copy());
					world.spawnEntityInWorld(itemEntity);
					infuserEntity.setInventorySlotContents(0, null);

					world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
				}
				return true;
			}
			else if (playerItem != null && playerItem.stackSize > 0 && infuserEntity.isItemValidForSlot(0, playerItem))
			{
				if (new ItemStack(ModItems.infusedWand).isItemEqual(playerItem)) return true;
				ItemStack tempItem = playerItem.splitStack(1);
				infuserEntity.setInventorySlotContents(0, tempItem);

				if (playerItem.stackSize == 0) player.setCurrentItemOrArmor(0, null);
				else player.setCurrentItemOrArmor(0, playerItem);

				return true;
			}
		}
		// player.openGui(EssenceMod.instance, GuiHandler.EssenceInfuserGui, world, x, y, z);
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
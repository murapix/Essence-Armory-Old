package essenceMod.blocks;

import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.items.IUpgradeable;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.tabs.ModTabs;

public class EssenceInfuser extends BlockContainer implements IUpgradeable, ITileEntityProvider
{
	public EssenceInfuser()
	{
		super(Material.rock);
		setCreativeTab(ModTabs.tabEssence);
		setHardness(5.0F);
		setResistance(10.0F);
		setDefaultState(this.blockState.getBaseState());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityEssenceInfuser();
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float par7, float par8, float par9)
	{
		if (world.isRemote) return true;
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null || !(tileEntity instanceof TileEntityEssenceInfuser)) return true;
		TileEntityEssenceInfuser infuserEntity = (TileEntityEssenceInfuser) tileEntity;
		
		world.markBlockForUpdate(pos);
		infuserEntity.markDirty();
		
		if (infuserEntity.isActive())
		{
			int percent = infuserEntity.infuseTime * 100 / infuserEntity.TotalInfuseTime;
			player.addChatComponentMessage(new ChatComponentText("Infuser Progress: " + percent + "%"));
			return true;
		}
		else
		{
			ItemStack item = infuserEntity.getStackInSlot(infuserEntity.InfuserSlot);
			ItemStack playerItem = player.getCurrentEquippedItem();
			if (item != null && item.stackSize > 0)
			{
				if (playerItem != null && playerItem.stackSize > 0 && new ItemStack(ModItems.infusedWand).isItemEqual(playerItem))
				{
					player.addChatComponentMessage(new ChatComponentText("Infuser Activated."));
					Upgrade upgrade = InfuserRecipes.checkUpgradeRecipe(item, infuserEntity.getPylonItems());
					ItemStack output = InfuserRecipes.checkItemRecipe(item, infuserEntity.getPylonItems());
					if (upgrade != null) player.addChatComponentMessage(new ChatComponentText("Upgrade: " + StatCollector.translateToLocal(upgrade.name)));
					else if (output != null) player.addChatComponentMessage(new ChatComponentText("Item: " + output.getDisplayName()));
					else player.addChatComponentMessage(new ChatComponentText("No valid recipe, Infuser Deactivated."));
					infuserEntity.activate();
				}
				else
				{
					EntityItem itemEntity = new EntityItem(world, player.posX, player.posY + player.getDefaultEyeHeight() / 2.0F, player.posZ, item.copy());
					world.spawnEntityInWorld(itemEntity);
					infuserEntity.setInventorySlotContents(0, null);

					world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
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
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		dropItems(world, pos);
		super.breakBlock(world, pos, state);
	}

	private void dropItems(World world, BlockPos pos)
	{
		Random rand = new Random();

		TileEntity tileEntity = world.getTileEntity(pos);
		if (!(tileEntity instanceof IInventory)) return;
		IInventory inventory = (IInventory) tileEntity;

		ItemStack item = inventory.getStackInSlot(0);
		if (item != null && item.stackSize > 0)
		{
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, item.copy());

			float factor = 0.05F;
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			world.spawnEntityInWorld(entityItem);
			item = null;
		}
	}
}
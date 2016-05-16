package essenceMod.registry;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.blocks.BossSpawner;
import essenceMod.blocks.EssenceInfuser;
import essenceMod.blocks.EssencePylon;
import essenceMod.blocks.ModBlock;
import essenceMod.utility.RegisterHelper;

public class ModBlocks
{
	public static Block infusedBlock = new ModBlock().setUnlocalizedName("infusedBlock");
	public static Block infusedStarmetal = new ModBlock().setUnlocalizedName("infusedStarmetal");
	public static Block shardBlock = new ModBlock().setUnlocalizedName("shardBlock");
	public static Block essenceInfuser = new EssenceInfuser().setUnlocalizedName("essenceInfuser");
	public static Block essencePylon = new EssencePylon().setUnlocalizedName("essencePylon");
	
	public static Block bossSpawner = new BossSpawner().setUnlocalizedName("bossSpawner");
	
	public static void init()
	{
		RegisterHelper.registerBlock(infusedBlock);
		RegisterHelper.registerBlock(essenceInfuser);
		RegisterHelper.registerBlock(shardBlock);
		RegisterHelper.registerBlock(essencePylon);
		RegisterHelper.registerBlock(infusedStarmetal);
		
		RegisterHelper.registerBlock(bossSpawner);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initItemRenderers()
	{
		((ModBlock) infusedBlock).initModel();
		((ModBlock) infusedStarmetal).initModel();
		((ModBlock) shardBlock).initModel();
		((EssenceInfuser) essenceInfuser).initModel();
		((EssencePylon) essencePylon).initModel();
		
		((BossSpawner) bossSpawner).initModel();
	}
}

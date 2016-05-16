package essenceMod.registry.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class Recipes
{
	public static void init()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.infusedIngot, 9), new Object[] { "blockEssenceInfused" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.infusedNugget, 9), new Object[] { "ingotEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedIngot), new Object[] { "AAA", "AAA", "AAA", 'A', "nuggetEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.infusedBlock), new Object[] { "AAA", "AAA", "AAA", 'A', "ingotEssenceInfused" }));
		
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.shardBlock), new Object[] { "AAA", "AAA", "AAA", 'A', ModItems.infusedShard });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.infusedShard, 9), new Object[] { ModBlocks.shardBlock });

		GameRegistry.addSmelting(ModItems.infusedDiamond, new ItemStack(ModItems.infusedIngot), 1.0F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedDiamond), new Object[] { " A ", "ABA", " A ", 'A', ModBlocks.shardBlock, 'B', "gemDiamond" }));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedStar), new Object[] { " A ", "ABA", " A ", 'A', ModBlocks.shardBlock, 'B', Items.nether_star });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.crystalDiamond), new Object[] { "CAC", "ABA", "CAC", 'A', "nuggetEssenceInfused", 'B', "gemDiamond", 'C', ModBlocks.shardBlock }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.crystalStar), new Object[] { "CAC", "ABA", "CAC", 'A', "nuggetEssenceInfused", 'B', Items.nether_star, 'C', ModBlocks.shardBlock }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.platedDiamond), new Object[] { "CAC", "ABA", "CAC", 'A', "ingotEssenceInfused", 'B', ModItems.crystalDiamond, 'C', ModBlocks.shardBlock }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.platedStar), new Object[] { "CAC", "ABA", "CAC", 'A', "ingotEssenceInfused", 'B', ModItems.crystalStar, 'C', ModBlocks.shardBlock }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.infusedStarmetal), new Object[] { "CAC", "ABA", "CAC", 'A', ModItems.platedDiamond, 'B', ModItems.platedStar, 'C', "ingotEssenceInfused" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.essenceInfuser), new Object[] { "ABA", "B B", "ABA", 'A', Blocks.stonebrick, 'B', "nuggetEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.essencePylon), new Object[] { "B B", " A ", "AAA", 'A', Blocks.stonebrick, 'B', "nuggetEssenceInfused" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedRod), new Object[] { "  A", " A ", "A  ", 'A', "ingotEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedString), new Object[] { "  A", " B ", "A  ", 'A', "nuggetEssenceInfused", 'B', Items.string }));

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedSword), new Object[] { "A", "A", "B", 'A', ModItems.platedDiamond, 'B', ModItems.infusedRod });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBow), new Object[] { "BA ", "B A", "BA ", 'A', ModItems.infusedRod, 'B', ModItems.infusedString });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBow), new Object[] { " AB", "A B", " AB", 'A', ModItems.infusedRod, 'B', ModItems.infusedString });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModArmory.infusedHelm), new Object[] { "AAA", "A A", 'A', "ingotEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModArmory.infusedPlate), new Object[] { "A A", "AAA", "AAA", 'A', "ingotEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModArmory.infusedPants), new Object[] { "AAA", "A A", "A A", 'A', "ingotEssenceInfused" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModArmory.infusedBoots), new Object[] { "A A", "A A", 'A', "ingotEssenceInfused" }));

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedApple), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedStar, 'B', Items.apple });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.crystalApple), new Object[] { " A ", "ABA", " A ", 'A', ModItems.crystalStar, 'B', ModItems.infusedApple });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.platedApple), new Object[] { " A ", "ABA", " A ", 'A', ModItems.platedStar, 'B', ModItems.crystalApple });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.infusedWand), new Object[] { "  A", " B ", "B  ", 'A', ModItems.infusedShard, 'B', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModArmory.infusedRing), new Object[] { " A ", "A A", " B ", 'A', "ingotGold", 'B', ModItems.crystalDiamond }));
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedAmulet), new Object[] { " A ", "A A", " B ", 'A', Items.string, 'B', ModItems.crystalDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBelt), new Object[] { " A ", "A A", " B ", 'A', Items.leather, 'B', ModItems.crystalDiamond });
		
//		if (Loader.isModLoaded("TravellersGear") && ConfigHandler.travellersgearIntegration)
//		{
//			GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedPauldrons), new Object[] { "AA ", "BCA", " BA", 'A', Items.leather, 'B', Items.string, 'C', ModItems.crystalDiamond });
//			GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedVambraces), new Object[] { "ABA", "   ", "ABA", 'A', Items.leather, 'B', ModItems.crystalDiamond });
//		}
		
		ItemStack lootSword1 = InfuserRecipes.addUpgrade(new ItemStack(ModArmory.lootSword, 1, 0), UpgradeRegistry.ShardSwordLooting.setLevel(1)).copy();
		ItemStack lootSword2 = InfuserRecipes.addUpgrade(new ItemStack(ModArmory.lootSword, 1, 1), UpgradeRegistry.ShardSwordLooting.setLevel(2)).copy();
		ItemStack lootSword3 = InfuserRecipes.addUpgrade(new ItemStack(ModArmory.lootSword, 1, 2), UpgradeRegistry.ShardSwordLooting.setLevel(3)).copy();
		ItemStack lootSword4 = InfuserRecipes.addUpgrade(new ItemStack(ModArmory.lootSword, 1, 3), UpgradeRegistry.ShardSwordLooting.setLevel(4)).copy();
		ItemStack lootSword5 = InfuserRecipes.addUpgrade(new ItemStack(ModArmory.lootSword, 1, 4), UpgradeRegistry.ShardSwordLooting.setLevel(5)).copy();
		GameRegistry.addShapedRecipe(lootSword1, new Object[] {" A ", "ABA" , "ACA" , 'A', ModItems.infusedShard, 'B', Items.diamond_sword, 'C', Items.diamond});
		GameRegistry.addShapedRecipe(lootSword2, new Object[] {" A ", "ABA" , "ACA" , 'A', ModItems.infusedShard, 'B', lootSword1, 'C', Items.diamond});
		GameRegistry.addShapedRecipe(lootSword3, new Object[] {" A ", "ABA" , "ACA" , 'A', ModItems.infusedShard, 'B', lootSword2, 'C', Items.diamond});
		GameRegistry.addShapedRecipe(lootSword4, new Object[] {" A ", "ABA" , "ACA" , 'A', ModItems.infusedShard, 'B', lootSword3, 'C', Items.diamond});
		GameRegistry.addShapedRecipe(lootSword5, new Object[] {" A ", "ABA" , "ACA" , 'A', ModItems.infusedShard, 'B', lootSword4, 'C', Items.diamond});
	}
}
package essenceMod.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.init.ModArmory;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModItems;
import essenceMod.items.baubles.ItemPotionRing;

public class Recipes
{
	public static void init()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.infusedIngot, 9), new Object[] { ModBlocks.infusedBlock });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.infusedNugget, 9), new Object[] { ModItems.infusedIngot });

		GameRegistry.addSmelting(ModItems.infusedCrystal, new ItemStack(ModItems.infusedNugget), 1.0F);
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedCrystal), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedShard, 'B', Items.diamond});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedShard, 'B', Items.nether_star});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedDiamond), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedNugget, 'B', Items.diamond});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.crystalStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedNugget, 'B', Items.nether_star});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedIngot), new Object[] { "AAA", "AAA", "AAA", 'A', ModItems.infusedNugget});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.platedDiamond), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedIngot, 'B', ModItems.infusedDiamond});
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.platedStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedIngot, 'B', ModItems.crystalStar});
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.infusedBlock), new Object[] { "AAA", "AAA", "AAA", 'A', ModItems.infusedIngot});
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.infusedStarmetal), new Object[] { "ABA", "BCB", "ABA", 'A', ModItems.infusedIngot, 'B', ModItems.platedDiamond, 'C', ModItems.platedStar});
		
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.essenceInfuser), new Object[] { "ABA", "B B", "ABA", 'A', Blocks.stonebrick, 'B', ModItems.infusedNugget });
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.essencePylon), new Object[] { "B B", " A ", "AAA", 'A', Blocks.stonebrick, 'B', ModItems.infusedNugget });

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedRod), new Object[] { "ABA", 'A', ModItems.infusedNugget, 'B', Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedRod), new Object[] { "A", "B", "A", 'A', ModItems.infusedNugget, 'B', Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedString), new Object[] { "ABA", 'A', ModItems.infusedNugget, 'B', Items.string });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedString), new Object[] { "A", "B", "A", 'A', ModItems.infusedNugget, 'B', Items.string });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedSword), new Object[] { "A", "A", "B", 'A', ModItems.infusedIngot, 'B', ModItems.infusedRod });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBow), new Object[] { "BA ", "B A", "BA ", 'A', ModItems.infusedRod, 'B', ModItems.infusedString });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBow), new Object[] { " AB", "A B", " AB", 'A', ModItems.infusedRod, 'B', ModItems.infusedString });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedHelm), new Object[] { "AAA", "A A", 'A', ModItems.infusedIngot });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedPlate), new Object[] { "A A", "AAA", "AAA", 'A', ModItems.infusedIngot });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedPants), new Object[] { "AAA", "A A", "A A", 'A', ModItems.infusedIngot });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBoots), new Object[] { "A A", "A A", 'A', ModItems.infusedIngot });

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedApple), new Object[] { " A ", "ABA", " A ", 'A', ModItems.platedStar, 'B', Items.apple });
		
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedPotionRing), new Object[] { " A ", "A A", " B ", 'A', Items.gold_nugget, 'B', ModItems.infusedDiamond});
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedLootAmulet), new Object[] { " A ", "A A", " B ", 'A', Items.string, 'B', ModItems.infusedDiamond});
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedStepBelt), new Object[] { " A ", "A A", " B ", 'A', Items.leather, 'B', ModItems.infusedDiamond});
	}
}
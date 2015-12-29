package essenceMod.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.init.ModArmory;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModItems;
import essenceMod.enchantment.EnchantmentShard;

public class Recipes
{
	public static void init()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.infusedIngot, 9), new Object[] { ModBlocks.infusedBlock });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.infusedNugget, 9), new Object[] { ModItems.infusedIngot });

		GameRegistry.addSmelting(ModItems.infusedCrystal, new ItemStack(ModItems.infusedNugget), 1.0F);

		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedCrystal), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedShard, 'B', Items.diamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedShard, 'B', Items.nether_star });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedDiamond), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedNugget, 'B', Items.diamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.crystalStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedNugget, 'B', Items.nether_star });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.infusedIngot), new Object[] { "AAA", "AAA", "AAA", 'A', ModItems.infusedNugget });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.platedDiamond), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedIngot, 'B', ModItems.infusedDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.platedStar), new Object[] { " A ", "ABA", " A ", 'A', ModItems.infusedIngot, 'B', ModItems.crystalStar });
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.infusedBlock), new Object[] { "AAA", "AAA", "AAA", 'A', ModItems.infusedIngot });
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.infusedStarmetal), new Object[] { "ABA", "BCB", "ABA", 'A', ModItems.infusedIngot, 'B', ModItems.platedDiamond, 'C', ModItems.platedStar });

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

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.baseRing), new Object[] { " A ", "A A", " B ", 'A', Items.gold_nugget, 'B', ModItems.infusedDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.baseAmulet), new Object[] { " A ", "A A", " B ", 'A', Items.string, 'B', ModItems.infusedDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.baseBelt), new Object[] { " A ", "A A", " B ", 'A', Items.leather, 'B', ModItems.infusedDiamond });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.infusedStar, 'C', ModArmory.baseBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.crystalStar, 'C', ModArmory.cleaveBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.platedDiamond, 'C', ModArmory.cleaveBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.platedStar, 'C', ModArmory.cleaveBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModBlocks.infusedStarmetal, 'C', ModArmory.cleaveBelt });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedShard, 'C', ModArmory.baseAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedCrystal, 'C', ModArmory.lootAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedStar, 'C', ModArmory.lootAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedDiamond, 'C', ModArmory.lootAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedIngot, 'C', ModArmory.lootAmulet });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.flightAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Items.feather, 'B', ModItems.platedStar, 'C', ModArmory.baseAmulet });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 1), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.infusedIngot, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 2), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.platedDiamond, 'C', new ItemStack(ModArmory.potionRing, 1, 1) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 3), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.platedStar, 'C', new ItemStack(ModArmory.potionRing, 1, 2) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 4), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.infusedDiamond, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 5), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.crystalStar, 'C', new ItemStack(ModArmory.potionRing, 1, 4) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 6), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.infusedIngot, 'C', new ItemStack(ModArmory.potionRing, 1, 5) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 7), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModItems.platedStar, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 8), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModBlocks.infusedBlock, 'C', new ItemStack(ModArmory.potionRing, 1, 7) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 9), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModBlocks.infusedStarmetal, 'C', new ItemStack(ModArmory.potionRing, 1, 8) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 10), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.infusedDiamond, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 11), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.crystalStar, 'C', new ItemStack(ModArmory.potionRing, 1, 10) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 12), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.infusedIngot, 'C', new ItemStack(ModArmory.potionRing, 1, 11) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 13), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModItems.platedStar, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 14), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModBlocks.infusedBlock, 'C', new ItemStack(ModArmory.potionRing, 1, 13) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 15), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModBlocks.infusedStarmetal, 'C', new ItemStack(ModArmory.potionRing, 1, 14) });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 16), new Object[] { "ABA", "BCB", "ABA", 'A', Items.magma_cream, 'B', ModItems.infusedIngot, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 17), new Object[] { "ABA", "BCB", "ABA", 'A', Items.fish, 'B', ModItems.infusedShard, 'C', ModArmory.baseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.potionRing, 1, 18), new Object[] { "ABA", "BCB", "ABA", 'A', Items.golden_carrot, 'B', ModItems.infusedShard, 'C', ModArmory.baseRing });
	}
}
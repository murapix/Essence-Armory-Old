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

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBaseRing), new Object[] { " A ", "A A", " B ", 'A', Items.gold_nugget, 'B', ModItems.infusedDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBaseAmulet), new Object[] { " A ", "A A", " B ", 'A', Items.string, 'B', ModItems.infusedDiamond });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedBaseBelt), new Object[] { " A ", "A A", " B ", 'A', Items.leather, 'B', ModItems.infusedDiamond });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveIBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.infusedStar, 'C', ModArmory.infusedBaseBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveIIBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.crystalStar, 'C', ModArmory.cleaveIBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveIIIBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.platedDiamond, 'C', ModArmory.cleaveIIBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveIVBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModItems.platedStar, 'C', ModArmory.cleaveIIIBelt });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.cleaveVBelt), new Object[] { "ABA", "BCB", "ABA", 'A', Items.gunpowder, 'B', ModBlocks.infusedStarmetal, 'C', ModArmory.cleaveIVBelt });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootIAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedShard, 'C', ModArmory.infusedBaseAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootIIAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedCrystal, 'C', ModArmory.lootIAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootIIIAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedStar, 'C', ModArmory.lootIIAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootIVAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedDiamond, 'C', ModArmory.lootIIIAmulet });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.lootVAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Blocks.lapis_block, 'B', ModItems.infusedIngot, 'C', ModArmory.lootIVAmulet });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.infusedFlightAmulet), new Object[] { "ABA", "BCB", "ABA", 'A', Items.feather, 'B', ModItems.platedStar, 'C', ModArmory.infusedBaseAmulet });

		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.fireRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.magma_cream, 'B', ModItems.infusedIngot, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.waterRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.fish, 'B', ModItems.infusedShard, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.nightRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.golden_carrot, 'B', ModItems.infusedShard, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.swiftnessIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.infusedIngot, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.swiftnessIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.platedDiamond, 'C', ModArmory.swiftnessIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.swiftnessIIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.sugar, 'B', ModItems.platedStar, 'C', ModArmory.swiftnessIIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.hasteIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.infusedDiamond, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.hasteIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.crystalStar, 'C', ModArmory.hasteIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.hasteIIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.redstone, 'B', ModItems.infusedIngot, 'C', ModArmory.hasteIIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.jumpIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.infusedDiamond, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.jumpIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.crystalStar, 'C', ModArmory.jumpIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.jumpIIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.slime_ball, 'B', ModItems.infusedIngot, 'C', ModArmory.jumpIIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.regenerationIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModItems.platedStar, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.regenerationIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModBlocks.infusedBlock, 'C', ModArmory.regenerationIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.regenerationIIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.ghast_tear, 'B', ModBlocks.infusedStarmetal, 'C', ModArmory.regenerationIIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.strengthIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModItems.platedStar, 'C', ModArmory.infusedBaseRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.strengthIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModBlocks.infusedBlock, 'C', ModArmory.strengthIRing });
		GameRegistry.addShapedRecipe(new ItemStack(ModArmory.strengthIIIRing), new Object[] { "ABA", "BCB", "ABA", 'A', Items.blaze_powder, 'B', ModBlocks.infusedStarmetal, 'C', ModArmory.strengthIIRing });
	}
}
package essenceMod.registry.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeCraftingRecipe;
import essenceMod.registry.crafting.upgrades.UpgradeRecipe;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class InfuserRecipes
{
	public static HashMap<Class, ArrayList<UpgradeRecipe>> upgradeRecipes;

	public static void init()
	{
		UpgradeRegistry.init();
		RecipeSorter.register("essenceArmory:upgradeShaped", UpgradeCraftingRecipe.class, Category.SHAPED, "after:minecraft:shapeless");

		upgradeRecipes = new HashMap<Class, ArrayList<UpgradeRecipe>>();

		swordRecipes();
		bowRecipes();
		armorRecipes();
		amuletRecipes();
		ringRecipes();
		beltRecipes();
//		if (Loader.isModLoaded("TravellersGear") && ConfigHandler.travellersgearIntegration)
//		{
//			pauldronRecipes();
//			vambraceRecipes();
//		}
	}

	private static void swordRecipes()
	{
		ItemStack sword = new ItemStack(ModArmory.infusedSword);

		addRecipe(sword, UpgradeRegistry.WeaponFireDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.coal, ModItems.infusedDiamond, Items.redstone);// Coal
		addRecipe(sword, UpgradeRegistry.WeaponFireDoT.setLevel(2), UpgradeRegistry.WeaponFireDoT.setLevel(1), Items.coal, ModItems.infusedDiamond, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponFireDoT.setLevel(3), UpgradeRegistry.WeaponFireDoT.setLevel(2), Items.coal, ModItems.crystalDiamond, Items.redstone);
		addRecipe(sword, UpgradeRegistry.WeaponFireDoT.setLevel(4), UpgradeRegistry.WeaponFireDoT.setLevel(3), Items.coal, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponFireDoT.setLevel(5), UpgradeRegistry.WeaponFireDoT.setLevel(4), Items.coal, ModItems.platedDiamond, Items.redstone);

		addRecipe(sword, UpgradeRegistry.WeaponMagicDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.spider_eye, ModItems.infusedDiamond, Items.redstone);// Spider Eye
		addRecipe(sword, UpgradeRegistry.WeaponMagicDoT.setLevel(2), UpgradeRegistry.WeaponMagicDoT.setLevel(1), Items.spider_eye, ModItems.infusedDiamond, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDoT.setLevel(3), UpgradeRegistry.WeaponMagicDoT.setLevel(2), Items.spider_eye, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDoT.setLevel(4), UpgradeRegistry.WeaponMagicDoT.setLevel(3), Items.spider_eye, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDoT.setLevel(5), UpgradeRegistry.WeaponMagicDoT.setLevel(4), Items.spider_eye, ModItems.platedDiamond, Items.glowstone_dust);

		addRecipe(sword, UpgradeRegistry.WeaponWitherDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.soul_sand, ModItems.infusedStar, Items.redstone);// Soul Sand
		addRecipe(sword, UpgradeRegistry.WeaponWitherDoT.setLevel(2), UpgradeRegistry.WeaponWitherDoT.setLevel(1), Blocks.soul_sand, ModItems.infusedStar, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDoT.setLevel(3), UpgradeRegistry.WeaponWitherDoT.setLevel(2), Blocks.soul_sand, ModItems.crystalStar, Items.redstone);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDoT.setLevel(4), UpgradeRegistry.WeaponWitherDoT.setLevel(3), Blocks.soul_sand, ModItems.crystalStar, Items.glowstone_dust);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDoT.setLevel(5), UpgradeRegistry.WeaponWitherDoT.setLevel(4), Blocks.soul_sand, ModItems.platedStar, Items.redstone);

		addRecipe(sword, UpgradeRegistry.WeaponArmorPiercing.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, ModItems.infusedStar, ModItems.infusedStar);// Ender Pearl
		addRecipe(sword, UpgradeRegistry.WeaponArmorPiercing.setLevel(2), UpgradeRegistry.WeaponArmorPiercing.setLevel(1), Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, Items.ender_eye, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(sword, UpgradeRegistry.WeaponArmorPiercing.setLevel(3), UpgradeRegistry.WeaponArmorPiercing.setLevel(2), Items.ender_pearl, Items.ender_pearl, Items.ender_eye, Items.ender_eye, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(sword, UpgradeRegistry.WeaponArmorPiercing.setLevel(4), UpgradeRegistry.WeaponArmorPiercing.setLevel(3), Items.ender_pearl, Items.ender_eye, Items.ender_eye, Items.ender_eye, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(sword, UpgradeRegistry.WeaponArmorPiercing.setLevel(5), UpgradeRegistry.WeaponArmorPiercing.setLevel(4), Items.ender_eye, Items.ender_eye, Items.ender_eye, Items.ender_eye, ModItems.platedStar, ModItems.platedStar);

		addRecipe(sword, UpgradeRegistry.SwordLifesteal.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.ghast_tear, Items.ghast_tear, Items.beef, Items.beef, ModItems.infusedStar, ModItems.infusedStar); // Ghast Tears + Raw Meat
		addRecipe(sword, UpgradeRegistry.SwordLifesteal.setLevel(2), UpgradeRegistry.SwordLifesteal.setLevel(1), Items.ghast_tear, Items.ghast_tear, Items.cooked_beef, Items.cooked_beef, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(sword, UpgradeRegistry.SwordLifesteal.setLevel(3), UpgradeRegistry.SwordLifesteal.setLevel(2), Items.ghast_tear, Items.ghast_tear, Items.cooked_beef, Items.cooked_beef, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(sword, UpgradeRegistry.SwordLifesteal.setLevel(4), UpgradeRegistry.SwordLifesteal.setLevel(3), Items.ghast_tear, Items.ghast_tear, Items.cooked_beef, Items.cooked_beef, ModItems.platedStar, ModItems.platedStar);
		addRecipe(sword, UpgradeRegistry.SwordLifesteal.setLevel(5), UpgradeRegistry.SwordLifesteal.setLevel(4), Items.ghast_tear, Items.ghast_tear, Items.cooked_beef, Items.cooked_beef, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		addRecipe(sword, UpgradeRegistry.WeaponKnockback.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.slime_ball, Items.slime_ball, ModItems.infusedDiamond, ModItems.infusedDiamond);// Slime Ball
		addRecipe(sword, UpgradeRegistry.WeaponKnockback.setLevel(2), UpgradeRegistry.WeaponKnockback.setLevel(1), Blocks.piston, Blocks.piston, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponKnockback.setLevel(3), UpgradeRegistry.WeaponKnockback.setLevel(2), Blocks.piston, Blocks.piston, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponKnockback.setLevel(4), UpgradeRegistry.WeaponKnockback.setLevel(3), Blocks.sticky_piston, Blocks.sticky_piston, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponKnockback.setLevel(5), UpgradeRegistry.WeaponKnockback.setLevel(4), Blocks.sticky_piston, Blocks.sticky_piston, ModItems.platedDiamond, ModItems.platedDiamond);

		ItemStack inkSac = new ItemStack(Items.dye, 1, 0);
		addRecipe(sword, UpgradeRegistry.WeaponBlind.setLevel(1), UpgradeRegistry.BaseUpgrade, inkSac, inkSac, inkSac, inkSac, ModItems.infusedStar, ModItems.infusedStar); // Soul Sand + Ink Sac
		addRecipe(sword, UpgradeRegistry.WeaponBlind.setLevel(2), UpgradeRegistry.WeaponBlind.setLevel(1), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(sword, UpgradeRegistry.WeaponBlind.setLevel(3), UpgradeRegistry.WeaponBlind.setLevel(2), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(sword, UpgradeRegistry.WeaponBlind.setLevel(4), UpgradeRegistry.WeaponBlind.setLevel(3), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedStar, ModItems.platedStar);
		addRecipe(sword, UpgradeRegistry.WeaponBlind.setLevel(5), UpgradeRegistry.WeaponBlind.setLevel(4), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		addRecipe(sword, UpgradeRegistry.WeaponSlow.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedDiamond, ModItems.infusedDiamond); // Cobweb
		addRecipe(sword, UpgradeRegistry.WeaponSlow.setLevel(2), UpgradeRegistry.WeaponSlow.setLevel(1), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponSlow.setLevel(3), UpgradeRegistry.WeaponSlow.setLevel(2), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(sword, UpgradeRegistry.WeaponSlow.setLevel(4), UpgradeRegistry.WeaponSlow.setLevel(3), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponSlow.setLevel(5), UpgradeRegistry.WeaponSlow.setLevel(4), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(sword, UpgradeRegistry.WeaponPhysicalDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.infusedDiamond, ModItems.infusedDiamond);// Quartz
		addRecipe(sword, UpgradeRegistry.WeaponPhysicalDamage.setLevel(2), UpgradeRegistry.WeaponPhysicalDamage.setLevel(1), Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponPhysicalDamage.setLevel(3), UpgradeRegistry.WeaponPhysicalDamage.setLevel(2), Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(sword, UpgradeRegistry.WeaponPhysicalDamage.setLevel(4), UpgradeRegistry.WeaponPhysicalDamage.setLevel(3), Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponPhysicalDamage.setLevel(5), UpgradeRegistry.WeaponPhysicalDamage.setLevel(4), Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		ItemStack witherSkull = new ItemStack(Items.skull, 1, 1);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, witherSkull, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar, ModItems.infusedStar);// Wither Skeleton Skull
		addRecipe(sword, UpgradeRegistry.WeaponWitherDamage.setLevel(2), UpgradeRegistry.WeaponWitherDamage.setLevel(1), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDamage.setLevel(3), UpgradeRegistry.WeaponWitherDamage.setLevel(2), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDamage.setLevel(4), UpgradeRegistry.WeaponWitherDamage.setLevel(3), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedStar, ModItems.platedStar);
		addRecipe(sword, UpgradeRegistry.WeaponWitherDamage.setLevel(5), UpgradeRegistry.WeaponWitherDamage.setLevel(4), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		ItemStack potionHarming = new ItemStack(Items.potionitem, 1, 8204);
		ItemStack potionHarmingII = new ItemStack(Items.potionitem, 1, 8236);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, potionHarming, potionHarming, potionHarming, ModItems.infusedDiamond, ModItems.infusedDiamond, ModItems.infusedDiamond);// Potion of Harming
		addRecipe(sword, UpgradeRegistry.WeaponMagicDamage.setLevel(2), UpgradeRegistry.WeaponMagicDamage.setLevel(1), potionHarming, potionHarming, potionHarming, ModItems.crystalDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDamage.setLevel(3), UpgradeRegistry.WeaponMagicDamage.setLevel(2), potionHarming, potionHarming, potionHarming, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDamage.setLevel(4), UpgradeRegistry.WeaponMagicDamage.setLevel(3), potionHarmingII, potionHarmingII, potionHarmingII, ModItems.platedDiamond, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponMagicDamage.setLevel(5), UpgradeRegistry.WeaponMagicDamage.setLevel(4), potionHarmingII, potionHarmingII, potionHarmingII, ModBlocks.infusedBlock, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(sword, UpgradeRegistry.WeaponFireDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.infusedDiamond, ModItems.infusedDiamond, ModItems.infusedDiamond);// Blaze Rod
		addRecipe(sword, UpgradeRegistry.WeaponFireDamage.setLevel(2), UpgradeRegistry.WeaponFireDamage.setLevel(1), Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.crystalDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponFireDamage.setLevel(3), UpgradeRegistry.WeaponFireDamage.setLevel(2), Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(sword, UpgradeRegistry.WeaponFireDamage.setLevel(4), UpgradeRegistry.WeaponFireDamage.setLevel(3), Items.fire_charge, Items.fire_charge, Items.fire_charge, ModItems.platedDiamond, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(sword, UpgradeRegistry.WeaponFireDamage.setLevel(5), UpgradeRegistry.WeaponFireDamage.setLevel(4), Items.fire_charge, Items.fire_charge, Items.fire_charge, ModBlocks.infusedBlock, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

//		if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
//		{
//			try
//			{
//				ExUHandler.addDivineRecipes(sword);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
//		{
//			try
//			{
//				DraconicEvolutionHandler.addChaosRecipes(sword);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("Thaumcraft") && ConfigHandler.thaumcraftIntegration)
//		{
//			try
//			{
//				ThaumcraftHandler.addTaintRecipes(sword);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("arsmagica2") && ConfigHandler.arsMagicaIntegration)
//		{
//			try
//			{
//				ArsMagicaHandler.addElementalRecipes(sword);
//			}
//			catch (Exception e)
//			{}
//		}
	}

	private static void bowRecipes()
	{
		ItemStack bow = new ItemStack(ModArmory.infusedBow);

		addRecipe(bow, UpgradeRegistry.WeaponFireDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.coal, ModItems.infusedDiamond, Items.redstone);// Coal
		addRecipe(bow, UpgradeRegistry.WeaponFireDoT.setLevel(2), UpgradeRegistry.WeaponFireDoT.setLevel(1), Items.coal, ModItems.infusedDiamond, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponFireDoT.setLevel(3), UpgradeRegistry.WeaponFireDoT.setLevel(2), Items.coal, ModItems.crystalDiamond, Items.redstone);
		addRecipe(bow, UpgradeRegistry.WeaponFireDoT.setLevel(4), UpgradeRegistry.WeaponFireDoT.setLevel(3), Items.coal, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponFireDoT.setLevel(5), UpgradeRegistry.WeaponFireDoT.setLevel(4), Items.coal, ModItems.platedDiamond, Items.redstone);

		addRecipe(bow, UpgradeRegistry.WeaponMagicDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.spider_eye, ModItems.infusedDiamond, Items.redstone);// Spider Eye
		addRecipe(bow, UpgradeRegistry.WeaponMagicDoT.setLevel(2), UpgradeRegistry.WeaponMagicDoT.setLevel(1), Items.spider_eye, ModItems.infusedDiamond, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDoT.setLevel(3), UpgradeRegistry.WeaponMagicDoT.setLevel(2), Items.spider_eye, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDoT.setLevel(4), UpgradeRegistry.WeaponMagicDoT.setLevel(3), Items.spider_eye, ModItems.crystalDiamond, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDoT.setLevel(5), UpgradeRegistry.WeaponMagicDoT.setLevel(4), Items.spider_eye, ModItems.platedDiamond, Items.glowstone_dust);

		addRecipe(bow, UpgradeRegistry.WeaponWitherDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.soul_sand, ModItems.infusedStar, Items.redstone);// Soul Sand
		addRecipe(bow, UpgradeRegistry.WeaponWitherDoT.setLevel(2), UpgradeRegistry.WeaponWitherDoT.setLevel(1), Blocks.soul_sand, ModItems.infusedStar, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDoT.setLevel(3), UpgradeRegistry.WeaponWitherDoT.setLevel(2), Blocks.soul_sand, ModItems.crystalStar, Items.redstone);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDoT.setLevel(4), UpgradeRegistry.WeaponWitherDoT.setLevel(3), Blocks.soul_sand, ModItems.crystalStar, Items.glowstone_dust);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDoT.setLevel(5), UpgradeRegistry.WeaponWitherDoT.setLevel(4), Blocks.soul_sand, ModItems.platedStar, Items.redstone);

		addRecipe(bow, UpgradeRegistry.WeaponArmorPiercing.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, ModItems.infusedStar, ModItems.infusedStar);// Ender Pearl
		addRecipe(bow, UpgradeRegistry.WeaponArmorPiercing.setLevel(2), UpgradeRegistry.WeaponArmorPiercing.setLevel(1), Items.ender_pearl, Items.ender_pearl, Items.ender_pearl, Items.ender_eye, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(bow, UpgradeRegistry.WeaponArmorPiercing.setLevel(3), UpgradeRegistry.WeaponArmorPiercing.setLevel(2), Items.ender_pearl, Items.ender_pearl, Items.ender_eye, Items.ender_eye, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(bow, UpgradeRegistry.WeaponArmorPiercing.setLevel(4), UpgradeRegistry.WeaponArmorPiercing.setLevel(3), Items.ender_pearl, Items.ender_eye, Items.ender_eye, Items.ender_eye, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(bow, UpgradeRegistry.WeaponArmorPiercing.setLevel(5), UpgradeRegistry.WeaponArmorPiercing.setLevel(4), Items.ender_eye, Items.ender_eye, Items.ender_eye, Items.ender_eye, ModItems.platedStar, ModItems.platedStar);

		addRecipe(bow, UpgradeRegistry.BowArrowSpeed.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.slime_ball, Items.slime_ball, Items.feather, Items.feather, ModItems.infusedDiamond, ModItems.infusedDiamond); // Slime + Feathers
		addRecipe(bow, UpgradeRegistry.BowArrowSpeed.setLevel(2), UpgradeRegistry.BowArrowSpeed.setLevel(1), Items.slime_ball, Items.slime_ball, Items.feather, Items.feather, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.BowArrowSpeed.setLevel(3), UpgradeRegistry.BowArrowSpeed.setLevel(2), Items.slime_ball, Items.slime_ball, Items.feather, Items.feather, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.BowArrowSpeed.setLevel(4), UpgradeRegistry.BowArrowSpeed.setLevel(3), Items.slime_ball, Items.slime_ball, Items.feather, Items.feather, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.BowArrowSpeed.setLevel(5), UpgradeRegistry.BowArrowSpeed.setLevel(4), Items.slime_ball, Items.slime_ball, Items.feather, Items.feather, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(bow, UpgradeRegistry.BowDrawSpeed.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.sugar, Items.sugar, Items.string, Items.string, ModItems.infusedDiamond, ModItems.infusedDiamond); // Sugar + String
		addRecipe(bow, UpgradeRegistry.BowDrawSpeed.setLevel(2), UpgradeRegistry.BowDrawSpeed.setLevel(1), Items.sugar, Items.sugar, Items.string, Items.string, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.BowDrawSpeed.setLevel(3), UpgradeRegistry.BowDrawSpeed.setLevel(2), Items.sugar, Items.sugar, Items.string, Items.string, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.BowDrawSpeed.setLevel(4), UpgradeRegistry.BowDrawSpeed.setLevel(3), Items.sugar, Items.sugar, Items.string, Items.string, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.BowDrawSpeed.setLevel(5), UpgradeRegistry.BowDrawSpeed.setLevel(4), Items.sugar, Items.sugar, Items.string, Items.string, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(bow, UpgradeRegistry.WeaponKnockback.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.slime_ball, Items.slime_ball, ModItems.infusedDiamond, ModItems.infusedDiamond);// Slime Ball
		addRecipe(bow, UpgradeRegistry.WeaponKnockback.setLevel(2), UpgradeRegistry.WeaponKnockback.setLevel(1), Blocks.piston, Blocks.piston, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponKnockback.setLevel(3), UpgradeRegistry.WeaponKnockback.setLevel(2), Blocks.piston, Blocks.piston, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponKnockback.setLevel(4), UpgradeRegistry.WeaponKnockback.setLevel(3), Blocks.sticky_piston, Blocks.sticky_piston, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponKnockback.setLevel(5), UpgradeRegistry.WeaponKnockback.setLevel(4), Blocks.sticky_piston, Blocks.sticky_piston, ModItems.platedDiamond, ModItems.platedDiamond);

		ItemStack inkSac = new ItemStack(Items.dye, 1, 0);
		addRecipe(bow, UpgradeRegistry.WeaponBlind.setLevel(1), UpgradeRegistry.BaseUpgrade, inkSac, inkSac, inkSac, inkSac, ModItems.infusedStar, ModItems.infusedStar); // Soul Sand + Ink Sac
		addRecipe(bow, UpgradeRegistry.WeaponBlind.setLevel(2), UpgradeRegistry.WeaponBlind.setLevel(1), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(bow, UpgradeRegistry.WeaponBlind.setLevel(3), UpgradeRegistry.WeaponBlind.setLevel(2), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(bow, UpgradeRegistry.WeaponBlind.setLevel(4), UpgradeRegistry.WeaponBlind.setLevel(3), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedStar, ModItems.platedStar);
		addRecipe(bow, UpgradeRegistry.WeaponBlind.setLevel(5), UpgradeRegistry.WeaponBlind.setLevel(4), inkSac, inkSac, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		addRecipe(bow, UpgradeRegistry.WeaponSlow.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedDiamond, ModItems.infusedDiamond); // Cobweb
		addRecipe(bow, UpgradeRegistry.WeaponSlow.setLevel(2), UpgradeRegistry.WeaponSlow.setLevel(1), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponSlow.setLevel(3), UpgradeRegistry.WeaponSlow.setLevel(2), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.WeaponSlow.setLevel(4), UpgradeRegistry.WeaponSlow.setLevel(3), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponSlow.setLevel(5), UpgradeRegistry.WeaponSlow.setLevel(4), Blocks.web, Blocks.web, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(bow, UpgradeRegistry.WeaponPhysicalDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.infusedDiamond, ModItems.infusedDiamond);// Quartz
		addRecipe(bow, UpgradeRegistry.WeaponPhysicalDamage.setLevel(2), UpgradeRegistry.WeaponPhysicalDamage.setLevel(1), Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponPhysicalDamage.setLevel(3), UpgradeRegistry.WeaponPhysicalDamage.setLevel(2), Items.quartz, Items.quartz, Items.quartz, Items.quartz, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.WeaponPhysicalDamage.setLevel(4), UpgradeRegistry.WeaponPhysicalDamage.setLevel(3), Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponPhysicalDamage.setLevel(5), UpgradeRegistry.WeaponPhysicalDamage.setLevel(4), Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, Blocks.quartz_block, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		ItemStack witherSkull = new ItemStack(Items.skull, 1, 1);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, witherSkull, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar, ModItems.infusedStar);// Wither Skeleton Skull
		addRecipe(bow, UpgradeRegistry.WeaponWitherDamage.setLevel(2), UpgradeRegistry.WeaponWitherDamage.setLevel(1), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDamage.setLevel(3), UpgradeRegistry.WeaponWitherDamage.setLevel(2), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDamage.setLevel(4), UpgradeRegistry.WeaponWitherDamage.setLevel(3), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModItems.platedStar, ModItems.platedStar);
		addRecipe(bow, UpgradeRegistry.WeaponWitherDamage.setLevel(5), UpgradeRegistry.WeaponWitherDamage.setLevel(4), witherSkull, witherSkull, witherSkull, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		ItemStack potionHarming = new ItemStack(Items.potionitem, 1, 8204);
		ItemStack potionHarmingII = new ItemStack(Items.potionitem, 1, 8236);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, potionHarming, potionHarming, potionHarming, ModItems.infusedDiamond, ModItems.infusedDiamond, ModItems.infusedDiamond);// Potion of Harming
		addRecipe(bow, UpgradeRegistry.WeaponMagicDamage.setLevel(2), UpgradeRegistry.WeaponMagicDamage.setLevel(1), potionHarming, potionHarming, potionHarming, ModItems.crystalDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDamage.setLevel(3), UpgradeRegistry.WeaponMagicDamage.setLevel(2), potionHarming, potionHarming, potionHarming, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDamage.setLevel(4), UpgradeRegistry.WeaponMagicDamage.setLevel(3), potionHarmingII, potionHarmingII, potionHarmingII, ModItems.platedDiamond, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponMagicDamage.setLevel(5), UpgradeRegistry.WeaponMagicDamage.setLevel(4), potionHarmingII, potionHarmingII, potionHarmingII, ModBlocks.infusedBlock, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		addRecipe(bow, UpgradeRegistry.WeaponFireDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.infusedDiamond, ModItems.infusedDiamond, ModItems.infusedDiamond);// Blaze Rod
		addRecipe(bow, UpgradeRegistry.WeaponFireDamage.setLevel(2), UpgradeRegistry.WeaponFireDamage.setLevel(1), Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.crystalDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponFireDamage.setLevel(3), UpgradeRegistry.WeaponFireDamage.setLevel(2), Items.blaze_rod, Items.blaze_rod, Items.blaze_rod, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(bow, UpgradeRegistry.WeaponFireDamage.setLevel(4), UpgradeRegistry.WeaponFireDamage.setLevel(3), Items.fire_charge, Items.fire_charge, Items.fire_charge, ModItems.platedDiamond, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(bow, UpgradeRegistry.WeaponFireDamage.setLevel(5), UpgradeRegistry.WeaponFireDamage.setLevel(4), Items.fire_charge, Items.fire_charge, Items.fire_charge, ModBlocks.infusedBlock, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

//		if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
//		{
//			try
//			{
//				ExUHandler.addDivineRecipes(bow);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
//		{
//			try
//			{
//				DraconicEvolutionHandler.addChaosRecipes(bow);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("Thaumcraft") && ConfigHandler.thaumcraftIntegration)
//		{
//			try
//			{
//				ThaumcraftHandler.addTaintRecipes(bow);
//			}
//			catch (Exception e)
//			{}
//		}
//
//		if (Loader.isModLoaded("arsmagica2") && ConfigHandler.arsMagicaIntegration)
//		{
//			try
//			{
//				ArsMagicaHandler.addElementalRecipes(bow);
//			}
//			catch (Exception e)
//			{}
//		}
	}

	private static void armorRecipes()
	{
		ArrayList<ItemStack> armorSet = new ArrayList<ItemStack>();
		armorSet.add(new ItemStack(ModArmory.infusedHelm));
		armorSet.add(new ItemStack(ModArmory.infusedPlate));
		armorSet.add(new ItemStack(ModArmory.infusedPants));
		armorSet.add(new ItemStack(ModArmory.infusedBoots));

		for (ItemStack armor : armorSet)
		{
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalThorns.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.cactus, Blocks.cactus, ModItems.infusedDiamond, ModItems.infusedDiamond); // Cactus
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalThorns.setLevel(2), UpgradeRegistry.ArmorPhysicalThorns.setLevel(1), Blocks.cactus, Blocks.cactus, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalThorns.setLevel(3), UpgradeRegistry.ArmorPhysicalThorns.setLevel(2), Blocks.cactus, Blocks.cactus, ModItems.platedDiamond, ModItems.platedDiamond);

			ItemStack poisonPotion = new ItemStack(Items.potionitem, 1, 8260);
			addRecipe(armor, UpgradeRegistry.ArmorMagicThorns.setLevel(1), UpgradeRegistry.BaseUpgrade, poisonPotion, poisonPotion, ModItems.infusedDiamond, ModItems.infusedDiamond); // Potion of Poison
			addRecipe(armor, UpgradeRegistry.ArmorMagicThorns.setLevel(2), UpgradeRegistry.ArmorMagicThorns.setLevel(1), poisonPotion, poisonPotion, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorMagicThorns.setLevel(3), UpgradeRegistry.ArmorMagicThorns.setLevel(2), poisonPotion, poisonPotion, ModItems.platedDiamond, ModItems.platedDiamond);

			addRecipe(armor, UpgradeRegistry.ArmorBlindThorns.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.soul_sand, Blocks.soul_sand, ModItems.infusedStar, ModItems.infusedStar); // Soul Sand
			addRecipe(armor, UpgradeRegistry.ArmorBlindThorns.setLevel(2), UpgradeRegistry.ArmorBlindThorns.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(armor, UpgradeRegistry.ArmorBlindThorns.setLevel(3), UpgradeRegistry.ArmorBlindThorns.setLevel(2), Blocks.soul_sand, Blocks.soul_sand, ModItems.platedStar, ModItems.platedStar);

			ItemStack notchApple = new ItemStack(Items.golden_apple, 1, 1);
			addRecipe(armor, UpgradeRegistry.ArmorAbsorption.setLevel(1), UpgradeRegistry.BaseUpgrade, notchApple, notchApple, Items.glowstone_dust, Items.glowstone_dust, ModItems.infusedDiamond, ModItems.infusedDiamond); // Notch Apple + Glowstone
			addRecipe(armor, UpgradeRegistry.ArmorAbsorption.setLevel(2), UpgradeRegistry.ArmorAbsorption.setLevel(1), notchApple, notchApple, Items.glowstone_dust, Items.glowstone_dust, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorAbsorption.setLevel(3), UpgradeRegistry.ArmorAbsorption.setLevel(2), notchApple, notchApple, Items.glowstone_dust, Items.glowstone_dust, ModItems.infusedIngot, ModItems.infusedIngot);
			addRecipe(armor, UpgradeRegistry.ArmorAbsorption.setLevel(4), UpgradeRegistry.ArmorAbsorption.setLevel(3), notchApple, notchApple, Items.glowstone_dust, Items.glowstone_dust, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorAbsorption.setLevel(5), UpgradeRegistry.ArmorAbsorption.setLevel(4), notchApple, notchApple, Items.glowstone_dust, Items.glowstone_dust, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			addRecipe(armor, UpgradeRegistry.ArmorHealthBoost.setLevel(1), UpgradeRegistry.BaseUpgrade, notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedDiamond, ModItems.infusedDiamond); // Notch Apple + Milk Bucket
			addRecipe(armor, UpgradeRegistry.ArmorHealthBoost.setLevel(2), UpgradeRegistry.ArmorHealthBoost.setLevel(1), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorHealthBoost.setLevel(3), UpgradeRegistry.ArmorHealthBoost.setLevel(2), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedIngot, ModItems.infusedIngot);
			addRecipe(armor, UpgradeRegistry.ArmorHealthBoost.setLevel(4), UpgradeRegistry.ArmorHealthBoost.setLevel(3), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorHealthBoost.setLevel(5), UpgradeRegistry.ArmorHealthBoost.setLevel(4), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModBlocks.infusedStarmetal, ModBlocks.infusedBlock);

			addRecipe(armor, UpgradeRegistry.ArmorResistance.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.diamond, Items.diamond, Items.diamond, Items.diamond, ModItems.infusedStar, ModItems.infusedStar); // Diamond
			addRecipe(armor, UpgradeRegistry.ArmorResistance.setLevel(2), UpgradeRegistry.ArmorResistance.setLevel(1), Items.diamond, Items.diamond, Items.diamond, Items.diamond, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(armor, UpgradeRegistry.ArmorResistance.setLevel(3), UpgradeRegistry.ArmorResistance.setLevel(2), Items.diamond, Items.diamond, Items.nether_star, Items.nether_star, ModItems.infusedIngot, ModItems.infusedIngot);
			addRecipe(armor, UpgradeRegistry.ArmorResistance.setLevel(4), UpgradeRegistry.ArmorResistance.setLevel(3), Items.diamond, Items.diamond, Items.diamond, Items.diamond, ModItems.platedStar, ModItems.platedStar);
			addRecipe(armor, UpgradeRegistry.ArmorResistance.setLevel(5), UpgradeRegistry.ArmorResistance.setLevel(4), Items.diamond, Items.diamond, Items.diamond, Items.diamond, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

			addRecipe(armor, UpgradeRegistry.ArmorPhysicalProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, ModItems.infusedDiamond, ModItems.infusedDiamond);// Iron
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalProtection.setLevel(2), UpgradeRegistry.ArmorPhysicalProtection.setLevel(1), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalProtection.setLevel(3), UpgradeRegistry.ArmorPhysicalProtection.setLevel(2), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorPhysicalProtection.setLevel(4), UpgradeRegistry.ArmorPhysicalProtection.setLevel(3), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			addRecipe(armor, UpgradeRegistry.ArmorFireProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.magma_cream, Items.magma_cream, Items.magma_cream, Items.magma_cream, ModItems.infusedDiamond, ModItems.infusedDiamond);// Magma Cream
			addRecipe(armor, UpgradeRegistry.ArmorFireProtection.setLevel(2), UpgradeRegistry.ArmorFireProtection.setLevel(1), Items.magma_cream, Items.magma_cream, Items.magma_cream, Items.magma_cream, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorFireProtection.setLevel(3), UpgradeRegistry.ArmorFireProtection.setLevel(2), Items.magma_cream, Items.magma_cream, Items.magma_cream, Items.magma_cream, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorFireProtection.setLevel(4), UpgradeRegistry.ArmorFireProtection.setLevel(3), Items.magma_cream, Items.magma_cream, Items.magma_cream, Items.magma_cream, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			addRecipe(armor, UpgradeRegistry.ArmorBlastProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, ModItems.infusedDiamond, ModItems.infusedDiamond);// Obsidian
			addRecipe(armor, UpgradeRegistry.ArmorBlastProtection.setLevel(2), UpgradeRegistry.ArmorBlastProtection.setLevel(1), Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorBlastProtection.setLevel(3), UpgradeRegistry.ArmorBlastProtection.setLevel(2), Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorBlastProtection.setLevel(4), UpgradeRegistry.ArmorBlastProtection.setLevel(3), Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, Blocks.obsidian, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			addRecipe(armor, UpgradeRegistry.ArmorProjectileProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.arrow, Items.arrow, Items.arrow, Items.arrow, ModItems.infusedDiamond, ModItems.infusedDiamond);// Arrow
			addRecipe(armor, UpgradeRegistry.ArmorProjectileProtection.setLevel(2), UpgradeRegistry.ArmorProjectileProtection.setLevel(1), Items.arrow, Items.arrow, Items.arrow, Items.arrow, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorProjectileProtection.setLevel(3), UpgradeRegistry.ArmorProjectileProtection.setLevel(2), Items.arrow, Items.arrow, Items.arrow, Items.arrow, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(armor, UpgradeRegistry.ArmorProjectileProtection.setLevel(4), UpgradeRegistry.ArmorProjectileProtection.setLevel(3), Items.arrow, Items.arrow, Items.arrow, Items.arrow, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			ItemStack witherSkull = new ItemStack(Items.skull, 1, 1);
			addRecipe(armor, UpgradeRegistry.ArmorWitherProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, witherSkull, witherSkull, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar);// Wither Skeleton Skull
			addRecipe(armor, UpgradeRegistry.ArmorWitherProtection.setLevel(2), UpgradeRegistry.ArmorWitherProtection.setLevel(1), witherSkull, witherSkull, witherSkull, witherSkull, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(armor, UpgradeRegistry.ArmorWitherProtection.setLevel(3), UpgradeRegistry.ArmorWitherProtection.setLevel(2), witherSkull, witherSkull, witherSkull, witherSkull, ModItems.platedStar, ModItems.platedStar);
			addRecipe(armor, UpgradeRegistry.ArmorWitherProtection.setLevel(4), UpgradeRegistry.ArmorWitherProtection.setLevel(3), witherSkull, witherSkull, witherSkull, witherSkull, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

			ItemStack potionHealth = new ItemStack(Items.potionitem, 1, 8197);
			addRecipe(armor, UpgradeRegistry.ArmorMagicProtection.setLevel(1), UpgradeRegistry.BaseUpgrade, potionHealth, potionHealth, potionHealth, potionHealth, ModItems.infusedStar, ModItems.infusedStar);// Potion of Instant Health
			addRecipe(armor, UpgradeRegistry.ArmorMagicProtection.setLevel(2), UpgradeRegistry.ArmorMagicProtection.setLevel(1), potionHealth, potionHealth, potionHealth, potionHealth, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(armor, UpgradeRegistry.ArmorMagicProtection.setLevel(3), UpgradeRegistry.ArmorMagicProtection.setLevel(2), potionHealth, potionHealth, potionHealth, potionHealth, ModItems.platedStar, ModItems.platedStar);
			addRecipe(armor, UpgradeRegistry.ArmorMagicProtection.setLevel(4), UpgradeRegistry.ArmorMagicProtection.setLevel(3), potionHealth, potionHealth, potionHealth, potionHealth, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
			
			addRecipe(armor, 2, UpgradeRegistry.ArmorStepAssist.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.leather, Items.leather, Items.slime_ball, Items.slime_ball, Items.string, Items.string, ModItems.infusedIngot, ModItems.infusedIngot);
			
			addRecipe(armor, 3, UpgradeRegistry.ArmorRunSpeed.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.sugar, Items.sugar, Items.glowstone_dust, Items.glowstone_dust, ModItems.infusedDiamond, ModItems.infusedDiamond);
			addRecipe(armor, 3, UpgradeRegistry.ArmorRunSpeed.setLevel(2), UpgradeRegistry.ArmorRunSpeed.setLevel(1), Items.sugar, Items.sugar, Blocks.glowstone, Blocks.glowstone, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(armor, 3, UpgradeRegistry.ArmorRunSpeed.setLevel(3), UpgradeRegistry.ArmorRunSpeed.setLevel(2), Items.sugar, Items.sugar, Blocks.ice, Blocks.ice, ModItems.platedDiamond, ModItems.platedDiamond);

//			if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
//			{
//				try
//				{
//					DraconicEvolutionHandler.addChaosProtectionRecipes(armor);
//				}
//				catch (Exception e)
//				{}
//			}
//
//			if (Loader.isModLoaded("Botania") && ConfigHandler.botaniaIntegration)
//			{
//				try
//				{
//					BotaniaHandler.addManaDiscountRecipes(armor);
//				}
//				catch (Exception e)
//				{}
//			}
//
//			if (Loader.isModLoaded("Thaumcraft") && ConfigHandler.thaumcraftIntegration)
//			{
//				try
//				{
//					ThaumcraftHandler.addTCArmorRecipes(armor);
//				}
//				catch (Exception e)
//				{}
//			}
		}
	}

	private static void amuletRecipes()
	{
		ItemStack amulet = new ItemStack(ModArmory.infusedAmulet);

		addRecipe(amulet, UpgradeRegistry.AmuletFlight.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.feather, Items.feather, Items.feather, Blocks.dragon_egg, ModItems.platedDiamond, ModItems.platedDiamond);

		ItemStack lapis = new ItemStack(Items.dye, 1, 4);
		addRecipe(amulet, UpgradeRegistry.AmuletLooting.setLevel(1), UpgradeRegistry.BaseUpgrade, lapis, lapis, ModItems.infusedShard);
		addRecipe(amulet, UpgradeRegistry.AmuletLooting.setLevel(2), UpgradeRegistry.AmuletLooting.setLevel(1), lapis, lapis, ModBlocks.shardBlock);
		addRecipe(amulet, UpgradeRegistry.AmuletLooting.setLevel(3), UpgradeRegistry.AmuletLooting.setLevel(2), lapis, lapis, ModItems.infusedDiamond);
		addRecipe(amulet, UpgradeRegistry.AmuletLooting.setLevel(4), UpgradeRegistry.AmuletLooting.setLevel(3), Blocks.lapis_block, Blocks.lapis_block, ModItems.crystalDiamond);
		addRecipe(amulet, UpgradeRegistry.AmuletLooting.setLevel(5), UpgradeRegistry.AmuletLooting.setLevel(4), Blocks.lapis_block, Blocks.lapis_block, ModItems.infusedIngot);

		if (!Loader.isModLoaded("TravellersGear"))
		{
			addRecipe(amulet, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.infusedDiamond, ModItems.infusedDiamond);
			addRecipe(amulet, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaubleFireImmunity.setLevel(1), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(amulet, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaubleFireImmunity.setLevel(1), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.infusedIngot, ModItems.infusedIngot);
			addRecipe(amulet, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaubleFireImmunity.setLevel(1), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(amulet, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaubleFireImmunity.setLevel(1), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			addRecipe(amulet, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.infusedDiamond, ModItems.infusedDiamond);
			addRecipe(amulet, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaublePoisonImmunity.setLevel(1), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.crystalDiamond, ModItems.crystalDiamond);
			addRecipe(amulet, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaublePoisonImmunity.setLevel(1), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.infusedIngot, ModItems.infusedIngot);
			addRecipe(amulet, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaublePoisonImmunity.setLevel(1), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.platedDiamond, ModItems.platedDiamond);
			addRecipe(amulet, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaublePoisonImmunity.setLevel(1), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

			ItemStack witherSkull = new ItemStack(Items.skull, 1, 1);
			addRecipe(amulet, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar);
			addRecipe(amulet, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaubleWitherImmunity.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar);
			addRecipe(amulet, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaubleWitherImmunity.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(amulet, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaubleWitherImmunity.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.platedStar, ModItems.platedStar);
			addRecipe(amulet, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaubleWitherImmunity.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

//			if (Loader.isModLoaded("Thaumcraft")) ThaumcraftHandler.addTaintInfusionRecipes(amulet);
		}
	}

	private static void ringRecipes()
	{
		ItemStack ring = new ItemStack(ModArmory.infusedRing);

		addRecipe(ring, UpgradeRegistry.RingPotionSwiftness.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.sugar, Items.sugar, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionSwiftness.setLevel(2), UpgradeRegistry.RingPotionSwiftness.setLevel(1), Items.nether_wart, Items.nether_wart, Items.sugar, Items.sugar, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionSwiftness.setLevel(3), UpgradeRegistry.RingPotionSwiftness.setLevel(2), Items.nether_wart, Items.nether_wart, Items.sugar, Items.sugar, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionHaste.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.redstone, Items.redstone, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionHaste.setLevel(2), UpgradeRegistry.RingPotionHaste.setLevel(1), Items.nether_wart, Items.nether_wart, Items.redstone, Items.redstone, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionHaste.setLevel(3), UpgradeRegistry.RingPotionHaste.setLevel(2), Items.nether_wart, Items.nether_wart, Items.redstone, Items.redstone, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionStrength.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.blaze_powder, Items.blaze_powder, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionStrength.setLevel(2), UpgradeRegistry.RingPotionStrength.setLevel(1), Items.nether_wart, Items.nether_wart, Items.blaze_powder, Items.blaze_powder, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionStrength.setLevel(3), UpgradeRegistry.RingPotionStrength.setLevel(2), Items.nether_wart, Items.nether_wart, Items.blaze_powder, Items.blaze_powder, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionJumpBoost.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.feather, Items.feather, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionJumpBoost.setLevel(2), UpgradeRegistry.RingPotionJumpBoost.setLevel(1), Items.nether_wart, Items.nether_wart, Items.feather, Items.feather, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionJumpBoost.setLevel(3), UpgradeRegistry.RingPotionJumpBoost.setLevel(2), Items.nether_wart, Items.nether_wart, Items.feather, Items.feather, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionRegeneration.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.ghast_tear, Items.ghast_tear, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionRegeneration.setLevel(2), UpgradeRegistry.RingPotionRegeneration.setLevel(1), Items.nether_wart, Items.nether_wart, Items.ghast_tear, Items.ghast_tear, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(ring, UpgradeRegistry.RingPotionRegeneration.setLevel(3), UpgradeRegistry.RingPotionRegeneration.setLevel(2), Items.nether_wart, Items.nether_wart, Items.ghast_tear, Items.ghast_tear, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionNightVision.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.golden_carrot, Items.golden_carrot, ModItems.platedDiamond, ModItems.platedDiamond);

		ItemStack pufferfish = new ItemStack(Items.fish, 1, 3);
		addRecipe(ring, UpgradeRegistry.RingPotionWaterBreathing.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, pufferfish, pufferfish, ModItems.platedDiamond, ModItems.platedDiamond);

		addRecipe(ring, UpgradeRegistry.RingPotionFireResistance.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.nether_wart, Items.nether_wart, Items.magma_cream, Items.magma_cream, ModItems.platedDiamond, ModItems.platedDiamond);
	}

	private static void beltRecipes()
	{
		ItemStack belt = new ItemStack(ModArmory.infusedBelt);

		addRecipe(belt, UpgradeRegistry.BeltCleave.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.stone_sword, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(belt, UpgradeRegistry.BeltCleave.setLevel(2), UpgradeRegistry.BeltCleave.setLevel(1), Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.iron_sword, ModItems.infusedStar, ModItems.infusedStar);
		addRecipe(belt, UpgradeRegistry.BeltCleave.setLevel(3), UpgradeRegistry.BeltCleave.setLevel(2), Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.iron_sword, ModItems.crystalStar, ModItems.crystalStar);
		addRecipe(belt, UpgradeRegistry.BeltCleave.setLevel(4), UpgradeRegistry.BeltCleave.setLevel(3), Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.iron_sword, ModItems.platedStar, ModItems.platedStar);
		addRecipe(belt, UpgradeRegistry.BeltCleave.setLevel(5), UpgradeRegistry.BeltCleave.setLevel(4), Items.gunpowder, Items.gunpowder, Items.gunpowder, Items.iron_sword, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

		addRecipe(belt, UpgradeRegistry.BeltKnockback.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.obsidian, Blocks.piston, Blocks.piston, Blocks.piston, ModItems.infusedDiamond, ModItems.infusedDiamond);
		addRecipe(belt, UpgradeRegistry.BeltKnockback.setLevel(2), UpgradeRegistry.BeltKnockback.setLevel(1), Blocks.obsidian, Blocks.piston, Blocks.piston, Blocks.piston, ModItems.crystalDiamond, ModItems.crystalDiamond);
		addRecipe(belt, UpgradeRegistry.BeltKnockback.setLevel(3), UpgradeRegistry.BeltKnockback.setLevel(2), Blocks.obsidian, Blocks.piston, Blocks.piston, Blocks.piston, ModItems.infusedIngot, ModItems.infusedIngot);
		addRecipe(belt, UpgradeRegistry.BeltKnockback.setLevel(4), UpgradeRegistry.BeltKnockback.setLevel(3), Blocks.obsidian, Blocks.piston, Blocks.piston, Blocks.piston, ModItems.platedDiamond, ModItems.platedDiamond);
		addRecipe(belt, UpgradeRegistry.BeltKnockback.setLevel(5), UpgradeRegistry.BeltKnockback.setLevel(4), Blocks.obsidian, Blocks.piston, Blocks.piston, Blocks.piston, ModBlocks.infusedBlock, ModBlocks.infusedBlock);

		if (!Loader.isModLoaded("TravellersGear"))
		{
			ItemStack notchApple = new ItemStack(Items.golden_apple, 1, 1);
			addRecipe(belt, UpgradeRegistry.BaubleHealthBoost.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.golden_apple, Items.golden_apple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedStar, ModItems.infusedStar);
			addRecipe(belt, UpgradeRegistry.BaubleHealthBoost.setLevel(2), UpgradeRegistry.BaubleHealthBoost.setLevel(1), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedStar, ModItems.infusedStar);
			addRecipe(belt, UpgradeRegistry.BaubleHealthBoost.setLevel(3), UpgradeRegistry.BaubleHealthBoost.setLevel(2), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.crystalStar, ModItems.crystalStar);
			addRecipe(belt, UpgradeRegistry.BaubleHealthBoost.setLevel(4), UpgradeRegistry.BaubleHealthBoost.setLevel(3), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.platedStar, ModItems.platedStar);
			addRecipe(belt, UpgradeRegistry.BaubleHealthBoost.setLevel(5), UpgradeRegistry.BaubleHealthBoost.setLevel(4), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);

			addRecipe(belt, UpgradeRegistry.BaubleMiningLimiter.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.gold_block, Items.string, Items.string, Items.string, Items.string, ModItems.infusedIngot);
		}
	}

//	private static void pauldronRecipes()
//	{
//		ItemStack pauldron = new ItemStack(ModArmory.infusedPauldrons);
//
//		addRecipe(pauldron, UpgradeRegistry.BaubleFireImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.infusedDiamond, ModItems.infusedDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaubleFireImmunity.setLevel(2), UpgradeRegistry.BaubleFireImmunity.setLevel(1), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaubleFireImmunity.setLevel(3), UpgradeRegistry.BaubleFireImmunity.setLevel(2), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.infusedIngot, ModItems.infusedIngot);
//		addRecipe(pauldron, UpgradeRegistry.BaubleFireImmunity.setLevel(4), UpgradeRegistry.BaubleFireImmunity.setLevel(3), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModItems.platedDiamond, ModItems.platedDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaubleFireImmunity.setLevel(5), UpgradeRegistry.BaubleFireImmunity.setLevel(4), Items.blaze_powder, Items.blaze_powder, Items.gunpowder, Items.gunpowder, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//
//		addRecipe(pauldron, UpgradeRegistry.BaublePoisonImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.infusedDiamond, ModItems.infusedDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaublePoisonImmunity.setLevel(2), UpgradeRegistry.BaublePoisonImmunity.setLevel(1), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaublePoisonImmunity.setLevel(3), UpgradeRegistry.BaublePoisonImmunity.setLevel(2), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.infusedIngot, ModItems.infusedIngot);
//		addRecipe(pauldron, UpgradeRegistry.BaublePoisonImmunity.setLevel(4), UpgradeRegistry.BaublePoisonImmunity.setLevel(3), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModItems.platedDiamond, ModItems.platedDiamond);
//		addRecipe(pauldron, UpgradeRegistry.BaublePoisonImmunity.setLevel(5), UpgradeRegistry.BaublePoisonImmunity.setLevel(4), Items.fermented_spider_eye, Items.fermented_spider_eye, Items.nether_wart, Items.nether_wart, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//
//		ItemStack witherSkull = new ItemStack(Items.skull, 1, 1);
//		addRecipe(pauldron, UpgradeRegistry.BaubleWitherImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar);
//		addRecipe(pauldron, UpgradeRegistry.BaubleWitherImmunity.setLevel(2), UpgradeRegistry.BaubleWitherImmunity.setLevel(1), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.infusedStar, ModItems.infusedStar);
//		addRecipe(pauldron, UpgradeRegistry.BaubleWitherImmunity.setLevel(3), UpgradeRegistry.BaubleWitherImmunity.setLevel(2), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.crystalStar, ModItems.crystalStar);
//		addRecipe(pauldron, UpgradeRegistry.BaubleWitherImmunity.setLevel(4), UpgradeRegistry.BaubleWitherImmunity.setLevel(3), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModItems.platedStar, ModItems.platedStar);
//		addRecipe(pauldron, UpgradeRegistry.BaubleWitherImmunity.setLevel(5), UpgradeRegistry.BaubleWitherImmunity.setLevel(4), Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, Blocks.soul_sand, witherSkull, witherSkull, witherSkull, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//
//		if (Loader.isModLoaded("Thaumcraft")) ThaumcraftHandler.addTaintInfusionRecipes(pauldron);
//	}

//	private static void vambraceRecipes()
//	{
//		ItemStack vambrace = new ItemStack(ModArmory.infusedVambraces);
//
//		ItemStack notchApple = new ItemStack(Items.golden_apple, 1, 1);
//		addRecipe(vambrace, UpgradeRegistry.BaubleHealthBoost.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.golden_apple, Items.golden_apple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedStar, ModItems.infusedStar);
//		addRecipe(vambrace, UpgradeRegistry.BaubleHealthBoost.setLevel(2), UpgradeRegistry.BaubleHealthBoost.setLevel(1), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.infusedStar, ModItems.infusedStar);
//		addRecipe(vambrace, UpgradeRegistry.BaubleHealthBoost.setLevel(3), UpgradeRegistry.BaubleHealthBoost.setLevel(2), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.crystalStar, ModItems.crystalStar);
//		addRecipe(vambrace, UpgradeRegistry.BaubleHealthBoost.setLevel(4), UpgradeRegistry.BaubleHealthBoost.setLevel(3), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModItems.platedStar, ModItems.platedStar);
//		addRecipe(vambrace, UpgradeRegistry.BaubleHealthBoost.setLevel(5), UpgradeRegistry.BaubleHealthBoost.setLevel(4), notchApple, notchApple, Items.milk_bucket, Items.milk_bucket, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//
//		addRecipe(vambrace, UpgradeRegistry.BaubleMiningLimiter.setLevel(1), UpgradeRegistry.BaseUpgrade, Blocks.gold_block, Items.string, Items.string, Items.string, Items.string, ModItems.infusedIngot);
//	}

	/**
	 * Adds a recipe for an upgrade using the Infusion structure.
	 * 
	 * @param item
	 *            The item that the upgrade can be applied to. item.getItem().getClass() is used to differentiate between items.
	 * @param outerItems
	 *            The list of items that must be in the outer ring of the Infusion structure, starting at the North, and going clockwise. All items must be non-null and have a non-zero stack size.
	 * @param upgrade
	 *            The upgrade that results from the recipe.
	 * @param upgradeReq
	 *            The prerequisite upgrade for the recipe's upgrade, i.e. Fire II requiring Fire I. If set to null, there is no prerequisite.
	 * @param recipeItems
	 *            The list of items that must be in the inner ring of the Infusion structure, starting at the North, and going clockwise. All items must be non-null and have a non-zero stack size.
	 */
	public static void addRecipe(ItemStack item, Upgrade upgrade, Upgrade upgradeReq, Object... recipeItems)
	{
		addRecipe(item, upgrade, new Upgrade[] { upgradeReq }, recipeItems);
	}

	public static void addRecipe(ItemStack item, int armorType, Upgrade upgrade, Upgrade upgradeReq, Object... recipeItems)
	{
		addRecipe(item, armorType, upgrade, new Upgrade[] { upgradeReq }, recipeItems);
	}

	public static void addRecipe(ItemStack item, Upgrade upgrade, Upgrade[] upgradeReqs, Object... recipeItems)
	{
		addRecipe(item, -1, upgrade, upgradeReqs, recipeItems);
	}

	public static void addRecipe(ItemStack item, int armorType, Upgrade upgrade, Upgrade[] upgradeReqs, Object... recipeItems)
	{
		UpgradeRecipe recipe = new UpgradeRecipe(item, armorType, upgrade, upgradeReqs, recipeItems);
		ArrayList<UpgradeRecipe> recipeList = upgradeRecipes.get(item.getItem().getClass());
		if (recipeList == null) recipeList = new ArrayList<UpgradeRecipe>();
		recipeList.add(recipe);
		upgradeRecipes.put(item.getItem().getClass(), recipeList);
	}

	/**
	 * Returns the upgrade that would be applied to the item in the central infuser slot, based on the items around it
	 * 
	 * @param item
	 *            The item being upgraded
	 * @param pylonItems
	 *            An ArrayList of the items found in the pylons found within 5 blocks of the infuser.
	 * @return The Upgrade resulting from the given item and ArrayLists. Returns null if any parts are null, the item contains an incompatible upgrade, or the item does not meet any prerequisites for upgrades.
	 */
	public static Upgrade checkRecipe(ItemStack item, ArrayList<ItemStack> pylonItems)
	{
		if (item == null || pylonItems == null) return null;

		Iterator<ItemStack> iter = pylonItems.iterator();
		while (iter.hasNext())
			if (iter.next() == null) iter.remove();
		if (pylonItems.size() == 0) return null;

		ArrayList<UpgradeRecipe> availableUpgrades = upgradeRecipes.get(item.getItem().getClass());
		if (availableUpgrades == null || availableUpgrades.size() == 0) return null; // Of course, if there are none, the check fails

		ArrayList<Upgrade> currentUpgrades = getCurrentUpgrades(item);

		for (UpgradeRecipe recipe : availableUpgrades)
		{
			Upgrade upgrade = recipe.getUpgrade();
			if (upgrade == null) continue;
			ArrayList<Upgrade> requirements = recipe.getRequirements();
			if (requirements == null) continue;
			int slot = recipe.getSlot();
			if (slot != -1 && item.getItem() instanceof ItemArmor && ((ItemArmor) item.getItem()).armorType != slot) continue;

			boolean hasBetter = false;
			for (Upgrade current : currentUpgrades)
			{
				if (current == null) continue;
				if (current.name.equals(upgrade.name) && upgrade.level <= current.level)
				{
					hasBetter = true;
					break;
				}
			}
			if (hasBetter) continue;

			currentUpgrades.add(UpgradeRegistry.BaseUpgrade);
			int reqsMet = 0;
			for (int i = 0; i < currentUpgrades.size(); i++)
			{
				for (int j = 0; j < requirements.size(); j++)
				{
					if (reqsMet == requirements.size()) break;

					Upgrade has = currentUpgrades.get(i);
					Upgrade needs = requirements.get(j);
					if (has == null) reqsMet++;
					else if (has.name.equals("BaseUpgrade")) reqsMet++;
					else if (has.name.equals(needs.name) && has.level >= needs.level) reqsMet++;
				}
			}
			if (reqsMet != requirements.size()) continue;

			boolean incompatible = false;
			for (Upgrade compat : currentUpgrades)
			{
				if (compat == null) continue;
				if (upgrade.incompatibleUpgrades.contains(compat.name))
				{
					incompatible = true;
					break;
				}
			}
			if (incompatible) continue;

			ArrayList<ItemStack> requiredItems = (ArrayList<ItemStack>) recipe.getRecipeItems().clone();
			ArrayList<ItemStack> currentItems = (ArrayList<ItemStack>) pylonItems.clone();
			boolean removed = true;
			for (int i = 0; i < currentItems.size(); i++)
			{
				for (int j = 0; j < requiredItems.size(); j++)
				{
					if (i < 0 || j < 0) continue;
					if (currentItems.get(i).isItemEqual(requiredItems.get(j)))
					{
						currentItems.remove(i);
						requiredItems.remove(j);
						i--;
						j--;
					}
				}
			}
			if (currentItems.size() != 0) continue;
			if (requiredItems.size() != 0) continue;

			return upgrade;
		}
		return null;
	}

	/**
	 * Gets the list of all upgrades currently on the item
	 * 
	 * @param item
	 *            The item from which the list of upgrades is being retrieved
	 * @return The list of empty upgrades found on the item. These upgrades have no data beyond name and level. The list ends with one null upgrade, to allow easier checking for upgrades with no prerequisite
	 */
	public static ArrayList<Upgrade> getCurrentUpgrades(ItemStack item)
	{
		ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
		if (!item.hasTagCompound()) return upgrades;
		NBTTagCompound itemCompound = item.getTagCompound();
		if (!itemCompound.hasKey("Upgrades")) return upgrades;
		NBTTagList upgradeList = itemCompound.getTagList("Upgrades", NBT.TAG_COMPOUND);

		// Go through the upgradeList tag to figure out what upgrades the item
		// has
		for (int i = 0; i < upgradeList.tagCount(); i++)
		{
			NBTTagCompound tag = upgradeList.getCompoundTagAt(i);
			upgrades.add(new Upgrade(tag.getString("Name"), tag.getInteger("Level")));
		}

		// Add in the "null upgrade" to allow level 1 upgrades to be added to
		// the item
		upgrades.add(null);

		return upgrades;
	}

	/**
	 * Adds the given upgrade to the given item.
	 * 
	 * @param item
	 *            The item being given the upgrade.
	 * @param upgrade
	 *            The upgrade being added to the item.
	 * @return The item with the upgrade added. If the upgrade did not exist, the initial item is returned instead.
	 */
	public static ItemStack addUpgrade(ItemStack item, Upgrade upgrade)
	{
		// If the upgrade is blank, change nothing
		if (upgrade == null) return item;

		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());

		// Get the TagList holding the upgrade data
		NBTTagList upgradeList = item.getTagCompound().getTagList("Upgrades", NBT.TAG_COMPOUND);

		// Make a new TagCompound for the upgrade being added, and store the name and level of the upgrade
		NBTTagCompound newTag = new NBTTagCompound();
		newTag.setString("Name", upgrade.name);
		newTag.setInteger("Level", upgrade.level);

		for (int i = 0; i < upgradeList.tagCount(); i++)
		{
			// If the upgrade already exists on the item, remove the current version and add the new one
			NBTTagCompound oldUpgrade = upgradeList.getCompoundTagAt(i);
			if (oldUpgrade.getString("Name").equals(upgrade.name))
			{
				upgradeList.removeTag(i);
				if (upgrade.level != 0)
				{
					upgradeList.appendTag(newTag);
					NBTTagCompound compound = item.getTagCompound();
					int itemLevel = compound.getInteger("Level");
					compound.setInteger("Level", itemLevel + newTag.getInteger("Level") - oldUpgrade.getInteger("Level"));
					item.setTagCompound(compound);
				}
				else
				{
					NBTTagCompound compound = item.getTagCompound();
					compound.setInteger("Level", compound.getInteger("Level") - oldUpgrade.getInteger("Level"));
					item.setTagCompound(compound);
				}
				NBTTagCompound compound = item.getTagCompound();
				compound.setTag("Upgrades", upgradeList);
				item.setTagCompound(compound);
				return item;
			}
		}

		// Otherwise just add the new one
		if (upgrade.level != 0)
		{
			upgradeList.appendTag(newTag);
			NBTTagCompound compound = item.getTagCompound();
			int itemLevel = compound.getInteger("Level");
			compound.setInteger("Level", itemLevel + newTag.getInteger("Level"));
			item.setTagCompound(compound);
		}
		NBTTagCompound compound = item.getTagCompound();
		compound.setTag("Upgrades", upgradeList);
		item.setTagCompound(compound);
		return item;
	}
}

//package essenceMod.handlers.compatibility;
//
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.fml.common.Optional;
//import essenceMod.registry.ModBlocks;
//import essenceMod.registry.ModItems;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.UpgradeRegistry;
//
//public class BotaniaHandler
//{
//	@Optional.Method(modid = "Botania")
//	public static void addManaDiscountRecipes(ItemStack armor)
//	{
//		ItemStack manaSteel = new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 0);
//		ItemStack manaDiamond = new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 2);
//		ItemStack terraSteel = new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 4);
//		ItemStack elementium = new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 7);
//		ItemStack gaiaSpirit = new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 5);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorManaDiscount.setLevel(1), null, manaSteel, manaSteel, manaSteel, ModItems.infusedDiamond, ModItems.infusedDiamond, ModItems.infusedDiamond);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorManaDiscount.setLevel(2), UpgradeRegistry.ArmorManaDiscount.setLevel(1), manaDiamond, manaDiamond, manaDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorManaDiscount.setLevel(3), UpgradeRegistry.ArmorManaDiscount.setLevel(2), terraSteel, terraSteel, terraSteel, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorManaDiscount.setLevel(4), UpgradeRegistry.ArmorManaDiscount.setLevel(3), elementium, elementium, elementium, ModItems.platedDiamond, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorManaDiscount.setLevel(5), UpgradeRegistry.ArmorManaDiscount.setLevel(4), gaiaSpirit, gaiaSpirit, gaiaSpirit, ModBlocks.infusedBlock, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//	}
//}

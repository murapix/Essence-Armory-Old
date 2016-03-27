//package essenceMod.handlers.compatibility;
//
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.fml.common.Optional;
//import com.rwtema.extrautils.ExtraUtils;
//import com.rwtema.extrautils.item.ItemLawSword.DamageSourceEvil;
//import essenceMod.handlers.ConfigHandler;
//import essenceMod.registry.ModBlocks;
//import essenceMod.registry.ModItems;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.UpgradeRegistry;
//import essenceMod.utility.UtilityHelper;
//
//public class ExUHandler
//{
//	@Optional.Method(modid = "ExtraUtilities")
//	public static void doDivineDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource divine = new DamageSourceEvil((EntityPlayer) player, true);
//		float divineDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponDivineDamage.name);
//		divineDamage *= ConfigHandler.isDivineDamagePercent ? ConfigHandler.divineDamageMulti * weaponDamage : ConfigHandler.divineDamageAmount;
//		divineDamage *= isBow ? ConfigHandler.divineBowMulti : 1;
//		if (divineDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(divine, Math.round(divineDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "ExtraUtilities")
//	public static void addDivineRecipes(ItemStack weapon)
//	{
//		ItemStack unstableIngot = new ItemStack(ExtraUtils.unstableIngot);
//		ItemStack unstableNugget = new ItemStack(ExtraUtils.unstableIngot, 1, 1);
//		ItemStack stableIngot = new ItemStack(ExtraUtils.unstableIngot, 1, 2);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(1), null, unstableNugget, unstableNugget, unstableNugget, ModItems.infusedStar, ModItems.infusedStar, ModItems.infusedStar);// Soul Fragment
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(2), UpgradeRegistry.WeaponDivineDamage.setLevel(1), unstableIngot, unstableIngot, unstableIngot, ModItems.crystalStar, ModItems.crystalStar, ModItems.crystalStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(3), UpgradeRegistry.WeaponDivineDamage.setLevel(2), unstableIngot, unstableIngot, unstableIngot, Blocks.obsidian, Blocks.obsidian, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(4), UpgradeRegistry.WeaponDivineDamage.setLevel(3), stableIngot, stableIngot, stableIngot, Blocks.obsidian, Blocks.obsidian, ModItems.platedStar, ModItems.platedStar, ModItems.platedStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(5), UpgradeRegistry.WeaponDivineDamage.setLevel(4), ExtraUtils.soul, ExtraUtils.soul, stableIngot, stableIngot, stableIngot, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponDivineDamage.setLevel(5), null, ExtraUtils.unstableIngot, ModBlocks.infusedStarmetal, new ItemStack(ExtraUtils.soul, 1, 3));// Soul of a Forgotten // Diety
//	}
//}

//package essenceMod.handlers.compatibility;
//
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.fml.common.Optional;
//import com.brandon3055.draconicevolution.common.ModItems;
//import com.brandon3055.draconicevolution.common.utills.DamageSourceChaos;
//import essenceMod.handlers.ConfigHandler;
//import essenceMod.registry.ModBlocks;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.UpgradeRegistry;
//import essenceMod.utility.UtilityHelper;
//
//public class DraconicEvolutionHandler
//{
//	@Optional.Method(modid = "DraconicEvolution")
//	public static void doChaosDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource chaos= new DamageSourceChaos((EntityPlayer) player);
//		float chaosDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponChaosDamage.name);
//		chaosDamage *= ConfigHandler.isChaosDamagePercent ? ConfigHandler.chaosDamageMulti * weaponDamage : ConfigHandler.chaosDamageAmount;
//		chaosDamage *= isBow ? ConfigHandler.chaosBowMulti : 1;
//		if (chaosDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(chaos, Math.round(chaosDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "DraconicEvolution")
//	public static void addChaosRecipes(ItemStack weapon)
//	{
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponChaosDamage.setLevel(1), null, ModItems.draconicCore, ModItems.draconicCore, ModItems.draconicCore, essenceMod.registry.ModItems.infusedStar, essenceMod.registry.ModItems.infusedStar, essenceMod.registry.ModItems.infusedStar);// Draconic Cores
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponChaosDamage.setLevel(2), UpgradeRegistry.WeaponChaosDamage.setLevel(1), ModItems.draconicCore, ModItems.draconicCore, ModItems.draconicCore, essenceMod.registry.ModItems.crystalStar, essenceMod.registry.ModItems.crystalStar, essenceMod.registry.ModItems.crystalStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponChaosDamage.setLevel(3), UpgradeRegistry.WeaponChaosDamage.setLevel(2), ModItems.wyvernCore, ModItems.wyvernCore, ModItems.wyvernCore, essenceMod.registry.ModItems.infusedIngot, essenceMod.registry.ModItems.infusedIngot, essenceMod.registry.ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponChaosDamage.setLevel(4), UpgradeRegistry.WeaponChaosDamage.setLevel(3), ModItems.wyvernCore, ModItems.wyvernCore, ModItems.wyvernCore, essenceMod.registry.ModItems.platedStar, essenceMod.registry.ModItems.platedStar, essenceMod.registry.ModItems.platedStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponChaosDamage.setLevel(5), UpgradeRegistry.WeaponChaosDamage.setLevel(4), ModItems.awakenedCore, ModItems.awakenedCore, ModItems.awakenedCore, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//	}
//	
//	@Optional.Method(modid = "DraconicEvolution")
//	public static void addChaosProtectionRecipes(ItemStack armor)
//	{
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorChaosProtection.setLevel(1), null, ModItems.draconiumEnergyCore, ModItems.draconiumEnergyCore, ModItems.draconiumEnergyCore, essenceMod.registry.ModItems.infusedStar, essenceMod.registry.ModItems.infusedStar, essenceMod.registry.ModItems.infusedStar);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorChaosProtection.setLevel(2), UpgradeRegistry.ArmorChaosProtection.setLevel(1), ModItems.wyvernEnergyCore, ModItems.wyvernEnergyCore, ModItems.wyvernEnergyCore, essenceMod.registry.ModItems.crystalStar, essenceMod.registry.ModItems.crystalStar, essenceMod.registry.ModItems.crystalStar);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorChaosProtection.setLevel(3), UpgradeRegistry.ArmorChaosProtection.setLevel(2), ModItems.draconicEnergyCore, ModItems.draconicEnergyCore, ModItems.draconicEnergyCore, essenceMod.registry.ModItems.platedStar, essenceMod.registry.ModItems.platedStar, essenceMod.registry.ModItems.platedStar);
//		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorChaosProtection.setLevel(4), UpgradeRegistry.ArmorChaosProtection.setLevel(3), ModItems.draconicEnergyCore, ModItems.draconicEnergyCore, ModItems.draconicEnergyCore, ModItems.awakenedCore, ModItems.awakenedCore, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//		
//	}
//	
//	@Optional.Method(modid = "DraconicEvolution")
//	public static int getChaosDamageProtection(ItemStack armor, DamageSource source)
//	{
//		return source instanceof DamageSourceChaos ? UtilityHelper.getUpgradeLevel(armor, UpgradeRegistry.ArmorChaosProtection) : 0;
//	}
//}

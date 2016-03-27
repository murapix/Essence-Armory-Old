//package essenceMod.handlers.compatibility;
//
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.fml.common.Optional;
//import am2.blocks.BlocksCommonProxy;
//import am2.buffs.BuffEffectEntangled;
//import am2.buffs.BuffEffectFrostSlowed;
//import am2.damage.DamageSourceFrost;
//import am2.damage.DamageSourceHoly;
//import am2.damage.DamageSourceLightning;
//import am2.damage.DamageSourceWind;
//import am2.items.ItemsCommonProxy;
//import essenceMod.handlers.ConfigHandler;
//import essenceMod.registry.ModBlocks;
//import essenceMod.registry.ModItems;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.UpgradeRegistry;
//import essenceMod.utility.UtilityHelper;
//
//public class ArsMagicaHandler
//{
//	@Optional.Method(modid = "arsmagica2")
//	public static void doFrostDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource frost = new DamageSourceFrost(player);
//		float frostDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostDamage.name);
//		frostDamage *= ConfigHandler.isFrostDamagePercent ? ConfigHandler.frostDamageMulti * weaponDamage : ConfigHandler.frostDamageAmount;
//		frostDamage *= isBow ? ConfigHandler.frostBowMulti : 1;
//		if (frostDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(frost, Math.round(frostDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void doHolyDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource holy = new DamageSourceHoly(player);
//		float holyDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponHolyDamage.name);
//		holyDamage *= ConfigHandler.isHolyDamagePercent ? ConfigHandler.holyDamageMulti * weaponDamage : ConfigHandler.holyDamageAmount;
//		holyDamage *= isBow ? ConfigHandler.holyBowMulti : 1;
//		if (holyDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(holy, Math.round(holyDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void doLightningDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource lightning = new DamageSourceLightning(player);
//		float lightningDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponLightningDamage.name);
//		lightningDamage *= ConfigHandler.isLightningDamagePercent ? ConfigHandler.lightningDamageMulti * weaponDamage : ConfigHandler.lightningDamageAmount;
//		lightningDamage *= isBow ? ConfigHandler.lightningBowMulti : 1;
//		if (lightningDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(lightning, Math.round(lightningDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void doWindDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
//	{
//		DamageSource wind = new DamageSourceWind(player);
//		float windDamage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponWindDamage.name);
//		windDamage *= ConfigHandler.isWindDamagePercent ? ConfigHandler.windDamageMulti * weaponDamage : ConfigHandler.windDamageAmount;
//		windDamage *= isBow ? ConfigHandler.windBowMulti : 1;
//		if (windDamage != 0)
//		{
//			enemy.hurtResistantTime = 0;
//			enemy.attackEntityFrom(wind, Math.round(windDamage * 4) / 4F);
//		}
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void doEntangle(ItemStack item, EntityLivingBase enemy)
//	{
//		int entangle = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponEntangled.name);
//		if (entangle != 0) enemy.addPotionEffect(new BuffEffectEntangled(2 * entangle, 0));
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void doFrostSlow(ItemStack item, EntityLivingBase enemy)
//	{
//		int frostSlow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.WeaponFrostSlow.name);
//		if (frostSlow != 0) enemy.addPotionEffect(new BuffEffectFrostSlowed(25 * frostSlow, (frostSlow - 1) / 2));
//	}
//	
//	@Optional.Method(modid = "arsmagica2")
//	public static void addElementalRecipes(ItemStack weapon)
//	{
//		ItemStack tarmaRoot = new ItemStack(BlocksCommonProxy.tarmaRoot);
//		ItemStack blueRune = new ItemStack(ItemsCommonProxy.rune, 1, 2);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostSlow.setLevel(1), null, blueRune, blueRune, Items.snowball, Items.snowball, ModItems.infusedStar, ModItems.infusedStar); // Blue Rune + Snow
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostSlow.setLevel(2), UpgradeRegistry.WeaponFrostSlow.setLevel(1), blueRune, blueRune, Blocks.snow, Blocks.snow, ModItems.infusedStar, ModItems.infusedStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostSlow.setLevel(3), UpgradeRegistry.WeaponFrostSlow.setLevel(2), blueRune, blueRune, Blocks.snow, Blocks.snow, ModItems.crystalStar, ModItems.crystalStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostSlow.setLevel(4), UpgradeRegistry.WeaponFrostSlow.setLevel(3), blueRune, blueRune, Blocks.snow, Blocks.snow, ModItems.platedStar, ModItems.platedStar);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostSlow.setLevel(5), UpgradeRegistry.WeaponFrostSlow.setLevel(4), blueRune, blueRune, Blocks.snow, Blocks.snow, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
//		
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponEntangled.setLevel(1), null, Blocks.leaves, Blocks.vine, Blocks.cactus, Blocks.waterlily); // Nature Essence
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponEntangled.setLevel(2), UpgradeRegistry.WeaponEntangled.setLevel(1), Blocks.leaves, Blocks.vine, Blocks.cactus, Blocks.waterlily, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponEntangled.setLevel(3), UpgradeRegistry.WeaponEntangled.setLevel(2), Blocks.leaves, Blocks.vine, Blocks.cactus, Blocks.waterlily, Items.redstone, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponEntangled.setLevel(4), UpgradeRegistry.WeaponEntangled.setLevel(3), Blocks.leaves, Blocks.vine, Blocks.cactus, Blocks.waterlily, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponEntangled.setLevel(5), UpgradeRegistry.WeaponEntangled.setLevel(4), Blocks.leaves, Blocks.vine, Blocks.cactus, Blocks.waterlily, Items.redstone, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//		
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostDamage.setLevel(1), null, Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow); // Ice Essence
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostDamage.setLevel(2), UpgradeRegistry.WeaponFrostDamage.setLevel(1), Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostDamage.setLevel(3), UpgradeRegistry.WeaponFrostDamage.setLevel(2), Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, Items.redstone, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostDamage.setLevel(4), UpgradeRegistry.WeaponFrostDamage.setLevel(3), Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponFrostDamage.setLevel(5), UpgradeRegistry.WeaponFrostDamage.setLevel(4), Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, Items.redstone, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//		
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponHolyDamage.setLevel(1), null, Items.golden_apple, Items.golden_apple, Items.egg, Items.egg); // Life Essence
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponHolyDamage.setLevel(2), UpgradeRegistry.WeaponHolyDamage.setLevel(1), Items.golden_apple, Items.golden_apple, Items.egg, Items.egg, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponHolyDamage.setLevel(3), UpgradeRegistry.WeaponHolyDamage.setLevel(2), Items.golden_apple, Items.golden_apple, Items.egg, Items.egg, Items.redstone, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponHolyDamage.setLevel(4), UpgradeRegistry.WeaponHolyDamage.setLevel(3), Items.golden_apple, Items.golden_apple, Items.egg, Items.egg, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponHolyDamage.setLevel(5), UpgradeRegistry.WeaponHolyDamage.setLevel(4), Items.golden_apple, Items.golden_apple, Items.egg, Items.egg, Items.redstone, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//		
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponLightningDamage.setLevel(1), null, Items.glowstone_dust, Items.glowstone_dust, Items.redstone, Items.redstone, ModItems.infusedDiamond, ModItems.infusedDiamond); // Lightning Essence
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponLightningDamage.setLevel(2), UpgradeRegistry.WeaponLightningDamage.setLevel(1), Items.glowstone_dust, Items.glowstone_dust, Items.redstone, Items.redstone, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponLightningDamage.setLevel(3), UpgradeRegistry.WeaponLightningDamage.setLevel(2), Items.glowstone_dust, Items.glowstone_dust, Items.redstone, Items.redstone, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponLightningDamage.setLevel(4), UpgradeRegistry.WeaponLightningDamage.setLevel(3), Items.glowstone_dust, Items.glowstone_dust, Items.redstone, Items.redstone, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponLightningDamage.setLevel(5), UpgradeRegistry.WeaponLightningDamage.setLevel(4), Items.glowstone_dust, Items.glowstone_dust, Items.redstone, Items.redstone, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//		
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponWindDamage.setLevel(1), null, tarmaRoot, tarmaRoot, Items.feather, Items.feather, ModItems.infusedDiamond, ModItems.infusedDiamond); // Air Essence
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponWindDamage.setLevel(2), UpgradeRegistry.WeaponWindDamage.setLevel(1), tarmaRoot, tarmaRoot, Items.feather, Items.feather, ModItems.crystalDiamond, ModItems.crystalDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponWindDamage.setLevel(3), UpgradeRegistry.WeaponWindDamage.setLevel(2), tarmaRoot, tarmaRoot, Items.feather, Items.feather, ModItems.infusedIngot, ModItems.infusedIngot);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponWindDamage.setLevel(4), UpgradeRegistry.WeaponWindDamage.setLevel(3), tarmaRoot, tarmaRoot, Items.feather, Items.feather, ModItems.platedDiamond, ModItems.platedDiamond);
//		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponWindDamage.setLevel(5), UpgradeRegistry.WeaponWindDamage.setLevel(4), tarmaRoot, tarmaRoot, Items.feather, Items.feather, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
//	}
//}

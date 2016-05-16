package essenceMod.handlers.compatibility;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.potions.PotionFluxTaint;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModItems;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class ThaumcraftHandler
{
	@Optional.Method(modid = "Thaumcraft")
	public static void doTaintDamage(ItemStack item, EntityPlayer player, EntityLivingBase enemy, float weaponDamage, boolean isBow)
	{
		DamageSource taint = DamageSourceThaumcraft.taint;
		float taintDamage = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDamage.name);
		taintDamage *= ConfigHandler.isTaintDamagePercent ? ConfigHandler.taintDamageMulti * weaponDamage : ConfigHandler.taintDamageAmount;
		taintDamage *= isBow ? ConfigHandler.taintBowMulti : 1;
		if (taintDamage != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(taint, Math.round(taintDamage * 4) / 4F);
		}
	}
	
	@Optional.Method(modid = "Thaumcraft")
	public static void doTaintDoT(ItemStack item, EntityLivingBase enemy)
	{
		int taint = Upgrade.getUpgradeLevel(item, UpgradeRegistry.WeaponTaintDoT.name);
		if (taint != 0) enemy.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.id, 40 * taint, 0));
	}
	
	@Optional.Method(modid = "Thaumcraft")
	public static void addTaintRecipes(ItemStack weapon)
	{
		ItemStack thaumium = new ItemStack(ItemsTC.ingots, 1, 0);
		ItemStack voidmetal = new ItemStack(ItemsTC.ingots, 1, 1);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDoT.setLevel(1), UpgradeRegistry.BaseUpgrade, thaumium, ModItems.infusedStar, Items.redstone);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDoT.setLevel(2), UpgradeRegistry.WeaponTaintDoT.setLevel(1), thaumium, ModItems.infusedStar, Items.glowstone_dust);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDoT.setLevel(3), UpgradeRegistry.WeaponTaintDoT.setLevel(2), thaumium, ModItems.crystalStar, Items.redstone);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDoT.setLevel(4), UpgradeRegistry.WeaponTaintDoT.setLevel(3), voidmetal, ModItems.crystalStar, Items.glowstone_dust);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDoT.setLevel(5), UpgradeRegistry.WeaponTaintDoT.setLevel(4), voidmetal, ModItems.platedStar, Items.redstone);
		
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDamage.setLevel(1), UpgradeRegistry.BaseUpgrade, thaumium, thaumium, thaumium, ModItems.infusedStar, ModItems.infusedStar, ModItems.infusedStar);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDamage.setLevel(2), UpgradeRegistry.WeaponTaintDamage.setLevel(1), thaumium, thaumium, thaumium, ModItems.crystalStar, ModItems.crystalStar, ModItems.crystalStar);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDamage.setLevel(3), UpgradeRegistry.WeaponTaintDamage.setLevel(2), thaumium, thaumium, thaumium, ModItems.infusedIngot, ModItems.infusedIngot, ModItems.infusedIngot);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDamage.setLevel(4), UpgradeRegistry.WeaponTaintDamage.setLevel(3), voidmetal, voidmetal, voidmetal, ModItems.platedStar, ModItems.platedStar, ModItems.platedStar);
		InfuserRecipes.addRecipe(weapon, UpgradeRegistry.WeaponTaintDamage.setLevel(5), UpgradeRegistry.WeaponTaintDamage.setLevel(4), voidmetal, voidmetal, voidmetal, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal, ModBlocks.infusedStarmetal);
	}
	
	@Optional.Method(modid = "Thaumcraft")
	public static void addTCArmorRecipes(ItemStack armor)
	{
		ItemStack thaumium = new ItemStack(ItemsTC.ingots, 1, 0);
		ItemStack voidmetal = new ItemStack(ItemsTC.ingots, 1, 1);
		ItemStack thaumometer = new ItemStack(ItemsTC.thaumometer);
		ItemStack salisMundus = new ItemStack(ItemsTC.salisMundus);
		ItemStack greatwood = new ItemStack(BlocksTC.log, 1, 0);
		ItemStack silverwood = new ItemStack(BlocksTC.log, 1, 1);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRevealing.setLevel(1), UpgradeRegistry.BaseUpgrade, thaumometer, thaumometer, Items.leather, Items.leather, Items.leather, Items.leather, Items.gold_ingot, Items.gold_ingot);
		
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRunicShielding.setLevel(1), UpgradeRegistry.BaseUpgrade, Items.diamond, salisMundus, ModItems.infusedDiamond, ModItems.infusedDiamond);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRunicShielding.setLevel(2), UpgradeRegistry.ArmorRunicShielding.setLevel(1), Items.diamond, salisMundus, salisMundus, ModItems.crystalDiamond, ModItems.crystalDiamond);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRunicShielding.setLevel(3), UpgradeRegistry.ArmorRunicShielding.setLevel(2), Items.diamond, salisMundus, salisMundus, salisMundus, ModItems.infusedIngot, ModItems.infusedIngot);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRunicShielding.setLevel(4), UpgradeRegistry.ArmorRunicShielding.setLevel(3), Items.diamond, salisMundus, salisMundus, salisMundus, salisMundus, ModItems.platedDiamond, ModItems.platedDiamond);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorRunicShielding.setLevel(5), UpgradeRegistry.ArmorRunicShielding.setLevel(4), Items.diamond, salisMundus, salisMundus, salisMundus, salisMundus, salisMundus, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
		
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorVisDiscount.setLevel(1), UpgradeRegistry.BaseUpgrade, thaumium, thaumium, greatwood, greatwood, ModItems.infusedDiamond, ModItems.infusedDiamond); // Greatwood, Silverwood, Thaumium, Voidmetal
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorVisDiscount.setLevel(2), UpgradeRegistry.ArmorVisDiscount.setLevel(1), thaumium, thaumium, greatwood, silverwood, ModItems.crystalDiamond, ModItems.crystalDiamond);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorVisDiscount.setLevel(3), UpgradeRegistry.ArmorVisDiscount.setLevel(2), thaumium, thaumium, silverwood, silverwood, ModItems.infusedIngot, ModItems.infusedIngot);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorVisDiscount.setLevel(4), UpgradeRegistry.ArmorVisDiscount.setLevel(3), voidmetal, voidmetal, greatwood, greatwood, ModItems.platedDiamond, ModItems.platedDiamond);
		InfuserRecipes.addRecipe(armor, UpgradeRegistry.ArmorVisDiscount.setLevel(5), UpgradeRegistry.ArmorVisDiscount.setLevel(4), voidmetal, voidmetal, silverwood, silverwood, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
	}
	
	@Optional.Method(modid = "Thaumcraft")
	public static void addTaintInfusionRecipes(ItemStack bauble)
	{
		ItemStack taintGoo = new ItemStack(ItemsTC.tainted, 1, 0);
		ItemStack taintTendril = new ItemStack(ItemsTC.tainted, 1, 1);
		InfuserRecipes.addRecipe(bauble, UpgradeRegistry.BaubleTaintImmunity.setLevel(1), UpgradeRegistry.BaseUpgrade, taintGoo, taintGoo, taintTendril, taintTendril, ModItems.infusedDiamond, ModItems.infusedDiamond);
		InfuserRecipes.addRecipe(bauble, UpgradeRegistry.BaubleTaintImmunity.setLevel(2), UpgradeRegistry.BaubleTaintImmunity.setLevel(1), taintGoo, taintGoo, taintTendril, taintTendril, ModItems.crystalDiamond, ModItems.crystalDiamond);
		InfuserRecipes.addRecipe(bauble, UpgradeRegistry.BaubleTaintImmunity.setLevel(3), UpgradeRegistry.BaubleTaintImmunity.setLevel(2), taintGoo, taintGoo, taintTendril, taintTendril, ModItems.infusedIngot, ModItems.infusedIngot);
		InfuserRecipes.addRecipe(bauble, UpgradeRegistry.BaubleTaintImmunity.setLevel(4), UpgradeRegistry.BaubleTaintImmunity.setLevel(3), taintGoo, taintGoo, taintTendril, taintTendril, ModItems.platedDiamond, ModItems.platedDiamond);
		InfuserRecipes.addRecipe(bauble, UpgradeRegistry.BaubleTaintImmunity.setLevel(5), UpgradeRegistry.BaubleTaintImmunity.setLevel(4), taintGoo, taintGoo, taintTendril, taintTendril, ModBlocks.infusedBlock, ModBlocks.infusedBlock);
	}
}

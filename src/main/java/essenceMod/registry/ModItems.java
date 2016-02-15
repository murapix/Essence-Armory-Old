package essenceMod.registry;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import essenceMod.items.ItemMod;
import essenceMod.items.ItemModFood;
import essenceMod.utility.RegisterHelper;

public class ModItems
{
	public static Item infusedShard = new ItemMod().setUnlocalizedName("shardInfused");
	public static Item infusedDiamond = new ItemMod().setUnlocalizedName("gemInfused");
	public static Item infusedStar = new ItemMod().setUnlocalizedName("starInfused");
	public static Item crystalDiamond = new ItemMod().setUnlocalizedName("gemCrystallized");
	public static Item crystalStar = new ItemMod().setUnlocalizedName("starCrystallized");
	public static Item platedDiamond = new ItemMod().setUnlocalizedName("gemPlated");
	public static Item platedStar = new ItemMod().setUnlocalizedName("starPlated");
	
	public static Item infusedNugget = new ItemMod().setUnlocalizedName("nuggetInfused");
	public static Item infusedIngot = new ItemMod().setUnlocalizedName("ingotInfused");
	public static Item infusedRod = new ItemMod().setUnlocalizedName("stickInfused");
	public static Item infusedString = new ItemMod().setUnlocalizedName("stringInfused");
	
	public static Item infusedApple = new ItemModFood(0, false).addPotionEffect(Potion.damageBoost.id, 90, 1).addPotionEffect(Potion.resistance.id, 90, 2).addPotionEffect(Potion.field_76444_x.id, 90, 4).setUnlocalizedName("appleInfused");
	public static Item crystalApple = new ItemModFood(0, false).addPotionEffect(Potion.damageBoost.id, 90, 2).addPotionEffect(Potion.resistance.id, 90, 3).addPotionEffect(Potion.field_76444_x.id, 90, 5).setUnlocalizedName("appleCrystallized");
	public static Item platedApple = new ItemModFood(0, false).addPotionEffect(Potion.damageBoost.id, 90, 3).addPotionEffect(Potion.resistance.id, 90, 4).addPotionEffect(Potion.field_76444_x.id, 90, 6).setUnlocalizedName("applePlated");
	
	public static Item infusedWand = new ItemMod().setUnlocalizedName("wandInfused");
	
	public static void init()
	{
		RegisterHelper.registerItem(infusedShard);
		RegisterHelper.registerItem(infusedDiamond);
		RegisterHelper.registerItem(infusedStar);
		RegisterHelper.registerItem(crystalDiamond);
		RegisterHelper.registerItem(crystalStar);
		RegisterHelper.registerItem(platedDiamond);
		RegisterHelper.registerItem(platedStar);
		
		RegisterHelper.registerItem(infusedNugget);
		RegisterHelper.registerItem(infusedIngot);
		RegisterHelper.registerItem(infusedRod);
		RegisterHelper.registerItem(infusedString);
		
		RegisterHelper.registerItem(infusedApple);
		RegisterHelper.registerItem(crystalApple);
		RegisterHelper.registerItem(platedApple);
		
		RegisterHelper.registerItem(infusedWand);
	}
}

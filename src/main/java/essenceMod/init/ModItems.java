package essenceMod.init;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import essenceMod.help.RegisterHelper;
import essenceMod.items.ItemMod;
import essenceMod.items.ItemModFood;

public class ModItems
{
	public static Item infusedIngot = new ItemMod().setUnlocalizedName("ingotInfused");
	public static Item infusedShard = new ItemMod().setUnlocalizedName("infusedShard");
	public static Item infusedCrystal = new ItemMod().setUnlocalizedName("gemInfused");
	public static Item infusedRod = new ItemMod().setUnlocalizedName("infusedRod");
	public static Item infusedNugget = new ItemMod().setUnlocalizedName("nuggetInfused");
	public static Item infusedString = new ItemMod().setUnlocalizedName("infusedString");
	public static Item infusedDiamond = new ItemMod().setUnlocalizedName("infusedDiamond");
	public static Item infusedStar = new ItemMod().setUnlocalizedName("infusedStar");
	public static Item crystalStar = new ItemMod().setUnlocalizedName("gemStar");
	public static Item platedStar = new ItemMod().setUnlocalizedName("platedStar");
	public static Item platedDiamond = new ItemMod().setUnlocalizedName("platedDiamond");
	
	public static Item infusedApple = new ItemModFood(0, false).addPotionEffect(Potion.damageBoost.id, 90, 1).addPotionEffect(Potion.resistance.id, 90, 2).addPotionEffect(Potion.field_76444_x.id, 90, 4).setUnlocalizedName("infusedApple");
	
	public static Item infusedBowstring = new ItemMod().setUnlocalizedName("infusedBowstring");
	
	public static void init()
	{
		RegisterHelper.registerItem(infusedIngot);
		RegisterHelper.registerItem(infusedShard);
		RegisterHelper.registerItem(infusedCrystal);
		RegisterHelper.registerItem(infusedRod);
		RegisterHelper.registerItem(infusedNugget);
		RegisterHelper.registerItem(infusedString);
		RegisterHelper.registerItem(infusedDiamond);
		RegisterHelper.registerItem(infusedStar);
		RegisterHelper.registerItem(crystalStar);
		RegisterHelper.registerItem(platedStar);
		RegisterHelper.registerItem(platedDiamond);
		
		RegisterHelper.registerItem(infusedApple);
		
		if (Loader.isModLoaded("TConstruct")) RegisterHelper.registerItem(infusedBowstring);
	}
}

package essenceMod.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import essenceMod.help.RegisterHelper;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModBow;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemBaseAmulet;
import essenceMod.items.baubles.ItemBaseBelt;
import essenceMod.items.baubles.ItemBaseRing;
import essenceMod.items.baubles.ItemCleaveBelt;
import essenceMod.items.baubles.ItemFlightAmulet;
import essenceMod.items.baubles.ItemKnockbackBelt;
import essenceMod.items.baubles.ItemLootAmulet;
import essenceMod.items.baubles.ItemPotionRing;

public class ModArmory
{
	public static Item.ToolMaterial INFUSED = EnumHelper.addToolMaterial("INFUSED", 6, 1500, 10.0F, 5.0F, 20);
	public static ArmorMaterial AINFUSED = EnumHelper.addArmorMaterial("AINFUSED", 1500, new int[]{5, 8, 7, 4}, 20);
	
	public static Item infusedSword = new ItemModSword(INFUSED).setUnlocalizedName("infusedSword");
	
	public static Item infusedBow = new ItemModBow().setUnlocalizedName("infusedBow");
	
	public static Item infusedHelm = new ItemModArmor(AINFUSED, 0, new String[]{}).setUnlocalizedName("infusedHelm");
	public static Item infusedPlate = new ItemModArmor(AINFUSED, 1, new String[]{}).setUnlocalizedName("infusedPlate");
	public static Item infusedPants = new ItemModArmor(AINFUSED, 2, new String[]{}).setUnlocalizedName("infusedPants");
	public static Item infusedBoots = new ItemModArmor(AINFUSED, 3, new String[]{}).setUnlocalizedName("infusedBoots");
	
	public static Item infusedBaseRing = new ItemBaseRing().setUnlocalizedName("infusedBaseRing");
	public static Item infusedBaseBelt = new ItemBaseBelt().setUnlocalizedName("infusedBaseBelt");
	public static Item infusedBaseAmulet = new ItemBaseAmulet().setUnlocalizedName("infusedBaseAmulet");
	
	public static Item infusedFlightAmulet = new ItemFlightAmulet().setUnlocalizedName("infusedFlightAmulet");
	
	public static Item swiftnessIRing = new ItemPotionRing(1, 1).setUnlocalizedName("infusedRing1-1");
	public static Item swiftnessIIRing = new ItemPotionRing(2, 1).setUnlocalizedName("infusedRing2-1");
	public static Item swiftnessIIIRing = new ItemPotionRing(3, 1).setUnlocalizedName("infusedRing3-1");
	public static Item hasteIRing = new ItemPotionRing(1, 3).setUnlocalizedName("infusedRing1-3");
	public static Item hasteIIRing = new ItemPotionRing(2, 3).setUnlocalizedName("infusedRing2-3");
	public static Item hasteIIIRing = new ItemPotionRing(3, 3).setUnlocalizedName("infusedRing3-3");
	public static Item strengthIRing = new ItemPotionRing(1, 5).setUnlocalizedName("infusedRing1-5");
	public static Item strengthIIRing = new ItemPotionRing(2, 5).setUnlocalizedName("infusedRing2-5");
	public static Item strengthIIIRing = new ItemPotionRing(3, 5).setUnlocalizedName("infusedRing3-5");
	public static Item jumpIRing = new ItemPotionRing(1, 8).setUnlocalizedName("infusedRing1-8");
	public static Item jumpIIRing = new ItemPotionRing(2, 8).setUnlocalizedName("infusedRing2-8");
	public static Item jumpIIIRing = new ItemPotionRing(3, 8).setUnlocalizedName("infusedRing3-8");
	public static Item regenerationIRing = new ItemPotionRing(1, 10).setUnlocalizedName("infusedRing1-10");
	public static Item regenerationIIRing = new ItemPotionRing(2, 10).setUnlocalizedName("infusedRing2-10");
	public static Item regenerationIIIRing = new ItemPotionRing(3, 10).setUnlocalizedName("infusedRing3-10");
	public static Item fireRing = new ItemPotionRing(1, 12).setUnlocalizedName("infusedRing1-12");
	public static Item waterRing = new ItemPotionRing(1, 13).setUnlocalizedName("infusedRing1-13");
	public static Item nightRing = new ItemPotionRing(1, 16).setUnlocalizedName("infusedRing1-16");
	
	public static Item lootIAmulet = new ItemLootAmulet(1).setUnlocalizedName("infusedLootAmulet1");
	public static Item lootIIAmulet = new ItemLootAmulet(2).setUnlocalizedName("infusedLootAmulet2");
	public static Item lootIIIAmulet = new ItemLootAmulet(3).setUnlocalizedName("infusedLootAmulet3");
	public static Item lootIVAmulet = new ItemLootAmulet(4).setUnlocalizedName("infusedLootAmulet4");
	public static Item lootVAmulet = new ItemLootAmulet(5).setUnlocalizedName("infusedLootAmulet5");
	
	public static Item cleaveIBelt = new ItemCleaveBelt(1).setUnlocalizedName("infusedCleaveBelt1");
	public static Item cleaveIIBelt = new ItemCleaveBelt(2).setUnlocalizedName("infusedCleaveBelt2");
	public static Item cleaveIIIBelt = new ItemCleaveBelt(3).setUnlocalizedName("infusedCleaveBelt3");
	public static Item cleaveIVBelt = new ItemCleaveBelt(4).setUnlocalizedName("infusedCleaveBelt4");
	public static Item cleaveVBelt = new ItemCleaveBelt(5).setUnlocalizedName("infusedCleaveBelt5");
	
	public static Item knockbackIBelt = new ItemKnockbackBelt(1).setUnlocalizedName("infusedKnockbackBelt1");
	public static Item knockbackIIBelt = new ItemKnockbackBelt(2).setUnlocalizedName("infusedKnockbackBelt2");
	public static Item knockbackIIIBelt = new ItemKnockbackBelt(3).setUnlocalizedName("infusedKnockbackBelt3");
	public static Item knockbackIVBelt = new ItemKnockbackBelt(4).setUnlocalizedName("infusedKnockbackBelt4");
	public static Item knockbackVBelt = new ItemKnockbackBelt(5).setUnlocalizedName("infusedKnockbackBelt5");

	public static void init()
	{
		RegisterHelper.registerItem(infusedSword);
		
		RegisterHelper.registerItem(infusedBow);
		
		RegisterHelper.registerItem(infusedHelm);
		RegisterHelper.registerItem(infusedPlate);
		RegisterHelper.registerItem(infusedPants);
		RegisterHelper.registerItem(infusedBoots);
		
		RegisterHelper.registerItem(infusedBaseAmulet);
		RegisterHelper.registerItem(infusedBaseBelt);
		RegisterHelper.registerItem(infusedBaseRing);
		
		RegisterHelper.registerItem(infusedFlightAmulet);
		
		RegisterHelper.registerItem(swiftnessIRing);
		RegisterHelper.registerItem(swiftnessIIRing);
		RegisterHelper.registerItem(swiftnessIIIRing);
		RegisterHelper.registerItem(hasteIRing);
		RegisterHelper.registerItem(hasteIIRing);
		RegisterHelper.registerItem(hasteIIIRing);
		RegisterHelper.registerItem(strengthIRing);
		RegisterHelper.registerItem(strengthIIRing);
		RegisterHelper.registerItem(strengthIIIRing);
		RegisterHelper.registerItem(jumpIRing);
		RegisterHelper.registerItem(jumpIIRing);
		RegisterHelper.registerItem(jumpIIIRing);
		RegisterHelper.registerItem(regenerationIRing);
		RegisterHelper.registerItem(regenerationIIRing);
		RegisterHelper.registerItem(regenerationIIIRing);
		RegisterHelper.registerItem(fireRing);
		RegisterHelper.registerItem(waterRing);
		RegisterHelper.registerItem(nightRing);
		
		RegisterHelper.registerItem(lootIAmulet);
		RegisterHelper.registerItem(lootIIAmulet);
		RegisterHelper.registerItem(lootIIIAmulet);
		RegisterHelper.registerItem(lootIVAmulet);
		RegisterHelper.registerItem(lootVAmulet);
		
		RegisterHelper.registerItem(cleaveIBelt);
		RegisterHelper.registerItem(cleaveIIBelt);
		RegisterHelper.registerItem(cleaveIIIBelt);
		RegisterHelper.registerItem(cleaveIVBelt);
		RegisterHelper.registerItem(cleaveVBelt);
		
		RegisterHelper.registerItem(knockbackIBelt);
		RegisterHelper.registerItem(knockbackIIBelt);
		RegisterHelper.registerItem(knockbackIIIBelt);
		RegisterHelper.registerItem(knockbackIVBelt);
		RegisterHelper.registerItem(knockbackVBelt);
	}
}
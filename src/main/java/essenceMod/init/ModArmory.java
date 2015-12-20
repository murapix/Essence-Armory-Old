package essenceMod.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Loader;
import essenceMod.enchantment.EnchantmentShard;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModBow;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemBaseAmulet;
import essenceMod.items.baubles.ItemBaseBelt;
import essenceMod.items.baubles.ItemBaseRing;
import essenceMod.items.baubles.ItemCleaveBelt;
import essenceMod.items.baubles.ItemFlightAmulet;
import essenceMod.items.baubles.ItemHealthyBelt;
import essenceMod.items.baubles.ItemImmunityAmulet;
import essenceMod.items.baubles.ItemKnockbackBelt;
import essenceMod.items.baubles.ItemLootAmulet;
import essenceMod.items.baubles.ItemMiningLimiterBelt;
import essenceMod.items.baubles.ItemPotionRing;
import essenceMod.items.travellersGear.ItemHealthyVambraces;
import essenceMod.items.travellersGear.ItemImmunityPauldrons;
import essenceMod.items.travellersGear.ItemMiningLimiterVambraces;
import essenceMod.utility.RegisterHelper;

public class ModArmory
{
	public static final Enchantment shardLooter = new EnchantmentShard(70, 5);
	
	public static Item.ToolMaterial INFUSED = EnumHelper.addToolMaterial("INFUSED", 6, 1500, 10.0F, 5.0F, 20);
	public static ArmorMaterial AINFUSED = EnumHelper.addArmorMaterial("AINFUSED", 1500, new int[] { 5, 8, 7, 4 }, 20);

	public static Item infusedSword = new ItemModSword(INFUSED).setUnlocalizedName("infusedSword");

	public static Item infusedBow = new ItemModBow().setUnlocalizedName("infusedBow");

	public static Item infusedHelm = new ItemModArmor(AINFUSED, 0).setUnlocalizedName("infusedHelm");
	public static Item infusedPlate = new ItemModArmor(AINFUSED, 1).setUnlocalizedName("infusedPlate");
	public static Item infusedPants = new ItemModArmor(AINFUSED, 2).setUnlocalizedName("infusedPants");
	public static Item infusedBoots = new ItemModArmor(AINFUSED, 3).setUnlocalizedName("infusedBoots");

	public static Item baseRing = new ItemBaseRing().setUnlocalizedName("infusedBaseRing");
	public static Item baseBelt = new ItemBaseBelt().setUnlocalizedName("infusedBaseBelt");
	public static Item baseAmulet = new ItemBaseAmulet().setUnlocalizedName("infusedBaseAmulet");

	public static Item flightAmulet = new ItemFlightAmulet().setUnlocalizedName("infusedFlightAmulet");
	public static Item potionRing = new ItemPotionRing().setUnlocalizedName("infusedRing");
	public static Item lootAmulet = new ItemLootAmulet(1).setUnlocalizedName("infusedLootAmulet");
	public static Item cleaveBelt = new ItemCleaveBelt(1).setUnlocalizedName("infusedCleaveBelt");
	public static Item knockbackBelt = new ItemKnockbackBelt().setUnlocalizedName("infusedKnockbackBelt");
	
	public static Item immunityAmulet = new ItemImmunityAmulet().setUnlocalizedName("infusedImmunityAmulet");
	public static Item healthyBelt = new ItemHealthyBelt().setUnlocalizedName("infusedHealthyBelt");
	public static Item miningLimiterBelt = new ItemMiningLimiterBelt().setUnlocalizedName("infusedMiningLimiter");

	public static Item healthyVambraces;
	public static Item immunityPauldrons;
	public static Item miningLimiterVambraces;

	public static void init()
	{
		RegisterHelper.registerItem(infusedSword);

		RegisterHelper.registerItem(infusedBow);

		RegisterHelper.registerItem(infusedHelm);
		RegisterHelper.registerItem(infusedPlate);
		RegisterHelper.registerItem(infusedPants);
		RegisterHelper.registerItem(infusedBoots);

		RegisterHelper.registerItem(baseAmulet);
		RegisterHelper.registerItem(baseBelt);
		RegisterHelper.registerItem(baseRing);

		RegisterHelper.registerItem(flightAmulet);
		RegisterHelper.registerItem(potionRing);
		RegisterHelper.registerItem(lootAmulet);
		RegisterHelper.registerItem(cleaveBelt);
		RegisterHelper.registerItem(knockbackBelt);

		if (Loader.isModLoaded("TravellersGear"))
		{
			try
			{
				healthyVambraces = new ItemHealthyVambraces().setUnlocalizedName("infusedHealthyVambraces");
				immunityPauldrons = new ItemImmunityPauldrons().setUnlocalizedName("infusedImmunityPauldrons");
				miningLimiterVambraces = new ItemMiningLimiterVambraces().setUnlocalizedName("infusedMiningLimiterVambraces");

				RegisterHelper.registerItem(healthyVambraces);
				RegisterHelper.registerItem(immunityPauldrons);
				RegisterHelper.registerItem(miningLimiterVambraces);
			}
			catch (Exception e)
			{
				RegisterHelper.registerItem(miningLimiterBelt);
				RegisterHelper.registerItem(immunityAmulet);
				RegisterHelper.registerItem(healthyBelt);
			}
		}
		else
		{
			RegisterHelper.registerItem(miningLimiterBelt);
			RegisterHelper.registerItem(immunityAmulet);
			RegisterHelper.registerItem(healthyBelt);
		}
	}
}
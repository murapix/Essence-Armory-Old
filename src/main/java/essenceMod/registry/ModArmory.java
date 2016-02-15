package essenceMod.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Loader;
import essenceMod.enchantment.EnchantmentShard;
import essenceMod.handlers.ConfigHandler;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModBow;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemAmulet;
import essenceMod.items.baubles.ItemBelt;
import essenceMod.items.baubles.ItemRing;
import essenceMod.items.travellersGear.ItemPauldron;
import essenceMod.items.travellersGear.ItemVambraces;
import essenceMod.utility.RegisterHelper;

public class ModArmory
{
	public static final Enchantment shardLooter = new EnchantmentShard(70, 5);
	
	public static Item.ToolMaterial INFUSED = EnumHelper.addToolMaterial("INFUSED", 6, 1500, 10.0F, 6.0F, 20);
	public static ArmorMaterial AINFUSED = EnumHelper.addArmorMaterial("AINFUSED", 1500, new int[] { 5, 8, 7, 4 }, 20);

	public static Item infusedSword = new ItemModSword(INFUSED).setUnlocalizedName("infusedSword");

	public static Item infusedBow = new ItemModBow().setUnlocalizedName("infusedBow");

	public static Item infusedHelm = new ItemModArmor(AINFUSED, 0).setUnlocalizedName("infusedHelm");
	public static Item infusedPlate = new ItemModArmor(AINFUSED, 1).setUnlocalizedName("infusedPlate");
	public static Item infusedPants = new ItemModArmor(AINFUSED, 2).setUnlocalizedName("infusedPants");
	public static Item infusedBoots = new ItemModArmor(AINFUSED, 3).setUnlocalizedName("infusedBoots");

	public static Item infusedRing = new ItemRing().setUnlocalizedName("infusedRing");
	public static Item infusedBelt = new ItemBelt().setUnlocalizedName("infusedBelt");
	public static Item infusedAmulet = new ItemAmulet().setUnlocalizedName("infusedAmulet");

	public static Item infusedPauldrons;
	public static Item infusedVambraces;

	public static void init()
	{
		RegisterHelper.registerItem(infusedSword);

		RegisterHelper.registerItem(infusedBow);

		RegisterHelper.registerItem(infusedHelm);
		RegisterHelper.registerItem(infusedPlate);
		RegisterHelper.registerItem(infusedPants);
		RegisterHelper.registerItem(infusedBoots);

		RegisterHelper.registerItem(infusedAmulet);
		RegisterHelper.registerItem(infusedBelt);
		RegisterHelper.registerItem(infusedRing);

		if (Loader.isModLoaded("TravellersGear") && ConfigHandler.travellersgearIntegration)
		{
			try
			{
				infusedPauldrons = new ItemPauldron().setUnlocalizedName("infusedPauldrons");
				infusedVambraces = new ItemVambraces().setUnlocalizedName("infusedVambraces");

				RegisterHelper.registerItem(infusedPauldrons);
				RegisterHelper.registerItem(infusedVambraces);
			}
			catch (Exception e)
			{}
		}
	}
}
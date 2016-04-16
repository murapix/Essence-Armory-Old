package essenceMod.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import essenceMod.enchantment.EnchantmentShard;
import essenceMod.handlers.ConfigHandler;
import essenceMod.items.ItemModArmor;
import essenceMod.items.ItemModBow;
import essenceMod.items.ItemModLootSword;
import essenceMod.items.ItemModSword;
import essenceMod.items.baubles.ItemAmulet;
import essenceMod.items.baubles.ItemBelt;
import essenceMod.items.baubles.ItemRing;
import essenceMod.utility.RegisterHelper;

public class ModArmory
{
	public static final Enchantment shardLooter = new EnchantmentShard(ConfigHandler.shardEnchantID, 5);
	
	public static ToolMaterial INFUSED = EnumHelper.addToolMaterial("INFUSED", 6, 1500, 10.0F, 6.0F, 20);
	public static ArmorMaterial AINFUSED = EnumHelper.addArmorMaterial("INFUSED", "infused", 1500, new int[] { 5, 8, 7, 4 }, 20);

	public static Item infusedSword = new ItemModSword(INFUSED).setUnlocalizedName("infusedSword");
	
	public static Item lootSword = new ItemModLootSword(INFUSED).setUnlocalizedName("lootSword");

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
		
		RegisterHelper.registerItem(lootSword);

		RegisterHelper.registerItem(infusedBow);

		RegisterHelper.registerItem(infusedHelm);
		RegisterHelper.registerItem(infusedPlate);
		RegisterHelper.registerItem(infusedPants);
		RegisterHelper.registerItem(infusedBoots);

		RegisterHelper.registerItem(infusedAmulet);
		RegisterHelper.registerItem(infusedBelt);
		RegisterHelper.registerItem(infusedRing);
		
		

//		if (Loader.isModLoaded("TravellersGear") && ConfigHandler.travellersgearIntegration)
//		{
//			try
//			{
//				infusedPauldrons = new ItemPauldron().setUnlocalizedName("infusedPauldrons");
//				infusedVambraces = new ItemVambraces().setUnlocalizedName("infusedVambraces");
//
//				RegisterHelper.registerItem(infusedPauldrons);
//				RegisterHelper.registerItem(infusedVambraces);
//			}
//			catch (Exception e)
//			{}
//		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void initItemRenderers()
	{
		((ItemModSword) infusedSword).initModel();
		
		((ItemModLootSword) lootSword).initModel();
		
		((ItemModBow) infusedBow).initModel();
		
		((ItemModArmor) infusedHelm).initModel();
		((ItemModArmor) infusedPlate).initModel();
		((ItemModArmor) infusedPants).initModel();
		((ItemModArmor) infusedBoots).initModel();
		
		((ItemRing) infusedRing).initModel();
		((ItemBelt) infusedBelt).initModel();
		((ItemAmulet) infusedAmulet).initModel();
		
//		if (Loader.isModLoaded("TravellersGear") && ConfigHandler.travellersgearIntegration)
//			{
//				try
//				{
//					((ItemPauldron) infusedPauldrons).initModel();
//					((ItemVambraces) infusedVambraces).initModel();
//				}
//				catch (Exception e)
//				{}
//			}
	}
}
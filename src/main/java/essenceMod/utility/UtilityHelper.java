package essenceMod.utility;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import essenceMod.crafting.Upgrade;

public class UtilityHelper
{
	private static int[] numbers = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };;
	private static String[] letters = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };;

	public static String toRoman(int arabic)
	{
		String roman = "";
		for (int i = 0; i < numbers.length; i++)
		{
			while (arabic >= numbers[i])
			{
				roman += letters[i];
				arabic -= numbers[i];
			}
		}
		return roman;
	}

	public static float round(float number, int places)
	{
		if (places == 0) return (float) ((int) number);

		int mult = (int) Math.pow(10, places);
		return ((int) (number * mult)) / (float) mult;
	}
	
	/**
	 * Returns the level of the upgrade with the given name found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param name
	 *            The name of the upgrade.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, String name)
	{
		NBTTagList upgradeList = item.stackTagCompound.getTagList("Upgrades", NBT.TAG_COMPOUND);
		for (int i = 0; i < upgradeList.tagCount(); i++)
		{
			NBTTagCompound tag = upgradeList.getCompoundTagAt(i);
			if (tag.getString("Name").equals(name)) return tag.getInteger("Level");
		}
		return 0;
	}

	/**
	 * Returns the level of the given upgrade found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param upgrade
	 *            The upgrade of which the level is being checked.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, Upgrade upgrade)
	{
		if (upgrade == null || upgrade.name == null) return 0;
		return UtilityHelper.getUpgradeLevel(item, upgrade.name);
	}
}

package essenceMod.registry.crafting.upgrades;

import java.util.HashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class Upgrade
{
	public HashSet<String> incompatibleUpgrades;
	public String name;
	public int level;

	public Upgrade(String name, int level, Upgrade... upgrades)
	{
		this.name = name;
		this.level = level;
		incompatibleUpgrades = new HashSet<String>();
		for (Upgrade upgrade : upgrades)
		{
			incompatibleUpgrades.add(upgrade.name);
			UpgradeRegistry.upgradeRegister.get(upgrade.name).addIncompatibleUpgrade(this.name);
		}
	}
	
	public Upgrade(String name, Upgrade... upgrades)
	{
		this(name, 0, upgrades);
	}
	
	public Upgrade(String name, int level, String... upgrades)
	{
		this.name = name;
		this.level = level;
		incompatibleUpgrades = new HashSet<String>();
		for (String str : upgrades)
		{
			incompatibleUpgrades.add(str);
			UpgradeRegistry.upgradeRegister.get(str).addIncompatibleUpgrade(this.name);
		}
	}
	
	public Upgrade(String name, String... upgrades)
	{
		this(name, 0, upgrades);
	}
	
	public Upgrade(String name, int level)
	{
		this(name, level, new String[]{});
	}

	public Upgrade(String name)
	{
		this(name, 0);
	}
	
	public boolean isSameAs(Upgrade upgrade)
	{
		return this.name.equals(upgrade.name) && this.level == upgrade.level;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
		if (!(o instanceof Upgrade)) return false;
		Upgrade that = (Upgrade) o;
		return (this.name.equals(that.name) && (this.level == that.level));
	}

	@Override
	public int hashCode()
	{
		return (name.hashCode() * 5) + level;
	}

	public HashSet<String> getIncompatibleUpgrades()
	{
		return incompatibleUpgrades;
	}
	
	public Upgrade addIncompatibleUpgrade(String... upgrades)
	{
		return addIncompatibleUpgrade(true, upgrades);
	}
	
	public Upgrade addIncompatibleUpgrade(boolean initial, String... upgrades)
	{
		for (String upgrade : upgrades)
		{
			incompatibleUpgrades.add(upgrade);
			if (initial && UpgradeRegistry.upgradeRegister.containsKey(upgrade))
				UpgradeRegistry.upgradeRegister.get(upgrade).addIncompatibleUpgrade(false, this.name);
		}
		return this;
	}
	
	public Upgrade setLevel(int level)
	{
		this.level = level;
		return new Upgrade(this.name, this.level, this.incompatibleUpgrades.toArray(new String[]{}));
	}
	
	public Upgrade setName(String name)
	{
		this.name = name;
		return this;
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
		if (!item.hasTagCompound()) return 0;
		if (!item.getTagCompound().hasKey("Upgrades")) return 0;
		NBTTagList upgradeList = item.getTagCompound().getTagList("Upgrades", NBT.TAG_COMPOUND);
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
		return getUpgradeLevel(item, upgrade.name);
	}
}
package essenceMod.registry.crafting.upgrades;

import java.util.ArrayList;
import java.util.HashSet;

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
}
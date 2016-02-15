package essenceMod.items;

import java.util.ArrayList;

public class Upgrade
{
	public ArrayList<String> incompatibleUpgrades;
	public String name;
	public int level;

	public Upgrade(String name, int level, Upgrade... upgrades)
	{
		this.name = name;
		this.level = level;
		incompatibleUpgrades = new ArrayList<String>();
		for (Upgrade upgrade : upgrades)
			incompatibleUpgrades.add(upgrade.name);
	}
	
	public Upgrade(String name, Upgrade... upgrades)
	{
		this(name, 0, upgrades);
	}
	
	public Upgrade(String name, int level, String... upgrades)
	{
		this.name = name;
		this.level = level;
		incompatibleUpgrades = new ArrayList<String>();
		for (String str : upgrades)
			incompatibleUpgrades.add(str);
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

	public ArrayList<String> getIncompatibleUpgrades()
	{
		return incompatibleUpgrades;
	}
	
	public Upgrade addIncompatibleUpgrade(String... upgrades)
	{
		for (String upgrade : upgrades)
			incompatibleUpgrades.add(upgrade);
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
package essenceMod.registry;

import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;
import essenceMod.EssenceMod;

public class ModEntities
{
	private static int startEntityID, i;

	public static void init()
	{}

	public static void registerModProjectile(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(entityClass, name, ++startEntityID, EssenceMod.instance, 64, 10, true);
	}

	public static void registerModEntity(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(entityClass, name, ++startEntityID, EssenceMod.instance, 80, 3, false);
	}

	public static void registerModEntityEgg(Class entityClass, String name, int primary, int secondary)
	{
		registerModEntity(entityClass, name);
		if (i == 0)
		{
			registerSpawnEgg(name, primary, secondary);
			i++;
		}
	}

	public static void registerSpawnEgg(String name, int primary, int secondary)
	{

	}

	public static int getUniqueEntityId()
	{
		do
		{
			startEntityID++;

		}
		while (EntityList.getStringFromID(startEntityID) != null);

		return startEntityID;
	}
}
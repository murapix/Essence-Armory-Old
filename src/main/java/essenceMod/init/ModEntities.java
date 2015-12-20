package essenceMod.init;

import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import essenceMod.EssenceMod;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.utility.LogHelper;

public class ModEntities
{
	private static int startEntityID, i;

	public static void init()
	{}

	public static void registerModProjectile(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(entityClass, name, ++startEntityID, EssenceMod.instance, 64, 10, true);
		LogHelper.info("Registering mod projectile " + name + " with ID = " + startEntityID);
	}

	public static void registerModEntity(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(entityClass, name, ++startEntityID, EssenceMod.instance, 80, 3, false);
		LogHelper.info("Registering mod entity " + name + " with ID = " + startEntityID);
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
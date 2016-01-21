package essenceMod;

import java.util.UUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import essenceMod.crafting.InfuserRecipes;
import essenceMod.crafting.Recipes;
import essenceMod.crafting.Upgrade;
import essenceMod.gui.GuiHandler;
import essenceMod.handlers.CommandUpgrade;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.EssenceEventHandler;
import essenceMod.handlers.compatibility.TConstructHandler;
import essenceMod.init.ModArmory;
import essenceMod.init.ModBlocks;
import essenceMod.init.ModEntities;
import essenceMod.init.ModItems;
import essenceMod.init.ModTileEntity;
import essenceMod.utility.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
dependencies = "required-after:Forge@[1.7.10-10.13.4.1448-1.7.10,);" +
		"required-after:Baubles;" +
		"after:TConstruct@[1.7.10-1.8.7,);" +
		"after:Draconic-Evolution@[1.7.10-1.0.2-Snapshot_3,);" +
		"after:TravellersGear@[1.7.10-1.16.6,);" +
		"after:extrautilities@[1.2.11,);")
public class EssenceMod
{
	@Instance
	public static EssenceMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.initProps(event.getSuggestedConfigurationFile());
		ModItems.init();
		ModBlocks.init();
		ModArmory.init();
		ModEntities.init();
		ModTileEntity.init();
		EssenceEventHandler.preinit();
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Recipes.init();
		InfuserRecipes.init();
		if (Loader.isModLoaded("TConstruct") && ConfigHandler.ticoIntegration)
		{
			try
			{
				TConstructHandler.init();
			}
			catch (Exception e){}
		}
		NetworkRegistry.INSTANCE.registerGuiHandler(EssenceMod.instance, new GuiHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
//		System.out.println(UUID.randomUUID());
	}
	
	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandUpgrade());
	}
	
	@Mod.EventHandler
	public void imcReader(IMCEvent event)
	{
		for (IMCMessage message : event.getMessages())
		{
			if (message.key.equalsIgnoreCase("upgrades"))
			{
				if (message.isNBTMessage())
				{
					NBTTagCompound upgrades = message.getNBTValue();
					NBTTagList upgradeList = upgrades.getTagList("Upgrades", NBT.TAG_COMPOUND);
					if (upgradeList == null) continue;
					for (int i = 0; i < upgradeList.tagCount(); i++)
					{
						NBTTagCompound upgrade = upgradeList.getCompoundTagAt(i);
						InfuserRecipes.upgradeLimits.put(new Upgrade(upgrade.getString("Name")), upgrade.getString("Type"));
					}
				}
			}
			else if (message.key.equalsIgnoreCase("upgrade"))
			{
				if (message.isNBTMessage())
				{
					NBTTagCompound upgrade = message.getNBTValue();
					InfuserRecipes.upgradeLimits.put(new Upgrade(upgrade.getString("Name")), upgrade.getString("Type"));
				}
			}
		}
	}
}
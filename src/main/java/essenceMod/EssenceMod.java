package essenceMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import essenceMod.handlers.CommandUpgrade;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.EssenceEventHandler;
import essenceMod.handlers.compatibility.TConstructHandler;
import essenceMod.proxy.CommonProxy;
import essenceMod.registry.ModArmory;
import essenceMod.registry.ModBlocks;
import essenceMod.registry.ModEntities;
import essenceMod.registry.ModItems;
import essenceMod.registry.ModTileEntity;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.Recipes;
import essenceMod.utility.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
dependencies = "required-after:Forge@[1.7.10-10.13.4.1448-1.7.10,);" +
		"required-after:Baubles;" +
		"after:NotEnoughItems;" +
		"after:TConstruct;" +
		"after:Draconic-Evolution;" +
		"after:TravellersGear;" +
		"after:extrautilities;" +
		"after:Thaumcraft;" +
		"after:arsmagica2;" +
		"after:Botania;")
public class EssenceMod
{
	@Instance
	public static EssenceMod instance;
	
	@SidedProxy(clientSide = "essenceMod.proxy.ClientProxy", serverSide = "essenceMod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.Init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
//		System.out.println(UUID.randomUUID());
	}
	
	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		proxy.serverLoad(event);
	}
}
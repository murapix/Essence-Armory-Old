package essenceMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import essenceMod.proxy.CommonProxy;
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
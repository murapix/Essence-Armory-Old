package essenceMod.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import essenceMod.utility.RegisterHelper;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		RegisterHelper.registerRenderThings();
	}
}

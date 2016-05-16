//package essenceMod.handlers.compatibility.psi;
//
//import net.minecraft.client.resources.model.ModelResourceLocation;
//import net.minecraft.item.ItemStack;
//import vazkii.psi.api.cad.EnumCADComponent;
//import vazkii.psi.api.cad.EnumCADStat;
//import vazkii.psi.api.cad.ICADAssembly;
//import vazkii.psi.client.core.handler.ModelHandler;
//import vazkii.psi.common.item.component.ItemCADComponent;
//
///**
// * @author Escapee
// * @category WIP
// */
//public class EvolvingCadAssembly extends ItemCADComponent implements ICADAssembly
//{
//	public EvolvingCadAssembly()
//	{
//		super("cadAssembly", "cadAssemblyEvolving");
//	}
//
//	@Override
//	public EnumCADComponent getComponentType(ItemStack arg0)
//	{
//		return EnumCADComponent.ASSEMBLY;
//	}
//
//	@Override
//	public ModelResourceLocation getCADModel(ItemStack arg0, ItemStack arg1)
//	{
//		return ModelHandler.resourceLocations.get("cadCreative");
//	}
//	
//	@Override
//	public void registerStats()
//	{
//		addStat(EnumCADStat.EFFICIENCY, 0, 70);
//		addStat(EnumCADStat.POTENCY, 0, 250);
//	}
//}

//package essenceMod.handlers.compatibility;
//
//import java.util.ListIterator;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumChatFormatting;
//import net.minecraftforge.event.entity.player.ItemTooltipEvent;
//import net.minecraftforge.fml.common.Optional;
//import net.minecraftforge.fml.common.event.FMLInterModComms;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import slimeknights.tconstruct.library.TinkerRegistry;
//import slimeknights.tconstruct.library.tools.ToolCore;
//import slimeknights.tconstruct.library.utils.ToolBuilder;
//import essenceMod.handlers.ConfigHandler;
//import essenceMod.registry.ModBlocks;
//import essenceMod.utility.Reference;
//
//public class TConstructHandler
//{
//	private static NBTTagCompound tag, item;
//	private static int infusedStarmetalId = ConfigHandler.ticoMaterialId;
//
//	@Optional.Method(modid = "TConstruct")
//	public static void init()
//	{
//		tag = new NBTTagCompound();
//		tag.setInteger("Id", infusedStarmetalId);
//		tag.setString("Name", "Infused Starmetal");
//		tag.setInteger("HarvestLevel", ConfigHandler.ticoHarvestLevel);//4
//		tag.setInteger("Durability", ConfigHandler.ticoDurability);//10000
//		tag.setInteger("MiningSpeed", ConfigHandler.ticoMiningSpeed);//900
//		tag.setInteger("Attack", ConfigHandler.ticoBaseDamage);//8
//		tag.setFloat("HandleModifier", ConfigHandler.ticoHandleMod);//1.5F
//		tag.setInteger("Reinforced", ConfigHandler.ticoReinforced);//10
//		tag.setString("Style", EnumChatFormatting.LIGHT_PURPLE.toString());
//		tag.setInteger("Bow_DrawSpeed", ConfigHandler.ticoDrawSpeed);//15
//		tag.setFloat("Bow_ProjectileSpeed", ConfigHandler.ticoProjSpeed);//9.6F
//		tag.setFloat("Projectile_Mass", ConfigHandler.ticoProjMass);//5.6F
//		tag.setFloat("Projectile_Fragility", ConfigHandler.ticoProjFrag);//0.9F
//		tag.setInteger("Color", 148 << 24 | 61 << 16 | 255 << 8 | 40);
//		FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
//		
//		tag = new NBTTagCompound();
//		tag.setInteger("MaterialId", infusedStarmetalId);
//		NBTTagCompound item = new NBTTagCompound();
//		(new ItemStack(ModBlocks.infusedStarmetal)).writeToNBT(item);
//		tag.setTag("Item", item);
//		tag.setTag("Shard", item);
//		tag.setInteger("Value", 1);
//		FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
//		
//		//int materialID, String texturePath, boolean customTexture
//		TinkerRegistry.addMaterial(null);
////		TConstructClientRegistry.addMaterialRenderMapping(infusedStarmetalId, Reference.MODID, "infusedstarmetal", true);
//	}
//
////	@SubscribeEvent
////	@Optional.Method(modid = "TConstruct")
////	public void handleInfusedStarmetal(TinkerEvent event)
////	{
////		NBTTagCompound tag = event.toolTag.getCompoundTag("InfiTool");
////
////		int modifiers = tag.getInteger("Modifiers");
////		int bonusModifiers = 0;
////		if (tag.getInteger("Head") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
////		if (tag.getInteger("Handle") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
////		if (tag.getInteger("Accessory") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
////		if (tag.getInteger("Extra") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
////		modifiers += (bonusModifiers == 0) ? 0 : (bonusModifiers + ConfigHandler.ticoBaseModCount);
////		tag.setInteger("Modifiers", modifiers);
////	}
//
////	@SubscribeEvent
////	@Optional.Method(modid = "TConstruct")
////	@SideOnly(Side.CLIENT)
////	public void infusedTooltip(ItemTooltipEvent event)
////	{
////		if (event.itemStack == null || event.entityPlayer == null || event.entityPlayer.worldObj == null) return;
////		if (ToolBuilder.getMaterialID(event.itemStack) == infusedStarmetalId)
////		{
////			ListIterator<String> iterator = event.toolTip.listIterator();
////			while (iterator.hasNext())
////			{
////				String next = iterator.next();
////				if (next.contains("Unbreakable") || next.contains("Reinforced"))
////				{
////					iterator.previous();
////					iterator.add(EnumChatFormatting.LIGHT_PURPLE + "Evolving" + EnumChatFormatting.RESET);
////					break;
////				}
////			}
////		}
////		else if (event.itemStack.getItem() instanceof ToolCore)
////		{
////			ItemStack item = event.itemStack;
////			NBTTagCompound compound = item.getTagCompound();
////			if (compound == null) compound = new NBTTagCompound();
////			NBTTagCompound tag = compound.hasKey("InfiTool") ? compound.getCompoundTag("InfiTool") : new NBTTagCompound();
////			boolean usesStarmetal = false;
////			if (tag.getInteger("Head") == infusedStarmetalId) usesStarmetal = true;
////			if (tag.getInteger("Handle") == infusedStarmetalId) usesStarmetal = true;
////			if (tag.getInteger("Accessory") == infusedStarmetalId) usesStarmetal = true;
////			if (tag.getInteger("Extra") == infusedStarmetalId) usesStarmetal = true;
////			if (usesStarmetal)
////			{
////				ListIterator<String> iterator = event.toolTip.listIterator();
////				while (iterator.hasNext())
////				{
////					String next = iterator.next();
////					if (next.contains("Unbreakable"))
////					{
////						iterator.previous();
////						iterator.add(EnumChatFormatting.LIGHT_PURPLE + "Evolving" + EnumChatFormatting.RESET);
////						break;
////					}
////				}
////			}
////		}
////	}
//}

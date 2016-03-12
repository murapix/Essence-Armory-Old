package essenceMod.handlers.compatibility;

import java.util.ListIterator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.event.ToolCraftEvent.NormalTool;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModBlocks;
import essenceMod.utility.LogHelper;
import essenceMod.utility.Reference;

public class TConstructHandler
{
	private static NBTTagCompound tag, item;
	private static int infusedStarmetalId = ConfigHandler.ticoMaterialId;

	@Optional.Method(modid = "TConstruct")
	public static void init()
	{
		tag = new NBTTagCompound();
		tag.setInteger("Id", infusedStarmetalId);
		tag.setString("Name", "Infused Starmetal");
		tag.setInteger("HarvestLevel", ConfigHandler.ticoHarvestLevel);//4
		tag.setInteger("Durability", ConfigHandler.ticoDurability);//10000
		tag.setInteger("MiningSpeed", ConfigHandler.ticoMiningSpeed);//900
		tag.setInteger("Attack", ConfigHandler.ticoBaseDamage);//8
		tag.setFloat("HandleModifier", ConfigHandler.ticoHandleMod);//1.5F
		tag.setInteger("Reinforced", ConfigHandler.ticoReinforced);//10
		tag.setString("Style", EnumChatFormatting.LIGHT_PURPLE.toString());
		tag.setInteger("Bow_DrawSpeed", ConfigHandler.ticoDrawSpeed);//15
		tag.setFloat("Bow_ProjectileSpeed", ConfigHandler.ticoProjSpeed);//9.6F
		tag.setFloat("Projectile_Mass", ConfigHandler.ticoProjMass);//5.6F
		tag.setFloat("Projectile_Fragility", ConfigHandler.ticoProjFrag);//0.9F
		FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
		
		tag = new NBTTagCompound();
		tag.setInteger("MaterialId", infusedStarmetalId);
		NBTTagCompound item = new NBTTagCompound();
		(new ItemStack(ModBlocks.infusedStarmetal)).writeToNBT(item);
		tag.setTag("Item", item);
		tag.setTag("Shard", item);
		tag.setInteger("Value", 1);
		FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
		
		//int materialID, String texturePath, boolean customTexture
		TConstructClientRegistry.addMaterialRenderMapping(infusedStarmetalId, Reference.MODID, "infusedstarmetal", true);
		
		LogHelper.info("Created Tinkers' Construct tool parts");
	}

	@SubscribeEvent
	@Optional.Method(modid = "TConstruct")
	public void handleInfusedStarmetal(NormalTool event)
	{
		NBTTagCompound tag = event.toolTag.getCompoundTag("InfiTool");

		int modifiers = tag.getInteger("Modifiers");
		int bonusModifiers = 0;
		if (tag.getInteger("Head") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
		if (tag.getInteger("Handle") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
		if (tag.getInteger("Accessory") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
		if (tag.getInteger("Extra") == infusedStarmetalId) bonusModifiers += ConfigHandler.ticoPartModCount;
		modifiers += (bonusModifiers == 0) ? 0 : (bonusModifiers + ConfigHandler.ticoBaseModCount);
		tag.setInteger("Modifiers", modifiers);
	}

	@SubscribeEvent
	@Optional.Method(modid = "TConstruct")
	@SideOnly(Side.CLIENT)
	public void infusedTooltip(ItemTooltipEvent event)
	{
		if (event.itemStack == null || event.entityPlayer == null || event.entityPlayer.worldObj == null) return;
		if (ToolBuilder.instance.getMaterialID(event.itemStack) == infusedStarmetalId)
		{
			ListIterator<String> iterator = event.toolTip.listIterator();
			while (iterator.hasNext())
			{
				String next = iterator.next();
				if (next.contains("Unbreakable") || next.contains("Reinforced"))
				{
					iterator.previous();
					iterator.add(EnumChatFormatting.LIGHT_PURPLE + "Evolving" + EnumChatFormatting.RESET);
					break;
				}
			}
		}
		else if (event.itemStack.getItem() instanceof ToolCore)
		{
			ItemStack item = event.itemStack;
			NBTTagCompound compound = item.getTagCompound();
			if (compound == null) compound = new NBTTagCompound();
			NBTTagCompound tag = compound.hasKey("InfiTool") ? compound.getCompoundTag("InfiTool") : new NBTTagCompound();
			boolean usesStarmetal = false;
			if (tag.getInteger("Head") == infusedStarmetalId) usesStarmetal = true;
			if (tag.getInteger("Handle") == infusedStarmetalId) usesStarmetal = true;
			if (tag.getInteger("Accessory") == infusedStarmetalId) usesStarmetal = true;
			if (tag.getInteger("Extra") == infusedStarmetalId) usesStarmetal = true;
			if (usesStarmetal)
			{
				ListIterator<String> iterator = event.toolTip.listIterator();
				while (iterator.hasNext())
				{
					String next = iterator.next();
					if (next.contains("Unbreakable"))
					{
						iterator.previous();
						iterator.add(EnumChatFormatting.LIGHT_PURPLE + "Evolving" + EnumChatFormatting.RESET);
						break;
					}
				}
			}
		}
	}
}

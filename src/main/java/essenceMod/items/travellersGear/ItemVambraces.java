//package essenceMod.items.travellersGear;
//
//import java.util.List;
//import java.util.UUID;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.ai.attributes.IAttributeInstance;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.IIcon;
//import net.minecraft.util.StatCollector;
//import net.minecraft.world.World;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
//import travellersgear.api.TravellersGearAPI;
//import cpw.mods.fml.common.eventhandler.EventPriority;
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
//import essenceMod.utility.Reference;
//import essenceMod.utility.UtilityHelper;
//
//public class ItemVambraces extends ItemTravellersGear
//{
//	private final AttributeModifier health = new AttributeModifier(UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC"), "EssenceArmoryVambraceHealth", 0.5D, 2);
//
//	public int level;
//	public IIcon[] icons = new IIcon[7];
//
//	public ItemVambraces()
//	{
//		this(0);
//	}
//
//	public ItemVambraces(int level)
//	{
//		super();
//		this.level = level;
//		MinecraftForge.EVENT_BUS.register(this);
//		this.setHasSubtypes(true);
//		this.setMaxDamage(0);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister iconRegister)
//	{
//		icons[0] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
//		for (int i = 1; i < icons.length; i++)
//			icons[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5)/* + "-" + i */);
//	}
//
//	@Override
//	public IIcon getIconFromDamage(int meta)
//	{
//		if (meta >= icons.length) meta = 0;
//		return icons[meta];
//	}
//
//	@Override
//	public void getSubItems(Item item, CreativeTabs tab, List list)
//	{
//		for (int i = 0; i < icons.length; i++)
//			list.add(new ItemStack(item, 1, i));
//	}
//
//	// @Override
//	// public String getUnlocalizedName(ItemStack item)
//	// {
//	// return this.getUnlocalizedName() + ":" + item.getItemDamage();
//	// }
//
//	@Override
//	public void onCreated(ItemStack item, World world, EntityPlayer player)
//	{
//		super.onCreated(item, world, player);
//		item.stackTagCompound.setInteger("Level", 0);
//		int meta = item.getItemDamage();
//		if (meta == 0) return;
//		else if (meta <= 5) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleHealthBoost.setLevel(meta));
//		else if (meta <= 6) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleMiningLimiter.setLevel(meta - 5));
//	}
//
//	@Override
//	public int getSlot(ItemStack item)
//	{
//		return 2;
//	}
//
//	public static int getLevel(ItemStack item)
//	{
//		return item.stackTagCompound.getInteger("Level");
//	}
//
//	@Override
//	public void onTravelGearUnequip(EntityPlayer player, ItemStack item)
//	{
//		super.onTravelGearUnequip(player, item);
//
//		IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
//		if (attribute != null) attribute.removeModifier(health);
//	}
//
//	@SubscribeEvent
//	public void updatePlayerHealth(LivingUpdateEvent event)
//	{
//		if (event.entityLiving instanceof EntityPlayer)
//		{
//			EntityPlayer player = (EntityPlayer) event.entityLiving;
//			ItemStack vambraces = TravellersGearAPI.getExtendedInventory(player)[2];
//			if (vambraces != null && vambraces.getItem() instanceof ItemVambraces)
//			{
//				IAttributeInstance attribute = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
//				if (attribute != null)
//				{
//					attribute.removeModifier(health);
//					int level = UtilityHelper.getUpgradeLevel(vambraces, UpgradeRegistry.BaubleHealthBoost);
//					attribute.applyModifier(new AttributeModifier(health.getID(), health.getName() + level, health.getAmount() * level, health.getOperation()));
//				}
//			}
//		}
//	}
//
//	@SubscribeEvent(priority = EventPriority.LOWEST)
//	public void onBlockBreak(BreakSpeed event)
//	{
//		EntityPlayer player = event.entityPlayer;
//		if (player == null) return;
//		ItemStack vambraces = TravellersGearAPI.getExtendedInventory(player)[2];
//		if (vambraces != null && vambraces.getItem() instanceof ItemVambraces && UtilityHelper.getUpgradeLevel(vambraces, UpgradeRegistry.BaubleMiningLimiter) != 0)
//		{
//			float hardness = event.block.getBlockHardness(player.worldObj, event.x, event.y, event.z);
//			float blockHealth = hardness * 30;
//			event.newSpeed = Math.min(event.newSpeed, blockHealth - 1);
//		}
//	}
//
//	@Override
//	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
//	{
//		int level = 0;
//		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);
//
//		level = item.stackTagCompound.getInteger("Level");
//		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));
//
//		int healthBoost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaubleHealthBoost);
//		int miningLimiter = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaubleMiningLimiter);
//
//		if (miningLimiter != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleMiningLimiter.name));
//		if (healthBoost != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleHealthBoost.name) + " " + UtilityHelper.toRoman(healthBoost));
//	}
//}

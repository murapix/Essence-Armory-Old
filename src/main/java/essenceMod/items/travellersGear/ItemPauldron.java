//package essenceMod.items.travellersGear;
//
//import java.util.List;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.StatCollector;
//import net.minecraft.world.World;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import thaumcraft.api.damagesource.DamageSourceThaumcraft;
//import essenceMod.handlers.ConfigHandler;
//import essenceMod.registry.crafting.InfuserRecipes;
//import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
//import essenceMod.utility.Reference;
//import essenceMod.utility.UtilityHelper;
//
//public class ItemPauldron extends ItemTravellersGear
//{
//	public int level;
//	public IIcon[] icons;
//	
//	public ItemPauldron()
//	{
//		this(0);
//	}
//	
//	public ItemPauldron(int level)
//	{
//		super();
//		MinecraftForge.EVENT_BUS.register(this);
//		this.setHasSubtypes(true);
//		this.setMaxDamage(0);
//		this.level = level;
//		icons = ConfigHandler.thaumcraftIntegration ? new IIcon[16] : new IIcon[21];
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
////	@Override
////	public String getUnlocalizedName(ItemStack item)
////	{
////		return this.getUnlocalizedName() + ":" + item.getItemDamage();
////	}
//	
//	@Override
//	public void onCreated(ItemStack item, World world, EntityPlayer player)
//	{
//		super.onCreated(item, world, player);
//		item.stackTagCompound.setInteger("Level", 0);
//		int meta = item.getItemDamage();
//		if (meta == 0) return;
//		else if (meta <= 5) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaublePoisonImmunity.setLevel(meta));
//		else if (meta <= 10) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleWitherImmunity.setLevel(meta - 5));
//		else if (meta <= 15) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleFireImmunity.setLevel(meta - 10));
//		else if (meta <= 20) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleTaintImmunity.setLevel(meta - 15));
//	}
//	
//	@Override
//	public int getSlot(ItemStack item)
//	{
//		return 1;
//	}
//	
//	public static int getLevel(ItemStack item)
//	{
//		return item.stackTagCompound.getInteger("Level");
//	}
//	
//	@SubscribeEvent
//	public void onPotionDamage(LivingHurtEvent event)
//	{
//		EntityLivingBase entity = event.entityLiving;
//		if (entity instanceof EntityPlayer)
//		{
//			EntityPlayer player = (EntityPlayer) entity;
//			ItemStack pauldrons = TravellersGearAPI.getExtendedInventory(player)[1];
//			if (pauldrons != null && pauldrons.getItem() instanceof ItemPauldron)
//			{
//				int poison = UtilityHelper.getUpgradeLevel(pauldrons, UpgradeRegistry.BaublePoisonImmunity);
//				int wither = UtilityHelper.getUpgradeLevel(pauldrons, UpgradeRegistry.BaubleWitherImmunity);
//				int fire = UtilityHelper.getUpgradeLevel(pauldrons, UpgradeRegistry.BaubleFireImmunity);
//				int taint = UtilityHelper.getUpgradeLevel(pauldrons, UpgradeRegistry.BaubleTaintImmunity);
//				if (poison != 0 && event.source.isMagicDamage())
//				{
//					event.ammount -= Math.min(poison * 0.25F, 1F);
//					player.heal(Math.max((poison - 4) * 0.25F, 0F));
//				}
//				if (wither != 0 && event.source.equals(DamageSource.wither))
//				{
//					event.ammount -= Math.min(wither * 0.25F, 1F);
//					player.heal(Math.max((wither - 4) * 0.25F, 0F));
//				}
//				if (fire != 0 && event.source.isFireDamage())
//				{
//					event.ammount -= Math.min(fire * 0.25F, 1F);
//					player.heal(Math.max((fire - 4) * 0.25F, 0F));
//				}
//				if (taint != 0 && event.source instanceof DamageSourceThaumcraft)
//				{
//					event.ammount -= Math.min(taint * 0.25F, 1F);
//					player.heal(Math.max((taint - 4) * 0.25F, 0F));
//				}
//			}
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
//		int poison = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaublePoisonImmunity);
//		int wither = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaubleWitherImmunity);
//		int fire = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaubleFireImmunity);
//		int taint = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.BaubleTaintImmunity);
//
//		if (poison != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaublePoisonImmunity.name) + " " + UtilityHelper.toRoman(poison));
//		if (wither != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleWitherImmunity.name) + " " + UtilityHelper.toRoman(wither));
//		if (fire != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleFireImmunity.name) + " " + UtilityHelper.toRoman(fire));
//		if (taint != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleTaintImmunity.name) + " " + UtilityHelper.toRoman(taint));
//	}
//}

package essenceMod.items.baubles;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
import essenceMod.utility.UtilityHelper;

public class ItemAmulet extends ItemBauble
{
	public int level;
	public static final int numSubTypes = ConfigHandler.thaumcraftIntegration ? 27 : 22;

	public ItemAmulet()
	{
		this(0);
	}

	public ItemAmulet(int level)
	{
		super();
		MinecraftForge.EVENT_BUS.register(this);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.level = level;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < numSubTypes; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		NBTTagCompound compound = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();
		compound.setInteger("Level", 0);
		item.setTagCompound(compound);
		int meta = item.getItemDamage();
		if (meta == 0) return;
		else if (meta <= 5) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaublePoisonImmunity.setLevel(meta));
		else if (meta <= 10) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleWitherImmunity.setLevel(meta - 5));
		else if (meta <= 15) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleFireImmunity.setLevel(meta - 10));
		else if (meta <= 20) InfuserRecipes.addUpgrade(item, UpgradeRegistry.AmuletLooting.setLevel(meta - 15));
		else if (meta <= 21) InfuserRecipes.addUpgrade(item, UpgradeRegistry.AmuletFlight.setLevel(meta - 20));
		else if (meta <= 26) InfuserRecipes.addUpgrade(item, UpgradeRegistry.BaubleTaintImmunity.setLevel(meta - 21));
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		for (int i = 0; i < numSubTypes; i++)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}

	public static int getLevel(ItemStack item)
	{
		return item.getTagCompound().getInteger("Level");
	}

	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);
		EntityPlayer p = (EntityPlayer) player;
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.AmuletFlight) != 0) p.capabilities.allowFlying = true;
	}

	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		super.onUnequipped(item, player);
		EntityPlayer p = (EntityPlayer) player;
		if (Upgrade.getUpgradeLevel(item, UpgradeRegistry.AmuletFlight) != 0) p.capabilities.allowFlying = false;
	}

	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
		if (amulet != null && amulet.getItem() instanceof ItemAmulet && Upgrade.getUpgradeLevel(amulet, UpgradeRegistry.AmuletFlight) != 0) player.capabilities.allowFlying = true;
	}

	@SubscribeEvent
	public void onPotionDamage(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack amulet = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);
			if (amulet != null && amulet.getItem() instanceof ItemAmulet)
			{
				int poison = Upgrade.getUpgradeLevel(amulet, UpgradeRegistry.BaublePoisonImmunity);
				int wither = Upgrade.getUpgradeLevel(amulet, UpgradeRegistry.BaubleWitherImmunity);
				int fire = Upgrade.getUpgradeLevel(amulet, UpgradeRegistry.BaubleFireImmunity);
				int taint = Upgrade.getUpgradeLevel(amulet, UpgradeRegistry.BaubleTaintImmunity);
				if (poison != 0 && event.source.isMagicDamage())
				{
					event.ammount -= Math.min(poison * 0.25F, 1F);
					player.heal(Math.max((poison - 4) * 0.25F, 0F));
				}
				if (wither != 0 && event.source.equals(DamageSource.wither))
				{
					event.ammount -= Math.min(wither * 0.25F, 1F);
					player.heal(Math.max((wither - 4) * 0.25F, 0F));
				}
				if (fire != 0 && event.source.isFireDamage())
				{
					event.ammount -= Math.min(fire * 0.25F, 1F);
					player.heal(Math.max((fire - 4) * 0.25F, 0F));
				}
				if (taint != 0 && event.source instanceof DamageSourceThaumcraft)
				{
					event.ammount -= Math.min(taint * 0.25F, 1F);
					player.heal(Math.max((taint - 4) * 0.25F, 0F));
				}
				if (event.ammount <= 0) event.setCanceled(true);
			}
		}
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		int level = 0;
		if (!item.hasTagCompound()) onCreated(item, entityPlayer.worldObj, entityPlayer);

		level = item.getTagCompound().getInteger("Level");
		if (level != 0) list.add("Level " + UtilityHelper.toRoman(level));

		int flight = Upgrade.getUpgradeLevel(item, UpgradeRegistry.AmuletFlight);
		int looting = Upgrade.getUpgradeLevel(item, UpgradeRegistry.AmuletLooting);
		int poison = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BaublePoisonImmunity);
		int wither = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BaubleWitherImmunity);
		int fire = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BaubleFireImmunity);
		int taint = Upgrade.getUpgradeLevel(item, UpgradeRegistry.BaubleTaintImmunity);

		if (flight != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.AmuletFlight.name));
		if (looting != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.AmuletLooting.name) + " " + UtilityHelper.toRoman(looting));
		if (poison != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaublePoisonImmunity.name) + " " + UtilityHelper.toRoman(poison));
		if (wither != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleWitherImmunity.name) + " " + UtilityHelper.toRoman(wither));
		if (fire != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleFireImmunity.name) + " " + UtilityHelper.toRoman(fire));
		if (taint != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.BaubleTaintImmunity.name) + " " + UtilityHelper.toRoman(taint));
	}
}
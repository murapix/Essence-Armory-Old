package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.crafting.upgrades.UpgradeRegistry;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.compatibility.ArsMagicaHandler;
import essenceMod.handlers.compatibility.DraconicEvolutionHandler;
import essenceMod.handlers.compatibility.ExUHandler;
import essenceMod.handlers.compatibility.ThaumcraftHandler;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModSword extends ItemSword implements IUpgradeable
{
	public final ToolMaterial toolMaterial;
	protected float weaponDamage;

	int level;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (item.stackTagCompound == null) onCreated(item, world, (EntityPlayer) entity);
		int level = item.stackTagCompound.getInteger("Level");
		float damage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordPhysicalDamage);
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		damage *= ConfigHandler.isNormalDamagePercent ? ConfigHandler.normalDamageMulti * weaponDamage : ConfigHandler.normalDamageAmount;
		weaponDamage += damage;
		item.stackTagCompound.setFloat("weaponDamage", weaponDamage);
	}

	public ItemModSword(ToolMaterial material)
	{
		super(material);
		toolMaterial = material;
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
		level = 0;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack item)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", item.stackTagCompound.getFloat("weaponDamage"), 0));
		return multimap;
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer entityPlayer)
	{
		if (item.stackTagCompound == null) item.setTagCompound(new NBTTagCompound());
		float damage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordPhysicalDamage);
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		damage *= ConfigHandler.isNormalDamagePercent ? ConfigHandler.normalDamageMulti * weaponDamage : ConfigHandler.normalDamageAmount;
		weaponDamage += damage;
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setFloat("weaponDamage", weaponDamage);
		item.addEnchantment(ModArmory.shardLooter, 1);
	}

	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase enemy, EntityLivingBase player)
	{
		super.hitEntity(item, enemy, player);

		DamageSource playerDamage = new EntityDamageSource("player", player);
		DamageSource fireDamage = new EntityDamageSource("onFire", player).setDamageBypassesArmor().setFireDamage();
		DamageSource witherDamage = new EntityDamageSource("wither", player).setDamageBypassesArmor();
		DamageSource magicDamage = new EntityDamageSource("magic", player).setDamageBypassesArmor().setMagicDamage();

		float weaponDamage = item.stackTagCompound.getFloat("weaponDamage");

		int pierce = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordArmorPiercing);
		int enemyArmor = enemy.getTotalArmorValue();
		if (enemyArmor >= 25) playerDamage = new EntityDamageSource("player", player).setDamageBypassesArmor();

		float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
		if (pierce != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(playerDamage, weaponDamage * pierceMultiplier);
		}

		float fire = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDamage);
		fire *= ConfigHandler.isFireDamagePercent ? ConfigHandler.fireDamageMulti * weaponDamage : ConfigHandler.fireDamageAmount;
		if (fire != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(fireDamage, Math.round(fire * 4) / 4F);
		}

		float wither = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDamage);
		wither *= ConfigHandler.isWitherDamagePercent ? ConfigHandler.witherDamageMulti * weaponDamage : ConfigHandler.witherDamageAmount;
		if (wither != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(witherDamage, Math.round(wither * 4) / 4F);
		}

		float magic = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDamage);
		magic *= ConfigHandler.isMagicDamagePercent ? ConfigHandler.magicDamageMulti * weaponDamage : ConfigHandler.magicDamageAmount;
		if (magic != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(magicDamage, Math.round(magic * 4) / 4F);
		}

		int poison = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDoT);
		if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));

		int burn = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDoT);
		if (burn != 0) enemy.setFire(burn);

		int decay = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDoT);
		if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));

		int lifesteal = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordLifesteal);
		if (lifesteal != 0) player.heal(lifesteal * weaponDamage * 0.05F);

		int slow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordSlow);
		if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));

		int blind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordBlind);
		if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));

		int knockback = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordKnockback);
		if (knockback != 0) enemy.knockBack(player, weaponDamage, (player.posX - enemy.posX) * knockback, (player.posZ - enemy.posZ) * knockback);

		if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
		{
			try
			{
				DraconicEvolutionHandler.doSwordChaosDamage(item, (EntityPlayer) player, enemy, weaponDamage);
			}
			catch (Exception e)
			{}
		}

		if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
		{
			try
			{
				ExUHandler.doSwordDivineDamage(item, (EntityPlayer) player, enemy, weaponDamage);
			}
			catch (Exception e)
			{}
		}

		if (Loader.isModLoaded("Thaumcraft") /* && ConfigHandler.thaumcraftIntegration */)
		{
			try
			{
				ThaumcraftHandler.doSwordTaintDamage(item, (EntityPlayer) player, enemy, weaponDamage);
				ThaumcraftHandler.doSwordTaintDoT(item, enemy);
			}
			catch (Exception e)
			{}
		}

		if (Loader.isModLoaded("arsmagica2") /* && ConfigHandler.arsMagicaIntegration */)
		{
			try
			{
				ArsMagicaHandler.doSwordFrostDamage(item, (EntityPlayer) player, enemy, weaponDamage);
				ArsMagicaHandler.doSwordHolyDamage(item, (EntityPlayer) player, enemy, weaponDamage);
				ArsMagicaHandler.doSwordLightningDamage(item, (EntityPlayer) player, enemy, weaponDamage);
				ArsMagicaHandler.doSwordWindDamage(item, (EntityPlayer) player, enemy, weaponDamage);
				ArsMagicaHandler.doSwordEntangle(item, enemy);
				ArsMagicaHandler.doSwordFrostSlow(item, enemy);
			}
			catch (Exception e)
			{}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}

	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (item.stackTagCompound == null) onCreated(item, entityPlayer.worldObj, entityPlayer);
		if (GuiScreen.isShiftKeyDown()) list.addAll(addShiftInfo(item));
		else list.addAll(addNormalInfo(item));
	}

	private List addNormalInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModSword.getLevel(item);
		int burn = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDoT);
		int poison = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDoT);
		int decay = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDoT);
		int flux = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordTaintDoT);
		int lifesteal = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordLifesteal);
		int knockback = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordKnockback);
		int blind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordBlind);
		int slow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordSlow);
		int entangle = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordEntangled);
		int frostSlow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFrostSlow);
		int pierce = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordArmorPiercing);
		int damage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordPhysicalDamage);
		int magic = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDamage);
		int fire = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDamage);
		int wither = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDamage);
		int chaos = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordChaosDamage);
		int divine = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordDivineDamage);
		int taint = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordTaintDamage);
		int frost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFrostDamage);
		int holy = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordHolyDamage);
		int lightning = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordLightningDamage);
		int wind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWindDamage);

		if (level != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(level));
		}
		if (burn != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFireDoT.name) + " " + UtilityHelper.toRoman(burn));
		if (poison != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordMagicDoT.name) + " " + UtilityHelper.toRoman(poison));
		if (decay != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWitherDoT.name) + " " + UtilityHelper.toRoman(decay));
		if (flux != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordTaintDoT.name) + " " + UtilityHelper.toRoman(flux));
		if (lifesteal != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordLifesteal.name) + " " + UtilityHelper.toRoman(lifesteal));
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordKnockback.name) + " " + UtilityHelper.toRoman(knockback));
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordBlind.name) + " " + UtilityHelper.toRoman(blind));
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordSlow.name) + " " + UtilityHelper.toRoman(slow));
		if (entangle != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordEntangled.name) + " " + UtilityHelper.toRoman(entangle));
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFrostSlow.name) + " " + UtilityHelper.toRoman(frostSlow));
		if (pierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordArmorPiercing.name) + " " + UtilityHelper.toRoman(pierce));
		if (damage != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordPhysicalDamage.name) + " " + UtilityHelper.toRoman(damage));
		if (magic != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordMagicDamage.name) + " " + UtilityHelper.toRoman(magic));
		if (fire != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFireDamage.name) + " " + UtilityHelper.toRoman(fire));
		if (wither != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWitherDamage.name) + " " + UtilityHelper.toRoman(wither));
		if (chaos != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordChaosDamage.name) + " " + UtilityHelper.toRoman(chaos));
		if (divine != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordDivineDamage.name) + " " + UtilityHelper.toRoman(divine));
		if (taint != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordTaintDamage.name) + " " + UtilityHelper.toRoman(taint));
		if (frost != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFrostDamage.name) + " " + UtilityHelper.toRoman(frost));
		if (holy != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordHolyDamage.name) + " " + UtilityHelper.toRoman(holy));
		if (lightning != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordLightningDamage.name) + " " + UtilityHelper.toRoman(lightning));
		if (wind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWindDamage.name) + " " + UtilityHelper.toRoman(wind));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModSword.getLevel(item);
		int burn = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDoT);
		int poison = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDoT);
		int decay = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDoT);
		int flux = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordTaintDoT);
		int lifesteal = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordLifesteal);
		int knockback = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordKnockback);
		int blind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordBlind);
		int slow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordSlow);
		int entangle = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordEntangled);
		int frostSlow = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFrostSlow);
		int pierce = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordArmorPiercing);
		int damage = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordPhysicalDamage);
		int magic = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordMagicDamage);
		int fire = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFireDamage);
		int wither = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWitherDamage);
		int chaos = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordChaosDamage);
		int divine = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordDivineDamage);
		int taint = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordTaintDamage);
		int frost = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordFrostDamage);
		int holy = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordHolyDamage);
		int lightning = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordLightningDamage);
		int wind = UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.SwordWindDamage);

		if (level != 0) list.add("Level: " + UtilityHelper.toRoman(level));
		if (burn != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFireDoT.name) + ": Attacks light enemies on fire for " + burn + " seconds.");
		if (poison != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordMagicDoT.name) + ": Attacks give Poison " + UtilityHelper.toRoman(poison) + " for 5 seconds.");
		if (decay != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWitherDoT.name) + ": Attacks give Wither " + UtilityHelper.toRoman(decay) + " for 5 seconds.");
		if (flux != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordTaintDoT.name) + ": Attacks taint enemies for " + flux + " seconds.");
		if (lifesteal != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordLifesteal.name) + ": Gain " + UtilityHelper.round(lifesteal * item.stackTagCompound.getFloat("weaponDamage") * 0.05F, 2) + " hearts per attack.");
		if (knockback != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordKnockback.name) + ": Knock enemies away on hit.");
		if (blind != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordBlind.name) + ": Attacks blind enemies for " + blind + " seconds.");
		if (slow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordSlow.name) + ": Attacks slow enemies for " + slow + " seconds.");
		if (entangle != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordEntangled.name) + ": Attacks entangle enemies for " + entangle * 2 + " ticks.");
		if (frostSlow != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFrostSlow.name) + ": Attacks heavily slow enemies for " + frostSlow + " seconds.");
		if (pierce != 0) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordArmorPiercing.name) + ": Attacks ignore " + pierce * 20 + "% of armor.");
		if (damage != 0)
		{
			if (ConfigHandler.isNormalDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordPhysicalDamage.name) + ": Attacks deal " + damage * ConfigHandler.normalDamageMulti * 100 + "% increased damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordPhysicalDamage.name) + ": Attacks deal " + damage * ConfigHandler.normalDamageAmount + " extra damage.");
		}
		if (magic != 0)
		{
			if (ConfigHandler.isMagicDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordMagicDamage.name) + ": Attacks deal " + magic * ConfigHandler.magicDamageMulti * 100 + "% more damage as magic damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordMagicDamage.name) + ": Attacks deal " + magic * ConfigHandler.magicDamageAmount + " extra damage as magic damage.");
		}
		if (fire != 0)
		{
			if (ConfigHandler.isFireDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFireDamage.name) + ": Attacks deal " + fire * ConfigHandler.fireDamageMulti * 100 + "% more damage as fire damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFireDamage.name) + ": Attacks deal " + fire * ConfigHandler.fireDamageAmount + " extra damage as fire damage.");
		}
		if (wither != 0)
		{
			if (ConfigHandler.isWitherDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWitherDamage.name) + ": Attacks deal " + wither * ConfigHandler.witherDamageMulti * 100 + "% more damage as wither damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWitherDamage.name) + ": Attacks deal " + wither * ConfigHandler.witherDamageAmount + " extra damage as wither damage.");
		}
		if (chaos != 0)
		{
			if (ConfigHandler.isChaosDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordChaosDamage.name) + ": Attacks deal " + chaos * ConfigHandler.chaosDamageMulti * 100 + "% more damage as chaos damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordChaosDamage.name) + ": Attacks deal " + chaos * ConfigHandler.chaosDamageAmount + " extra damage as chaos damage.");
		}
		if (divine != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordDivineDamage.name) + ": Attacks deal " + divine * ConfigHandler.divineDamageMulti * 100 + "% more damage as divine damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordDivineDamage.name) + ": Attacks deal " + divine * ConfigHandler.divineDamageAmount + " extra damage as divine damage.");
		}
		if (taint != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordTaintDamage.name) + ": Attacks deal " + taint * ConfigHandler.taintDamageMulti * 100 + "% more damage as taint damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordTaintDamage.name) + ": Attacks deal " + taint * ConfigHandler.taintDamageAmount + " extra damage as taint damage.");
		}
		if (frost != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFrostDamage.name) + ": Attacks deal " + frost * ConfigHandler.frostDamageMulti * 100 + "% more damage as frost damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordFrostDamage.name) + ": Attacks deal " + frost * ConfigHandler.frostDamageAmount + " extra damage as frost damage.");
		}
		if (holy != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordHolyDamage.name) + ": Attacks deal " + holy * ConfigHandler.holyDamageMulti * 100 + "% more damage as holy damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordHolyDamage.name) + ": Attacks deal " + holy * ConfigHandler.holyDamageAmount + " extra damage as holy damage.");
		}
		if (lightning != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordLightningDamage.name) + ": Attacks deal " + lightning * ConfigHandler.lightningDamageMulti * 100 + "% more damage as lightning damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordLightningDamage.name) + ": Attacks deal " + lightning * ConfigHandler.lightningDamageAmount + " extra damage as lightning damage.");
		}
		if (wind != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWindDamage.name) + ": Attacks deal " + wind * ConfigHandler.windDamageMulti * 100 + "% more damage as wind damage.");
			else list.add(StatCollector.translateToLocal(UpgradeRegistry.SwordWindDamage.name) + ": Attacks deal " + wind * ConfigHandler.windDamageAmount + " extra damage as wind damage.");
		}
		return list;
	}
}
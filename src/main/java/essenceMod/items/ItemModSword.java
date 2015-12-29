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
import net.minecraft.world.World;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.handlers.ConfigHandler;
import essenceMod.handlers.compatibility.DraconicEvolutionHandler;
import essenceMod.handlers.compatibility.ExUHandler;
import essenceMod.init.ModArmory;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModSword extends ItemSword implements IModItem
{
	public final ToolMaterial toolMaterial;
	protected float weaponDamage;

	int level;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (item.stackTagCompound == null) onCreated(item, world, (EntityPlayer) entity);
		int level = item.stackTagCompound.getInteger("Level");
		int damage = UtilityHelper.getUpgradeLevel(item, "Damage");
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.05 * damage);
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
		int damage = UtilityHelper.getUpgradeLevel(item, "Damage");
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.05 * damage);
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
		DamageSource magicDamage = new EntityDamageSource("magic", player).setDamageBypassesArmor();

		float weaponDamage = item.stackTagCompound.getFloat("weaponDamage");

		int pierce = UtilityHelper.getUpgradeLevel(item, "Pierce");
		int enemyArmor = enemy.getTotalArmorValue();
		if (enemyArmor >= 25)
		{
			playerDamage = new EntityDamageSource("player", player).setDamageBypassesArmor();
		}

		float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
		if (pierce != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(playerDamage, weaponDamage * pierceMultiplier);
		}

		float fire = UtilityHelper.getUpgradeLevel(item, "Fire");
		fire *= ConfigHandler.isFireDamagePercent ? ConfigHandler.fireDamageMulti * weaponDamage : ConfigHandler.fireDamageAmount;
		if (fire != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(fireDamage, Math.round(fire * 4) / 4F);
		}

		float wither = UtilityHelper.getUpgradeLevel(item, "Wither");
		wither *= ConfigHandler.isWitherDamagePercent ? ConfigHandler.witherDamageMulti * weaponDamage : ConfigHandler.witherDamageAmount;
		if (wither != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(witherDamage, Math.round(wither * 4) / 4F);
		}

		float magic = UtilityHelper.getUpgradeLevel(item, "Magic");
		magic *= ConfigHandler.isMagicDamagePercent ? ConfigHandler.magicDamageMulti * weaponDamage : ConfigHandler.magicDamageAmount;
		if (magic != 0)
		{
			enemy.hurtResistantTime = 0;
			enemy.attackEntityFrom(magicDamage, Math.round(magic * 4) / 4F);
		}

		int poison = UtilityHelper.getUpgradeLevel(item, "Poison");
		if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));

		int burn = UtilityHelper.getUpgradeLevel(item, "Burn");
		if (burn != 0) enemy.setFire(burn);

		int decay = UtilityHelper.getUpgradeLevel(item, "Decay");
		if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));

		int lifesteal = UtilityHelper.getUpgradeLevel(item, "Lifesteal");
		if (lifesteal != 0) player.heal(lifesteal * weaponDamage * 0.05F);

		int slow = UtilityHelper.getUpgradeLevel(item, "Slow");
		if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));

		int blind = UtilityHelper.getUpgradeLevel(item, "Blind");
		if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));

		int knockback = UtilityHelper.getUpgradeLevel(item, "Knockback");
		if (knockback != 0) enemy.knockBack(player, weaponDamage, (player.posX - enemy.posX) * knockback, (player.posZ - enemy.posZ) * knockback);

		if (Loader.isModLoaded("DraconicEvolution") && ConfigHandler.draconicevolutionIntegration)
		{
			try
			{
				DraconicEvolutionHandler.doChaosDamage(item, (EntityPlayer) player, enemy, weaponDamage);
			}
			catch (Exception e)
			{}
		}

		if (Loader.isModLoaded("ExtraUtilities") && ConfigHandler.extrautilitiesIntegration)
		{
			try
			{
				ExUHandler.doDivineDamage(item, (EntityPlayer) player, enemy, weaponDamage);
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
		int burn = UtilityHelper.getUpgradeLevel(item, "Burn");
		int poison = UtilityHelper.getUpgradeLevel(item, "Poison");
		int decay = UtilityHelper.getUpgradeLevel(item, "Decay");
		int lifesteal = UtilityHelper.getUpgradeLevel(item, "Lifesteal");
		int knockback = UtilityHelper.getUpgradeLevel(item, "Knockback");
		int blind = UtilityHelper.getUpgradeLevel(item, "Blind");
		int slow = UtilityHelper.getUpgradeLevel(item, "Slow");
		int pierce = UtilityHelper.getUpgradeLevel(item, "Pierce");
		int damage = UtilityHelper.getUpgradeLevel(item, "Damage");
		int magic = UtilityHelper.getUpgradeLevel(item, "Magic");
		int fire = UtilityHelper.getUpgradeLevel(item, "Fire");
		int wither = UtilityHelper.getUpgradeLevel(item, "Wither");
		int chaos = UtilityHelper.getUpgradeLevel(item, "Chaos");
		int divine = UtilityHelper.getUpgradeLevel(item, "Divine");

		if (level != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(level));
		}
		if (burn != 0) list.add("Burn " + UtilityHelper.toRoman(burn));
		if (poison != 0) list.add("Poison " + UtilityHelper.toRoman(poison));
		if (decay != 0) list.add("Decay " + UtilityHelper.toRoman(decay));
		if (lifesteal != 0) list.add("Leech " + UtilityHelper.toRoman(lifesteal));
		if (knockback != 0) list.add("Knockback " + UtilityHelper.toRoman(knockback));
		if (blind != 0) list.add("Blind " + UtilityHelper.toRoman(blind));
		if (slow != 0) list.add("Slow " + UtilityHelper.toRoman(slow));
		if (pierce != 0) list.add("Piercing " + UtilityHelper.toRoman(pierce));
		if (damage != 0) list.add("Sharpness " + UtilityHelper.toRoman(damage));
		if (magic != 0) list.add("Wrath " + UtilityHelper.toRoman(magic));
		if (fire != 0) list.add("Anger " + UtilityHelper.toRoman(fire));
		if (wither != 0) list.add("Hatred " + UtilityHelper.toRoman(wither));
		if (chaos != 0) list.add("Entropy " + UtilityHelper.toRoman(chaos));
		if (divine != 0) list.add("Divinity " + UtilityHelper.toRoman(divine));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();

		int level = ItemModSword.getLevel(item);
		int burn = UtilityHelper.getUpgradeLevel(item, "Burn");
		int poison = UtilityHelper.getUpgradeLevel(item, "Poison");
		int decay = UtilityHelper.getUpgradeLevel(item, "Decay");
		int lifesteal = UtilityHelper.getUpgradeLevel(item, "Lifesteal");
		int knockback = UtilityHelper.getUpgradeLevel(item, "Knockback");
		int blind = UtilityHelper.getUpgradeLevel(item, "Blind");
		int slow = UtilityHelper.getUpgradeLevel(item, "Slow");
		int pierce = UtilityHelper.getUpgradeLevel(item, "Pierce");
		int damage = UtilityHelper.getUpgradeLevel(item, "Damage");
		int magic = UtilityHelper.getUpgradeLevel(item, "Magic");
		int fire = UtilityHelper.getUpgradeLevel(item, "Fire");
		int wither = UtilityHelper.getUpgradeLevel(item, "Wither");
		int chaos = UtilityHelper.getUpgradeLevel(item, "Chaos");
		int divine = UtilityHelper.getUpgradeLevel(item, "Divine");

		if (level != 0) list.add("Level: " + UtilityHelper.toRoman(level));
		if (burn != 0) list.add("Burn: Attacks light enemies on fire for " + burn + " seconds.");
		if (poison != 0) list.add("Poison: Attacks give Poison " + UtilityHelper.toRoman(poison) + " for 5 seconds.");
		if (decay != 0) list.add("Decay: Attacks give Wither " + UtilityHelper.toRoman(decay) + " for 5 seconds.");
		if (lifesteal != 0) list.add("Leech: Gain " + UtilityHelper.round(lifesteal * item.stackTagCompound.getFloat("weaponDamage") * 0.05F, 2) + " hearts per attack.");
		if (knockback != 0) list.add("Knockback: Knock enemies away on hit.");
		if (blind != 0) list.add("Blind: Attacks blind enemies for " + blind + " seconds.");
		if (slow != 0) list.add("Slow: Attacks slow enemies for " + slow + " seconds.");
		if (pierce != 0) list.add("Piercing: Attacks ignore " + pierce * 20 + "% of armor.");
		if (damage != 0)
		{
			if (ConfigHandler.isNormalDamagePercent) list.add("Sharpness: Attacks deal " + damage * ConfigHandler.normalDamageMulti * 100 + "% increased damage.");
			else list.add("Sharpness: Attacks deal " + damage * ConfigHandler.normalDamageAmount + " extra damage.");
		}
		if (magic != 0)
		{
			if (ConfigHandler.isMagicDamagePercent) list.add("Wrath: Attacks deal " + magic * ConfigHandler.magicDamageMulti * 100 + "% more damage as magic damage.");
			else list.add("Wrath: Attacks deal " + magic * ConfigHandler.magicDamageAmount + " extra damage as magic damage.");
		}
		if (fire != 0)
		{
			if (ConfigHandler.isFireDamagePercent) list.add("Anger: Attacks deal " + fire * ConfigHandler.fireDamageMulti * 100 + "% more damage as fire damage.");
			else list.add("Anger: Attacks deal " + fire * ConfigHandler.fireDamageAmount + " extra damage as fire damage.");
		}
		if (wither != 0)
		{
			if (ConfigHandler.isWitherDamagePercent) list.add("Hatred: Attacks deal " + wither * ConfigHandler.witherDamageMulti * 100 + "% more damage as wither damage.");
			else list.add("Hatred: Attacks deal " + wither * ConfigHandler.witherDamageAmount + " extra damage as wither damage.");
		}
		if (chaos != 0)
		{
			if (ConfigHandler.isChaosDamagePercent) list.add("Entropy: Attacks deal " + chaos * ConfigHandler.chaosDamageMulti * 100 + "% more damage as chaos damage.");
			else list.add("Entropy: Attacks deal " + chaos * ConfigHandler.chaosDamageAmount + " extra damage as chaos damage.");
		}
		if (divine != 0)
		{
			if (ConfigHandler.isDivineDamagePercent) list.add("Divinity: Attacks deal " + divine * ConfigHandler.divineDamageMulti * 100 + "% more damage as divine damage.");
			else list.add("Divinity: Attacks deal " + divine * ConfigHandler.divineDamageAmount + " extra damage as divine damage.");
		}
		return list;
	}
}
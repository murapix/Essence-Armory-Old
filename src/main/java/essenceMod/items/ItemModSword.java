package essenceMod.items;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import com.brandon3055.draconicevolution.common.utills.DamageSourceChaos;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rwtema.extrautils.item.ItemLawSword.DamageSourceEvil;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.crafting.Upgrade;
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
		int damage = ItemModSword.getUpgradeLevel(item, "Damage");
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
		int damage = ItemModSword.getUpgradeLevel(item, "Damage");
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.05 * damage);
		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setFloat("weaponDamage", weaponDamage);
		item.addEnchantment(ModArmory.shardLooter, 1);
	}

	public boolean hitEntity(ItemStack item, EntityLivingBase enemy, EntityLivingBase player)
	{
		DamageSource playerDamage = DamageSource.causePlayerDamage((EntityPlayer) player);
		DamageSource fireDamage = DamageSource.onFire;
		DamageSource witherDamage = DamageSource.wither;
		DamageSource magicDamage = DamageSource.magic;

		float weaponDamage = item.stackTagCompound.getFloat("weaponDamage");

		int pierce = item.stackTagCompound.getInteger("Pierce");
		int enemyArmor = enemy.getTotalArmorValue();
		if (enemyArmor >= 25)
		{
			playerDamage = new EntityDamageSource("player", player).setDamageBypassesArmor();
		}

		float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
		if (pierce != 0) enemy.attackEntityFrom(playerDamage, weaponDamage * pierceMultiplier);

		float fireMult = item.stackTagCompound.getInteger("Fire") * 0.05F;
		if (fireMult != 0) enemy.attackEntityFrom(fireDamage, weaponDamage * fireMult);

		float witherMult = item.stackTagCompound.getInteger("Wither") * 0.05F;
		if (witherMult != 0) enemy.attackEntityFrom(witherDamage, weaponDamage * witherMult);

		float magicMult = item.stackTagCompound.getInteger("Magic") * 0.05F;
		if (magicMult != 0) enemy.attackEntityFrom(magicDamage, weaponDamage * magicMult);

		int poison = item.stackTagCompound.getInteger("Poison");
		if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));

		int burn = item.stackTagCompound.getInteger("Burn");
		if (burn != 0) enemy.setFire(burn);

		int decay = item.stackTagCompound.getInteger("Decay");
		if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));

		int lifesteal = item.stackTagCompound.getInteger("Lifesteal");
		if (lifesteal != 0) player.heal(lifesteal * weaponDamage * 0.05F);

		int slow = item.stackTagCompound.getInteger("Slow");
		if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));

		int blind = item.stackTagCompound.getInteger("Blind");
		if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));

		int knockback = item.stackTagCompound.getInteger("Knockback");
		if (knockback != 0) enemy.knockBack(player, weaponDamage, (player.posX - enemy.posX) * knockback, (player.posZ - enemy.posZ) * knockback);

		if (Loader.isModLoaded("DraconicEvolution"))
		{
			DamageSource chaos = new DamageSourceChaos(player);
			float chaosMult = ItemModSword.getUpgradeLevel(item, "Chaos") * 0.05F;
			if (chaosMult != 0) enemy.attackEntityFrom(chaos, weaponDamage * chaosMult);
		}
		
		if (Loader.isModLoaded("ExtraUtilities"))
		{
			if (player instanceof EntityPlayer)
			{
				DamageSource divine = new DamageSourceEvil((EntityPlayer) player, true);
				float divineMult = ItemModSword.getUpgradeLevel(item, "Divine") * 0.05F;
				if (divineMult != 0) enemy.attackEntityFrom(divine, weaponDamage * divineMult);
			}
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
		int burn = ItemModSword.getUpgradeLevel(item, "Burn");
		int poison = ItemModSword.getUpgradeLevel(item, "Poison");
		int decay = ItemModSword.getUpgradeLevel(item, "Decay");
		int lifesteal = ItemModSword.getUpgradeLevel(item, "Lifesteal");
		int knockback = ItemModSword.getUpgradeLevel(item, "Knockback");
		int blind = ItemModSword.getUpgradeLevel(item, "Blind");
		int slow = ItemModSword.getUpgradeLevel(item, "Slow");
		int pierce = ItemModSword.getUpgradeLevel(item, "Pierce");
		int damage = ItemModSword.getUpgradeLevel(item, "Damage");
		int magic = ItemModSword.getUpgradeLevel(item, "Magic");
		int fire = ItemModSword.getUpgradeLevel(item, "Fire");
		int wither = ItemModSword.getUpgradeLevel(item, "Wither");
		int chaos = ItemModSword.getUpgradeLevel(item, "Chaos");
		int divine = ItemModSword.getUpgradeLevel(item, "Divine");

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
		int burn = ItemModSword.getUpgradeLevel(item, "Burn");
		int poison = ItemModSword.getUpgradeLevel(item, "Poison");
		int decay = ItemModSword.getUpgradeLevel(item, "Decay");
		int lifesteal = ItemModSword.getUpgradeLevel(item, "Lifesteal");
		int knockback = ItemModSword.getUpgradeLevel(item, "Knockback");
		int blind = ItemModSword.getUpgradeLevel(item, "Blind");
		int slow = ItemModSword.getUpgradeLevel(item, "Slow");
		int pierce = ItemModSword.getUpgradeLevel(item, "Pierce");
		int damage = ItemModSword.getUpgradeLevel(item, "Damage");
		int magic = ItemModSword.getUpgradeLevel(item, "Magic");
		int fire = ItemModSword.getUpgradeLevel(item, "Fire");
		int wither = ItemModSword.getUpgradeLevel(item, "Wither");
		int chaos = ItemModSword.getUpgradeLevel(item, "Chaos");
		int divine = ItemModSword.getUpgradeLevel(item, "Divine");

		if (level != 0) list.add("Level: " + UtilityHelper.toRoman(level));
		if (burn != 0) list.add("Burn: Attacks light enemies on fire for " + burn + " seconds.");
		if (poison != 0) list.add("Poison: Attacks give Poison " + UtilityHelper.toRoman(poison) + " for 5 seconds.");
		if (decay != 0) list.add("Decay: Attacks give Wither " + UtilityHelper.toRoman(decay) + " for 5 seconds.");
		if (lifesteal != 0) list.add("Leech: Gain " + UtilityHelper.round(lifesteal * item.stackTagCompound.getFloat("weaponDamage") * 0.05F, 2) + " hearts per attack.");
		if (knockback != 0) list.add("Knockback: Knock enemies away on hit.");
		if (blind != 0) list.add("Blind: Attacks blind enemies for " + blind + " seconds.");
		if (slow != 0) list.add("Slow: Attacks slow enemies for " + slow + " seconds.");
		if (pierce != 0) list.add("Piercing: Attacks ignore " + pierce * 20 + "% of armor.");
		if (damage != 0) list.add("Sharpness: Attacks deal " + damage * 5 + "% increased damage.");
		if (magic != 0) list.add("Wrath: Attacks deal " + magic * 5 + "% more damage as magic damage.");
		if (fire != 0) list.add("Anger: Attacks deal " + fire * 5 + "% more damage as fire damage.");
		if (wither != 0) list.add("Hatred: Attacks deal " + wither * 5 + "% more damage as wither damage.");
		if (chaos != 0) list.add("Entropy: Attacks deal " + chaos * 5 + "% more damage as chaos damage.");
		if (divine != 0) list.add("Divinity: Attacks deal " + divine * 5 + "% more damage as divine damage.");
		return list;
	}
	
	/**
	 * Returns the level of the upgrade with the given name found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param name
	 *            The name of the upgrade.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, String name)
	{
		NBTTagList upgradeList = item.stackTagCompound.getTagList("Upgrades", NBT.TAG_COMPOUND);
		for (int i = 0; i < upgradeList.tagCount(); i++)
		{
			NBTTagCompound tag = upgradeList.getCompoundTagAt(i);
			if (tag.getString("Name").equals(name)) return tag.getInteger("Level");
		}
		return 0;
	}

	/**
	 * Returns the level of the given upgrade found on the given item. If the upgrade does not exist on the item, a level of 0 will be returned.
	 * 
	 * @param item
	 *            The item being checked for the upgrade.
	 * @param upgrade
	 *            The upgrade of which the level is being checked.
	 * @return The level of the given upgrade. Returns 0 if the upgrade is not found.
	 */
	public static int getUpgradeLevel(ItemStack item, Upgrade upgrade)
	{
		if (upgrade == null || upgrade.name == null) return 0;
		return ItemModSword.getUpgradeLevel(item, upgrade.name);
	}
}
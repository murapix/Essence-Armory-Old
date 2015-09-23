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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.help.Reference;
import essenceMod.help.UtilityHelper;
import essenceMod.tabs.ModTabs;

public class ItemModSword extends ItemSword implements IModItem
{
	public final ToolMaterial toolMaterial;
	protected float weaponDamage;

	int level, burn, poison, decay, pierce, lifesteal, knockback, blind, slow,
			damage, wither, magic, fire;

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		if (item.stackTagCompound == null) onCreated(item, world, (EntityPlayer) entity);
		level = item.stackTagCompound.getInteger("Level");
		damage = item.stackTagCompound.getInteger("Damage");
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.2 * damage);
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

	public ItemModSword(ToolMaterial material, String[] upgrades)
	{
		this(material);

		level = upgrades.length;
		for (String str : upgrades)
		{
			if (str.equals("Burn")) burn++;
			else if (str.equals("Poison")) poison++;
			else if (str.equals("Decay")) decay++;
			else if (str.equals("Pierce")) pierce++;
			else if (str.equals("Lifesteal")) lifesteal++;
			else if (str.equals("Knockback")) knockback++;
			else if (str.equals("Blind")) blind++;
			else if (str.equals("Slow")) slow++;
			else if (str.equals("Damage")) damage++;
			else if (str.equals("Wither")) wither++;
			else if (str.equals("Magic")) magic++;
			else if (str.equals("Fire")) fire++;
		}
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
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.2 * damage);

		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setFloat("weaponDamage", weaponDamage);
		item.stackTagCompound.setInteger("Burn", burn);
		item.stackTagCompound.setInteger("Poison", poison);
		item.stackTagCompound.setInteger("Decay", decay);
		item.stackTagCompound.setInteger("Pierce", pierce);
		item.stackTagCompound.setInteger("Lifesteal", lifesteal);
		item.stackTagCompound.setInteger("Knockback", knockback);
		item.stackTagCompound.setInteger("Blind", blind);
		item.stackTagCompound.setInteger("Slow", slow);
		item.stackTagCompound.setInteger("Damage", damage);
		item.stackTagCompound.setInteger("Wither", wither);
		item.stackTagCompound.setInteger("Magic", magic);
		item.stackTagCompound.setInteger("Fire", fire);
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

		float fireMult = item.stackTagCompound.getInteger("Fire") / 5F;
		if (fireMult != 0) enemy.attackEntityFrom(fireDamage, weaponDamage * fireMult);

		float witherMult = item.stackTagCompound.getInteger("Wither") / 5F;
		if (witherMult != 0) enemy.attackEntityFrom(witherDamage, weaponDamage * witherMult);

		float magicMult = item.stackTagCompound.getInteger("Magic") / 5F;
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

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}

	@Override
	public boolean hasEffect(ItemStack item)
	{
		return true;
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
		if (item.stackTagCompound.getInteger("Level") != 0)
		{
			list.add("Hold SHIFT for more information");
			list.add("Level: " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Level")));
		}
		if (item.stackTagCompound.getInteger("Burn") != 0) list.add("Burn " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Burn")));
		if (item.stackTagCompound.getInteger("Poison") != 0) list.add("Poison " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Poison")));
		if (item.stackTagCompound.getInteger("Decay") != 0) list.add("Decay " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Decay")));
		if (item.stackTagCompound.getInteger("Lifesteal") != 0) list.add("Leech " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Lifesteal")));
		if (item.stackTagCompound.getInteger("Knockback") != 0) list.add("Knockback " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Knockback")));
		if (item.stackTagCompound.getInteger("Blind") != 0) list.add("Blind " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Blind")));
		if (item.stackTagCompound.getInteger("Slow") != 0) list.add("Slow " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Slow")));
		if (item.stackTagCompound.getInteger("Pierce") != 0) list.add("Piercing " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Pierce")));
		if (item.stackTagCompound.getInteger("Damage") != 0) list.add("Sharpness " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Damage")));
		if (item.stackTagCompound.getInteger("Magic") != 0) list.add("Wrath " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Magic")));
		if (item.stackTagCompound.getInteger("Fire") != 0) list.add("Anger " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Fire")));
		if (item.stackTagCompound.getInteger("Wither") != 0) list.add("Hatred " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Wither")));

		return list;
	}

	private List addShiftInfo(ItemStack item)
	{
		List list = new ArrayList();
		if (item.stackTagCompound.getInteger("Level") != 0) list.add("Level: " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Level")));
		if (item.stackTagCompound.getInteger("Burn") != 0) list.add("Burn: Attacks light enemies on fire for " + item.stackTagCompound.getInteger("Burn") + " seconds.");
		if (item.stackTagCompound.getInteger("Poison") != 0) list.add("Poison: Attacks give Poison " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Poison")) + " for 5 seconds.");
		if (item.stackTagCompound.getInteger("Decay") != 0) list.add("Decay: Attacks give Wither " + UtilityHelper.toRoman(item.stackTagCompound.getInteger("Decay")) + " for 5 seconds.");
		if (item.stackTagCompound.getInteger("Lifesteal") != 0) list.add("Leech: Gain " + UtilityHelper.round(item.stackTagCompound.getInteger("Lifesteal") * item.stackTagCompound.getFloat("weaponDamage") * 0.05F, 2) + " hearts per attack.");
		if (item.stackTagCompound.getInteger("Knockback") != 0) list.add("Knockback: Knock enemies away on hit.");
		if (item.stackTagCompound.getInteger("Blind") != 0) list.add("Blind: Attacks blind enemies for " + item.stackTagCompound.getInteger("Blind") + " seconds.");
		if (item.stackTagCompound.getInteger("Slow") != 0) list.add("Slow: Attacks slow enemies for " + item.stackTagCompound.getInteger("Slow") + " seconds.");
		if (item.stackTagCompound.getInteger("Pierce") != 0) list.add("Piercing: Attacks ignore " + item.stackTagCompound.getInteger("Pierce") * 20 + "% of armor.");
		if (item.stackTagCompound.getInteger("Damage") != 0) list.add("Sharpness: Attacks deal " + item.stackTagCompound.getInteger("Damage") * 20 + "% increased damage.");
		if (item.stackTagCompound.getInteger("Magic") != 0) list.add("Wrath: Attacks deal " + item.stackTagCompound.getInteger("Magic") * 20 + "% more damage as magic damage.");
		if (item.stackTagCompound.getInteger("Fire") != 0) list.add("Anger: Attacks deal " + item.stackTagCompound.getInteger("Fire") * 20 + "% more damage as fire damage.");
		if (item.stackTagCompound.getInteger("Wither") != 0) list.add("Hatred: Attacks deal " + item.stackTagCompound.getInteger("Wither") * 20 + "% more damage as wither damage.");
		return list;
	}

	public static ItemStack addLevel(ItemStack item, String upgrade)
	{
		if (upgrade == null || upgrade.length() == 0) return item;

		int level = item.stackTagCompound.getInteger("Level");
		int upgradeStat = item.stackTagCompound.getInteger(upgrade);

		if (upgradeStat < 5)
		{
			level++;
			upgradeStat++;
		}

		item.stackTagCompound.setInteger("Level", level);
		item.stackTagCompound.setInteger(upgrade, upgradeStat);

		return item;
	}
}
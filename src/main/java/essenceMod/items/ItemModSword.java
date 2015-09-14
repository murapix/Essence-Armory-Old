package essenceMod.items;

import java.util.List;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.help.Reference;
import essenceMod.help.UtilityHelper;
import essenceMod.tabs.ModTabs;

public class ItemModSword extends ItemSword implements IModItem
{
	public final ToolMaterial toolMaterial;
	protected float weaponDamage;

	int level, burn, poison, decay, pierce, lifesteal, knockback, blind, slow, damage, wither, magic, fire;

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b)
	{
		if (itemStack.stackTagCompound == null) onCreated(itemStack, world, (EntityPlayer) entity);
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
	public Multimap getAttributeModifiers(ItemStack stack)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", stack.stackTagCompound.getFloat("weaponDamage"), 0));
		return multimap;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if (itemStack.stackTagCompound == null) itemStack.setTagCompound(new NBTTagCompound());
		weaponDamage = 4.0F + toolMaterial.getDamageVsEntity() + level;
		weaponDamage *= (1 + 0.2 * damage);

		itemStack.stackTagCompound.setInteger("Level", level);
		itemStack.stackTagCompound.setFloat("weaponDamage", weaponDamage);
		itemStack.stackTagCompound.setInteger("Burn", burn);
		itemStack.stackTagCompound.setInteger("Poison", poison);
		itemStack.stackTagCompound.setInteger("Decay", decay);
		itemStack.stackTagCompound.setInteger("Pierce", pierce);
		itemStack.stackTagCompound.setInteger("Lifesteal", lifesteal);
		itemStack.stackTagCompound.setInteger("Knockback", knockback);
		itemStack.stackTagCompound.setInteger("Blind", blind);
		itemStack.stackTagCompound.setInteger("Slow", slow);
		itemStack.stackTagCompound.setInteger("Damage", damage);
		itemStack.stackTagCompound.setInteger("Wither", wither);
		itemStack.stackTagCompound.setInteger("Magic", magic);
		itemStack.stackTagCompound.setInteger("Fire", fire);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase enemy, EntityLivingBase player)
	{
		DamageSource playerDamage = DamageSource.causePlayerDamage((EntityPlayer) player);
		DamageSource fireDamage = DamageSource.onFire;
		DamageSource witherDamage = DamageSource.wither;
		DamageSource magicDamage = DamageSource.magic;

		float weaponDamage = stack.stackTagCompound.getFloat("weaponDamage");

		int pierce = stack.stackTagCompound.getInteger("Pierce");
		int enemyArmor = enemy.getTotalArmorValue();
		if (enemyArmor >= 25)
		{
			playerDamage = new EntityDamageSource("player", player).setDamageBypassesArmor();
		}
		
		float pierceMultiplier = ((1F / (1F - (enemy.getTotalArmorValue() * 0.04F)) - 1F) * pierce / 5F);
		if (pierce != 0) enemy.attackEntityFrom(playerDamage, weaponDamage * pierceMultiplier);
		
		float fireMult = stack.stackTagCompound.getInteger("Fire") / 5F;
		if (fireMult != 0) enemy.attackEntityFrom(fireDamage, weaponDamage * fireMult);
		
		float witherMult = stack.stackTagCompound.getInteger("Wither") / 5F;
		if (witherMult != 0) enemy.attackEntityFrom(witherDamage, weaponDamage * witherMult);
		
		float magicMult = stack.stackTagCompound.getInteger("Magic") / 5F;
		if (magicMult != 0) enemy.attackEntityFrom(magicDamage, weaponDamage * magicMult);
		
		int poison = stack.stackTagCompound.getInteger("Poison");
		if (poison != 0) enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 25 * poison, poison - 1));
		
		int burn = stack.stackTagCompound.getInteger("Burn");
		if (burn != 0) enemy.setFire(burn);
		
		int decay = stack.stackTagCompound.getInteger("Decay");
		if (decay != 0) enemy.addPotionEffect(new PotionEffect(Potion.wither.id, 25 * decay, decay - 1));
		
		int lifesteal = stack.stackTagCompound.getInteger("Lifesteal");
		if (lifesteal != 0) player.heal(lifesteal * weaponDamage * 0.05F);
		
		int slow = stack.stackTagCompound.getInteger("Slow");
		if (slow != 0) enemy.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 25 * slow, slow - 1));
		
		int blind = stack.stackTagCompound.getInteger("Blind");
		if (blind != 0) enemy.addPotionEffect(new PotionEffect(Potion.blindness.id, 25 * slow));
		
		int knockback = stack.stackTagCompound.getInteger("Knockback");
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
	public boolean hasEffect(ItemStack itemStack)
	{
		return true;
	}

	public static int getLevel(ItemStack item)
	{
		return item.stackTagCompound.getInteger("Level");
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean bool)
	{
		if (itemStack.stackTagCompound == null) onCreated(itemStack, entityPlayer.worldObj, entityPlayer);

		if (itemStack.stackTagCompound.getInteger("Level") != 0) list.add("Level: " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Level")));
		if (itemStack.stackTagCompound.getInteger("Burn") != 0) list.add("Burn " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Burn")));
		if (itemStack.stackTagCompound.getInteger("Poison") != 0) list.add("Poison " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Poison")));
		if (itemStack.stackTagCompound.getInteger("Decay") != 0) list.add("Decay " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Decay")));
		if (itemStack.stackTagCompound.getInteger("Lifesteal") != 0) list.add("Leech " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Lifesteal")));
		if (itemStack.stackTagCompound.getInteger("Knockback") != 0) list.add("Knockback " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Knockback")));
		if (itemStack.stackTagCompound.getInteger("Blind") != 0) list.add("Blind " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Blind")));
		if (itemStack.stackTagCompound.getInteger("Slow") != 0) list.add("Slow " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Slow")));
		if (itemStack.stackTagCompound.getInteger("Pierce") != 0) list.add("Piercing " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Pierce")));
		if (itemStack.stackTagCompound.getInteger("Damage") != 0) list.add("Sharpness " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Damage")));
		if (itemStack.stackTagCompound.getInteger("Magic") != 0) list.add("Wrath " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Magic")));
		if (itemStack.stackTagCompound.getInteger("Fire") != 0) list.add("Anger " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Fire")));
		if (itemStack.stackTagCompound.getInteger("Wither") != 0) list.add("Hatred " + UtilityHelper.toRoman(itemStack.stackTagCompound.getInteger("Wither")));
	}
}
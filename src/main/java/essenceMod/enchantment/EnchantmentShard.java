package essenceMod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentShard extends Enchantment
{
	public EnchantmentShard(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		this.setName("shardLoot");
	}
	
	public int getMinEnchantability(int level)
	{
		return 1;
	}
	
	public int getMaxEnchantability(int level)
	{
		return Integer.MAX_VALUE;
	}
}
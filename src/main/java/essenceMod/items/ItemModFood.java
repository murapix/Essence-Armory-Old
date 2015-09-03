package essenceMod.items;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import essenceMod.tabs.ModTabs;

public class ItemModFood extends ItemFood 
{
	ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
	
	public ItemModFood(int healAmount, boolean wolfFood)
	{
		super(healAmount, wolfFood);
		setCreativeTab(ModTabs.tabEssence);
		setAlwaysEdible();
	}
	
	public ItemModFood(int healAmount, float saturationModifier, boolean wolfFood)
	{
		super(healAmount, saturationModifier, wolfFood);
		setCreativeTab(ModTabs.tabEssence);
		setAlwaysEdible();
	}
	
	@Override
	protected void onFoodEaten(ItemStack item, World world, EntityPlayer player)
	{
		if (world.isRemote) return;
		for (PotionEffect potion : effects)
		{
			if (potion.getPotionID() > 0) player.addPotionEffect(potion);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		player.setItemInUse(item, getMaxItemUseDuration(item));
		return item;
	}
	
	@Override
	public ItemModFood setPotionEffect(int potionID, int potionDuration, int potionAmplifier, float effectProbability)
	{
		PotionEffect effect = new PotionEffect(potionID, potionDuration * 20, potionAmplifier);
		effects.add(effect);
		return this;
	}
	
	public ItemModFood addPotionEffect(int potionID, int potionDuration, int potionAmplifier)
	{
		return setPotionEffect(potionID, potionDuration, potionAmplifier, 0);
	}
}
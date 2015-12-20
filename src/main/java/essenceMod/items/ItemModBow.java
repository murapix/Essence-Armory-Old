package essenceMod.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;

public class ItemModBow extends ItemBow implements IModItem
{
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public ItemModBow()
	{
		super();
		setMaxDamage(1500);
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_standby");
		iconArray = new IIcon[bowPullIconNameArray.length];
		for (int i = 0; i < iconArray.length; ++i)
		{
			iconArray[i] = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5) + "_" + bowPullIconNameArray[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int duration)
	{
		return iconArray[duration];
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.epic;
	}
}
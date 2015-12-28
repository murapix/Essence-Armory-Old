package essenceMod.items.baubles;

import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

public class ItemBaseAmulet extends ItemBauble
{
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}
}

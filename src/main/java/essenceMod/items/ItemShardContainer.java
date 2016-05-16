package essenceMod.items;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemShardContainer extends ItemMod
{
	public ItemShardContainer()
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(16384);
	}
	
	public static int addShards(ItemStack item, int amount)
	{
		if (!(item.getItem() instanceof ItemShardContainer)) return amount;
		int space = item.getMaxDamage() - item.getItemDamage();
		if (amount > space)
		{
			item.setItemDamage(item.getMaxDamage());
			return amount - space;
		}
		item.setItemDamage(item.getItemDamage() + amount);
		return 0;
	}
	
	public static int removeShards(ItemStack item, int amount)
	{
		if (!(item.getItem() instanceof ItemShardContainer)) return amount;
		int left = item.getItemDamage();
		if (amount > left)
		{
			item.setItemDamage(0);
			return amount - left;
		}
		item.setItemDamage(item.getItemDamage() - amount);
		return 0;
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
		tooltip.add("Shards: " + item.getItemDamage());
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack item)
    {
        return false;
    }
}

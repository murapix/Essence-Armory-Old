package essenceMod.items.baubles;

import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.common.lib.PlayerHandler;

public class ItemFlightAmulet extends ItemBauble
{
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}
	
	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player)
	{
		super.onWornTick(item, player);
		EntityPlayer p = (EntityPlayer) player;
		p.capabilities.allowFlying = true;
	}
	
	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player)
	{
		EntityPlayer p = (EntityPlayer) player;
		p.capabilities.allowFlying = false;
	}
	
	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		if (PlayerHandler.getPlayerBaubles(player).getStackInSlot(0).getItem() instanceof ItemFlightAmulet) player.capabilities.allowFlying = true;
	}
}
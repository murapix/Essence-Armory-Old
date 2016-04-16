package essenceMod.blocks.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import essenceMod.entities.tileEntities.TileEntityEssencePylon;

@SuppressWarnings("rawtypes")
public class PylonRenderer extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int i)
	{
		if (te instanceof TileEntityEssencePylon)
		{
			TileEntityEssencePylon pylon = (TileEntityEssencePylon) te;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
			if (pylon.getStackInSlot(0) != null)
			{
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld, x, y, z, pylon.getStackInSlot(0));
				item.hoverStart = 0;
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
			}
			GL11.glPopMatrix();
		}
	}
}
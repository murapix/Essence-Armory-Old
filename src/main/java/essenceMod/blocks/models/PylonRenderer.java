package essenceMod.blocks.models;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.utility.Reference;

public class PylonRenderer extends TileEntitySpecialRenderer
{
	private ResourceLocation pylonModel = new ResourceLocation(Reference.MODID + ":models/block/EssencePylon.obj");
	private final IModelCustom model = AdvancedModelLoader.loadModel(pylonModel);
	private RenderItem itemRenderer;
	
	public PylonRenderer()
	{
		itemRenderer = new RenderItem();
		itemRenderer.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z + 1);
		ResourceLocation textures = new ResourceLocation(Reference.MODID + ":textures/blocks/essencePylon.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		model.renderAll();
		GL11.glPopMatrix();
		
		if (te instanceof TileEntityEssencePylon)
		{
			TileEntityEssencePylon pylon = (TileEntityEssencePylon) te;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
			if (pylon.getStackInSlot(0) != null)
			{
				EntityItem hover = new EntityItem(pylon.getWorldObj());
				hover.hoverStart = 0.0F;
				hover.setEntityItemStack(pylon.getStackInSlot(0));
				itemRenderer.doRender(hover, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix();
		}
	}
}
package essenceMod.handlers;

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
import org.lwjgl.opengl.GL11;
import essenceMod.blocks.models.ModelEssencePylon;
import essenceMod.entities.tileEntities.TileEntityEssencePylon;
import essenceMod.utility.Reference;

public class TileEntityEssencePylonRenderer extends TileEntitySpecialRenderer
{
	private final ModelEssencePylon model;
	private RenderItem ghostItemRenderer;
	
	public TileEntityEssencePylonRenderer()
	{
		model = new ModelEssencePylon();
		ghostItemRenderer = new RenderItem();
		ghostItemRenderer.setRenderManager(RenderManager.instance);
	}
	
	private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		GL11.glPushMatrix();
		GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		ResourceLocation textures = new ResourceLocation(Reference.MODID + ":textures/blocks/essencePylon.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		if (te instanceof TileEntityEssencePylon)
		{
			TileEntityEssencePylon pylon = (TileEntityEssencePylon) te;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.5, z + 0.5);
			if (pylon.getStackInSlot(0) != null)
			{
				EntityItem hover = new EntityItem(pylon.getWorldObj());
				hover.hoverStart = 0.0F;
				hover.setEntityItemStack(pylon.getStackInSlot(0));
				ghostItemRenderer.doRender(hover, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix();
		}
	}
	
	private void adjustLightingFixture(World world, int i, int j, int k, Block block)
	{
			Tessellator tess = Tessellator.instance;
			float brightness = block.getLightValue(world, i, j, k);
			int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
			int modulusModifier = skyLight % 65536;
			int divModifier = skyLight / 65536;
			tess.setColorOpaque_F(brightness, brightness, brightness);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulusModifier, divModifier);
	}

	
}

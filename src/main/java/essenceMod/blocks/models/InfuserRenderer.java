package essenceMod.blocks.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import essenceMod.entities.tileEntities.TileEntityEssenceInfuser;
import essenceMod.utility.Reference;

public class InfuserRenderer extends TileEntitySpecialRenderer
{
	private ResourceLocation infuserModel = new ResourceLocation(Reference.MODID + ":models/block/EssenceInfuser.obj");
	private final IModelCustom model = AdvancedModelLoader.loadModel(infuserModel);
	private RenderItem itemRenderer;
	
	public static InfuserRenderer instance = new InfuserRenderer();
	
	public InfuserRenderer()
	{
		itemRenderer = new RenderItem();
		itemRenderer.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z + 1);
		ResourceLocation textures = new ResourceLocation(Reference.MODID + ":textures/blocks/essenceInfuser.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		model.renderAll();
		GL11.glPopMatrix();
		
		if (te instanceof TileEntityEssenceInfuser)
		{
			TileEntityEssenceInfuser infuser = (TileEntityEssenceInfuser) te;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
			if (infuser.getStackInSlot(0) != null)
			{
				EntityItem hover = new EntityItem(infuser.getWorldObj());
				hover.hoverStart = 0.0F;
				hover.setEntityItemStack(infuser.getStackInSlot(0));
				itemRenderer.doRender(hover, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix();
		}
	}
}
package essenceMod.entities.boss;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import essenceMod.utility.Reference;

public class BossRender extends RenderLiving<EntityBoss>
{
	protected ResourceLocation texture = new ResourceLocation(Reference.MODID + ":textures/entity/boss/base.png");
	
	public BossRender()
	{
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0);
		texture = new ResourceLocation(Reference.MODID + ":textures/entity/boss/base.png");
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBoss boss)
	{
		return texture;
	}
}

package essenceMod.entities.boss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoss extends ModelBase // Not implemented yet, please ignore
{
	ModelRenderer base;

	public ModelBoss()
	{
		textureWidth = 64;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 16, 16, 16);
		base.setRotationPoint(-8F, -8F, -8F);
		base.setTextureSize(textureWidth, textureHeight);
		base.mirror = true;
		setRotation(base, (float) -Math.PI / 4, (float) -Math.PI / 4, (float) -Math.PI / 4);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
package essenceMod.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEssenceInfuser extends ModelBase
{
  //fields
    ModelRenderer Base_1;
    ModelRenderer Base_2;
    ModelRenderer Base_3;
    ModelRenderer Base_4;
    ModelRenderer Corner_Column_Front_Left;
    ModelRenderer Corner_Column_Front_Right;
    ModelRenderer Corner_Column_Back_Right;
    ModelRenderer Corner_Column_Back_Left;
    ModelRenderer Side_Slope_Left;
    ModelRenderer Side_Slope_Right;
    ModelRenderer Side_Slope_Back;
    ModelRenderer Side_Slope_Front;
    ModelRenderer Inner_Column_Front_Left;
    ModelRenderer Inner_Column_Front_Right;
    ModelRenderer Inner_Column_Back_Left;
    ModelRenderer Inner_Column_Back_Right;
  
  public ModelEssenceInfuser()
  {
    textureWidth = 64;
    textureHeight = 128;
    
      Base_1 = new ModelRenderer(this, 0, 0);
      Base_1.addBox(0F, 0F, 0F, 16, 2, 16);
      Base_1.setRotationPoint(-8F, 22F, -8F);
      Base_1.setTextureSize(64, 128);
      Base_1.mirror = true;
      setRotation(Base_1, 0F, 0F, 0F);
      Base_2 = new ModelRenderer(this, 0, 18);
      Base_2.addBox(0F, 0F, 0F, 14, 2, 14);
      Base_2.setRotationPoint(-7F, 20F, -7F);
      Base_2.setTextureSize(64, 128);
      Base_2.mirror = true;
      setRotation(Base_2, 0F, 0.0174533F, 0F);
      Base_3 = new ModelRenderer(this, 0, 36);
      Base_3.addBox(0F, 0F, 0F, 12, 2, 12);
      Base_3.setRotationPoint(-6F, 18F, -6F);
      Base_3.setTextureSize(64, 128);
      Base_3.mirror = true;
      setRotation(Base_3, 0F, 0F, 0F);
      Base_4 = new ModelRenderer(this, 0, 54);
      Base_4.addBox(0F, 0F, 0F, 10, 2, 10);
      Base_4.setRotationPoint(-5F, 16F, -5F);
      Base_4.setTextureSize(64, 128);
      Base_4.mirror = true;
      setRotation(Base_4, 0F, 0F, 0F);
      Corner_Column_Front_Left = new ModelRenderer(this, 56, 18);
      Corner_Column_Front_Left.addBox(0F, 0F, 0F, 2, 8, 2);
      Corner_Column_Front_Left.setRotationPoint(-8F, 14F, -8F);
      Corner_Column_Front_Left.setTextureSize(64, 128);
      Corner_Column_Front_Left.mirror = true;
      setRotation(Corner_Column_Front_Left, 0F, 0F, 0F);
      Corner_Column_Front_Right = new ModelRenderer(this, 56, 36);
      Corner_Column_Front_Right.addBox(0F, 0F, 0F, 2, 8, 2);
      Corner_Column_Front_Right.setRotationPoint(6.013333F, 14F, -8F);
      Corner_Column_Front_Right.setTextureSize(64, 128);
      Corner_Column_Front_Right.mirror = true;
      setRotation(Corner_Column_Front_Right, 0F, 0F, 0F);
      Corner_Column_Back_Right = new ModelRenderer(this, 56, 54);
      Corner_Column_Back_Right.addBox(0F, 0F, 0F, 2, 8, 2);
      Corner_Column_Back_Right.setRotationPoint(6F, 14F, 6F);
      Corner_Column_Back_Right.setTextureSize(64, 128);
      Corner_Column_Back_Right.mirror = true;
      setRotation(Corner_Column_Back_Right, 0F, 0F, 0F);
      Corner_Column_Back_Left = new ModelRenderer(this, 56, 72);
      Corner_Column_Back_Left.addBox(0F, 0F, 0F, 2, 8, 2);
      Corner_Column_Back_Left.setRotationPoint(-8F, 14F, 6F);
      Corner_Column_Back_Left.setTextureSize(64, 128);
      Corner_Column_Back_Left.mirror = true;
      setRotation(Corner_Column_Back_Left, 0F, 0F, 0F);
      Side_Slope_Left = new ModelRenderer(this, 24, 72);
      Side_Slope_Left.addBox(0F, 0F, 0F, 1, 15, 2);
      Side_Slope_Left.setRotationPoint(-2F, 8F, -1F);
      Side_Slope_Left.setTextureSize(64, 128);
      Side_Slope_Left.mirror = true;
      setRotation(Side_Slope_Left, 0F, 0F, 0.3926991F);
      Side_Slope_Right = new ModelRenderer(this, 30, 72);
      Side_Slope_Right.addBox(-1F, 0F, 0F, 1, 15, 2);
      Side_Slope_Right.setRotationPoint(2.2F, 8F, -1F);
      Side_Slope_Right.setTextureSize(64, 128);
      Side_Slope_Right.mirror = true;
      setRotation(Side_Slope_Right, 0F, 0F, -0.3926991F);
      Side_Slope_Back = new ModelRenderer(this, 36, 72);
      Side_Slope_Back.addBox(0F, 0F, -1F, 2, 15, 1);
      Side_Slope_Back.setRotationPoint(-1F, 8F, 2F);
      Side_Slope_Back.setTextureSize(64, 128);
      Side_Slope_Back.mirror = true;
      setRotation(Side_Slope_Back, 0.3926991F, 0F, 0F);
      Side_Slope_Front = new ModelRenderer(this, 42, 72);
      Side_Slope_Front.addBox(0F, 0F, 0F, 2, 15, 1);
      Side_Slope_Front.setRotationPoint(-1F, 8F, -2.2F);
      Side_Slope_Front.setTextureSize(64, 128);
      Side_Slope_Front.mirror = true;
      setRotation(Side_Slope_Front, -0.3926991F, 0F, 0F);
      Inner_Column_Front_Left = new ModelRenderer(this, 0, 72);
      Inner_Column_Front_Left.addBox(0F, 0F, 0F, 1, 16, 1);
      Inner_Column_Front_Left.setRotationPoint(4F, 8F, -5F);
      Inner_Column_Front_Left.setTextureSize(64, 128);
      Inner_Column_Front_Left.mirror = true;
      setRotation(Inner_Column_Front_Left, 0F, 0F, 0F);
      Inner_Column_Front_Right = new ModelRenderer(this, 4, 72);
      Inner_Column_Front_Right.addBox(0F, 0F, 0F, 1, 16, 1);
      Inner_Column_Front_Right.setRotationPoint(4F, 8F, 4F);
      Inner_Column_Front_Right.setTextureSize(64, 128);
      Inner_Column_Front_Right.mirror = true;
      setRotation(Inner_Column_Front_Right, 0F, 0F, 0F);
      Inner_Column_Back_Left = new ModelRenderer(this, 8, 72);
      Inner_Column_Back_Left.addBox(0F, 0F, 0F, 1, 16, 1);
      Inner_Column_Back_Left.setRotationPoint(-5F, 8F, -5F);
      Inner_Column_Back_Left.setTextureSize(64, 128);
      Inner_Column_Back_Left.mirror = true;
      setRotation(Inner_Column_Back_Left, 0F, 0F, 0F);
      Inner_Column_Back_Right = new ModelRenderer(this, 12, 72);
      Inner_Column_Back_Right.addBox(0F, 0F, 0F, 1, 16, 1);
      Inner_Column_Back_Right.setRotationPoint(-5F, 8F, 4F);
      Inner_Column_Back_Right.setTextureSize(64, 128);
      Inner_Column_Back_Right.mirror = true;
      setRotation(Inner_Column_Back_Right, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base_1.render(f5);
    Base_2.render(f5);
    Base_3.render(f5);
    Base_4.render(f5);
    Corner_Column_Front_Left.render(f5);
    Corner_Column_Front_Right.render(f5);
    Corner_Column_Back_Right.render(f5);
    Corner_Column_Back_Left.render(f5);
    Side_Slope_Left.render(f5);
    Side_Slope_Right.render(f5);
    Side_Slope_Back.render(f5);
    Side_Slope_Front.render(f5);
    Inner_Column_Front_Left.render(f5);
    Inner_Column_Front_Right.render(f5);
    Inner_Column_Back_Left.render(f5);
    Inner_Column_Back_Right.render(f5);
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

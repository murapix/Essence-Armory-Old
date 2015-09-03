package essenceMod.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEssencePylon extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Corner_Pillar_Front_Left;
    ModelRenderer Corner_Pillar_Back_Left;
    ModelRenderer Corner_Pillar_Back_Right;
    ModelRenderer Corner_Pillar_Front_Right;
    ModelRenderer Central_Column;
    ModelRenderer Central_Column_Cap;
    ModelRenderer Column_Curve_Front_Left;
    ModelRenderer Column_Curve_Back_Left;
    ModelRenderer Column_Curve_Back_Right;
    ModelRenderer Column_Curve_Front_Right;
    ModelRenderer Central_Column_Cap_2;
    ModelRenderer Central_Column_Cap_3;
    ModelRenderer Crossbar_1;
    ModelRenderer Crossbar_2;
  
  public ModelEssencePylon()
  {
    textureWidth = 64;
    textureHeight = 128;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 14, 4, 14);
      Base.setRotationPoint(-7F, 20F, -7F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Corner_Pillar_Front_Left = new ModelRenderer(this, 0, 20);
      Corner_Pillar_Front_Left.addBox(-1F, 0F, -1F, 2, 8, 2);
      Corner_Pillar_Front_Left.setRotationPoint(-6.4F, 16F, -6.4F);
      Corner_Pillar_Front_Left.setTextureSize(64, 32);
      Corner_Pillar_Front_Left.mirror = true;
      setRotation(Corner_Pillar_Front_Left, 0F, 0.7853982F, 0F);
      Corner_Pillar_Back_Left = new ModelRenderer(this, 10, 20);
      Corner_Pillar_Back_Left.addBox(-1F, 0F, -1F, 2, 8, 2);
      Corner_Pillar_Back_Left.setRotationPoint(-6.4F, 16F, 6.4F);
      Corner_Pillar_Back_Left.setTextureSize(64, 32);
      Corner_Pillar_Back_Left.mirror = true;
      setRotation(Corner_Pillar_Back_Left, 0F, 0.7853982F, 0F);
      Corner_Pillar_Back_Right = new ModelRenderer(this, 20, 20);
      Corner_Pillar_Back_Right.addBox(-1F, 0F, -1F, 2, 8, 2);
      Corner_Pillar_Back_Right.setRotationPoint(6.4F, 16F, 6.4F);
      Corner_Pillar_Back_Right.setTextureSize(64, 32);
      Corner_Pillar_Back_Right.mirror = true;
      setRotation(Corner_Pillar_Back_Right, 0F, 0.7853982F, 0F);
      Corner_Pillar_Front_Right = new ModelRenderer(this, 30, 20);
      Corner_Pillar_Front_Right.addBox(-1F, 0F, -1F, 2, 8, 2);
      Corner_Pillar_Front_Right.setRotationPoint(6.4F, 16F, -6.4F);
      Corner_Pillar_Front_Right.setTextureSize(64, 32);
      Corner_Pillar_Front_Right.mirror = true;
      setRotation(Corner_Pillar_Front_Right, 0F, 0.7853982F, 0F);
      Central_Column = new ModelRenderer(this, 40, 20);
      Central_Column.addBox(0F, 0F, 0F, 2, 8, 2);
      Central_Column.setRotationPoint(-1F, 12F, -1F);
      Central_Column.setTextureSize(64, 32);
      Central_Column.mirror = true;
      setRotation(Central_Column, 0F, 0F, 0F);
      Central_Column_Cap = new ModelRenderer(this, 0, 32);
      Central_Column_Cap.addBox(0F, 0F, 0F, 4, 2, 4);
      Central_Column_Cap.setRotationPoint(-2F, 11F, -2F);
      Central_Column_Cap.setTextureSize(64, 32);
      Central_Column_Cap.mirror = true;
      setRotation(Central_Column_Cap, 0F, 0F, 0F);
      Column_Curve_Front_Left = new ModelRenderer(this, 20, 32);
      Column_Curve_Front_Left.addBox(-1F, 0F, -1F, 2, 8, 2);
      Column_Curve_Front_Left.setRotationPoint(-3.5F, 9.7F, -3.5F);
      Column_Curve_Front_Left.setTextureSize(64, 32);
      Column_Curve_Front_Left.mirror = true;
      setRotation(Column_Curve_Front_Left, -0.5585054F, 0.7853982F, 0F);
      Column_Curve_Back_Left = new ModelRenderer(this, 30, 32);
      Column_Curve_Back_Left.addBox(-1F, 0F, -1F, 2, 8, 2);
      Column_Curve_Back_Left.setRotationPoint(-3.5F, 9.7F, 3.5F);
      Column_Curve_Back_Left.setTextureSize(64, 32);
      Column_Curve_Back_Left.mirror = true;
      setRotation(Column_Curve_Back_Left, 0.5585054F, 0.7853982F, 0.8471841F);
      Column_Curve_Back_Right = new ModelRenderer(this, 40, 32);
      Column_Curve_Back_Right.addBox(-1F, 0F, -1F, 2, 8, 2);
      Column_Curve_Back_Right.setRotationPoint(3.5F, 9.7F, 3.5F);
      Column_Curve_Back_Right.setTextureSize(64, 32);
      Column_Curve_Back_Right.mirror = true;
      setRotation(Column_Curve_Back_Right, 0.5585054F, 0.7853982F, 0F);
      Column_Curve_Front_Right = new ModelRenderer(this, 50, 32);
      Column_Curve_Front_Right.addBox(-1F, 0F, -1F, 2, 8, 2);
      Column_Curve_Front_Right.setRotationPoint(3.5F, 9.7F, -3.5F);
      Column_Curve_Front_Right.setTextureSize(64, 32);
      Column_Curve_Front_Right.mirror = true;
      setRotation(Column_Curve_Front_Right, -0.5585054F, 0.7853982F, -0.8471841F);
      Central_Column_Cap_2 = new ModelRenderer(this, 0, 45);
      Central_Column_Cap_2.addBox(-2.5F, 0F, -2.5F, 5, 2, 5);
      Central_Column_Cap_2.setRotationPoint(0F, 12F, 0F);
      Central_Column_Cap_2.setTextureSize(64, 32);
      Central_Column_Cap_2.mirror = true;
      setRotation(Central_Column_Cap_2, 0F, 0.7853982F, 0F);
      Central_Column_Cap_3 = new ModelRenderer(this, 25, 45);
      Central_Column_Cap_3.addBox(-3F, 0F, -3F, 6, 2, 6);
      Central_Column_Cap_3.setRotationPoint(0F, 13F, 0F);
      Central_Column_Cap_3.setTextureSize(64, 32);
      Central_Column_Cap_3.mirror = true;
      setRotation(Central_Column_Cap_3, 0F, 0F, 0F);
      Crossbar_1 = new ModelRenderer(this, 0, 55);
      Crossbar_1.addBox(-0.5F, 0F, 0F, 1, 4, 17);
      Crossbar_1.setRotationPoint(-6F, 16F, -6F);
      Crossbar_1.setTextureSize(64, 32);
      Crossbar_1.mirror = true;
      setRotation(Crossbar_1, 0F, 0.7853982F, 0F);
      Crossbar_2 = new ModelRenderer(this, 0, 80);
      Crossbar_2.addBox(-0.5F, 0F, 0F, 1, 4, 17);
      Crossbar_2.setRotationPoint(6F, 16F, -6F);
      Crossbar_2.setTextureSize(64, 32);
      Crossbar_2.mirror = true;
      setRotation(Crossbar_2, 0F, -0.7853982F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    Corner_Pillar_Front_Left.render(f5);
    Corner_Pillar_Back_Left.render(f5);
    Corner_Pillar_Back_Right.render(f5);
    Corner_Pillar_Front_Right.render(f5);
    Central_Column.render(f5);
    Central_Column_Cap.render(f5);
    Column_Curve_Front_Left.render(f5);
    Column_Curve_Back_Left.render(f5);
    Column_Curve_Back_Right.render(f5);
    Column_Curve_Front_Right.render(f5);
    Central_Column_Cap_2.render(f5);
    Central_Column_Cap_3.render(f5);
    Crossbar_1.render(f5);
    Crossbar_2.render(f5);
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

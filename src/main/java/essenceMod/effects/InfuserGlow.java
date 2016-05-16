package essenceMod.effects;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import essenceMod.utility.Reference;

public class InfuserGlow extends EntityFX
{
	Random rand = new Random();
	public int lifetime = 20;
	public ResourceLocation texture = new ResourceLocation(Reference.MODID + ":" + Reference.SPRITES[0]);

	public InfuserGlow(World world, double x, double y, double z, double red, double green, double blue)
	{
		super(world, x, y, z, 1, 1, 1);
		setRBGColorF((float) red / 255, (float) green / 255, (float) blue / 255);
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		particleMaxAge = 10;
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		sprite.load(Minecraft.getMinecraft().getResourceManager(), texture);
		setParticleIcon(sprite);
	}

	@Override
	public int getFXLayer()
	{
		return 1;
	}

	@Override
	public float getAlpha()
	{
		return 0.99F;
	}

	@Override
	public void onEntityUpdate()
	{
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		int random = rand.nextInt(4);
		System.out.println(random);
		if (random >= 2) particleAge++;
		if (particleAge > particleMaxAge)
		{
			getEntityWorld().removeEntity(this);
			kill();
		}
	}
}

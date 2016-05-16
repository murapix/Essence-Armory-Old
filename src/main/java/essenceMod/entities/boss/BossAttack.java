package essenceMod.entities.boss;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BossAttack
{
	// FINAL VARIABLES
	protected EntityBoss boss;
	protected DamageSource damageType;
	protected float damage;
	protected int attackTimer;
	protected int duration;
	
	// Non-Final Variables
	protected int cooldown;
	
	public BossAttack(EntityBoss boss, DamageSource damageType, float damage, int attackTimer, int duration)
	{
		this.boss = boss;
		this.damageType = damageType;
		this.damage = damage;
		this.attackTimer = attackTimer;
		this.duration = duration;
	}
	
	public abstract void update(List<EntityPlayer> players);
	
	@SideOnly(Side.CLIENT)
	public abstract void drawParticles();
	
	public abstract int getCrystalColor();
}

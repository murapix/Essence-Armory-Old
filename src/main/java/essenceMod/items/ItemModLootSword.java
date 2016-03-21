package essenceMod.items;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import essenceMod.handlers.ConfigHandler;
import essenceMod.registry.ModArmory;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;
import essenceMod.tabs.ModTabs;
import essenceMod.utility.Reference;
import essenceMod.utility.UtilityHelper;

public class ItemModLootSword extends ItemSword
{
	public final ToolMaterial toolMaterial;
	
	public ItemModLootSword(ToolMaterial material)
	{
		super(material);
		toolMaterial = material;
		setCreativeTab(ModTabs.tabEssence);
		setMaxDamage(0);
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack item)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", ToolMaterial.EMERALD.getDamageVsEntity() + 4.0F, 0));
		return multimap;
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer entityPlayer)
	{
		if (!item.hasTagCompound()) item.setTagCompound(new NBTTagCompound());
		if (EnchantmentHelper.getEnchantmentLevel(ConfigHandler.shardEnchantID, item) == 0) item.addEnchantment(ModArmory.shardLooter, 1);
		item.stackTagCompound.setInteger("Level", 0);
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b)
	{
		onCreated(item, world, (EntityPlayer) entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer entityPlayer, List list, boolean bool)
	{
		list.add("Increases shard drop chance to " + ((UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.ShardSwordLooting) + 1) * 100 / 6) + "%");
		list.add("Multiplies shard drop amount by " + (UtilityHelper.getUpgradeLevel(item, UpgradeRegistry.ShardSwordLooting) + 1)); 
	}
}

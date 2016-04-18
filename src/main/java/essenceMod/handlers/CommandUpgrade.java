package essenceMod.handlers;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import essenceMod.items.IUpgradeable;
import essenceMod.registry.crafting.InfuserRecipes;
import essenceMod.registry.crafting.upgrades.Upgrade;
import essenceMod.registry.crafting.upgrades.UpgradeRegistry;

public class CommandUpgrade implements ICommand
{
	private final List aliases;

	protected String fullUpgradeName;
	protected int upgradeLevel;

	public CommandUpgrade()
	{
		aliases = new ArrayList<String>();
		aliases.add("upgrade");
	}

	@Override
	public int compareTo(ICommand command)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "/upgrade";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/upgrade <UpgradeName> <UpgradeLevel>";
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] input)
	{
		if (input.length != 2)
		{
			sender.addChatMessage(new ChatComponentText("Invalid argument"));
			return;
		}
		fullUpgradeName = input[0];
		upgradeLevel = 0;
		try
		{
			upgradeLevel = Integer.parseInt(input[1]);
		}
		catch (Exception e)
		{
			sender.addChatMessage(new ChatComponentText("Invalid argument"));
		}
		if (!(sender instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) sender;
		Upgrade newUpgrade = new Upgrade(fullUpgradeName, upgradeLevel);
		ItemStack item = player.getCurrentEquippedItem();
		if (item == null || !(item.getItem() instanceof IUpgradeable)) return;
		InfuserRecipes.addUpgrade(player.getCurrentEquippedItem(), newUpgrade);
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return MinecraftServer.getServer().getConfigurationManager().canSendCommands(((EntityPlayer) sender).getGameProfile());
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] input, BlockPos pos)
	{
		ArrayList<String> options = new ArrayList<String>();
		options.add(UpgradeRegistry.AmuletFlight.name);
		options.add(UpgradeRegistry.AmuletLooting.name);
		options.add(UpgradeRegistry.ArmorAbsorption.name);
		options.add(UpgradeRegistry.ArmorBlastProtection.name);
		options.add(UpgradeRegistry.ArmorBlindThorns.name);
		options.add(UpgradeRegistry.ArmorChaosProtection.name);
		options.add(UpgradeRegistry.ArmorFireProtection.name);
		options.add(UpgradeRegistry.ArmorHealthBoost.name);
		options.add(UpgradeRegistry.ArmorInvisible.name);
		options.add(UpgradeRegistry.ArmorMagicProtection.name);
		options.add(UpgradeRegistry.ArmorMagicThorns.name);
		options.add(UpgradeRegistry.ArmorManaDiscount.name);
		options.add(UpgradeRegistry.ArmorPhysicalProtection.name);
		options.add(UpgradeRegistry.ArmorPhysicalThorns.name);
		options.add(UpgradeRegistry.ArmorProjectileProtection.name);
		options.add(UpgradeRegistry.ArmorResistance.name);
		options.add(UpgradeRegistry.ArmorRevealing.name);
		options.add(UpgradeRegistry.ArmorRunicShielding.name);
		options.add(UpgradeRegistry.ArmorVisDiscount.name);
		options.add(UpgradeRegistry.ArmorWitherProtection.name);
		options.add(UpgradeRegistry.BeltCleave.name);
		options.add(UpgradeRegistry.BeltKnockback.name);
		options.add(UpgradeRegistry.BaubleMiningLimiter.name);
		options.add(UpgradeRegistry.BaubleFireImmunity.name);
		options.add(UpgradeRegistry.BaublePoisonImmunity.name);
		options.add(UpgradeRegistry.BaubleTaintImmunity.name);
		options.add(UpgradeRegistry.BaubleWitherImmunity.name);
		options.add(UpgradeRegistry.BaubleHealthBoost.name);
		options.add(UpgradeRegistry.BaubleMiningLimiter.name);
		options.add(UpgradeRegistry.RingPotionFireResistance.name);
		options.add(UpgradeRegistry.RingPotionHaste.name);
		options.add(UpgradeRegistry.RingPotionJumpBoost.name);
		options.add(UpgradeRegistry.RingPotionNightVision.name);
		options.add(UpgradeRegistry.RingPotionRegeneration.name);
		options.add(UpgradeRegistry.RingPotionStrength.name);
		options.add(UpgradeRegistry.RingPotionSwiftness.name);
		options.add(UpgradeRegistry.RingPotionWaterBreathing.name);
		options.add(UpgradeRegistry.SwordLifesteal.name);
		options.add(UpgradeRegistry.BowArrowSpeed.name);
		options.add(UpgradeRegistry.BowDrawSpeed.name);
		options.add(UpgradeRegistry.WeaponArmorPiercing.name);
		options.add(UpgradeRegistry.WeaponBlind.name);
		options.add(UpgradeRegistry.WeaponChaosDamage.name);
		options.add(UpgradeRegistry.WeaponDivineDamage.name);
		options.add(UpgradeRegistry.WeaponEntangled.name);
		options.add(UpgradeRegistry.WeaponFireDamage.name);
		options.add(UpgradeRegistry.WeaponFireDoT.name);
		options.add(UpgradeRegistry.WeaponFrostDamage.name);
		options.add(UpgradeRegistry.WeaponFrostSlow.name);
		options.add(UpgradeRegistry.WeaponHolyDamage.name);
		options.add(UpgradeRegistry.WeaponKnockback.name);
		options.add(UpgradeRegistry.WeaponLightningDamage.name);
		options.add(UpgradeRegistry.WeaponMagicDamage.name);
		options.add(UpgradeRegistry.WeaponMagicDoT.name);
		options.add(UpgradeRegistry.WeaponPhysicalDamage.name);
		options.add(UpgradeRegistry.WeaponSlow.name);
		options.add(UpgradeRegistry.WeaponTaintDamage.name);
		options.add(UpgradeRegistry.WeaponTaintDoT.name);
		options.add(UpgradeRegistry.WeaponWindDamage.name);
		options.add(UpgradeRegistry.WeaponWitherDamage.name);
		options.add(UpgradeRegistry.WeaponWitherDoT.name);
		return options;
	}

	@Override
	public boolean isUsernameIndex(String[] input, int i)
	{
		return false;
	}
}

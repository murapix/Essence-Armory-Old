package essenceMod.registry.crafting;

import essenceMod.items.Upgrade;

public class UpgradeRegistry
{
	public static Upgrade WeaponFireDoT;
	public static Upgrade WeaponMagicDoT;
	public static Upgrade WeaponWitherDoT;
	public static Upgrade WeaponTaintDoT; // Thaumcraft Required
	public static Upgrade WeaponArmorPiercing;
	public static Upgrade WeaponKnockback;
	public static Upgrade WeaponBlind;
	public static Upgrade WeaponSlow;
	public static Upgrade WeaponEntangled; // Ars Magica 2 Required
	public static Upgrade WeaponFrostSlow; // Ars Magica 2 Required
	public static Upgrade WeaponPhysicalDamage;
	public static Upgrade WeaponFireDamage;
	public static Upgrade WeaponMagicDamage;
	public static Upgrade WeaponWitherDamage;
	public static Upgrade WeaponDivineDamage; // Extra Utilities Required
	public static Upgrade WeaponChaosDamage; // Draconic Evolution Required
	public static Upgrade WeaponTaintDamage; // Thaumcraft Required
	public static Upgrade WeaponFrostDamage; // Ars Magica 2 Required
	public static Upgrade WeaponHolyDamage; // Ars Magica 2 Required
	public static Upgrade WeaponLightningDamage; // Ars Magica 2 Required
	public static Upgrade WeaponWindDamage; // Ars Magica 2 Required
	
	public static Upgrade SwordLifesteal;
	
	public static Upgrade BowArrowSpeed;
	public static Upgrade BowDrawSpeed;

	public static Upgrade AmuletFlight;
	public static Upgrade AmuletLooting;
	
	public static Upgrade BaublePoisonImmunity;
	public static Upgrade BaubleWitherImmunity;
	public static Upgrade BaubleFireImmunity;
	public static Upgrade BaubleTaintImmunity; // Thaumcraft Required

	public static Upgrade RingPotionSwiftness;
	public static Upgrade RingPotionHaste;
	public static Upgrade RingPotionStrength;
	public static Upgrade RingPotionJumpBoost;
	public static Upgrade RingPotionRegeneration;
	public static Upgrade RingPotionNightVision;
	public static Upgrade RingPotionWaterBreathing;
	public static Upgrade RingPotionFireResistance;

	public static Upgrade BeltCleave;
	public static Upgrade BeltKnockback;
	
	public static Upgrade BaubleMiningLimiter;
	public static Upgrade BaubleHealthBoost;

	public static Upgrade ArmorPhysicalThorns;
	public static Upgrade ArmorMagicThorns;
	public static Upgrade ArmorBlindThorns;
	public static Upgrade ArmorAbsorption;
	public static Upgrade ArmorHealthBoost;
	public static Upgrade ArmorResistance;
	public static Upgrade ArmorPhysicalProtection;
	public static Upgrade ArmorFireProtection;
	public static Upgrade ArmorWitherProtection;
	public static Upgrade ArmorMagicProtection;
	public static Upgrade ArmorBlastProtection;
	public static Upgrade ArmorProjectileProtection;
	public static Upgrade ArmorChaosProtection; // Draconic Evolution Required
	public static Upgrade ArmorInvisible;
	public static Upgrade ArmorRevealing; // Thaumcraft Required
	public static Upgrade ArmorVisDiscount; // Thaumcraft Required
	public static Upgrade ArmorRunicShielding; // Thaumcraft Required
	public static Upgrade ArmorManaDiscount; // Botania Required

	public static void init()
	{
		// Weapon Upgrades
		WeaponFireDoT = new Upgrade("WeaponFireDoT").addIncompatibleUpgrade("WeaponMagicDoT", "WeaponWitherDoT", "WeaponTaintDoT");
		WeaponMagicDoT = new Upgrade("WeaponMagicDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponWitherDoT", "WeaponTaintDoT");
		WeaponWitherDoT = new Upgrade("WeaponWitherDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT", "WeaponTaintDoT");
		WeaponTaintDoT = new Upgrade("WeaponTaintDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT", "WeaponWitherDoT");
		WeaponArmorPiercing = new Upgrade("WeaponArmorPiercing").addIncompatibleUpgrade("WeaponKnockback", "WeaponBlind", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow", "SwordLifesteal", "BowArrowSpeed", "BowDrawSpeed");
		WeaponKnockback = new Upgrade("WeaponKnockback").addIncompatibleUpgrade("WeaponArmorPiercing", "SwordLifesteal");
		WeaponBlind = new Upgrade("WeaponBlind").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow");
		WeaponSlow = new Upgrade("WeaponSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow");
		WeaponEntangled = new Upgrade("WeaponEntangled").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow", "WeaponFrostSlow");
		WeaponFrostSlow = new Upgrade("WeaponFrostSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow", "WeaponEntangled");
		WeaponPhysicalDamage = new Upgrade("WeaponPhysicalDamage").addIncompatibleUpgrade("WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage");
		WeaponFireDamage = new Upgrade("WeaponFireDamage").addIncompatibleUpgrade("WeaponPhysicalDamage");
		WeaponMagicDamage = new Upgrade("WeaponMagicDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponWitherDamage = new Upgrade("WeaponWitherDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponDivineDamage = new Upgrade("WeaponDivineDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponChaosDamage = new Upgrade("WeaponChaosDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponTaintDamage = new Upgrade("WeaponTaintDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponFrostDamage = new Upgrade("WeaponFrostDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponHolyDamage = new Upgrade("WeaponHolyDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponLightningDamage", "WeaponWindDamage");
		WeaponLightningDamage = new Upgrade("WeaponLightningDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponWindDamage");
		WeaponWindDamage = new Upgrade("WeaponWindDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage");
		
		// Sword Upgrades
		SwordLifesteal = new Upgrade("SwordLifesteal").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponKnockback");
		
		// Bow Upgrades
		BowArrowSpeed = new Upgrade("BowArrowSpeed").addIncompatibleUpgrade("WeaponArmorPiercing", "BowDrawSpeed");
		BowDrawSpeed = new Upgrade("BowDrawSpeed").addIncompatibleUpgrade("WeaponArmorPiercing", "BowArrowSpeed");

		// Amulet Upgrades
		AmuletFlight = new Upgrade("AmuletFlight");
		AmuletLooting = new Upgrade("AmuletLooting");
		
		// Amulet + Pauldron Upgrades
		BaublePoisonImmunity = new Upgrade("BaublePoisonImmunity").addIncompatibleUpgrade("BaubleWitherImmunity", "BaubleFireImmunity", "BaubleTaintImmunity");
		BaubleWitherImmunity = new Upgrade("BaubleWitherImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleFireImmunity", "BaubleTaintImmunity");
		BaubleFireImmunity = new Upgrade("BaubleFireImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity", "BaubleTaintImmunity");
		BaubleTaintImmunity = new Upgrade("BaubleTaintImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity", "BaubleFireImmunity");

		// Ring Upgrades
		RingPotionSwiftness = new Upgrade("RingPotionSwiftness").addIncompatibleUpgrade("RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionHaste = new Upgrade("RingPotionHaste").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionStrength = new Upgrade("RingPotionStrength").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionJumpBoost = new Upgrade("RingPotionJumpBoost").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionRegeneration = new Upgrade("RingPotionRegeneration").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionNightVision = new Upgrade("RingPotionNightVision").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionWaterBreathing", "RingPotionFireResistance");
		RingPotionWaterBreathing = new Upgrade("RingPotionWaterBreathing").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionFireResistance");
		RingPotionFireResistance = new Upgrade("RingPotionFireResistance").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing");

		// Belt Upgrades
		BeltCleave = new Upgrade("BeltCleave").addIncompatibleUpgrade("BeltKnockback", "BaubleHealthBoost");
		BeltKnockback = new Upgrade("BeltKnockback").addIncompatibleUpgrade("BeltCleave", "BaubleHealthBoost");
		
		// Belt + Vambrace Upgrades
		BaubleMiningLimiter = new Upgrade("BaubleMiningLimiter");
		BaubleHealthBoost = new Upgrade("BaubleHealthBoost").addIncompatibleUpgrade("BeltCleave", "BeltKnockback");

		// Armor Upgrades
		ArmorPhysicalThorns = new Upgrade("ArmorPhysicalThorns").addIncompatibleUpgrade("ArmorMagicThorns", "ArmorBlindThorns");
		ArmorMagicThorns = new Upgrade("ArmorMagicThorns").addIncompatibleUpgrade("ArmorPhysicalThorns", "ArmorBlindThorns");
		ArmorBlindThorns = new Upgrade("ArmorBlindThorns").addIncompatibleUpgrade("ArmorPhysicalThorns", "ArmorMagicThorns");
		ArmorAbsorption = new Upgrade("ArmorAbsorption").addIncompatibleUpgrade("ArmorHealthBoost", "ArmorResistance", "ArmorRunicShielding");
		ArmorHealthBoost = new Upgrade("ArmorHealthBoost").addIncompatibleUpgrade("ArmorAbsorption", "ArmorResistance", "ArmorRunicShielding");
		ArmorResistance = new Upgrade("ArmorResistance").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost", "ArmorRunicShielding");
		ArmorPhysicalProtection = new Upgrade("ArmorPhysicalProtection");
		ArmorFireProtection = new Upgrade("ArmorFireProtection").addIncompatibleUpgrade("ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection");
		ArmorWitherProtection = new Upgrade("ArmorWitherProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection");
		ArmorMagicProtection = new Upgrade("ArmorMagicProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection");
		ArmorBlastProtection = new Upgrade("ArmorBlastProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorProjectileProtection", "ArmorChaosProtection");
		ArmorProjectileProtection = new Upgrade("ArmorProjectileProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorChaosProtection");
		ArmorChaosProtection = new Upgrade("ArmorChaosProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection");
		ArmorInvisible = new Upgrade("ArmorInvisible");
		ArmorRevealing = new Upgrade("ArmorRevealing");
		ArmorVisDiscount = new Upgrade("ArmorVisDiscount");
		ArmorRunicShielding = new Upgrade("ArmorRunicShielding").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost", "ArmorResistance");
		ArmorManaDiscount = new Upgrade("ArmorManaDiscount");
	}
}

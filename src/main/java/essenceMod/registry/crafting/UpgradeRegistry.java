package essenceMod.registry.crafting;

import java.util.HashMap;
import essenceMod.items.upgrades.Upgrade;

public class UpgradeRegistry
{
	public static HashMap<String, Upgrade> upgradeRegister = new HashMap<String, Upgrade>();
	
	public static Upgrade BaseUpgrade; // Fake Upgrade, use this to have a 'no upgrade' requirement
	
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
	
	public static Upgrade ShardSwordLooting;
	
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
		upgradeRegister.put("BaseUpgrade", BaseUpgrade = new Upgrade("BaseUpgrade"));
		
		// Weapon Upgrades
		upgradeRegister.put("WeaponFireDoT", WeaponFireDoT = new Upgrade("WeaponFireDoT").addIncompatibleUpgrade("WeaponMagicDoT", "WeaponWitherDoT", "WeaponTaintDoT"));
		upgradeRegister.put("WeaponMagicDoT", WeaponMagicDoT = new Upgrade("WeaponMagicDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponWitherDoT", "WeaponTaintDoT"));
		upgradeRegister.put("WeaponWitherDoT", WeaponWitherDoT = new Upgrade("WeaponWitherDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT", "WeaponTaintDoT"));
		upgradeRegister.put("WeaponTaintDoT", WeaponTaintDoT = new Upgrade("WeaponTaintDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT", "WeaponWitherDoT"));
		upgradeRegister.put("WeaponArmorPiercing", WeaponArmorPiercing = new Upgrade("WeaponArmorPiercing").addIncompatibleUpgrade("WeaponKnockback", "WeaponBlind", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow", "SwordLifesteal", "BowArrowSpeed", "BowDrawSpeed"));
		upgradeRegister.put("WeaponKnockback", WeaponKnockback = new Upgrade("WeaponKnockback").addIncompatibleUpgrade("WeaponArmorPiercing", "SwordLifesteal"));
		upgradeRegister.put("WeaponBlind", WeaponBlind = new Upgrade("WeaponBlind").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow"));
		upgradeRegister.put("WeaponSlow", WeaponSlow = new Upgrade("WeaponSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponSlow", "WeaponEntangled", "WeaponFrostSlow"));
		upgradeRegister.put("WeaponEntangled", WeaponEntangled = new Upgrade("WeaponEntangled").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow", "WeaponFrostSlow"));
		upgradeRegister.put("WeaponFrostSlow", WeaponFrostSlow = new Upgrade("WeaponFrostSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow", "WeaponEntangled"));
		upgradeRegister.put("WeaponPhysicalDamage", WeaponPhysicalDamage = new Upgrade("WeaponPhysicalDamage").addIncompatibleUpgrade("WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage"));
		upgradeRegister.put("WeaponFireDamage", WeaponFireDamage = new Upgrade("WeaponFireDamage").addIncompatibleUpgrade("WeaponPhysicalDamage"));
		upgradeRegister.put("WeaponMagicDamage", WeaponMagicDamage = new Upgrade("WeaponMagicDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponWitherDamage", WeaponWitherDamage = new Upgrade("WeaponWitherDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponDivineDamage", WeaponDivineDamage = new Upgrade("WeaponDivineDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponChaosDamage", WeaponChaosDamage = new Upgrade("WeaponChaosDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponTaintDamage", WeaponTaintDamage = new Upgrade("WeaponTaintDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponFrostDamage", WeaponFrostDamage = new Upgrade("WeaponFrostDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponHolyDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponHolyDamage", WeaponHolyDamage = new Upgrade("WeaponHolyDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponLightningDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponLightningDamage", WeaponLightningDamage = new Upgrade("WeaponLightningDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponWindDamage"));
		upgradeRegister.put("WeaponWindDamage", WeaponWindDamage = new Upgrade("WeaponWindDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage"));
		
		// Sword Upgrades
		upgradeRegister.put("SwordLifesteal", SwordLifesteal = new Upgrade("SwordLifesteal").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponKnockback"));
		
		upgradeRegister.put("ShardSwordLooting", ShardSwordLooting = new Upgrade("ShardSwordLooting"));
		
		// Bow Upgrades
		upgradeRegister.put("BowArrowSpeed", BowArrowSpeed = new Upgrade("BowArrowSpeed").addIncompatibleUpgrade("WeaponArmorPiercing", "BowDrawSpeed"));
		upgradeRegister.put("BowDrawSpeed", BowDrawSpeed = new Upgrade("BowDrawSpeed").addIncompatibleUpgrade("WeaponArmorPiercing", "BowArrowSpeed"));

		// Amulet Upgrades
		upgradeRegister.put("AmuletFlight", AmuletFlight = new Upgrade("AmuletFlight"));
		upgradeRegister.put("AmuletLooting", AmuletLooting = new Upgrade("AmuletLooting"));
		
		// Amulet + Pauldron Upgrades
		upgradeRegister.put("BaublePoisonImmunity", BaublePoisonImmunity = new Upgrade("BaublePoisonImmunity").addIncompatibleUpgrade("BaubleWitherImmunity", "BaubleFireImmunity", "BaubleTaintImmunity"));
		upgradeRegister.put("BaubleWitherImmunity", BaubleWitherImmunity = new Upgrade("BaubleWitherImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleFireImmunity", "BaubleTaintImmunity"));
		upgradeRegister.put("BaubleFireImmunity", BaubleFireImmunity = new Upgrade("BaubleFireImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity", "BaubleTaintImmunity"));
		upgradeRegister.put("BaubleTaintImmunity", BaubleTaintImmunity = new Upgrade("BaubleTaintImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity", "BaubleFireImmunity"));

		// Ring Upgrades
		upgradeRegister.put("RingPotionSwiftness", RingPotionSwiftness = new Upgrade("RingPotionSwiftness").addIncompatibleUpgrade("RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionHaste", RingPotionHaste = new Upgrade("RingPotionHaste").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionStrength", RingPotionStrength = new Upgrade("RingPotionStrength").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionJumpBoost", RingPotionJumpBoost = new Upgrade("RingPotionJumpBoost").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionRegeneration", RingPotionRegeneration = new Upgrade("RingPotionRegeneration").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionNightVision", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionNightVision", RingPotionNightVision = new Upgrade("RingPotionNightVision").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionWaterBreathing", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionWaterBreathing", RingPotionWaterBreathing = new Upgrade("RingPotionWaterBreathing").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionFireResistance"));
		upgradeRegister.put("RingPotionFireResistance", RingPotionFireResistance = new Upgrade("RingPotionFireResistance").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing"));

		// Belt Upgrades
		upgradeRegister.put("BeltCleave", BeltCleave = new Upgrade("BeltCleave").addIncompatibleUpgrade("BeltKnockback", "BaubleHealthBoost"));
		upgradeRegister.put("BeltKnockback", BeltKnockback = new Upgrade("BeltKnockback").addIncompatibleUpgrade("BeltCleave", "BaubleHealthBoost"));
		
		// Belt + Vambrace Upgrades
		upgradeRegister.put("BaubleMiningLimiter", BaubleMiningLimiter = new Upgrade("BaubleMiningLimiter"));
		upgradeRegister.put("BaubleHealthBoost", BaubleHealthBoost = new Upgrade("BaubleHealthBoost").addIncompatibleUpgrade("BeltCleave", "BeltKnockback"));

		// Armor Upgrades
		upgradeRegister.put("ArmorPhysicalThorns", ArmorPhysicalThorns = new Upgrade("ArmorPhysicalThorns").addIncompatibleUpgrade("ArmorMagicThorns", "ArmorBlindThorns"));
		upgradeRegister.put("ArmorMagicThorns", ArmorMagicThorns = new Upgrade("ArmorMagicThorns").addIncompatibleUpgrade("ArmorPhysicalThorns", "ArmorBlindThorns"));
		upgradeRegister.put("ArmorBlindThorns", ArmorBlindThorns = new Upgrade("ArmorBlindThorns").addIncompatibleUpgrade("ArmorPhysicalThorns", "ArmorMagicThorns"));
		upgradeRegister.put("ArmorAbsorption", ArmorAbsorption = new Upgrade("ArmorAbsorption").addIncompatibleUpgrade("ArmorHealthBoost", "ArmorResistance", "ArmorRunicShielding"));
		upgradeRegister.put("ArmorHealthBoost", ArmorHealthBoost = new Upgrade("ArmorHealthBoost").addIncompatibleUpgrade("ArmorAbsorption", "ArmorResistance", "ArmorRunicShielding"));
		upgradeRegister.put("ArmorResistance", ArmorResistance = new Upgrade("ArmorResistance").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost", "ArmorRunicShielding"));
		upgradeRegister.put("ArmorPhysicalProtection", ArmorPhysicalProtection = new Upgrade("ArmorPhysicalProtection"));
		upgradeRegister.put("ArmorFireProtection", ArmorFireProtection = new Upgrade("ArmorFireProtection").addIncompatibleUpgrade("ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection"));
		upgradeRegister.put("ArmorWitherProtection", ArmorWitherProtection = new Upgrade("ArmorWitherProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection"));
		upgradeRegister.put("ArmorMagicProtection", ArmorMagicProtection = new Upgrade("ArmorMagicProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorBlastProtection", "ArmorProjectileProtection", "ArmorChaosProtection"));
		upgradeRegister.put("ArmorBlastProtection", ArmorBlastProtection = new Upgrade("ArmorBlastProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorProjectileProtection", "ArmorChaosProtection"));
		upgradeRegister.put("ArmorProjectileProtection", ArmorProjectileProtection = new Upgrade("ArmorProjectileProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorChaosProtection"));
		upgradeRegister.put("ArmorChaosProtection", ArmorChaosProtection = new Upgrade("ArmorChaosProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection"));
		upgradeRegister.put("ArmorInvisible", ArmorInvisible = new Upgrade("ArmorInvisible"));
		upgradeRegister.put("ArmorRevealing", ArmorRevealing = new Upgrade("ArmorRevealing"));
		upgradeRegister.put("ArmorVisDiscount", ArmorVisDiscount = new Upgrade("ArmorVisDiscount"));
		upgradeRegister.put("ArmorRunicShielding", ArmorRunicShielding = new Upgrade("ArmorRunicShielding").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost", "ArmorResistance"));
		upgradeRegister.put("ArmorManaDiscount", ArmorManaDiscount = new Upgrade("ArmorManaDiscount"));
	}
}

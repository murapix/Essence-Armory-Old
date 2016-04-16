package essenceMod.registry.crafting.upgrades;

import java.util.HashMap;

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
	public static Upgrade ArmorRevealing; // Thaumcraft Required, Helmet Only
	public static Upgrade ArmorVisDiscount; // Thaumcraft Required
	public static Upgrade ArmorRunicShielding; // Thaumcraft Required
	public static Upgrade ArmorManaDiscount; // Botania Required
	public static Upgrade ArmorStepAssist; // Legs Only
	public static Upgrade ArmorRunSpeed; // Boots Only
	
	/*
	 * All upgrade sets. Effects at 4/6/8 given by:
	 * 4: Effect
	 * 6: Effect/None
	 * 8: Effect/None
	 */
	
	/*
	 * Knight
	 * 4: Damage reduced by 1/2/3, immediately after armor
	 * 6: Maximum of 5/3 hearts recieved per attack
	 * 8: Ignore 1 hit (10 sec)
	 */
	public static UpgradeSet SetKnight;
	/*
	 * Barbarian
	 * 4: 5/10/15% bonus damage per attack, up to 200%. Decays after 10 sec
	 * 6: 10/15% bonus movespeed per attack, up to 50/75%. Decays after 2 sec
	 * 8: No damage taken for 3 sec (1 min, activate upon recieving fatal blow)
	 */
	public static UpgradeSet SetBarbarian;
	/*
	 * Assassin
	 * 4: 20/30/40% Chance to deal 2x damage
	 * 6: Chance increased to 60/100% on first attack (10 sec, reset on kill)
	 * 8: None
	 */
	public static UpgradeSet SetAssassin;
	/*
	 * Juggernaut
	 * 4: Knockback Resistance +0.5 then *2/4
	 * 6: Return 1 hit (10 sec)
	 * 8: None
	 */
	public static UpgradeSet SetJuggernaut;
	/*
	 * Looter
	 * 4: 20/40/60% Chance to double vanilla mob drops
	 * 6: 50/100% Increased xp drops
	 * 8:
	 */
	public static UpgradeSet SetLooter;
	/*
	 * Witch
	 * 4: 50/75/100% Bonus damage vs slow/blind/impaired enemies
	 * 6: 25/50% Increased non-physical damage
	 * 8: None
	 */
	public static UpgradeSet SetWitch;
	/*
	 * Warrior
	 * 4:
	 * 6:
	 * 8:
	 */
	public static UpgradeSet SetWarrior;
	/*
	 * Pyromaniac
	 * 4: 50/100/150% Increased damage to burning enemies
	 * 6: None
	 * 8: None
	 */
	public static UpgradeSet SetPyromaniac;
	/*
	 * Priest
	 * 4: 50/100/150% Bonus damage vs undead
	 * 6: None
	 * 8: None
	 */
	public static UpgradeSet SetPriest;

	public static void init()
	{
		upgradeRegister.put("BaseUpgrade", BaseUpgrade = new Upgrade("BaseUpgrade"));
		
		// Weapon Upgrades
		upgradeRegister.put("WeaponFireDoT", WeaponFireDoT = new Upgrade("WeaponFireDoT"));
		upgradeRegister.put("WeaponMagicDoT", WeaponMagicDoT = new Upgrade("WeaponMagicDoT").addIncompatibleUpgrade("WeaponFireDoT"));
		upgradeRegister.put("WeaponWitherDoT", WeaponWitherDoT = new Upgrade("WeaponWitherDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT"));
		upgradeRegister.put("WeaponTaintDoT", WeaponTaintDoT = new Upgrade("WeaponTaintDoT").addIncompatibleUpgrade("WeaponFireDoT", "WeaponMagicDoT", "WeaponWitherDoT"));
		upgradeRegister.put("WeaponArmorPiercing", WeaponArmorPiercing = new Upgrade("WeaponArmorPiercing"));
		upgradeRegister.put("WeaponKnockback", WeaponKnockback = new Upgrade("WeaponKnockback").addIncompatibleUpgrade("WeaponArmorPiercing"));
		upgradeRegister.put("WeaponBlind", WeaponBlind = new Upgrade("WeaponBlind").addIncompatibleUpgrade("WeaponArmorPiercing"));
		upgradeRegister.put("WeaponSlow", WeaponSlow = new Upgrade("WeaponSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind"));
		upgradeRegister.put("WeaponEntangled", WeaponEntangled = new Upgrade("WeaponEntangled").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow"));
		upgradeRegister.put("WeaponFrostSlow", WeaponFrostSlow = new Upgrade("WeaponFrostSlow").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponBlind", "WeaponSlow", "WeaponEntangled"));
		upgradeRegister.put("WeaponPhysicalDamage", WeaponPhysicalDamage = new Upgrade("WeaponPhysicalDamage"));
		upgradeRegister.put("WeaponFireDamage", WeaponFireDamage = new Upgrade("WeaponFireDamage").addIncompatibleUpgrade("WeaponPhysicalDamage"));
		upgradeRegister.put("WeaponMagicDamage", WeaponMagicDamage = new Upgrade("WeaponMagicDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage"));
		upgradeRegister.put("WeaponWitherDamage", WeaponWitherDamage = new Upgrade("WeaponWitherDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage"));
		upgradeRegister.put("WeaponDivineDamage", WeaponDivineDamage = new Upgrade("WeaponDivineDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage"));
		upgradeRegister.put("WeaponChaosDamage", WeaponChaosDamage = new Upgrade("WeaponChaosDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage"));
		upgradeRegister.put("WeaponTaintDamage", WeaponTaintDamage = new Upgrade("WeaponTaintDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage"));
		upgradeRegister.put("WeaponFrostDamage", WeaponFrostDamage = new Upgrade("WeaponFrostDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage"));
		upgradeRegister.put("WeaponHolyDamage", WeaponHolyDamage = new Upgrade("WeaponHolyDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage"));
		upgradeRegister.put("WeaponLightningDamage", WeaponLightningDamage = new Upgrade("WeaponLightningDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage"));
		upgradeRegister.put("WeaponWindDamage", WeaponWindDamage = new Upgrade("WeaponWindDamage").addIncompatibleUpgrade("WeaponPhysicalDamage", "WeaponFireDamage", "WeaponMagicDamage", "WeaponWitherDamage", "WeaponDivineDamage", "WeaponChaosDamage", "WeaponTaintDamage", "WeaponFrostDamage", "WeaponHolyDamage", "WeaponLightningDamage"));
		
		// Sword Upgrades
		upgradeRegister.put("SwordLifesteal", SwordLifesteal = new Upgrade("SwordLifesteal").addIncompatibleUpgrade("WeaponArmorPiercing", "WeaponKnockback"));
		
		upgradeRegister.put("ShardSwordLooting", ShardSwordLooting = new Upgrade("ShardSwordLooting"));
		
		// Bow Upgrades
		upgradeRegister.put("BowArrowSpeed", BowArrowSpeed = new Upgrade("BowArrowSpeed").addIncompatibleUpgrade("WeaponArmorPiercing"));
		upgradeRegister.put("BowDrawSpeed", BowDrawSpeed = new Upgrade("BowDrawSpeed").addIncompatibleUpgrade("WeaponArmorPiercing", "BowArrowSpeed"));

		// Amulet Upgrades
		upgradeRegister.put("AmuletFlight", AmuletFlight = new Upgrade("AmuletFlight"));
		upgradeRegister.put("AmuletLooting", AmuletLooting = new Upgrade("AmuletLooting"));
		
		// Amulet + Pauldron Upgrades
		upgradeRegister.put("BaublePoisonImmunity", BaublePoisonImmunity = new Upgrade("BaublePoisonImmunity").addIncompatibleUpgrade());
		upgradeRegister.put("BaubleWitherImmunity", BaubleWitherImmunity = new Upgrade("BaubleWitherImmunity").addIncompatibleUpgrade("BaublePoisonImmunity"));
		upgradeRegister.put("BaubleFireImmunity", BaubleFireImmunity = new Upgrade("BaubleFireImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity"));
		upgradeRegister.put("BaubleTaintImmunity", BaubleTaintImmunity = new Upgrade("BaubleTaintImmunity").addIncompatibleUpgrade("BaublePoisonImmunity", "BaubleWitherImmunity", "BaubleFireImmunity"));

		// Ring Upgrades
		upgradeRegister.put("RingPotionSwiftness", RingPotionSwiftness = new Upgrade("RingPotionSwiftness"));
		upgradeRegister.put("RingPotionHaste", RingPotionHaste = new Upgrade("RingPotionHaste").addIncompatibleUpgrade("RingPotionSwiftness"));
		upgradeRegister.put("RingPotionStrength", RingPotionStrength = new Upgrade("RingPotionStrength").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste"));
		upgradeRegister.put("RingPotionJumpBoost", RingPotionJumpBoost = new Upgrade("RingPotionJumpBoost").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength"));
		upgradeRegister.put("RingPotionRegeneration", RingPotionRegeneration = new Upgrade("RingPotionRegeneration").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost"));
		upgradeRegister.put("RingPotionNightVision", RingPotionNightVision = new Upgrade("RingPotionNightVision").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration"));
		upgradeRegister.put("RingPotionWaterBreathing", RingPotionWaterBreathing = new Upgrade("RingPotionWaterBreathing").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision"));
		upgradeRegister.put("RingPotionFireResistance", RingPotionFireResistance = new Upgrade("RingPotionFireResistance").addIncompatibleUpgrade("RingPotionSwiftness", "RingPotionHaste", "RingPotionStrength", "RingPotionJumpBoost", "RingPotionRegeneration", "RingPotionNightVision", "RingPotionWaterBreathing"));

		// Belt Upgrades
		upgradeRegister.put("BeltCleave", BeltCleave = new Upgrade("BeltCleave"));
		upgradeRegister.put("BeltKnockback", BeltKnockback = new Upgrade("BeltKnockback").addIncompatibleUpgrade("BeltCleave"));
		
		// Belt + Vambrace Upgrades
		upgradeRegister.put("BaubleMiningLimiter", BaubleMiningLimiter = new Upgrade("BaubleMiningLimiter"));
		upgradeRegister.put("BaubleHealthBoost", BaubleHealthBoost = new Upgrade("BaubleHealthBoost").addIncompatibleUpgrade("BeltCleave", "BeltKnockback"));

		// Armor Upgrades
		upgradeRegister.put("ArmorPhysicalThorns", ArmorPhysicalThorns = new Upgrade("ArmorPhysicalThorns"));
		upgradeRegister.put("ArmorMagicThorns", ArmorMagicThorns = new Upgrade("ArmorMagicThorns").addIncompatibleUpgrade("ArmorPhysicalThorns"));
		upgradeRegister.put("ArmorBlindThorns", ArmorBlindThorns = new Upgrade("ArmorBlindThorns").addIncompatibleUpgrade("ArmorPhysicalThorns", "ArmorMagicThorns"));
		upgradeRegister.put("ArmorAbsorption", ArmorAbsorption = new Upgrade("ArmorAbsorption"));
		upgradeRegister.put("ArmorHealthBoost", ArmorHealthBoost = new Upgrade("ArmorHealthBoost").addIncompatibleUpgrade("ArmorAbsorption"));
		upgradeRegister.put("ArmorResistance", ArmorResistance = new Upgrade("ArmorResistance").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost"));
		upgradeRegister.put("ArmorPhysicalProtection", ArmorPhysicalProtection = new Upgrade("ArmorPhysicalProtection"));
		upgradeRegister.put("ArmorFireProtection", ArmorFireProtection = new Upgrade("ArmorFireProtection"));
		upgradeRegister.put("ArmorWitherProtection", ArmorWitherProtection = new Upgrade("ArmorWitherProtection").addIncompatibleUpgrade("ArmorFireProtection"));
		upgradeRegister.put("ArmorMagicProtection", ArmorMagicProtection = new Upgrade("ArmorMagicProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection"));
		upgradeRegister.put("ArmorBlastProtection", ArmorBlastProtection = new Upgrade("ArmorBlastProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection"));
		upgradeRegister.put("ArmorProjectileProtection", ArmorProjectileProtection = new Upgrade("ArmorProjectileProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection"));
		upgradeRegister.put("ArmorChaosProtection", ArmorChaosProtection = new Upgrade("ArmorChaosProtection").addIncompatibleUpgrade("ArmorFireProtection", "ArmorWitherProtection", "ArmorMagicProtection", "ArmorBlastProtection", "ArmorProjectileProtection"));
		upgradeRegister.put("ArmorInvisible", ArmorInvisible = new Upgrade("ArmorInvisible"));
		upgradeRegister.put("ArmorRevealing", ArmorRevealing = new Upgrade("ArmorRevealing"));
		upgradeRegister.put("ArmorVisDiscount", ArmorVisDiscount = new Upgrade("ArmorVisDiscount"));
		upgradeRegister.put("ArmorRunicShielding", ArmorRunicShielding = new Upgrade("ArmorRunicShielding").addIncompatibleUpgrade("ArmorAbsorption", "ArmorHealthBoost", "ArmorResistance"));
		upgradeRegister.put("ArmorManaDiscount", ArmorManaDiscount = new Upgrade("ArmorManaDiscount"));
		upgradeRegister.put("ArmorStepAssist", ArmorStepAssist = new Upgrade("ArmorStepAssist"));
		upgradeRegister.put("ArmorRunSpeed", ArmorRunSpeed = new Upgrade("ArmorRunSpeed"));
		
		upgradeRegister.put("SetKnight", SetKnight = new UpgradeSet("SetKnight"));
		upgradeRegister.put("SetJuggernaut", SetJuggernaut = new UpgradeSet("SetJuggernaut"));
		upgradeRegister.put("SetBarbarian", SetBarbarian = new UpgradeSet("SetBarbarian"));
		upgradeRegister.put("SetAssassin", SetAssassin = new UpgradeSet("SetAssassin"));
		upgradeRegister.put("SetWarrior", SetWarrior = new UpgradeSet("SetWarrior"));
		upgradeRegister.put("SetPyromaniac", SetPyromaniac = new UpgradeSet("SetPyromaniac"));
		upgradeRegister.put("SetLooter", SetLooter = new UpgradeSet("SetLooter"));
		upgradeRegister.put("SetPriest", SetPriest = new UpgradeSet("SetPriest"));
		upgradeRegister.put("SetWitch", SetWitch = new UpgradeSet("SetWitch"));
	}
}

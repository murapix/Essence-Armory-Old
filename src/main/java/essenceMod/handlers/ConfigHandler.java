package essenceMod.handlers;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	//Config Categories
	public static final String CATEGORY_BASIC = "Essence Shard Basic Settings";
	public static final String CATEGORY_DROPS = "Essence Shard Drop Settings";
	public static final String CATEGORY_WEAPON = "Essence Infused Weapon Upgrade Settings";
	public static final String CATEGORY_ARMOR = "Essence Infused Armor Upgrade Setting";
	public static final String CATEGORY_COMPAT = "Mod Compatibility Settings";
	public static final String CATEGORY_BAUBLES = "Essence Infused Trinkets Settings";
	public static final String CATEGORY_TRAVELGEAR = "Essence Infused Traveller's Gear Settings";
	public static final String CATEGORY_TINKERS = "Tinkers' Construct Compatibility Settings";
	
	//CATEGORY_BASIC variables
	public static int shardEnchantID = 70;
	
	//CATEGORY_DROPS variables
	public static boolean useWhiteList;
	public static String[] whiteList;
	public static boolean useBlackList;
	public static String[] blackList;
	public static float dragonShardChance;
	public static int dragonShardCount;
	public static float witherShardChance;
	public static int witherShardCount;
	
	//CATEGORY_SWORD variables
	public static boolean isNormalDamagePercent;
	public static float normalDamageMulti;
	public static int normalDamageAmount;
	public static float normalBowMulti;
	public static boolean isFireDamagePercent;
	public static float fireDamageMulti;
	public static int fireDamageAmount;
	public static float fireBowMulti;
	public static boolean isMagicDamagePercent;
	public static float magicDamageMulti;
	public static int magicDamageAmount;
	public static float magicBowMulti;
	public static boolean isWitherDamagePercent;
	public static float witherDamageMulti;
	public static int witherDamageAmount;
	public static float witherBowMulti;
	
	//CATEGORY_COMPAT variables
	// Extra Utilities
	public static boolean extrautilitiesIntegration;
	public static boolean isDivineDamagePercent;
	public static float divineDamageMulti;
	public static int divineDamageAmount;
	public static float divineBowMulti;
	// Draconic Evolution
	public static boolean draconicevolutionIntegration;
	public static boolean isChaosDamagePercent;
	public static float chaosDamageMulti;
	public static int chaosDamageAmount;
	public static float chaosBowMulti;
	// Thaumcraft
	public static boolean thaumcraftIntegration = true;
	public static boolean isTaintDamagePercent = true;
	public static float taintDamageMulti = 0.05F;
	public static int taintDamageAmount = 1;
	public static float taintBowMulti = 1;
	// Ars Magica 2
	public static boolean arsMagicaIntegration = true;
	public static boolean isFrostDamagePercent = true;
	public static float frostDamageMulti = 0.05F;
	public static int frostDamageAmount = 1;
	public static float frostBowMulti = 1;
	public static boolean isHolyDamagePercent = true;
	public static float holyDamageMulti = 0.05F;
	public static int holyDamageAmount = 1;
	public static float holyBowMulti = 1;
	public static boolean isLightningDamagePercent = true;
	public static float lightningDamageMulti = 0.05F;
	public static int lightningDamageAmount = 1;
	public static float lightningBowMulti = 1;
	public static boolean isWindDamagePercent = true;
	public static float windDamageMulti = 0.05F;
	public static int windDamageAmount = 1;
	public static float windBowMulti = 1;
	// Botania
	public static boolean botaniaIntegration = true;
	
	//CATEGORY_ARMOR variables
	public static float thornsDamage;
	public static int poisonThornsDuration;
	public static int blindThornsDuration;
	public static int blindThornsChance;
	public static int healthBoostCount;
	public static int absorptionCount;
	public static int absorptionDelay;
	public static int absorptionRecharge = 20;
	public static float maxProtectionValue;
	
	//CATEGORY_BAUBLES variables
	
	
	//CATEGORY_TINKERS variables
	public static boolean ticoIntegration;
	public static int ticoMaterialId;
	public static int ticoHarvestLevel;
	public static int ticoDurability;
	public static int ticoMiningSpeed;
	public static int ticoBaseDamage;
	public static float ticoHandleMod;
	public static int ticoReinforced;
	public static int ticoDrawSpeed;
	public static float ticoProjSpeed;
	public static float ticoProjMass;
	public static float ticoProjFrag;
	public static int ticoBaseModCount;
	public static int ticoPartModCount;
	
	//CATEGORY_TRAVELGEAR variables
	public static boolean travellersgearIntegration;
	
	public static void initProps(File location)
	{
		Configuration config = new Configuration(location);
		config.load();
		
		useWhiteList = config.getBoolean("useWhiteList", CATEGORY_DROPS, false, "If set to true, only the listed mobs will drop Essence Shards. Default: false");
		whiteList = config.getStringList("whiteList", CATEGORY_DROPS, new String[]{""}, "The array of mobs that can drop Essence Shards, separated with a ,");
		useBlackList = config.getBoolean("useBlackList", CATEGORY_DROPS, false, "If set to true, the following listed mobs will NOT drop Essence Shards. Default: false");
		blackList = config.getStringList("blackList", CATEGORY_DROPS, new String[]{""}, "The array of mobs that will NOT drop Essence Shards, separated with a ,");
		dragonShardChance = config.getFloat("dragonShardChance", CATEGORY_DROPS, 0, 0, 1, "The chance that the Ender Dragon will drop Essence Shards. Default: 0");
		dragonShardCount = config.getInt("dragonShardCount", CATEGORY_DROPS, 64, 0, Integer.MAX_VALUE, "The base number of Essence Shards the Ender Dragon can drop when slain. Default: 64");
		witherShardChance = config.getFloat("witherShardChance", CATEGORY_DROPS, 0, 0, 1, "The chance that the Wither will drop Essence Shards. Default: 0");
		witherShardCount = config.getInt("witherShardCount", CATEGORY_DROPS, 16, 0, Integer.MAX_VALUE, "The base number of Essence Shards the Wither can drop when slain. Default: 16");
		
		isNormalDamagePercent = config.getBoolean("isNormalDamagePercent", CATEGORY_WEAPON, true, "If set to true, the normal damage upgrade adds a percentage of the total damage as plain damage. If set to false, it adds a flat amount. Default: true");
		normalDamageMulti = config.getFloat("normalDamageMulti", CATEGORY_WEAPON, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as normal damage per level of the normal damage upgrade. Default: 0.05");
		normalDamageAmount = config.getInt("normalDamageAmount", CATEGORY_WEAPON, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the normal damage upgrade. Default: 1");
		normalBowMulti = config.getFloat("normalBowMulti", CATEGORY_WEAPON, 1, 0, Float.MAX_VALUE, "The effectiveness of physical damage upgrades on the bow. Default: 1");
		isFireDamagePercent = config.getBoolean("isFireDamagePercent", CATEGORY_WEAPON, true, "If set to true, the fire damage upgrade adds a percentage of the total damage as fire damage. If set to false, it adds a flat amount. Default: true");
		fireDamageMulti = config.getFloat("fireDamageMulti", CATEGORY_WEAPON, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as fire damage per level of the fire damage upgrade. Default: 0.05");
		fireDamageAmount = config.getInt("fireDamageAmount", CATEGORY_WEAPON, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the fire damage upgrade. Default: 1");
		fireBowMulti = config.getFloat("fireBowMulti", CATEGORY_WEAPON, 1, 0, Float.MAX_VALUE, "The effectiveness of fire damage upgrades on the bow. Default: 1");
		isMagicDamagePercent = config.getBoolean("isMagicDamagePercent", CATEGORY_WEAPON, true, "If set to true, the magic damage upgrade adds a percentage of the total damage as magic damage. If set to false, it adds a flat amount. Default: true");
		magicDamageMulti = config.getFloat("magicDamageMulti", CATEGORY_WEAPON, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as magic damage per level of the magic damage upgrade. Default: 0.05");
		magicDamageAmount = config.getInt("magicDamageAmount", CATEGORY_WEAPON, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the magic damage upgrade. Default: 1");
		magicBowMulti = config.getFloat("magicBowMulti", CATEGORY_WEAPON, 1, 0, Float.MAX_VALUE, "The effectiveness of magic damage upgrades on the bow. Default: 1");
		isWitherDamagePercent = config.getBoolean("isWitherDamagePercent", CATEGORY_WEAPON, true, "If set to true, the wither damage upgrade adds a percentage of the total damage as wither damage. If set to false, it adds a flat amount. Default: true");
		witherDamageMulti = config.getFloat("witherDamageMulti", CATEGORY_WEAPON, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as wither damage per level of the wither damage upgrade. Default: 0.05");
		witherDamageAmount = config.getInt("witherDamageAmount", CATEGORY_WEAPON, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the wither damage upgrade. Default: 1");
		witherBowMulti = config.getFloat("witherBowMulti", CATEGORY_WEAPON, 1, 0, Float.MAX_VALUE, "The effectiveness of wither damage upgrades on the bow. Default: 1");
		
		extrautilitiesIntegration = config.getBoolean("extrautilitiesIntegration", CATEGORY_COMPAT, true, "If set to true, Extra Utilities compatibility will be used. Default: true");
		isDivineDamagePercent = config.getBoolean("isDivineDamagePercent", CATEGORY_COMPAT, true, "If set to true, the divine damage upgrade adds a percentage of the total damage as divine damage. If set to false, it adds a flat amount. Default: true");
		divineDamageMulti = config.getFloat("divineDamageMulti", CATEGORY_COMPAT, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as divine damage per level of the divine damage upgrade. Default: 0.05");
		divineDamageAmount = config.getInt("divineDamageAmount", CATEGORY_COMPAT, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the divine damage up" + "grade. Default: 1");
		divineBowMulti = config.getFloat("divineBowMulti", CATEGORY_COMPAT, 1, 0, Float.MAX_VALUE, "The effectiveness of divine damage upgrades on the bow. Default: 1");
		draconicevolutionIntegration = config.getBoolean("draconicevolutionIntegration", CATEGORY_COMPAT, true, "If set to true, Draconic Evolution compatibility will be used. Default: true");
		isChaosDamagePercent = config.getBoolean("isChaosDamagePercent", CATEGORY_COMPAT, true, "If set to true, the chaos damage upgrade adds a percentage of the total damage as chaos damage. If set to false, it adds a flat amount. Default: true");
		chaosDamageMulti = config.getFloat("chaosDamageMulti", CATEGORY_COMPAT, 0.05F, 0, Float.MAX_VALUE, "The percentage of the total damage added as chaos damage per level of the chaos damage upgrade. Default: 0.05");
		chaosDamageAmount = config.getInt("chaosDamageAmount", CATEGORY_COMPAT, 1, 0, Integer.MAX_VALUE, "The amount of damage gained per level of the chaos damage upgrade. Default: 1");
		chaosBowMulti = config.getFloat("chaosBowMulti", CATEGORY_COMPAT, 1, 0, Float.MAX_VALUE, "The effectivness of chaos damage upgrades on the bow. Default: 1");
		
		thornsDamage = config.getFloat("thornsDamage", CATEGORY_ARMOR, 0.25F, 0, Float.MAX_VALUE, "The amount of damage the thorns armor upgrade deals per level. Default: 0.25");
		poisonThornsDuration = config.getInt("poisonThornsDuration", CATEGORY_ARMOR, 20, 0, Integer.MAX_VALUE, "The duration in ticks for which the poisonous armor upgrade adds per level. Remember, 20 ticks is 1 second. Default: 10");
		blindThornsDuration = config.getInt("blindThornsDuration", CATEGORY_ARMOR, 10, 0, Integer.MAX_VALUE, "The duration in ticks for which the blinding armor upgrade adds per level. Rememeber, 20 ticks is 1 second. Default: 10");
		blindThornsChance = config.getInt("blindThornsChance", CATEGORY_ARMOR, 25, 0, 100, "The chance each piece of armor with the blinding armor upgrade adds to blind the enemy. Default: 25 (x4 is a 100% chance)");
		healthBoostCount = config.getInt("healthBoostCount", CATEGORY_ARMOR, 1, 1, Integer.MAX_VALUE, "The amount of health the health boost upgrade adds per level. Remember, each heart is two health. Default: 2");
		absorptionCount = config.getInt("absorptionCount", CATEGORY_ARMOR, 1, 1, Integer.MAX_VALUE, "The amount of temporary health the absorption upgrade adds per level. Remember, each heart is two health. Default: 2");
		absorptionDelay = config.getInt("absorptionDelay", CATEGORY_ARMOR, 200, 0, Integer.MAX_VALUE, "The number of ticks without taking any damage required for absorption hearts to return. Remember, 20 ticks is 1 second. Default: 200");
		maxProtectionValue = config.getFloat("maxProtectionValue", CATEGORY_ARMOR, 0.8F, 0, 1, "The percent of damage reduction gained from a full armor set with specialized protection. The protection upgrade gives 40% of this value, and the specialized protection upgrades give 60%.");
		
		ticoIntegration = config.getBoolean("ticoIntegration", CATEGORY_TINKERS, true, "If set to true, the Tinkers' Construct parts will be generated if possible. Default: true");
		ticoMaterialId = config.getInt("ticoMaterialId", CATEGORY_TINKERS, 70, 1, 256, "The material id for Infused Starmetal. Must be unique from all other Tinkers' Construct materials. Default: 70");
		ticoHarvestLevel = config.getInt("ticoHarvestLevel", CATEGORY_TINKERS, 4, 1, Integer.MAX_VALUE, "The harvest level given by Tinkers' Construct parts. Default: 4");
		ticoDurability = config.getInt("ticoDurability", CATEGORY_TINKERS, 10000, 1, Integer.MAX_VALUE, "The durability of Tinkers' Construct parts. Default: 10000");
		ticoMiningSpeed = config.getInt("ticoMiningSpeed", CATEGORY_TINKERS, 900, 1, Integer.MAX_VALUE, "The mining speed of Tinkers' Construct parts. Remember, a value of 100 is only a mining speed of 1. Default: 900");
		ticoBaseDamage = config.getInt("ticoBaseDamage", CATEGORY_TINKERS, 8, 1, Integer.MAX_VALUE, "The damage dealt by Tinkers' Construct parts. Default: 8");
		ticoHandleMod = config.getFloat("ticoHandleMod", CATEGORY_TINKERS, 1.5F, 0F, Float.MAX_VALUE, "The handle modifier given by Tinkers' Construct parts. Default: 1.5");
		ticoReinforced = config.getInt("ticoReinforced", CATEGORY_TINKERS, 10, 0, 10, "The number of reinforced modifiers added by each Tinkers' Construct part. Default: 10");
		ticoDrawSpeed = config.getInt("ticoDrawSpeed", CATEGORY_TINKERS, 15, 0, Integer.MAX_VALUE, "The draw speed of Tinkers' Construct parts. Note: Not a 1 to 1 ratio, possibly a 1 to 0.15 ratio. Default: 15");
		ticoProjSpeed = config.getFloat("ticoProjSpeed", CATEGORY_TINKERS, 9.6F, 0F, Float.MAX_VALUE, "The arrow speed of Tinkers' Construct parts. Default: 9.6");
		ticoProjMass = config.getFloat("ticoProjMass", CATEGORY_TINKERS, 5.6F, 0F, Float.MAX_VALUE, "The weight of Tinkers' Construct parts. Default: 5.6");
		ticoProjFrag = config.getFloat("ticoProjFrag", CATEGORY_TINKERS, 0.9F, 0F, 1F, "The chance that Tinkers' Construct ammo won't break on impact. Default: 0.9");
		ticoBaseModCount = config.getInt("ticoBaseModCount", CATEGORY_TINKERS, 1, 0, Integer.MAX_VALUE, "The number of extra modifiers given by using Infused Starmetal parts. Default: 1");
		ticoPartModCount = config.getInt("ticoPartModCount", CATEGORY_TINKERS, 1, 0, Integer.MAX_VALUE, "The number of extra modifiers given for each Infused Starmetal part. Default: 1");
		
		travellersgearIntegration = config.getBoolean("travellersgearIntegration", CATEGORY_TRAVELGEAR, true, "If set to true, the Traveller's Gear versions of baubles will be used if possible. Default: true");
		
		config.save();
	}
}
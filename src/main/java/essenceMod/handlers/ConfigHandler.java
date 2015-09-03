package essenceMod.handlers;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static boolean miaticActive;
	public static boolean iaticActive;
	public static boolean diaticActive;

	public static boolean miaticDrops;
	public static boolean iaticDrops;
	public static boolean diaticDrops;

	public static double miaticDropProbability;
	public static int miaticMin;
	public static int miaticMax;
	public static double iaticDropProbability;
	public static int iaticMin;
	public static int iaticMax;
	public static double diaticDropProbability;
	public static int diaticMin;
	public static int diaticMax;

	public static boolean miaticRecipeActive;
	public static boolean iaticRecipeActive;
	public static boolean diaticRecipeActive;

	public static int miaticRecipeResult;
	public static int iaticRecipeResult;
	public static int diaticRecipeResult;

	public static void initProps(File location)
	{
		File mainFile = new File(location + "/Mianite.cfg");

		Configuration config = new Configuration(mainFile);
		config.load();

		miaticActive = config.get("sectionUse", "miaticActive", true).getBoolean(true);
		iaticActive = config.get("sectionUse", "iaticActive", true).getBoolean(true);
		diaticActive = config.get("sectionUse", "diaticActive", true).getBoolean(true);

		miaticDrops = config.get("entityDrops", "miaticDrops", true).getBoolean(true);
		miaticDropProbability = config.get("miaticDrops", "miaticDropProbability", 0.1).getDouble(0.1);
		miaticMin = config.get("entityDrops", "miaticMin", 1).getInt(1);
		miaticMax = config.get("entityDrops", "miaticMax", 1).getInt(1);

		iaticDrops = config.get("entityDrops", "iaticDrops", true).getBoolean(true);
		iaticDropProbability = config.get("iaticDrops", "iaticDropProbability", 0.1).getDouble(0.1);
		iaticMin = config.get("entityDrops", "iaticMin", 1).getInt(1);
		iaticMax = config.get("entityDrops", "iaticMax", 1).getInt(1);

		diaticDrops = config.get("entityDrops", "diaticDrops", true).getBoolean(true);
		diaticDropProbability = config.get("diaticDrops", "diaticDropProbability", 0.1).getDouble(0.1);
		diaticMin = config.get("entityDrops", "diaticMin", 1).getInt(1);
		diaticMax = config.get("entityDrops", "diaticMax", 1).getInt(1);

		miaticRecipeActive = config.get("recipies", "miaticRecipeActive", true).getBoolean(true);
		miaticRecipeResult = config.get("recipes", "miaticRecipeResult", 1).getInt(1);

		iaticRecipeActive = config.get("recipes", "iaticRecipeActive", true).getBoolean(true);
		iaticRecipeResult = config.get("recipes", "iaticRecipeResult", 1).getInt(1);

		diaticRecipeActive = config.get("recipes", "diaticRecipeActive", true).getBoolean(true);
		diaticRecipeResult = config.get("recipes", "diaticRecipeResult", 1).getInt(1);
	}
}
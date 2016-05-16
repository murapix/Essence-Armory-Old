package essenceMod.utility;

public class UtilityHelper
{
	private static int[] numbers = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };;
	private static String[] letters = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };;

	public static String toRoman(int arabic)
	{
		String roman = "";
		for (int i = 0; i < numbers.length; i++)
		{
			while (arabic >= numbers[i])
			{
				roman += letters[i];
				arabic -= numbers[i];
			}
		}
		return roman;
	}

	public static float round(float number, int places)
	{
		if (places == 0) return (float) ((int) number);

		int mult = (int) Math.pow(10, places);
		return ((int) (number * mult)) / (float) mult;
	}
	
	public static int rgbToHex(int red, int green, int blue)
	{
		return Integer.parseInt(Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue), 16);
	}
}

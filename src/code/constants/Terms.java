package code.constants;

public class Terms {

	/*
	 * Array constants which represent the 5 base stats, 3 kinds of points, and 3 additional
	 * stats.
	 */
	public static final String[] stats = {"attack", "intelligence", "speed", "defense", "mdefense"};
	public static final String[] points = {"HP", "MP", "AP"};
	public static final String[] finesse = {"accuracy", "evasion", "critChance"};
	
	/*
	 * Constants which store what the XML value for the different ability types is.
	 */
	public static final String statBasedAbility = "Stat-Based";
	public static final String multiplierBasedAbility = "Multiplier";
	public static final String fixedDamageAbility = "Fixed";
	
	/*
	 * Constants which store what the XML value for the different status effect types is.
	 */
	public static final String damageStatusEffect = "Damage";
	public static final String statStatusEffect = "Stat";
	public static final String vampireStatusEffect = "Vampire";
	
	/*
	 * A function to get the index of a given stat in the stats array.
	 */
	public static int getStat(String stat) {
		for (int i = 0; i < stats.length; i++) {
			if (stats[i].compareToIgnoreCase(stat) == 0) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * A function to get the index of a given point in the points array.
	 */
	public static int getPoint(String point) {
		for (int i = 0; i < points.length; i++) {
			if (points[i].compareToIgnoreCase(point) == 0) {
				return i;
			}
		}
		return -1;
	}
	
}

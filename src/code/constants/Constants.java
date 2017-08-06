package code.constants;

public class Constants {

	/*
	 * The threshold a character's current speed must pass for them to get a turn.
	 * Speed varies from 10 - 100 in extreme cases, so 1000 ensures that several
	 * rounds of speed increasing will pass before a character's turn.
	 */
	public static final int speedThreshold = 1000;
	
	/*
	 * The number of turns ahead the turn counter shows. With a value of 12, it shows
	 * each character's next roughly 2-3 turns.
	 */
	public static final int turnsAhead = 12;
	
	/*
	 * The height in pixels of the messageboxes that show player messages such as the
	 * player portraits, the act menu, and the skill menu.
	 */
	public static final int playerMessageHeight = 212;
	
	/*
	 * The width in pixels of the menu for selecting an action and canceling out of a menu.
	 */
	public static final int menuWidth = 192;
	
	/*
	 * The height in pixels of the status box displaying names and status ailments
	 */
	public static final int statusBoxHeight = 160;
	
	/*
	 * The height in pixels of player and enemy bars such as the health bar and 
	 * mana bar.
	 */
	public static final int barHeight = 24;
	
	/*
	 * The icon representing player characters in the turn counter.
	 */
	public static final String playerIcon = "player.png";
	
	/*
	 * The icon representing enemy characters in the turn counter.
	 */
	public static final String enemyIcon = "enemy.png";
	
	/*
	 * The icon representing the selected character in the turn counter.
	 */
	public static final String selectedIcon = "selected.png";
	
	/*
	 * Array constants which represent the 5 base stats, 3 kinds of points, and 3 additional
	 * stats.
	 */
	public static final String[] stats = {"attack", "intelligence", "speed", "defense", "mdefense"};
	public static final String[] points = {"HP", "MP", "AP"};
	public static final String[] finesse = {"accuracy", "evasion", "critChance"};
	
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

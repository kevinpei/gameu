package code.game_mechanics;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * A class to contain lists of abilities, status effects, items, etc. after reading them
 * from XML. This is only initialized when the game boots up and is kept static thereafter.
 */
public class Library {

	/*
	 * A hashmap of the abilities in the game. The key for each ability corresponds to its
	 * id, and the value to the actual ability.
	 */
	public static HashMap<Integer, Ability> abilities;
	
	/*
	 * A hashmap of the status effects in the game. The key for each status effect corresponds 
	 * to its id, and the value to the actual status effect.
	 */
	public static HashMap<Integer, StatusEffect> statusEffects;
	
	/*
	 * A hashmap of the items in the game. The key for each item corresponds to its id, and 
	 * the value to the actual item.
	 */
	public static ArrayList<Item> items;
	
	/*
	 * A function to initialize the hashmaps of abilities, status effects, and items. It is
	 * only ever run once, at the beginning of the game.
	 */
	public static void initialize() {
		
	}
}

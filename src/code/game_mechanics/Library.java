package code.game_mechanics;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import code.game_mechanics.abilities.Ability;
import code.game_mechanics.status_effects.StatusEffect;
import code.xml.XMLReader;

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
	 * A function to get the ability with the given id from the library.
	 */
	public static Ability getAbility(int id) {
		return abilities.get(id);
	}
	
	/*
	 * A hashmap of the status effects in the game. The key for each status effect corresponds 
	 * to its id, and the value to the actual status effect.
	 */
	public static HashMap<Integer, StatusEffect> statusEffects;
	
	/*
	 * A function to get the status effect with the given id from the library.
	 */
	public static StatusEffect getStatusEffect(int id) {
		return statusEffects.get(id);
	}
	
	/*
	 * Gets the id of the given ability or status effect given its name. It takes the name
	 * and whether you're looking for an ability as arguments.
	 */
	public static Integer getIDFromName(String name, boolean isAbility) {
		if (isAbility) {
			for (Integer key : abilities.keySet()) {
				if (abilities.get(key).name.compareToIgnoreCase(name) == 0) {
					return key;
				}
			}
		}
		else {
			for (Integer key : statusEffects.keySet()) {
				if (statusEffects.get(key).name.compareToIgnoreCase(name) == 0) {
					return key;
				}
			}
		}
		return -1;
	}
	
	/*
	 * A hashmap of the items in the game. The key for each item corresponds to its id, and 
	 * the value to the actual item.
	 */
	public static HashMap<Integer, Item> items;
	
	/*
	 * A function to initialize the hashmaps of abilities, status effects, and items. It is
	 * only ever run once, at the beginning of the game.
	 */
	public static void initialize() {
		Library.abilities = new HashMap<Integer, Ability>();
		Library.statusEffects = new HashMap<Integer, StatusEffect>();
		Library.items = new HashMap<Integer, Item>();
		Library.initAbilities();
		Library.initEffects();
		Library.initItems();
	}
	
	/*
	 * A method to initialize all the abilities using the abilities XML file.
	 */
	public static void initAbilities() {
		Document abilities = XMLReader.createDocument("abilities");
		try {
			for (int id = 0; id < 99; id++) {
				System.out.println(id);
				Library.abilities.put(id, XMLReader.initAbility(XMLReader.getElementByID(abilities, "ability", id)));
			}
		} catch (NullPointerException e) {
			System.out.println("Whoops");
		}
		try {
			for (int id = 100; id < 199; id++) {
				Library.abilities.put(id, XMLReader.initAbility(XMLReader.getElementByID(abilities, "ability", id)));
			}
		} catch (NullPointerException e) {
			
		}
		try {
			for (int id = 200; id < 999; id++) {
				Library.abilities.put(id, XMLReader.initAbility(XMLReader.getElementByID(abilities, "ability", id)));
			}
		} catch (NullPointerException e) {
			
		}
	}
	
	/*
	 * A method to initialize all the status effects using the status_effects XML file.
	 */
	public static void initEffects() {
		Document effects = XMLReader.createDocument("status_effects");
		try {
			for (int id = 0; id < 999; id++) {
				Library.statusEffects.put(id, XMLReader.initStatusEffect(XMLReader.getElementByID(effects, "statusEffect", id)));
			}
		} catch (NullPointerException e) {
			
		}
	}
	
	public static void initItems() {
		
	}
}

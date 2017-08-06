package code.game_mechanics.characters;

import java.util.ArrayList;
import java.util.HashMap;

import code.constants.Constants;
import code.game_mechanics.Ability;
import code.game_mechanics.StatusEffect;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

public abstract class GameCharacter {
	/*
	 * Stores the name and description of the character, for displaying
	 * information about the given character.
	 */
	public String name;
	public String description;
	/*
	 * The 5 base stats are as follows:
	 * Attack - Determines how much damage physical attacks do
	 * Intelligence - Determines how much damage magical attacks do
	 * Speed - Determines how often the character acts
	 * Defense - Determines how much physical damage is reduced
	 * Magical Defense - Determines how much magical damage is reduced
	 * 
	 * The HashMap storing these stats has the stat name as the key and 
	 * int arrays as the values. The first index corresponds to the base
	 * value and the second index corresponds to the current value.
	 */
	public HashMap<String, int[]> stats;
	/*
	 * The 3 types of points are as follows:
	 * HP: When it reaches zero, the character dies
	 * MP: Used for some abilities
	 * AP: Used to execute actions, with larger actions requiring more AP
	 * 
	 * The HashMap storing these points has the points name as the key and 
	 * int arrays as the values. The first index corresponds to the base max
	 * value, the second index corresponds to the current max value, and the
	 * third index corresponds to the current value.
	 */
	public HashMap<String, int[]> points;
	/*
	 * There are 3 finesse stats:
	 * Accuracy: Used to determine the chance of landing an attack.
	 * Evasion: Used to determine the chance of dodging an attack.
	 * Crit Rate: When an attack hits, there is a chance of it being critical.
	 * 
	 * The HashMap storing these finesse stats has the finesse stat name as 
	 * the key and int arrays as the values. The first index corresponds to 
	 * the base value and the second index corresponds to the current value.
	 */
	public HashMap<String, int[]> finesse;
	/*
	 * currSpeed is used to track how far the current character is
	 * in the turn order. When it reaches 1000 the character gets
	 * a turn and more AP to use.
	 */
	public int currSpeed;
	/*
	 * A stat to multiply all incoming damage by. By default, it's
	 * 1, but status effects like Breach and Shield can affect it.
	 * Certain equipment also affects it.
	 */
	public double damageMultiplier;
	/*
	 * A hashmap of integers with integer keys representing the different status ailments
	 * with their ids as the keys and the values representing their magnitude.
	 * Each status ailment is a continuum ranging from a negative number to a positive 
	 * number, representing either a positive status for positive numbers or a negative 
	 * status for negative numbers. It is a hashmap so that more status effects can be 
	 * added as necessary later. The continuum status effects are as follows:
	 * 
	 * Poison -- Regen		Poison causes HP loss every turn. Regen causes HP gain every turn.
	 * 
	 * Vulnerable -- Null	Vulnerable causes a large amount of damage when the afflicted is attacked.
	 * 						Null prevents damage from the next attack.
	 * 
	 * Curse -- Bless		Curse causes damage whenever the afflicted deals damage. Bless heals
	 * 						the afflicted whenever they deal damage.
	 * 
	 * Fear -- Courage		Fear decreases Attack and Intelligence, Courage increases Attack and
	 * 						Intelligence.
	 * 
	 * Slow -- Haste		Slow decreases Speed and Evasion, Haste increases Speed and Evasion.
	 * 
	 * Addle -- Clarity		Addle causes MP loss every turn. Clarity causes MP gain every turn.
	 * 
	 * Breach -- Shield		Breach decreases Defense and Magic Defense. Shield increases Defense 
	 * 						and Magic Defense. 
	 * 
	 * Unlucky -- Lucky		Unlucky decreases all stats and finesse stats by a small amount, lucky
	 * 						increases all stats and finesse stats by a small amount.
	 * 
	 * Blind -- Eagle Eye	Blind decreases Accuracy and CritChance, Eagle Eye drastically
	 * 						increases Accuracy and CritChance.
	 * 
	 * (Element) Brand -- (Element) Wall	Brand decreases resistance to a given element, Wall increases
	 * 										resistance to a given element.
	 */
	public HashMap<Integer, Integer> statusEffects;
	
	/*
	 * A hashmap containing the character's resistances to various
	 * status effects. If there is no resistance associated with the
	 * given status effect, chance is assumed to be 100%. The key is
	 * the id of the status effect, and the value is the resistance
	 * to that status effect.
	 * 
	 * Status resistances are ints - they modify the chance for a
	 * status ailment to land in the following way:
	 * 0: Normal chance
	 * 50: Half chance
	 * 100+: No chance
	 * -100: Double chance
	 * 
	 * Resistance is purely positive - it only affects the chance for
	 * negative status effects to land, not positive status effects.
	 */
	public HashMap<Integer, Integer> statusResistances;
	
	/*
	 * A hashmap containing various multipliers to apply to the
	 * damage dealt to the character. The possible multipliers 
	 * are as follows:
	 *
	 * Equipment multipliers - various equipment-based multipliers
	 */
	public HashMap<String, Double> multipliers;
	/*
	 * A hashmap containing the character's resistances to various
	 * elements. The 6 elements are as follows:
	 * 
	 * Physical - Attacks involving physical strength and materials use this element.
	 * Mystic - Attacks involving raw magic use this element.
	 * Fire - Attacks involving heat use this element.
	 * Water - Attacks involving water and ice use this element.
	 * Dark - Attacks involving darkness and death use this element.
	 * Light - Attacks involving light and life use this element.
	 * 
	 * Elemental resistances are doubles - final damage is modified
	 * by elemental resistance in the following way:
	 * 
	 * 0: Normal damage
	 * 50: Half damage
	 * 100: No damage
	 * 200: Absorb damage
	 * -100: Double damage
	 */
	public HashMap<String, Double> elementalResistances;
	
	public GameCharacter(String name) {
		this.name = name;
		this.stats = new HashMap<String, int[]>();
		for (String stat: Constants.stats) {
			this.stats.put(stat, new int[2]);
		}
		this.points = new HashMap<String, int[]>();
		for (String point: Constants.points) {
			this.points.put(point, new int[3]);
		}
		this.finesse = new HashMap<String, int[]>();
		for (String finesse: Constants.finesse) {
			this.finesse.put(finesse, new int[2]);
		}
	}
	
	/*
	 * All characters have a graphical group which is their graphical representation.
	 * This variable stores it for modification of the graphics later.
	 */
	public Group graphics;
	
	/*
	 * An arraylist holding all the turns this character will act on in the future.
	 */
	public ArrayList<Integer> futureTurns = new ArrayList<Integer>();
	
	/*
	 * All characters have an image to depict them. The file name is stored in 
	 * the img String (e.g. hexer.png)
	 */
	public String img;
	
	/*
	 * Every character has getter and setter functions for their img.
	 */
	public String getIMG() { return img; }
	public void setIMG(String img) { this.img = img; }
}

package code.game_mechanics.characters;

import java.util.ArrayList;
import java.util.HashMap;

import code.constants.Constants;
import code.constants.Terms;
import code.game_mechanics.Multiplier;
import code.game_mechanics.abilities.Ability;
import code.game_mechanics.status_effects.StatusEffect;
import code.graphics.EnemyGraphics;
import code.graphics.PlayerGraphics;
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
	 * These Hashmaps all store multipliers to the base stats, points, and finesse.
	 * All stat multipliers are additive and can come from status effects, equipment,
	 * etc.
	 */
	public HashMap<String, Multiplier> statMultipliers;
	public HashMap<String, Multiplier> pointMultipliers;
	public HashMap<String, Multiplier> finesseMultipliers;
	/*
	 * currSpeed is used to track how far the current character is
	 * in the turn order. When it reaches 1000 the character gets
	 * a turn and more AP to use.
	 */
	public int currSpeed;

	/*
	 * A hashmap of integers with integer keys representing the different status effects
	 * with their ids as the keys and the values representing their magnitude.
	 * Magnitude serves as a way to track the duration of a status ailment, but some status
	 * effects also increase in severity as the magnitude increases.
	 * 
	 * Negative Status Ailments
	 * 
	 * Poison 		Poison causes life loss every turn. Higher magnitudes mean more life is lost. Is
	 * 				opposed to Regen.
	 * 
	 * Bomb			Causes a large amount of damage to the afflicted when the afflicted is attacked.
	 * 				Higher magnitudes increase the amount of damage taken.
	 * 
	 * Curse		Causes damage whenever the afflicted deals damage. Higher magnitudes mean more life
	 * 				is lost whenever the afflicted deals damage.
	 * 
	 * (Stat) Down	Causes a drop in that stat. Attack Down is for Attack and Intelligence,
	 * 				Speed Down is for Speed, Defense Down is for Defense and Magic Defense, and Luck Down
	 * 				is for Accuracy, Evasion, and CritChance. Higher magnitudes mean more of a decrease.
	 * 
	 * (Element) Brand	Decreases resistance to the given element. Higher magnitudes mean more of a
	 * 					decrease.
	 * 
	 * Sleep		Causes the afflicted to lose their turns. Also sets their evasion to 0 and ensures
	 * 				the next hit against them will be a critical.
	 * 
	 * Madness		Causes the afflicted to attack randomly. Magnitude decreases by 1 whenever they take
	 * 				damage.
	 * 
	 * Fear			Causes the afflicted to randomly lose turns. Also 
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
		this.statMultipliers = new HashMap<String, Multiplier>();
		for (String stat: Terms.stats) {
			this.stats.put(stat, new int[2]);
			this.statMultipliers.put(stat, new Multiplier());
		}
		this.points = new HashMap<String, int[]>();
		this.pointMultipliers = new HashMap<String, Multiplier>();
		for (String point: Terms.points) {
			this.points.put(point, new int[3]);
			this.pointMultipliers.put(point, new Multiplier());
		}
		this.finesse = new HashMap<String, int[]>();
		this.finesseMultipliers = new HashMap<String, Multiplier>();
		for (String finesse: Terms.finesse) {
			this.finesse.put(finesse, new int[2]);
			this.finesseMultipliers.put(finesse, new Multiplier());
		}
	}
	
	/*
	 * All characters have a graphical group which is their graphical representation.
	 * This variable stores it for modification of the graphics later.
	 */
	public Group graphics;
	
	/*
	 * All characters have canvases which contain images of themselves. These images can
	 * be accessed using the portrait variable.
	 */
	public Canvas portrait;
	
	/*
	 * Point Bars are the bars such as health and mana bars used by the character.
	 */
	public Canvas[] pointBars;
	
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

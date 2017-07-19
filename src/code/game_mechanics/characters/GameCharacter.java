package code.game_mechanics.characters;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * The 6 base stats are as follows:
	 * Attack - Determines how much damage physical attacks do
	 * Intelligence - Determines how much damage magical attacks do
	 * Speed - Determines how often the character acts
	 * Finesse - Determines accuracy, evasion, and crit rate
	 * Defense - Determines how much physical damage is reduced
	 * Magical Defense - Determines how much magical damage is reduced
	 */
	public int[] baseStats;
	public int[] currStats;
	/*
	 * The 3 types of points are as follows:
	 * HP: When it reaches zero, the character dies
	 * MP: Used for some abilities
	 * AP: Used to execute actions, with larger actions requiring more AP
	 */
	public int[] basePoints;
	public int[] currPoints;
	/*
	 * currSpeed is used to track how far the current character is
	 * in the turn order. When it reaches 1000 the character gets
	 * a turn and more AP to use.
	 */
	public int currSpeed;
	/*
	 * There are 3 stats determined by finesse:
	 * Accuracy: Used to determine the chance of landing an attack.
	 * Evasion: Used to determine the chance of dodging an attack.
	 * Crit Rate: When an attack hits, there is a chance of it being critical.
	 */
	public int[] finesseStats;
	/*
	 * A stat to multiply all incoming damage by. By default, it's
	 * 1, but status effects like Breach and Shield can affect it.
	 * Certain equipment also affects it.
	 */
	public double damageMultiplier;
	/*
	 * A hashmap of integers with string keys representing the different status ailments.
	 * Each status ailment is a continuum ranging from -10 to 10, representing
	 * either a positive status for positive numbers or a negative status for
	 * negative numbers. It is a hashmap so that more status effects can be added as
	 * necessary later. The continuum status effects are as follows:
	 * 
	 * Poison -- Regen
	 * Lethargy -- 
	 * Fear -- Courage
	 * Paralysis -- 
	 * Slow -- Haste
	 * Addle -- Clarity
	 * 
	 */
	public HashMap<String, Integer> statusEffects;
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
	 * elements. The 8 elements are as follows:
	 * 
	 * Physical - Most basic attacks and abilities have this element.
	 * Fire - Attacks using heat have this element.
	 * Water - Attacks using water and ice have this element.
	 * Air - Attacks using wind and lightning have this element.
	 * Earth - Attacks using rocks and nature have this element.
	 * Light - Attacks using life and light have this element.
	 * Dark - Attacks using darkness and death have this element.
	 * Non-Elemental - Very difficult to resist this element.
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
	/*
	 * A hashmap containing the character's resistances to various
	 * status effects. If there is no resistance associated with the
	 * given status effect, chance is assumed to be 100%.
	 * 
	 * Status resistances are ints - they modify the chance for a
	 * status ailment to land in the following way:
	 * 0: Normal chance
	 * 50: Half chance
	 * 100+: No chance
	 * -100: Double chance
	 * 
	 * It also modifies the magnitude of the status effect if it
	 * does land in the following way:
	 * 0: Normal magnitude
	 * 50: Half magnitude
	 * 100+: No Magnitude
	 * -100: Double magnitude
	 */
	public HashMap<String, Integer> statusResistances;
	public GameCharacter(String name) {
		this.name = name;
		this.baseStats = new int[6];
		this.basePoints = new int[3];
		this.finesseStats = new int[3];
		this.currStats = new int[6];
		this.currPoints = new int[3];
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
	 * All characters also have an icon to depict them in menus and the turn counter
	 * and the like. The file name of the icon is stored in the icon String (e.g. player.png)
	 */
	public String icon;
	/*
	 * Every character has getter and setter functions for their img.
	 */
	public String getIMG() { return img; }
	public void setIMG(String img) { this.img = img; }
}

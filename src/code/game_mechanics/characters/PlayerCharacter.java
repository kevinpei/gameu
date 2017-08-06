package code.game_mechanics.characters;

import java.util.ArrayList;

import org.w3c.dom.Document;

import code.constants.Constants;
import code.game_mechanics.Ability;
import code.xml.XMLReader;

public class PlayerCharacter extends GameCharacter {
	/*
	 * An arraylist that stores all abilities the character can use.
	 * It stores the ID numbers of each of those abilities.
	 */
	public ArrayList<Integer> abilities;
	
	/*
	 * An integer tracking enemy aggression towards this character. A
	 * character with higher aggression will be targeted more often.
	 */
	public int aggression;
	
	public PlayerCharacter(String name) {
		super(name);
	}
	public PlayerCharacter() {
		super("");
	}
	/*
	 * This ends combat for the given player character. It clears
	 * all status effects and sets all stats to their base levels.
	 * Current HP and MP are set to their maxes and AP is set to 0.
	 * Speed is also set to 0.
	 */
	public void endCombat() {
		this.statusEffects.clear();
		this.currSpeed = 0;
		for (String stat: Constants.stats) {
			this.stats.get(stat)[1] = this.stats.get(stat)[0];
		}
		for (String point: Constants.points) {
			this.points.get(point)[1] = this.points.get(point)[0];
			this.points.get(point)[2] = this.points.get(point)[0];
		}
		this.points.get("AP")[2] = 0;
		for (String finesse: Constants.finesse) {
			this.finesse.get(finesse)[1] = this.points.get(finesse)[0];
		}
		damageMultiplier = 1.0;
	}
}
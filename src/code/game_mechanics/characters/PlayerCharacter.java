package code.game_mechanics.characters;

import java.util.ArrayList;

import org.w3c.dom.Document;

import code.game_mechanics.Ability;
import code.xml.XMLReader;

public class PlayerCharacter extends GameCharacter {
	/*
	 * An arraylist that stores all abilities the character can use.
	 */
	public ArrayList<Ability> abilities;
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
		for (String status : this.statusEffects.keySet()) {
			this.statusEffects.get(status);
		}
		this.currSpeed = 0;
		for (int i = 0; i < 2; i++) {
			currPoints[i] = basePoints[i];
			currStats[i] = baseStats[i];
		}
		for (int j = 2; j < 6; j++) {
			currStats[j] = baseStats[j];
		}
		currPoints[2] = 0;
		damageMultiplier = 1.0;
	}
}
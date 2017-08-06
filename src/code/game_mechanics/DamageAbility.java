package code.game_mechanics;

import java.util.ArrayList;
import java.util.HashMap;

import code.game_mechanics.characters.GameCharacter;

/*
 * Damage abilities are abilities that deal damage to their targets.
 * Damage abilities are an abstract class that will be implemented
 * by each specific damage ability.
 */
public abstract class DamageAbility extends Ability{

	/*
	 * Additional multipliers are any context-specific multipliers that occur, such as
	 * double damage if the ability is effective against humans.
	 */
	public double additionalMultipliers;
	
	/*
	 * Variance is how much the final number can fluctuate in value from its base amount.
	 */
	public double variance;
	
	/*
	 * Hits is how many times the ability hits the enemy.
	 */
	public int hits;
	/*
	 * Abilities have their own separate accuracy, crit rate, and element.
	 */
	public int accuracy;
	public int critChance;
	public String element;
	
	public int ailmentID;
	public int ailmentChance;
	public int ailmentMagnitude;
	/*
	 * Damage abilities may also inflict status ailments. The ailments they
	 * can inflict are stored in a hashmap, with the status effect as the
	 * key and the value being the chance of landing it (as a percent)
	 */
	HashMap<String, Integer> effects;
	/*
	 * An abstract method describing the damage formula for the given
	 * ability.
	 */
	public abstract int damageFormula(GameCharacter user, GameCharacter target);
	
	/*
	 * A method to try to inflict a status ailment on a target.
	 */
	public boolean statusInfliction(GameCharacter target) {
		/*
		 * Pick a random number between 0 and 1 and see if the 
		 * resulting number is greater than (100 - %chance) multiplied
		 * by (100 - resistance / 100). If so, then the status ailment 
		 * lands.
		 * 
		 * This number is also affected by the target's resistance
		 * to the ailment, with larger resistances lowering the
		 * chance.
		 * 
		 * Ailment accuracy is unaffected by things like evasion,
		 * only the chance of landing it.
		 */
		if (Math.random() > ((100.0 - (double)ailmentChance) / 100.0) * 
				(100.0 - (double)target.statusResistances.get(ailmentID) / 100.0)) {
			return true;
		}
		return false;
	}
	
	/*
	 * A function to calculate the total multiplier to damage this ability receives. It
	 * counts context-specific multipliers, elemental resistance, and variance.
	 */
	public double totalMultipliers(GameCharacter user, GameCharacter target) {
		double multipliers = additionalMultipliers;
		multipliers *= (((double)target.elementalResistances.get(element) + 100.0) / 100.0);
		multipliers *= (Math.random() * 2 * variance + 1.0 - variance);
		return multipliers;
	}
}

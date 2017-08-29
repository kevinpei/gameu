package code.game_mechanics.abilities;

import java.util.ArrayList;
import java.util.HashMap;

import code.game_mechanics.Library;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.status_effects.StatusEffect;

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

	/*
	 * Abilities can also inflict status effects, with arrays containing their
	 * ids, chances, and magnitudes when inflicted being here.
	 */
	public int[] effectIDs;
	public int[] effectChances;
	public int[] effectMagnitudes;
	
	/*
	 * An abstract method describing the damage formula for the given
	 * ability.
	 */
	public abstract int damageFormula(GameCharacter user, GameCharacter target);
	
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

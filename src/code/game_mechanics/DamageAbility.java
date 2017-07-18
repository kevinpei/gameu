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
	 * Power measures the base power of the ability. It will be used in the
	 * damage calculation for the ability.
	 */
	int power;
	/*
	 * Abilities have their own separate accuracy, crit rate, and element.
	 */
	int accuracy;
	int critrate;
	int element;
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
	public abstract void damageFormula(GameCharacter user, GameCharacter target);
	
	/*
	 * A method to try to inflict a status ailment on a target.
	 */
	public boolean statusInfliction(String name, Integer magnitude, Integer chance,
			GameCharacter target) {
		/*
		 * Pick a random number between 0 and 1 and see if the 
		 * resulting number is greater than (100 - %chance). If 
		 * so, then the status ailment lands.
		 * 
		 * Ailment accuracy is unaffected by things like evasion,
		 * only the chance of landing it.
		 */
		if (Math.random() > (100.0 - (double)chance) / 100.0) {
			/*
			 * If the target already has the status ailment, then its
			 * magnitude is increased by the magnitude of the status
			 * ailment that would've been inflicted.
			 */
			if (target.statusEffects.containsKey(name))
				target.statusEffects.put(name, target.statusEffects.get(name) + magnitude);
			else
				target.statusEffects.put(name, magnitude);
			return true;
		}
		return false;
	}
}

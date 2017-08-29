package code.game_mechanics;

import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.graphics.EnemyGraphics;
import code.graphics.PlayerGraphics;
import code.game_mechanics.abilities.*;
import code.game_mechanics.status_effects.*;

public class Damage {

	/*
	 * A function to have a character deal damage to its targets given a specific ability.
	 * Includes damage dealing and status infliction.
	 */
	public void damageAbility(GameCharacter source, GameCharacter[] targets, int abilityID) {
		for (GameCharacter target : targets) {
			DamageAbility ability = (DamageAbility)Library.abilities.get(abilityID);
			int amount = ability.damageFormula(source, target);
			Damage.takeDamage(target, amount);
			for (int i = 0; i < ability.effectChances.length; i ++) {
				if (Math.random() * 100 < ability.effectChances[i] * 
						(1 - ((double)target.statusResistances.get(ability.effectIDs[i]) / 100.0))) {
					Library.getStatusEffect(ability.effectIDs[i]).
						changeMagnitude(target, ability.effectMagnitudes[i]);
				}
			}
			if (source.statusEffects.containsKey(Library.getIDFromName("Curse", false))) {
				((VampireStatusEffect)Library.getStatusEffect
					(Library.getIDFromName("Curse", false))).onAttack(source, amount);
			}
		}
	}
	
	/*
	 * A method to deal the specified amount of damage and show the animations for
	 * taking damage.
	 */
	public static void takeDamage(GameCharacter target, int amount) {
		target.points.get("HP")[2] -= amount;
		if (target instanceof PlayerCharacter) {
			PlayerGraphics.hitPlayer((PlayerCharacter) target, amount);
		} else {
			EnemyGraphics.hitEnemy((Enemy) target, amount);
		}
	}
}

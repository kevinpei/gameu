package code.game_mechanics.status_effects;

import code.game_mechanics.Damage;
import code.game_mechanics.characters.GameCharacter;

public class VampireStatusEffect extends StatusEffect{

	public double damageMultiplier;
	public double exponentialMultiplier;
	
	public VampireStatusEffect(double multiplier, double exponent) {
		damageMultiplier = multiplier;
		exponentialMultiplier = exponent;
	}
	
	public void onAttack(GameCharacter target, int damage) {
		int amount = (int)Math.round(damage * (damageMultiplier * 
				Math.pow(target.statusEffects.get(id), exponentialMultiplier)));
		Damage.takeDamage(target, amount);
	}
	
	@Override
	public void execute(GameCharacter target) {
		
	}

}

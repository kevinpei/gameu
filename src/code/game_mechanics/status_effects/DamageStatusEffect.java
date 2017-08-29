package code.game_mechanics.status_effects;

import code.game_mechanics.Damage;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.graphics.EnemyGraphics;
import code.graphics.PlayerGraphics;

public class DamageStatusEffect extends StatusEffect{

	/*
	 * The base damage is the base amount of damage the effect does per magnitude.
	 */
	public double baseDamage;
	
	/*
	 * The multiplier is what power the magnitude is raised to when multiplied by
	 * the base damage.
	 */
	public double multiplier;
	
	/*
	 * The variance is how much the damage done each turn can vary.
	 */
	public double variance;
	
	public DamageStatusEffect(double baseDamage, double multiplier, double variance) {
		this.baseDamage = baseDamage;
		this.multiplier = multiplier;
		this.variance = variance;
	}
	
	/*
	 * (non-Javadoc)
	 * @see code.game_mechanics.status_effects.StatusEffect#execute(code.game_mechanics.characters.GameCharacter)
	 * 
	 * The execute function for a damage status effect deals damage equal to the following
	 * formula:
	 * 
	 * base damage * magnitude ^ multiplier
	 * 
	 * This damage varies based on the multiplier.
	 */
	@Override
	public void execute(GameCharacter target) {
		double rawDamage = baseDamage * Math.pow(target.statusEffects.get(id), multiplier);
		int finalDamage = (int)Math.round(rawDamage * (1 - variance + 2 * (Math.random() * variance)));
		Damage.takeDamage(target, finalDamage);
	}

}

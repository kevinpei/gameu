package code.game_mechanics;

import code.game_mechanics.characters.GameCharacter;

public class StatDamageAbility extends DamageAbility{
	
	/*
	 * A set of strings and doubles representing the stats that determine the damage of the
	 * ability and the amount each one is multiplied by.
	 */
	String[] attackStats;
	double[] attackMultipliers;
	
	/*
	 * A set of strings and doubles representing the stats that determine how to reduce the
	 * damage of the ability
	 */
	String[] defenseStats;
	double[] defenseMultipliers;
	
	public StatDamageAbility(String[] att, double[] attMul, String[] def, double[] defMul) {
		this.attackStats = att;
		this.attackMultipliers = attMul;
		this.defenseStats = def;
		this.defenseMultipliers = defMul;
	}
	
	/*
	 * This function defines the damage formula for an ability based on the stats of the attacked
	 * and defender. It allows the use of any number of stats from the attacker and any stats
	 * from the defender, each with different multipliers. This formula then multiplies each of the
	 * attack stats by their multipliers, adds them together, and subtracts the sum of each of the
	 * defense stats muliplied by their multipliers. This is the final raw damage.
	 */
	public int rawDamageFormula(GameCharacter user, GameCharacter target) {
		double rawDamage = 0;
		for (int i = 0; i < attackStats.length; i++) {
			rawDamage += user.stats.get(attackStats[i])[1] * attackMultipliers[i];
		}
		double rawDefense = 0;
		for (int j = 0; j < defenseStats.length; j++) {
			rawDamage += user.stats.get(defenseStats[j])[1] * defenseMultipliers[j];
		}
		return (int)Math.round(rawDamage - rawDefense);
	}

	/*
	 * (non-Javadoc)
	 * @see code.game_mechanics.DamageAbility#damageFormula(code.game_mechanics.characters.GameCharacter, code.game_mechanics.characters.GameCharacter)
	 * 
	 * A function to calculate the final damage, taking the raw damage and multiplying
	 * it by all multipliers.
	 */
	@Override
	public int damageFormula(GameCharacter user, GameCharacter target) {
		double damage = this.rawDamageFormula(user, target);
		damage *= this.totalMultipliers(user, target);
		return (int) damage;
	}
	
	
	
}

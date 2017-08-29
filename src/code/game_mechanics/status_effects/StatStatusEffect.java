package code.game_mechanics.status_effects;

import code.game_mechanics.characters.GameCharacter;

public class StatStatusEffect extends StatusEffect{

	/*
	 * Stats is an array of all stats this status effect affects, while statMultipliers
	 * is an array of the muultipliers for each of those stats. Each stat is modified
	 * by the appropriate stat multiplier times the magnitude.
	 */
	public String[] stats;
	public double[] statMultipliers;
	
	/*
	 * A constructor for this status effect.
	 */
	public StatStatusEffect(String[] stats, double[] statMultipliers) {
		this.stats = stats;
		this.statMultipliers = statMultipliers;
	}

	/*
	 * (non-Javadoc)
	 * @see code.game_mechanics.status_effects.StatusEffect#execute(code.game_mechanics.characters.GameCharacter)
	 * 
	 * Sets the target's stat multipliers from this status effect appropriately.
	 */
	@Override
	public void execute(GameCharacter target) {
		for (int i = 0; i < stats.length; i++) {
			if (target.stats.containsKey(stats[i])) {
				target.statMultipliers.get(stats[i]).statusEffectMultipliers.put
					(this.id, statMultipliers[i] * target.statusEffects.get(id));
			}
		}
	}
	
	
}

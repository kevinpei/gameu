package code.game_mechanics.abilities;

import code.game_mechanics.characters.GameCharacter;

public class FixedDamageAbility extends DamageAbility{

	public int power;
	public int currentPercent;
	public int maxPercent;
	
	public FixedDamageAbility(int power, int currentPercent, int maxPercent) {
		this.power = power;
		this.currentPercent = currentPercent;
		this.maxPercent = maxPercent;
	}
	
	/*
	 * (non-Javadoc)
	 * @see code.game_mechanics.abilities.DamageAbility#damageFormula(code.game_mechanics.characters.GameCharacter, code.game_mechanics.characters.GameCharacter)
	 * 
	 * Fixed damage abilities can have either a fixed amount or a fixed percent damage.
	 * The fixed amount is modified by variance and elemental resistances, but the percentage
	 * amount is not. Max percentage is based on max HP and current percentage is based on current
	 * HP. All damage is added up and returned.
	 */
	@Override
	public int damageFormula(GameCharacter user, GameCharacter target) {
		int powerDamage = (int)Math.round(power * this.totalMultipliers(user, target));
		int percentDamage = (int)Math.round(target.points.get("HP")[1] * (maxPercent / 100.0));
		percentDamage += (int)Math.round(target.points.get("HP")[2] * (currentPercent / 100.0));
		return powerDamage + percentDamage;
	}
}

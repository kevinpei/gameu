package code.game_mechanics.status_effects;

import code.game_mechanics.characters.GameCharacter;

public abstract class StatusEffect {
	/*
	 * Every status effect has a name and description for what
	 * it does. Each status effect also has an id to identify it.
	 */
	public String name;
	public String description;
	public int id;
	
	/*
	 * Every status effect has a magnitude, which can't exceed its
	 * max magnitude. The magnitude tracks both the duration of
	 * the status effect and its severity. The higher the magnitude,
	 * the longer and more severe the effect. Abilities can add or
	 * subtract from this magnitude, and landing the same effect
	 * twice often increases the magnitude.
	 */
	public int maxMagnitude;
	/*
	 * An abstract method that is run at the beginning of every
	 * turn. It executes whatever the ailment does.
	 */
	public abstract void execute(GameCharacter target);
	
	/*
	 * The method that is run at the beginning of a character's turn.
	 * It executes the action of the status effect and decrements its
	 * magnitude. If the magnitude is 0, then the status effect is 
	 * removed from the character's list of status effects.
	 */
	public void beginTurn(GameCharacter target) {
		this.execute(target);
		changeMagnitude(target, -1);
		if (target.statusEffects.get(this.id) == 0) {
			target.statusEffects.remove(this.id);
		}
	}
	
	/*
	 * A method to increase the magnitude. It will not increase it
	 * beyond the max magnitude.
	 * 
	 * The higher a character's resistance to an ailment, the less
	 * the magnitude will increase. Therefore, resistance decreases 
	 * the magnitude of an ailment when it lands.
	 * 
	 * The amount the magnitude goes up depending on resistance varies
	 * as follows:
	 * 
	 * 0: Regular magnitude increase
	 * 50: Half magnitude increase
	 * 100: No magnitude increase
	 * -100: Double magnitude increase
	 * 
	 * Round the final increase in magnitude to the nearest int.
	 * Follows rounding rules of rounding 0.5+ to 1 and 0.5- to 0.
	 */
	public void changeMagnitude(GameCharacter target, int mag) {
		int finalMagnitude = adjustedMagnitude(target, mag);
		if (target.statusEffects.get(id) + finalMagnitude > maxMagnitude) {
			target.statusEffects.put(id, maxMagnitude);
		} else if (target.statusEffects.get(id) + finalMagnitude < 0) {
			target.statusEffects.put(id, 0);
		} else {
			target.statusEffects.put(id, target.statusEffects.get(id) + finalMagnitude);
		}
	}
	
	/*
	 * A function to get the final magnitude after accounting for the target's resistance
	 * to this staus effect.
	 */
	public int adjustedMagnitude(GameCharacter target, int mag) {
		if (mag > 0) {
			double resistanceMultiplier = ((-1.0 * (double)target
					.statusResistances.get(this.id) + 100.0) / 100.0);
			if (resistanceMultiplier < 0) resistanceMultiplier = 0;
			return (int) Math.round(mag * resistanceMultiplier);
		}
		return mag;
	}
	
}

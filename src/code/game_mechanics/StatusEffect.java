package code.game_mechanics;

import code.game_mechanics.characters.GameCharacter;

public abstract class StatusEffect {
	/*
	 * Every status effect has a name and description for what
	 * it does.
	 */
	public String name;
	public String description;
	/*
	 * Every status effect has a magnitude, which can't exceed its
	 * max magnitude. The magnitude tracks both the duration of
	 * the status effect and its severity. The higher the magnitude,
	 * the longer and more severe the effect. Abilities can add or
	 * subtract from this magnitude, and landing the same effect
	 * twice often increases the magnitude.
	 */
	public int magnitude;
	public int maxMagnitude;
	/*
	 * The target is the character currently being affected by the
	 * status effect.
	 */
	public GameCharacter target;
	/*
	 * An abstract method that is run at the beginning of every
	 * turn. It executes whatever the ailment does.
	 */
	public StatusEffect(int mag, GameCharacter target) {
		this.magnitude = mag;
		this.target = target;
	}
	public abstract void execute();
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
	 */
	public void increaseMagnitude(int mag) {
		double resistanceMultiplier = ((-1.0 * (double)target
				.statusResistances.get(this.name) + 100.0) / 100.0);
		if (resistanceMultiplier < 0) resistanceMultiplier = 0;
		/*
		 * Round the final increase in magnitude to the nearest int.
		 * Follows rounding rules of rounding 0.5+ to 1 and 0.5- to 0.
		 */
		int finalMagnitude = (int) Math.round(mag * resistanceMultiplier);
		if (this.magnitude + finalMagnitude > maxMagnitude) {
			this.magnitude = maxMagnitude;
		} else {
			this.magnitude += finalMagnitude;
		}
	}
}

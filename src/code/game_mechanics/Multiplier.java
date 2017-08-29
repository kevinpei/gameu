package code.game_mechanics;

import java.util.HashMap;

public class Multiplier {

	/*
	 * The multiplier is made up of multipliers from status effects,
	 * equipment, and other sources. Status effects and equipment are
	 * stored in separate hashmaps because status effects are reset at
	 * the end of every battle but equipmentMultipliers are not.
	 * The key is the id of the status effect or equipment and the
	 * value is the multiplier.
	 */
	public HashMap<Integer, Double> statusEffectMultipliers;
	public HashMap<Integer, Double> equipmentMultipliers;
	public double baseMultiplier = 1.0;
	
	/*
	 * The constructor for a multiplier initializes the multipliers hashmap.
	 */
	public Multiplier() {
		statusEffectMultipliers = new HashMap<Integer, Double>();
		equipmentMultipliers = new HashMap<Integer, Double>();
	}
	
	/*
	 * This function calculates the overall multiplier and then returns it.
	 */
	public double getMultiplier() {
		double overallMultiplier = 0;
		for (Integer multiplier : statusEffectMultipliers.keySet()) {
			overallMultiplier += statusEffectMultipliers.get(multiplier);
		}
		for (Integer multiplier : equipmentMultipliers.keySet()) {
			overallMultiplier += equipmentMultipliers.get(multiplier);
		}
		return baseMultiplier + overallMultiplier;
	}
	
	/*
	 * This function resets the status effect multipliers while keeping equipment
	 * multipliers intact.
	 */
	public void resetMultipliers() {
		statusEffectMultipliers.clear();
	}
}

package code.game_mechanics.characters.playerclasses.defender;

import code.game_mechanics.characters.PlayerCharacter;

public class Defender extends PlayerCharacter{
	public Defender() {
		/*
		 * Initializes the base stats for a defender. A defender has
		 * the following base stats:
		 * 
		 * Attack:
		 * Intelligence:
		 * Speed:
		 * Finesse:
		 * Defense:
		 * Magic Defense:
		 * Max HP:
		 * Max MP:
		 * Max AP:
		 */
		super("Defender");
		this.baseStats[0] = 200;
		this.baseStats[1] = 100;
		this.baseStats[2] = 30;
		this.baseStats[3] = 100;
		this.baseStats[4] = 300;
		this.baseStats[5] = 200;
		this.baseStats[6] = 3500;
		this.baseStats[7] = 300;
		this.baseStats[8] = 3;
		this.finesseStats[0] = 10;
		this.finesseStats[1] = 30;
		this.finesseStats[2] = 3;
		this.description = "A stalwart class devoted to the defense"
				+ "of innocents. They are capable of shrugging off"
				+ "attacks that would incapacitate weaker classes.";
	}
	
}

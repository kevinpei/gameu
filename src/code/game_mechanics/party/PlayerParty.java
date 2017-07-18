package code.game_mechanics.party;

import java.util.ArrayList;

import code.game_mechanics.characters.PlayerCharacter;

/*
 * The PlayerParty class will be a singleton. There will only be 
 */
public class PlayerParty {
	
	public static PlayerCharacter[] party;
	public static int victories;
	public static int challengeVictories;
	public static void initialize(PlayerCharacter[] members) {
		party = members;
		victories = 0;
		challengeVictories = 0;
	}
	
}

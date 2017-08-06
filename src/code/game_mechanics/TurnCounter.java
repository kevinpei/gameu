package code.game_mechanics;

import java.util.ArrayList;

import code.constants.Constants;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.encounters.Encounter;
import code.game_mechanics.party.PlayerParty;
import code.graphics.MessageBoxGraphics;
import javafx.scene.Group;

public class TurnCounter {
	
	public static GameCharacter[] turnOrder = new GameCharacter[Constants.turnsAhead];
	private static ArrayList<Integer> projectedSpeed = new ArrayList<Integer>();
	public static int turnCounter;
	private static ArrayList<GameCharacter> characters;
	
	public static void initialize(Encounter encounter) {
		characters = new ArrayList<GameCharacter>();
		for (Enemy enemy : encounter.getEnemies()) {
			characters.add(enemy);
		}
		for (PlayerCharacter player : PlayerParty.party) {
			characters.add(player);
		}
	}
	
	public static ArrayList<GameCharacter> getCharacters() {
		return characters;
	}
	
	/*
	 * A function to get the next character that's acting. It adds each character's
	 * current speed to their speed counter. When a character's speed counter reaches
	 * the speed threshold for acting (found in Constants), that character acts.
	 */
	public static GameCharacter getNextTurn() {
		while (true) {
			for (GameCharacter character : characters) {
				character.currSpeed += character.stats.get("speed")[1];
				if (character.currSpeed >= Constants.speedThreshold) {
					character.currSpeed = 0;
					return character;
				}
			}
		}
	}
	
	/*
	 * A function to predict who the next character will be. It does not actually 
	 * modify the currSpeed stat of characters, instead modifying a projected Speed
	 * arraylist that mimics it. This allows prediction without actually changing
	 * the current speed of characters.
	 */
	public static GameCharacter predictNextTurn() {
		while (true) {
			for (int i = 0; i < characters.size(); i++) {
				projectedSpeed.set(i, projectedSpeed.get(i) + characters.get(i).stats.get("speed")[1]);
				if (projectedSpeed.get(i) >= Constants.speedThreshold) {
					projectedSpeed.set(i, 0);
					return characters.get(i);
				}
			}
		}
	}
	
	/*
	 * A function to predict the next 12 turns. Used at the beginning of a battle
	 * and whenever a character's speed changes.
	 */
	public static void predictTurns(ArrayList<GameCharacter> characters) {
		TurnCounter.characters = characters;
		projectedSpeed.clear();
		for (GameCharacter character : characters) {
			projectedSpeed.add(character.currSpeed);
		}
		for (int i = 0; i < Constants.turnsAhead; i++) {
			turnOrder[i] = predictNextTurn();
		}
	}
	
	/*
	 * A function to end a character's turn. It moves the turn order ahead by one
	 * and increases the turn counter.
	 */
	public static void endTurn() {
		for (int i = 1; i < Constants.turnsAhead; i++) {
			turnOrder[i-1] = turnOrder[i];
		}
		turnOrder[Constants.turnsAhead - 1] = getNextTurn();
		turnCounter++;
		MessageBoxGraphics.updateTurnCounter();
	}
}

package code.game_mechanics.inventory;

import java.util.ArrayList;

import code.game_mechanics.characters.GameCharacter;

public abstract class Item {
	
	public String name;
	public String description;
	
	public abstract void execute(GameCharacter target);
}

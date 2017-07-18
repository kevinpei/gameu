package code.game_mechanics.characters;

import javafx.scene.Group;

public class Enemy extends GameCharacter {
	
	public Enemy(String name) {
		super(name);
	}
	public Enemy() {
		super("");
	}
	public int width;

	public int getWidth() {return width; }
	public void setGraphics(Group graphics) {this.graphics = graphics;}
	public Group getGraphics() {return graphics;}
}

package code.game_mechanics.characters;


import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

public class Enemy extends GameCharacter {
	
	public Enemy(String name) {
		super(name);
		this.pointBars = new Canvas[1];
	}
	public Enemy() {
		super("");
		this.pointBars = new Canvas[1];
	}
	public int width;

	public int getWidth() {return width; }
	public void setGraphics(Group graphics) {this.graphics = graphics;}
	public Group getGraphics() {return graphics;}
}

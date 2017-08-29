package code.graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import code.constants.Constants;
import code.file_management.FileManager;
import code.game_mechanics.TurnCounter;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.encounters.Encounter;
import code.game_mechanics.party.PlayerParty;
import code.mouse_engine.MouseEngine;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class EnemyGraphics{
	
	/*
	 * Draws a new encounter. Draws all the enemies, their names, their health bars, and
	 * the background.
	 */
	public static void drawEncounter(Encounter encounter, AnchorPane graphics) {
    	int rightOffset = 0;
    	int leftOffset = 0;
    	for (int i = 0; i < encounter.getEnemies().size(); i++) {
    		Enemy e = encounter.getEnemies().get(i);
    		if (i == 0) {
    			e.setGraphics(drawNewEnemy(encounter.getEnemies().get(i), 0, graphics));
    			rightOffset += e.getWidth() * 7 / 8;
    			leftOffset += e.getWidth() * 7 / 8;
    		} else if (i % 2 == 0) {
    			e.setGraphics(drawNewEnemy(e, rightOffset, graphics));
    			rightOffset += e.getWidth() * 7 / 8;
    		} else {
    			e.setGraphics(drawNewEnemy(e, -leftOffset, graphics));
    			leftOffset += e.getWidth() * 7 / 8;
    		}
    		MouseEngine.setOnMouseOver(e.getGraphics().getChildren().get(0), e, 
    				MessageBoxGraphics::mouseOverCharacter, MessageBoxGraphics::stopMouseOverCharacter);
    	}
    	Graphics.drawBackground(encounter.bg, graphics);
    }
	
	/*
	 * Function to draw a new enemy. It creates a group and adds to it a canvas containing
	 * the enemy graphic, a label containing the enemy name, and a canvas containing the 
	 * enemy healthbar. Finally, the group is returned.
	 */
    public static Group drawNewEnemy(Enemy character, int offset, AnchorPane graphics) {
    	Group enemyGraphics = new Group();
    	//Retrieve the img file of the enemy and set its scale and position on the canvas.
    	Image enemyIMG = FileManager.getImage(character);
    	double scale = character.getWidth() / enemyIMG.getWidth();
    	double Xpos = ((graphics.getWidth() - enemyIMG.getWidth() * scale)/2) + offset;
    	double Ypos = ((graphics.getHeight() - enemyIMG.getHeight() * scale)/2.5) - Math.abs(offset / 10);
    	//Create the canvas that contains the enemy img.
    	Canvas IMGcanvas = Graphics.drawCanvas(enemyIMG, scale);
    	character.portrait = IMGcanvas;
    	//Add the canvas to the group.
    	Graphics.addToGroup(enemyGraphics, IMGcanvas, Xpos, Ypos);
    	//Create the canvas that contains the enemy health bar and add it to the group.
    	Canvas HealthBarCanvas = new Canvas(200, Constants.barHeight);
    	Graphics.drawBar(HealthBarCanvas, Color.RED, character.points.get("HP")[2], character.points.get("HP")[1]);
    	HealthBarCanvas.setVisible(false);
    	Graphics.addToGroup(enemyGraphics, HealthBarCanvas, Xpos + (enemyIMG.getWidth() * scale / 2)
    			- HealthBarCanvas.getWidth() / 2, Ypos + enemyIMG.getHeight() * scale * 1.02);
    	//Add the group to the anchor pane.
    	character.pointBars[0] = HealthBarCanvas;
    	graphics.getChildren().add(enemyGraphics);
    	enemyGraphics.toBack();
    	return enemyGraphics;
    }
    
    /*
     * A function to animate the enemy when hit. It causes the enemy to flash briefly and redraws
     * their healthbar to show the new amount.
     */
    public static void hitEnemy(Enemy enemy, int amount) {
    	enemy.pointBars[0].setVisible(true);
    	Animations.hideObject(enemy.pointBars[0], 1500);
    	Graphics.hitCharacter(enemy, amount);
    }
	
}

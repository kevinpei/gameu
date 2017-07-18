package code.graphics;

import java.util.ArrayList;

import code.constants.Constants;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.party.PlayerParty;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class PlayerGraphics {

    /*
     * Draws the party by drawing each individual character and spacing them appropriately.
     */
    public static void drawPlayerCharacters(AnchorPane graphics) {
    	Group party = new Group();
    	double xOffset = 0;
    	for (PlayerCharacter character : PlayerParty.party) {
    		Graphics.addToGroup(party, drawPlayerCharacter(character), xOffset, 0);
    		xOffset += 200;
    	}
    	graphics.getChildren().add(party);
    	party.setLayoutX((graphics.getWidth() / 2) - 400);
    	party.setLayoutY(graphics.getHeight() - Constants.playerMessageHeight - 8);
    	party.toFront();
    }
    
    /*
     * Draws an individual player character by getting the appropriate image, sizing it
     * appropriately, and drawing a bordered box around the image. It also draws the
     * healthbar, manabar, and AP for the character.
     */
    public static Group drawPlayerCharacter(PlayerCharacter character) {
    	Group playerGraphics = MessageBox.createBorderedBox(192, Constants.playerMessageHeight);
    	Image playerIMG = Graphics.getImage(character);
    	Canvas IMGcanvas = Graphics.drawCanvas(playerIMG, 1);
    	IMGcanvas.setWidth(180);
    	IMGcanvas.setHeight(180);
    	Graphics.addToGroup(playerGraphics, IMGcanvas, 6, 6);
    	Graphics.addToGroup(playerGraphics, drawPlayerPoints(character, Constants.barHeight), 0, 0);
    	character.graphics = playerGraphics;
    	return playerGraphics;
    }
    
    /*
     * A function to draw the health bar, mana bar, and AP bar of the given player character, and
     * return the group that contains them.
     */
    public static Group drawPlayerPoints(PlayerCharacter character, int height) {
    	Group points = new Group();
    	Canvas healthBar = new Canvas(180, height);
    	healthBar.setLayoutX(6);
    	healthBar.setLayoutY(180 - height);
    	Graphics.drawBar(healthBar,Color.RED, character.currPoints[0], character.basePoints[0]);
    	Label health = Graphics.drawLabel(10, 180 - height, Color.BLACK, 16, "HP: " + 
    			character.currPoints[0] + "/" + character.basePoints[0]);
    	Canvas manaBar = new Canvas(180, height);
    	manaBar.setLayoutX(6);
    	manaBar.setLayoutY(178);
    	Graphics.drawBar(manaBar,Color.DODGERBLUE, character.currPoints[1], character.basePoints[1]);
    	Label mana = Graphics.drawLabel(10, 178, Color.BLACK, 16, "MP: " + 
    			character.currPoints[1] + "/" + character.basePoints[1]);
    	Canvas apBar = new Canvas(180, height);
    	apBar.setLayoutX(6);
    	apBar.setLayoutY(180 + height - 4);
    	Graphics.drawBar(apBar,Color.LIMEGREEN, character.currPoints[2], character.basePoints[2]);
    	Label ap = Graphics.drawLabel(10, 180 + height - 4, Color.BLACK, 16, "AP: " 
    			+ character.currPoints[2] + "/" + character.basePoints[2]);
    	points.getChildren().addAll(healthBar, health, manaBar, mana, apBar, ap);
    	points.toFront();
    	points.setMouseTransparent(true);
    	return points;
    }
    
    /*
     * A function to redraw the various bars a player character possesses.
     * First, it removes the old ones. Then, it draws new ones and adds them
     * to the player's graphics.
     */
    public static void redrawBars(PlayerCharacter character) {
    	character.graphics.getChildren().remove(1);
    	Graphics.addToGroup(drawPlayerPoints(character, Constants.barHeight), character.graphics, 0, 0);
    }
}

package code.graphics;

import java.util.ArrayList;
import java.util.function.Consumer;

import code.battle_screen.BattleScreen;
import code.battle_screen.BattleScreenController;
import code.constants.Constants;
import code.file_management.FileManager;
import code.game_mechanics.TurnCounter;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.encounters.Encounter;
import code.game_mechanics.party.PlayerParty;
import code.graphics.message_box.Menu;
import code.graphics.message_box.MessageBox;
import code.graphics.message_box.TextBox;
import code.mouse_engine.MouseEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/*
 * This class will allow for the creation of messageboxes used for stuff like getting the
 * names of enemies and the menu for selecting things.
 */
public class MessageBoxGraphics {
	
	/*
	 * Static groups representing three message boxes used for every fight: a box showing 
	 * the enemy status, a box containing the available commands, a box containing a
	 * list of abilities or similar, and a box for canceling an action.
	 */
	public static TextBox statusBox;
	public static Menu playerCommandMenu;
	public static Menu playerListAbilities;
	public static Menu cancelBox;
	public static MessageBox turnCounter;
	
	/*
	 * A static method to initialize the various static groups representing messageboxes.
	 */
	public static void initialize(AnchorPane graphics) {
		statusBox = new TextBox(8, 8, 
    			graphics.getWidth() - 16, Constants.statusBoxHeight);
    	statusBox.messageBox.setMouseTransparent(true);
    	statusBox.setVisible(true);
    	playerCommandMenu = new Menu(graphics.getWidth() - 200, graphics.getHeight() - 
    			Constants.playerMessageHeight - 8, Constants.menuWidth, Constants.playerMessageHeight);
    	MessageBoxGraphics.initializePlayerActionMenu();
    	//playerCommandMenu.setVisible(false);
    	playerListAbilities = new Menu(8, graphics.getHeight() - Constants.playerMessageHeight - 8, 
    			graphics.getWidth() - 20 - Constants.menuWidth, Constants.playerMessageHeight);
    	cancelBox = new Menu(graphics.getWidth() - 200, graphics.getHeight() - 
    			Constants.playerMessageHeight - 8, Constants.menuWidth, Constants.playerMessageHeight);
    	cancelBox.addGraphic(MessageBoxGraphics.createButton(12, Constants.playerMessageHeight / 2 - 21, 
    			Constants.menuWidth - 24, 42, "Cancel"));
    	MessageBoxGraphics.initializeTurnCounter();
    	graphics.getChildren().addAll(statusBox.messageBox, playerCommandMenu.messageBox, 
    			playerListAbilities.messageBox, cancelBox.messageBox, turnCounter.messageBox);
	}
	
	/*
	 * Initializes the message box by drawing its label and border, then adding those
	 * to the mBox group. Initialize will only ever be called once. Other function calls
	 * will merely add more canvases or change the label's text.
	 */
	public static Group createMessageBox(double xPos, double yPos, double width, double height) {
		Group messageBox = createBorderedBox(width, height);
		messageBox.setLayoutX(xPos);
		messageBox.setLayoutY(yPos);
		return messageBox;
	}
	
	/*
	 * A function to create a cornflower blue messagebox bordered by white, then black. The
	 * box is rounded at the corners. This function is used for various things, like the
	 * portraits for the player characters, messageboxes, and menus.
	 */
	public static Group createBorderedBox(double width, double height) {
		Group box = new Group();
		Canvas border = new Canvas(width, height);
		GraphicsContext gc = border.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.strokeRoundRect(1.5, 1.5, width - 3, height - 3, 23, 23);
		gc.setStroke(Color.WHITE);
		gc.strokeRoundRect(4.5, 4.5, width - 9, height - 9, 23, 23);
		gc.setFill(Color.CORNFLOWERBLUE);
		gc.fillRoundRect(6, 6, width - 12, height - 12, 20, 20);
		box.getChildren().add(border);
		return box;
	}

	/*
	 * A function to create a button that lights up when moused over. Consists of a
	 * label representing the button and a white overlay to be shown when mousing
	 * over the button.
	 */
	public static Group createButton(double xPos, double yPos, double width, double height, String text) {
		Group button = new Group();
		Label buttonText = Graphics.drawLabel(10, 0, Color.WHITE, 24, text);
		buttonText.setMinWidth(width);
		buttonText.setMinHeight(height);
		Canvas whiteBorder = MessageBoxGraphics.createBorder(buttonText, Color.CRIMSON);
		whiteBorder.setVisible(false);
		button.getChildren().addAll(buttonText, whiteBorder);
		MouseEngine.setOnMouseOver(buttonText, whiteBorder, Graphics::makeVisible, Graphics::makeInvisible);
		button.setLayoutX(xPos);
		button.setLayoutY(yPos);
		return button;
	}
	
	/*
	 * A method to create a white border around a region. It is used for buttons to
	 * show when they are being moused over.
	 */
	public static Canvas createBorder(Region element, Color color) {
		Canvas border = new Canvas(0, 0);
		border.setWidth(element.getMinWidth() + 22);
		border.setHeight(element.getMinHeight() + 6);
		GraphicsContext gc = border.getGraphicsContext2D();
		gc.setStroke(color);
        gc.setLineWidth(2);
        gc.strokeRoundRect(1, 1, element.getMinWidth(), element.getMinHeight(), 15, 15);
        border.setMouseTransparent(true);
        border.toFront();
		return border;
	}
	
	/*
	 * Creates a menu containing the four player commands: fight, ability, defend,
	 * and run.
	 */
	public static void initializePlayerActionMenu() {
		Group action = MessageBoxGraphics.createButton(12, 12, Constants.menuWidth - 24, 
				(Constants.playerMessageHeight - 24)/4, "Action");
		Group items = MessageBoxGraphics.createButton(12, 12 + (Constants.playerMessageHeight - 24)/4, 
				Constants.menuWidth - 24, (Constants.playerMessageHeight - 24)/4, "Items");
		Group endTurn = MessageBoxGraphics.createButton(12, 12 + (Constants.playerMessageHeight - 24)/2, 
				Constants.menuWidth - 24, (Constants.playerMessageHeight - 24)/4, "End Turn");
		Group run = MessageBoxGraphics.createButton(12, 12 + 3*(Constants.playerMessageHeight - 24)/4, 
				Constants.menuWidth - 24, (Constants.playerMessageHeight - 24)/4, "Run");
		
		playerCommandMenu.addButton(action);
		playerCommandMenu.addButton(items);
		playerCommandMenu.addButton(endTurn);
		playerCommandMenu.addButton(run);
	}
	
	public static void updateActionMenu(PlayerCharacter character) {
		playerListAbilities.graphics.clear();
		int i = 0;
		while (i < character.abilities.size()) {
			if (i%2 == 0) {
				
			} else {
				
			}
			i++;
		}
	}
	
	/*
	 * A function to initialize the turn counter. It draws up the initial turn order
	 * and creates instances of the group for the turn counter and the canvases
	 * for the turn icons.
	 */
	public static void initializeTurnCounter() {
		TurnCounter.predictTurns(TurnCounter.getCharacters());
		Group turnGraphic = new Group();
		turnGraphic.toFront();
		turnGraphic.setLayoutY(Constants.statusBoxHeight + 10);
		turnGraphic.setLayoutX(2);
		turnCounter = new MessageBox(turnGraphic);
		int offset = 0;
		for (int i = 0; i < 12; i++) {
			GameCharacter character = TurnCounter.turnOrder[i];
			Canvas icon = new Canvas(24, 24);
			GraphicsContext gc = icon.getGraphicsContext2D();
			if (character instanceof PlayerCharacter) {
				gc.drawImage(FileManager.getIcon(Constants.playerIcon), 0, 0);
			} else {
				gc.drawImage(FileManager.getIcon(Constants.enemyIcon), 0, 0);
			}
			icon.setLayoutX(offset);
			character.futureTurns.add(i);
			offset += 28;
			turnCounter.addGraphic(icon);
		}
	}
	
	/*
	 * Creates the turn counter showing the next 12 turns. It draws the icons of
	 * the characters that are going next.
	 */
	public static void updateTurnCounter() {
		TurnCounter.predictTurns(TurnCounter.getCharacters());
		for (GameCharacter character : TurnCounter.turnOrder) {
			character.futureTurns.clear();
		}
		for (int i = 0; i < 12; i++) {
			GameCharacter character = TurnCounter.turnOrder[i];
			Canvas icon = (Canvas) turnCounter.graphics.get(i);
			GraphicsContext gc = icon.getGraphicsContext2D();
			gc.clearRect(0, 0, icon.getWidth(), icon.getHeight());
			if (character instanceof PlayerCharacter) {
				gc.drawImage(FileManager.getIcon(Constants.playerIcon), 0, 0);
			} else {
				gc.drawImage(FileManager.getIcon(Constants.enemyIcon), 0, 0);
			}
			character.futureTurns.add(i);
		}
	}
	
	/*
	 * A function to highlight the selected game character's turns.
	 */
	public static void highlightTurns(GameCharacter character) {
		for (Integer turn : character.futureTurns) {
			Canvas turnIcon = (Canvas) turnCounter.graphics.get(turn);
			GraphicsContext gc = turnIcon.getGraphicsContext2D();
			gc.clearRect(0, 0, 24, 24);
			gc.drawImage(FileManager.getIcon(Constants.selectedIcon), 0, 0);
		}
	}
	
	/*
	 * A function to hide the status box and health bar after a character is no
	 * longer moused over. The turn counter is also reset so no icons are highlighted.
	 */
	public static void stopMouseOverCharacter(Object character) {
		if (character instanceof Enemy) {
			((Enemy) character).getGraphics().getChildren().get(1).setVisible(false);
		}
		statusBox.text.setText("");
		MessageBoxGraphics.updateTurnCounter();
	}
	
	/*
	 * A function to change the status box whenever a character is moused over.
	 * If the character is an enemy, their healthbar is also displayed. The turn
	 * counter is also highlighted to indicated which turns they have.
	 */
	public static void mouseOverCharacter(Object character) {
		if (character instanceof Enemy) {
			((Enemy) character).getGraphics().getChildren().get(1).setVisible(true);
		}
		MessageBoxGraphics.statusBox.setVisible(true);
		MessageBoxGraphics.statusBox.toFront();
		MessageBoxGraphics.statusBox.text.setText(((GameCharacter)character).name);
		MessageBoxGraphics.highlightTurns((GameCharacter)character);
	}
	
	public static void showActionMenu(Object character) {
		MessageBoxGraphics.updateActionMenu((PlayerCharacter)character);
		playerCommandMenu.setVisible(false);
		playerListAbilities.setVisible(true);
		cancelBox.setVisible(true);
	}
}

package code.graphics;

import java.util.ArrayList;
import java.util.function.Consumer;

import code.battle_screen.BattleScreen;
import code.battle_screen.BattleScreenController;
import code.constants.Constants;
import code.game_mechanics.TurnCounter;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.encounters.Encounter;
import code.game_mechanics.party.PlayerParty;
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
public class MessageBox {
	
	/*
	 * Static groups representing three message boxes used for every fight: a box showing 
	 * the enemy status, a box containing the available commands, a box containing a
	 * list of abilities or similar, and a box for canceling an action.
	 */
	public static Group enemyStatusBox;
	public static Group playerCommandMenu;
	public static Group playerListAbilities;
	public static Group cancelBox;
	public static Group turnCounter;
	
	/*
	 * A static method to initialize the various static groups representing messageboxes.
	 */
	public static void initialize(AnchorPane graphics) {
		enemyStatusBox = MessageBox.createMessageBox(8, 28, 
    			graphics.getWidth() - 16, 150, graphics);
    	enemyStatusBox.setMouseTransparent(true);
    	enemyStatusBox.setVisible(false);
    	playerCommandMenu = new Group();
    	playerCommandMenu.setLayoutX(graphics.getWidth() - 200);
    	playerCommandMenu.setLayoutY(graphics.getHeight() - Constants.playerMessageHeight - 8);
    	playerCommandMenu.getChildren().add(MessageBox.makePlayerActionMenu());
    	//playerCommandMenu.setVisible(false);
    	playerListAbilities = MessageBox.createBorderedBox(graphics.getWidth() - 16, Constants.playerMessageHeight);
    	playerListAbilities.setLayoutX(8);
    	playerListAbilities.setLayoutY(graphics.getHeight() - Constants.playerMessageHeight - 8);
    	playerListAbilities.toFront();
    	playerListAbilities.setVisible(false);
    	cancelBox = MessageBox.createBorderedBox(192, Constants.playerMessageHeight);
    	cancelBox.setLayoutX(graphics.getWidth() - 200);
    	cancelBox.setLayoutY(graphics.getHeight() - Constants.playerMessageHeight - 8);
    	cancelBox.getChildren().add(MessageBox.createButton(12, 75, 168, 42, "Cancel"));
    	cancelBox.toFront();
    	cancelBox.setVisible(false);
    	turnCounter = MessageBox.initializeTurnCounter();
    	graphics.getChildren().addAll(enemyStatusBox, playerCommandMenu, playerListAbilities, cancelBox, turnCounter);
	}
	
	/*
	 * Initializes the message box by drawing its label and border, then adding those
	 * to the mBox group. Initialize will only ever be called once. Other function calls
	 * will merely add more canvases or change the label's text.
	 */
	public static Group createMessageBox(double xPos, double yPos, double width, double height, Region parent) {
		Group messageBox = createBorderedBox(width, height);
		messageBox.setLayoutX(xPos);
		messageBox.setLayoutY(yPos);
		Label text = new Label();
		text.setMinSize(width - 16, height - 16);
		text.setLayoutX(8);
		text.setLayoutY(8);
		text.setTextFill(Color.WHITE);
		text.setFont(new Font(24));
		text.setAlignment(Pos.TOP_CENTER);
		messageBox.getChildren().add(text);
		messageBox.toFront();
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
		Canvas whiteBorder = MessageBox.createBorder(buttonText, Color.CRIMSON);
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
	public static Group makePlayerActionMenu() {
		Group menu = MessageBox.createBorderedBox(192, Constants.playerMessageHeight);
		Group action = MessageBox.createButton(12, 12, 168, (Constants.playerMessageHeight - 24)/4, "Action");
		Group items = MessageBox.createButton(12, 12 + (Constants.playerMessageHeight - 24)/4, 168, (Constants.playerMessageHeight - 24)/4, "Items");
		Group endTurn = MessageBox.createButton(12, 12 + (Constants.playerMessageHeight - 24)/2, 168, (Constants.playerMessageHeight - 24)/4, "End Turn");
		Group run = MessageBox.createButton(12, 12 + 3*(Constants.playerMessageHeight - 24)/4, 168, (Constants.playerMessageHeight - 24)/4, "Run");
		menu.getChildren().addAll(action, items, endTurn, run);
		menu.toFront();
		return menu;
	}
	
	/*
	 * A function to initialize the turn counter. It draws up the initial turn order
	 * and creates instances of the group for the turn counter and the canvases
	 * for the turn icons.
	 */
	public static Group initializeTurnCounter() {
		TurnCounter.predictTurns(TurnCounter.getCharacters());
		turnCounter = new Group();
		int offset = 0;
		for (int i = 0; i < 12; i++) {
			GameCharacter character = TurnCounter.turnOrder[i];
			Canvas icon = new Canvas(24, 24);
			GraphicsContext gc = icon.getGraphicsContext2D();
			gc.drawImage(Graphics.getIcon(character.icon), 0, 0);
			icon.setLayoutX(offset);
			character.futureTurns.add(i);
			offset += 28;
			turnCounter.getChildren().add(icon);
		}
		turnCounter.toFront();
		return turnCounter;
	}
	
	/*
	 * Creates the turn counter showing the next 12 turns. It draws the icons of
	 * the characters that are going next.
	 */
	public static Group drawTurnCounter() {
		TurnCounter.predictTurns(TurnCounter.getCharacters());
		for (GameCharacter character : TurnCounter.turnOrder) {
			character.futureTurns.clear();
		}
		for (int i = 0; i < 12; i++) {
			GameCharacter character = TurnCounter.turnOrder[i];
			Canvas icon = (Canvas) turnCounter.getChildren().get(i);
			GraphicsContext gc = icon.getGraphicsContext2D();
			gc.clearRect(0, 0, icon.getWidth(), icon.getHeight());
			gc.drawImage(Graphics.getIcon(character.icon), 0, 0);
			character.futureTurns.add(i);
		}
		turnCounter.toFront();
		return turnCounter;
	}
	
	/*
	 * A function to highlight the selected game character's turns.
	 */
	public static void highlightTurns(GameCharacter character) {
		for (Integer turn : character.futureTurns) {
			Canvas turnIcon = (Canvas) turnCounter.getChildren().get(turn);
			GraphicsContext gc = turnIcon.getGraphicsContext2D();
			gc.clearRect(0, 0, 24, 24);
			gc.drawImage(Graphics.getIcon("selected.png"), 0, 0);
		}
	}
	
	/*
	 * Updates the appearance of the turnCounter.
	 */
	public static void updateTurnCounter() {
		turnCounter = drawTurnCounter();
		turnCounter.setVisible(false);
		turnCounter.setVisible(true);
	}
}

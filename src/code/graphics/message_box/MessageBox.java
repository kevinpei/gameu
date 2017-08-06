package code.graphics.message_box;

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
import code.graphics.MessageBoxGraphics;
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
	
	public Group messageBox;

	public ArrayList<Node> graphics;
	
	public MessageBox(double xPos, double yPos, double width, double height) {
		messageBox = MessageBoxGraphics.createMessageBox(xPos, yPos, width, height);
		messageBox.setVisible(false);
		messageBox.toFront();
		graphics = new ArrayList<Node>();
	}
	
	public MessageBox(Group messageBox) {
		this.messageBox = messageBox;
		graphics = new ArrayList<Node>();
	}
	
	public void addGraphic(Node graphic) {
		graphics.add(graphic);
		messageBox.getChildren().add(graphic);
	}
	
	public void setVisible(boolean value) {
		messageBox.setVisible(value);
	}
	
	public void toFront() {
		messageBox.toFront();
	}
}

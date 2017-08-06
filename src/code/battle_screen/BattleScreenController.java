package code.battle_screen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import code.file_management.FileManager;
import code.game_mechanics.TurnCounter;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.encounters.Encounter;
import code.graphics.EnemyGraphics;
import code.graphics.MessageBoxGraphics;
import code.music_player.MusicPlayer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;

public class BattleScreenController {
	/*
	 * The anchorpane on the righthand side of the screen that contains the enemy graphics.
	 */
	@FXML private AnchorPane graphics;
    /**
     * The main application that uses this class as a controller
     */
    @FXML private BattleScreen mainApp;
    
	public BattleScreenController() {
    }
	
    /**
     * Initializes the list of users
     * 
     * Sets the value of each column to the appropriate values for each user
     */
    @FXML private void initialize() {
    	// Initialize the user table with the appropriate columns.
    }

    /*
     * Stops an encounter. Clears the graphics and stops the music.
     */
    public void clearEncounter() {
    	graphics.getChildren().clear();
    	MusicPlayer.stopBGM();
    }
    
    public AnchorPane getGraphics() {return graphics;}
    
    @FXML private void changeEncounter() {
    	BattleScreen newEncounter = new BattleScreen(1);
    	newEncounter.start(mainApp.getPrimaryStage());
    }
    
    @FXML private void hurtEnemy() {
    	Enemy testEnemy = mainApp.getEncounter().getEnemies().get(0);
    	testEnemy.points.get("HP")[2] -= 100;
    	EnemyGraphics.hitEnemy(testEnemy, 100);
    	TurnCounter.endTurn();
    }
    
    public void hitEnemy() {
    	Enemy testEnemy = mainApp.getEncounter().getEnemies().get(0);
    	testEnemy.points.get("HP")[2] -= 100;
    	EnemyGraphics.hitEnemy(testEnemy, 100);
    	TurnCounter.endTurn();
    }
    
    /*
     * Sets the main app that uses this class as a controller
     */
    public void setMainApp(BattleScreen mainApp) {
        this.mainApp = mainApp;
    }
}

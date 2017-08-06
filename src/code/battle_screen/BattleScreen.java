package code.battle_screen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import code.battle_screen.BattleScreenController;
import code.file_management.FileManager;
import code.game_mechanics.TurnCounter;
import code.game_mechanics.characters.Enemy;
import code.game_mechanics.encounters.Encounter;
import code.graphics.EnemyGraphics;
import code.graphics.MessageBoxGraphics;
import code.graphics.PlayerGraphics;
import code.music_player.MusicPlayer;
import code.xml.XMLReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BattleScreen extends Application{

	/*
	 * Store the Primary Stage object of the Application
	 */
    private Stage primaryStage;
    /*
     * A FXML loader to read the fxml for the Battle Screen GUI
     */
    FXMLLoader loader = new FXMLLoader();
    /*
     * The controller that controls the GUI
     */
    BattleScreenController controller;
    /*
     * An Encounter object to store all the enemies that will be encountered in this battle,
     * their bgm, and the bg.
     */
    private Encounter encounter;
    /*
     * A constructor.
    */     
    public BattleScreen(){
    	MusicPlayer.stopBGM();
    	encounter = Encounter.InitEncounter(0);
    }

	public BattleScreen(int id) {
		MusicPlayer.stopBGM();
		encounter = Encounter.InitEncounter(id);
	}
	
    /*
     * Overwrites the default start method of Application in order to set up the enemy xml
     * file and select the appropriate enemies for the encounter.
     */
    @Override public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //We call the window "Song Library".
        this.primaryStage.setTitle("Battle Screen");
        showBattleScreen();
        //We set the window to not be resizable to avoid formatting errors.
        primaryStage.setResizable(false);
    }
    
    /*
	 * A function to show the GUI associated with the battle screen. It sets the FXML file
	 * and creates a new window, then sets the main app of the controller to this class.
     */
    public void showBattleScreen() {
        try {
            loader.setLocation(BattleScreen.class.getResource("view/BattleScreen.fxml"));
            AnchorPane battleScene = (AnchorPane) loader.load();
            Scene scene = new Scene(battleScene);
            primaryStage.setScene(scene);
            primaryStage.show();
            controller = loader.getController();
            controller.setMainApp(this);
           /* int alert = controller.showAlert();
            while (alert == 0)
            	alert = controller.showAlert();*/
            FileManager.setCSS("styles.css", scene);
            System.out.println(scene.getStylesheets());
            TurnCounter.initialize(encounter);
            MessageBoxGraphics.initialize(controller.getGraphics());
            
            EnemyGraphics.drawEncounter(this.encounter, controller.getGraphics());
            PlayerGraphics.drawPlayerCharacters(controller.getGraphics());
            MessageBoxGraphics.playerCommandMenu.graphics.get(0).setOnMouseClicked(e -> {
            	controller.hitEnemy();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //A getter function to get the current encounter.
    public Encounter getEncounter() {return encounter;}
    
    
    
    /**
     * Gets the primary Stage of the application
     *
     * <p>
     *     Returns the current primaryStage of this Application to update and show.
     * </p>
     *
     * @return the primary Stage of this Application.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}

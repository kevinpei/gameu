package code.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import code.battle_screen.BattleScreen;
import code.file_management.FileManager;
import code.game_mechanics.Library;
import code.game_mechanics.characters.PlayerCharacter;
import code.game_mechanics.party.PlayerParty;
import code.xml.XMLReader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * The main class.
 */
public class Main extends Application{
	
	private Stage primaryStage = new Stage();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startBattle();
	}
	
	public static void main(String[] args) {
		Library.initialize();
		for (int key : Library.abilities.keySet()) {
			System.out.println(Library.abilities.get(key).name);
		}
		for (int key : Library.statusEffects.keySet()) {
			System.out.println(Library.statusEffects.get(key).name);
		}
		initializeParty();
		launch(args);
	}
	
	public void startBattle() {
		BattleScreen battle = new BattleScreen(1);
		battle.start(primaryStage);
	}
	
	public static void initializeParty() {
		PlayerCharacter[] partyMembers = new PlayerCharacter[4];
		Document players = XMLReader.createDocument("player_characters");
		for (int i = 0; i < 4; i++) {
			Element playerClass = (Element)XMLReader.getElementByID(players, "character", i);
			partyMembers[i] = (PlayerCharacter) XMLReader.initCharacter(playerClass);
		}
		PlayerParty.initialize(partyMembers);
		
	}

}

package code.file_management;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import code.game_mechanics.characters.Enemy;
import code.game_mechanics.characters.GameCharacter;
import code.game_mechanics.characters.PlayerCharacter;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class FileManager {

	/*
	 * A function to get the path to the specified folder within the dat folder which holds
	 * all game data such as art assets and music.
	 */
	public static String getResource(String folder) {
		try {
			File parent = new File(FileManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String path = parent.getParent();
			path += (File.separator + "dat" + File.separator + folder + File.separator);
			return path;
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	/*
	 * Returns an Image object of the given icon.
	 */
	public static Image getIcon(String name) {
		File iconFile = new File(FileManager.getResource("icon") + name);
		return new Image(iconFile.toURI().toString());
	}
	
	/*
	 * Returns an Image object given a game character. It gets the appropriate file depending
	 * on the type of character.
	 */
	public static Image getImage(GameCharacter character) {
    	File imgFile = null;
    	if (character instanceof Enemy) {
    		imgFile = new File(FileManager.getResource("img") + "enemy\\" + character.getIMG());
    	} else if (character instanceof PlayerCharacter) {
    		imgFile = new File(FileManager.getResource("img") + "player\\" + character.getIMG());
    	}
    	return new Image(imgFile.toURI().toString());
    }
	
	/*
	 * Returns an Image object of the given background.
	 */
	public static Image getBackground(String bg) {
		File bgFile = new File(FileManager.getResource("bg") + bg);
    	return new Image(bgFile.toURI().toString());
	}
	
	/*
	 * Returns a file object of the given bgm.
	 */
	public static File getMusic(String bgm) {
		return new File(FileManager.getResource("bgm") + bgm);
	}
	
	/*
	 * Sets the given scene's css to the given css.
	 */
	public static void setCSS(String css, Scene scene) {
		try {
			scene.getStylesheets().add(new File(FileManager.getResource("css") + css).toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			System.out.println("failed :(");
		}
	}
}

package code.music_player;

import java.io.File;

import code.file_management.FileManager;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

	private static MediaPlayer player;
	
	public static void playBGM(String bgm) {
    	File music = new File(FileManager.getResource("bgm") + bgm);
    	if (music != null) {
    		player = new MediaPlayer(new Media(music.toURI().toString()));
    	    player.play();
    	    player.setOnEndOfMedia(new Runnable() {
    	        public void run() {
    	          player.seek(Duration.ZERO);
    	        }
    	    });
    	    player.play();
    	}
    }
	
	public static void stopBGM() {
		try {
			player.stop();	
		} catch (NullPointerException e) {
			return;
		}
	}
	
}

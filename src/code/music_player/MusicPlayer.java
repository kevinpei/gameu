package code.music_player;

import java.io.File;

import code.file_management.FileManager;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

	private static MediaPlayer player;
	
	/*
	 * A function to play and loop a chosen bgm track.
	 */
	public static void playBGM(String bgm) {
    	File music = FileManager.getMusic(bgm);
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
	
	/*
	 * A function to stop whichever bgm is currently playing.
	 */
	public static void stopBGM() {
		try {
			player.stop();	
		} catch (NullPointerException e) {
			return;
		}
	}
	
}

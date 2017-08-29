package code.graphics;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Animations {

	static Timeline timeline = new Timeline();
	
	/*
	 * A function that makes the given target object flash the given number of times.
	 * It alternates between showing and hiding the object.
	 */
	public static void flashObject(Node target, int times) {
		for (int i = 0; i < times * 2; i++) {
    		if (i%2 == 0) {
    			timeline.getKeyFrames().addAll(new KeyFrame(new Duration(i * 100), 
    					new KeyValue(target.visibleProperty(), false)));
    		} else {
    			timeline.getKeyFrames().addAll(new KeyFrame(new Duration(i * 100), 
    					new KeyValue(target.visibleProperty(), true)));
    		}
    	}
	}
	
	/*
	 * A function to hide the given object after a certain amount of time has passed.
	 * The time is in milliseconds.
	 */
	public static void hideObject(Node target, int duration) {
    	timeline.getKeyFrames().addAll(new KeyFrame(new Duration(duration), 
    			new KeyValue(target.visibleProperty(), false)));
	}
	
	/*
	 * A function to play all the animations saved to the timeline.
	 */
	public static void playAnimations() {
		timeline.playFromStart();
	}
	/*
	 * A function to return a Text object that shows how much damage an attack did.
	 */
	public static Text dealDamage(int amount) {
		Text damage = new Text();
		if (amount >= 0) {
			damage.setText(-1 * amount + "");
		} else {
			damage.setText("+" + -1 * amount);
		}
    	damage.setId("damage");
    	damage.setScaleX(3);
    	damage.setScaleY(3);
    	return damage;
	}
	
	public static void displayDamage(int amount) {
		
	}
}

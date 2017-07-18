package code.mouse_engine;

import java.util.function.Consumer;

import code.game_mechanics.characters.Enemy;
import code.graphics.MessageBox;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseEngine {

	/*
	 * A function to set an action when the mouse enters an element and exits it.
	 * The enterAction is run when the mouse enters the element and the extAction
	 * is run when the mouse exits the element. Both are function pointers.
	 */
	public static void setOnMouseOver(Node element, Object target, Consumer<Object> enterAction, Consumer<Object> exitAction) {
		element.setOnMouseMoved(evt -> {
			if (element.getLayoutBounds().contains(evt.getX(), evt.getY()))
				enterAction.accept(target);
		});
		element.setOnMouseExited(evt -> {
			exitAction.accept(target);
		});
	}
	
}
